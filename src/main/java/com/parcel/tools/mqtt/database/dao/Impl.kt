package com.parcel.tools.mqtt.database.dao

import com.parcel.tools.mqtt.database.entities.Board
import com.parcel.tools.mqtt.database.entities.DigitalInPin
import com.parcel.tools.mqtt.database.entities.DigitalOutPin
import com.parcel.tools.mqtt.database.entities.User
import org.springframework.stereotype.Component
import java.sql.SQLException
//http://code-flow-hjbello.blogspot.com/2017/07/using-sqlite-with-jdbc.html



@Component
class UsersDaoImpl : UsersDao()
{

    init {
        connect()
    }

    override fun save(users: User) {
        val sql = "INSERT INTO User (login, pas, deleted) " +
                  "values (?,?,?)"

        try {
            this.connect().use { conn ->
                conn.prepareStatement(sql).use { pstmt ->

                    pstmt.setString(1, users.login)
                    pstmt.setString(2, users.pas)
                    pstmt.setShort(3, users.deleted)
                    pstmt.executeUpdate()
                    System.out.printf("Activity recorded")
                }
            }
        } catch (e: SQLException) {
            println(e.message)
        }
    }

    override  fun getAll() : ArrayList<User>
    {
        resSet = statmt.executeQuery("SELECT * FROM Users;")
        val users = ArrayList<User>()
        while (resSet.next()) {
            val user = User()
            user.id = resSet.getInt("id")
            user.login = resSet.getString("login")
            user.pas = resSet.getString("pas")
            user.deleted = resSet.getShort("deleted")
            users.add(user)
        }
        return users
    }
    override  fun get(login: String) : User
    {

        resSet = statmt.executeQuery("SELECT * FROM Users WHERE login=\"$login\";")
        val users = ArrayList<User>()
        while (resSet.next()) {
            val user = User()
            user.id = resSet.getInt("id")
            user.login = resSet.getString("login")
            user.pas = resSet.getString("pas")
            user.deleted = resSet.getShort("deleted")
            users.add(user)
        }
        return users.get(0)
    }
    override  fun get(id: Int) : User
    {

        resSet = statmt.executeQuery("SELECT * FROM Users WHERE Users.id=$id;")
        val users = ArrayList<User>()
        while (resSet.next()) {
            val user = User()
            user.id = resSet.getInt("id")
            user.login = resSet.getString("login")
            user.pas = resSet.getString("pas")
            user.deleted = resSet.getShort("deleted")
            users.add(user)
        }
        return users.get(0)
    }
}

@Component
class BoardsDaoImpl : BoardsDao()
{
    init {
        connect()
    }
    override fun save(boards: Board) {
        val sql = "INSERT INTO User (devId, userLogin, boardRsName, boardType, description, deleted) " +
                  "values (?,?,?,?,?,?);"
        try {
            this.connect().use { conn ->
                conn.prepareStatement(sql).use { pstmt ->

                    pstmt.setString(1, boards.devId)
                    pstmt.setString(2, boards.user.login)
                    pstmt.setString(3, boards.boardRsName)
                    pstmt.setShort(4, boards.boardType)
                    pstmt.setString(5, boards.description)
                    pstmt.setShort(6, boards.deleted)
                    pstmt.executeUpdate()
                    System.out.printf("Activity recorded")
                }
            }
        } catch (e: SQLException) {
            println(e.message)
        }

    }

    override  fun getAll(boardType : Int) : ArrayList<Board>
    {
        val udi = UsersDaoImpl()
        val boards = ArrayList<Board>()
        resSet = statmt.executeQuery("SELECT * FROM Boards  WHERE boardType = $boardType;")
        while (resSet.next()) {
            val board = Board()
            board.id = resSet.getInt("id")
            board.devId = resSet.getString("devId")
            board.user = udi.get(resSet.getInt("userId"))
            board.boardRsName= resSet.getString("boardRsName")
            board.boardType= resSet.getShort("boardType")
            board.description= resSet.getString("description")
            board.deleted = resSet.getShort("deleted")
            boards.add(board)
        }
        return boards
    }
    override  fun getBoard(mqttTopic : String) : Board
    {
        val udi = UsersDaoImpl()
        val boards = ArrayList<Board>()

        val login = getLoginFromMqttTopic(mqttTopic)
        val user = udi.get(login)
        val userId = user.id
        val boardRsName = getBoardRsNameFromMqttTopic(mqttTopic)
        resSet = statmt.executeQuery("SELECT * FROM Boards  WHERE boardRsName = \"$boardRsName\" AND userId = \"$userId\";")
        while (resSet.next()) {
            val board = Board()
            board.id = resSet.getInt("id")
            board.devId = resSet.getString("devId")
            board.user = user
            board.boardRsName= resSet.getString("boardRsName")
            board.boardType= resSet.getShort("boardType")
            board.description= resSet.getString("description")
            board.deleted = resSet.getShort("deleted")
            boards.add(board)
        }
        return boards.get(0)
    }

    override fun getAll(user : User) : ArrayList<Board>
    {
        val boards = ArrayList<Board>()
        val userId = user.id
        resSet = statmt.executeQuery("SELECT * FROM Boards  WHERE userId = $userId;")
        while (resSet.next()) {
            val board = Board()
            board.id = resSet.getInt("id")
            board.devId = resSet.getString("devId")
            board.user = user
            board.boardRsName= resSet.getString("boardRsName")
            board.boardType= resSet.getShort("boardType")
            board.description= resSet.getString("description")
            board.deleted = resSet.getShort("deleted")
            boards.add(board)
        }
        return boards
    }

    override  fun get(id: Int) : Board
    {
        val udi = UsersDaoImpl()
        resSet = statmt.executeQuery("SELECT * FROM Boards WHERE Boards.id=$id;")
        val boards = ArrayList<Board>()
        while (resSet.next()) {
            val board = Board()
            board.id = resSet.getInt("id")
            board.devId = resSet.getString("devId")
            board.user = udi.get(resSet.getInt("userId"))
            board.boardRsName= resSet.getString("boardRsName")
            board.boardType= resSet.getShort("boardType")
            board.description= resSet.getString("description")
            board.deleted = resSet.getShort("deleted")
            boards.add(board)
        }
        return boards.get(0)
    }

    override fun setInPins(mqttTopic:  String, pinVals: String)
    {
        var dipdi = DigitalInPinsDaoImpl()
        val board = getBoard(mqttTopic)
        var j=0
        for(i in pinVals)
        {
            val pin = dipdi.getPin(board, j)
            if(pin!=null) {
                pin.value ="$i".toShort()
                dipdi.updatePin(pin)
            }
            j++
        }
    }

    override fun setOutPins(mqttTopic:  String, pinVals: String)
    {
        var dopdi = DigitalOutPinsDaoImpl()
        val board = getBoard(mqttTopic)
        for(i in 0..pinVals.length)
        {
            val pin = dopdi.getPin(board, "$i".toInt())
            if(pin!=null) {
                println("Updating pin: $pin")
                dopdi.updatePin(pin)
            }
        }
    }
}

@Component
class DigitalInPinsDaoImpl : DigitalInPinsDao()
{

    init {
        connect()
    }
    override fun save(digitalInPins: DigitalInPin) {
        val sql = "INSERT INTO DigitalInPin (boardDevId, pinNumber, value, description,  deleted) " +
                "values (?,?,?,?,?)"
        try {
            this.connect().use { conn ->
                conn.prepareStatement(sql).use { pstmt ->
                    pstmt.setString(1, digitalInPins.board.devId)
                    pstmt.setShort(2, digitalInPins.pinNumber)
                    pstmt.setShort(3, digitalInPins.value)
                    pstmt.setString(4, digitalInPins.description)
                    pstmt.setShort(5, digitalInPins.deleted)
                    pstmt.executeUpdate()
                    System.out.printf("Activity recorded")
                }
            }
        } catch (e: SQLException) {
            println(e.message)
        }

    }

    override  fun getPin(board: Board, pinNumber: Int) : DigitalInPin?
    {
        try {

            val pins = ArrayList<DigitalInPin>()
            val boardId = board.id
            val sql = "SELECT * FROM DigitalInPins  WHERE boardId = \"$boardId\" AND pinNumber = \"$pinNumber\";"
            resSet = statmt.executeQuery(sql)
            while (resSet.next()) {
                val pin = DigitalInPin()
                pin.id = resSet.getInt("id")
                pin.board = board
                pin.pinNumber = resSet.getShort("pinNumber")
                pin.value = resSet.getShort("value")
                pin.description = resSet.getString("description")
                pin.deleted = resSet.getShort("deleted")
                pins.add(pin)
            }

            if(pins.size!=0)
                return pins.get(0)
            else return null
        }
        catch (e: Exception)
        {return null}
    }
    override  fun getAll() : ArrayList<DigitalInPin>
    {

        val bdi = BoardsDaoImpl()
        val pins = ArrayList<DigitalInPin>()
        resSet = statmt.executeQuery("SELECT * FROM DigitalInPins;")
        while (resSet.next()) {
            val pin = DigitalInPin()
            pin.id = resSet.getInt("id")
            pin.board = bdi.get(resSet.getInt("boardId"))
            pin.pinNumber= resSet.getShort("pinNumber")
            pin.value= resSet.getShort("value")
            pin.description= resSet.getString("description")
            pin.deleted = resSet.getShort("deleted")
            pins.add(pin)
        }
        return pins
    }
    override  fun getAll(board: Board) : ArrayList<DigitalInPin>
    {
        val bdi = BoardsDaoImpl()
        val pins = ArrayList<DigitalInPin>()
        val boardId = board.id
        resSet = statmt.executeQuery("SELECT * FROM DigitalInPins  WHERE userId = \"$boardId\";")
        while (resSet.next()) {
            val pin = DigitalInPin()
            pin.id = resSet.getInt("id")
            pin.board = bdi.get(resSet.getInt("boardId"))
            pin.pinNumber= resSet.getShort("pinNumber")
            pin.value= resSet.getShort("value")
            pin.description= resSet.getString("description")
            pin.deleted = resSet.getShort("deleted")
            pins.add(pin)
        }
        return pins
    }
    override fun updatePin(digitalInPins: DigitalInPin)
    {


        var value = digitalInPins.value
        var description = digitalInPins.description
        var deleted = digitalInPins.deleted
        val sql = "UPDATE DigitalInPins SET value = \"$value\", description = \"$description\" ,  deleted = \"$deleted\" WHERE id = \"${digitalInPins.id}\";"
/*
        try {
            this.connect().use { conn ->
                conn.prepareStatement(sql).use {pstmt ->
                    pstmt.setShort(1, value.toShort())
                    pstmt.setString(2, description)
                    pstmt.setShort(3, deleted)
                    pstmt.setInt(4, digitalInPins.id)
                    pstmt.executeUpdate()
                }
            }
        } catch (e: SQLException) {
            println(e.message)
        }
        */
        try {
            val statement = Db.conn.createStatement()
                val comand = (sql)
                statement.executeUpdate(comand)


        } catch (ex: SQLException) {

        }


    }
}
@Component
class DigitalOutPinsDaoImpl : DigitalOutPinsDao()
{
    init {
        connect()
    }
    override fun save(digitalOutPins: DigitalOutPin) {
        val sql = "INSERT INTO DigitalOutPin (boardDevId, pinNumber, value, description,  deleted) " +
                "values (?,?,?,?,?)"
        try {
            this.connect().use { conn ->
                conn.prepareStatement(sql).use { pstmt ->
                    pstmt.setString(1, digitalOutPins.board.devId)
                    pstmt.setShort(2, digitalOutPins.pinNumber)
                    pstmt.setShort(3, digitalOutPins.value)
                    pstmt.setString(4, digitalOutPins.description)
                    pstmt.setShort(5, digitalOutPins.deleted)
                    pstmt.executeUpdate()
                    System.out.printf("Activity recorded")
                }
            }
        } catch (e: SQLException) {
            println(e.message)
        }

    }
    override  fun getAll() : ArrayList<DigitalOutPin>
    {
        val bdi = BoardsDaoImpl()
        val pins = ArrayList<DigitalOutPin>()
        resSet = statmt.executeQuery("SELECT * FROM DigitalOutPins;")
        while (resSet.next()) {
            val pin = DigitalOutPin()
            pin.id = resSet.getInt("id")
            pin.board = bdi.get(resSet.getInt("boardId"))
            pin.pinNumber= resSet.getShort("pinNumber")
            pin.value= resSet.getShort("value")
            pin.description= resSet.getString("description")
            pin.deleted = resSet.getShort("deleted")
            pins.add(pin)
        }
        return pins
    }
    override  fun getAll(board: Board) : ArrayList<DigitalOutPin>
    {
        val bdi = BoardsDaoImpl()
        val pins = ArrayList<DigitalOutPin>()
        val boardId = board.id
        resSet = statmt.executeQuery("SELECT * FROM DigitalOutPins  WHERE boardId = $boardId;")
        while (resSet.next()) {
            val pin = DigitalOutPin()
            pin.id = resSet.getInt("id")
            pin.board = bdi.get(resSet.getInt("boardId"))
            pin.pinNumber= resSet.getShort("pinNumber")
            pin.value= resSet.getShort("value")
            pin.description= resSet.getString("description")
            pin.deleted = resSet.getShort("deleted")
            pins.add(pin)
        }
        return pins
    }
    override  fun getPin(board: Board, pinNumber: Int) : DigitalOutPin?
    {
        val pins = ArrayList<DigitalOutPin>()
        val boardId = board.id
        resSet = statmt.executeQuery("SELECT * FROM DigitalOutPins  WHERE boardId = \"$boardId\" AND pinNumber=\"$pinNumber\";")
        while (resSet.next()) {
            val pin = DigitalOutPin()
            pin.id = resSet.getInt("id")
            pin.board = board
            pin.pinNumber= resSet.getShort("pinNumber")
            pin.value= resSet.getShort("value")
            pin.description= resSet.getString("description")
            pin.deleted = resSet.getShort("deleted")
            pins.add(pin)
        }
        if(pins.size!=0)
            return pins.get(0)
        else
            return null
    }

    override fun updatePin(digitalOutPins: DigitalOutPin)
    {


        var value = digitalOutPins.value
        var description = digitalOutPins.description
        var deleted = digitalOutPins.deleted
        val sql = "UPDATE DigitalOutPins SET value = $value, description = $description,  deleted = $deleted);"
        try {
            this.connect().use { conn ->
                conn.prepareStatement(sql).use {
                    System.out.printf("Activity recorded")
                }
            }
        } catch (e: SQLException) {
            println(e.message)
        }
    }
}

private fun getLoginFromMqttTopic(mqttTopic: String) : String
{
    var args =mqttTopic.split("/")
    return args[1]

}
private fun getBoardRsNameFromMqttTopic(mqttTopic: String) :String
{
    var args =mqttTopic.split("/")
    return args[args.size-2]
}

