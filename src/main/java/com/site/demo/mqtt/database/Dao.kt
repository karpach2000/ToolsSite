package com.site.demo.mqtt.database


import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement


object Db
{
     lateinit var conn :Connection
}

abstract class Dao {
    private var url = "jdbc:sqlite:mqtt.db"

    protected lateinit var resSet: ResultSet
    protected lateinit var statmt: Statement
    fun connect(): Connection {
        Db.conn = DriverManager.getConnection(url)
        statmt = Db.conn.createStatement()
        return Db.conn
    }

}


abstract class UsersDao : Dao() {
    abstract fun save(users: User)
    abstract fun getAll() : ArrayList<User>
    abstract fun get(login: String) : User
    abstract fun get(id: Int) : User
}
abstract class BoardsDao  : Dao() {
    abstract fun save(boards: Board)
    abstract fun getAll(boardType : Int) : ArrayList<Board>
    abstract fun getAll(user : User) : ArrayList<Board>
    abstract fun get(id: Int) : Board
    abstract fun  getBoard(mqttTopic : String) : Board
    abstract fun setOutPins(mqttTopic:  String, pinVals: String)
    abstract fun setInPins(mqttTopic:  String, pinVals: String)
}
abstract  class  DigitalInPinsDao  : Dao() {
    abstract fun save(digitalInPins: DigitalInPin)
    abstract fun getAll() : ArrayList<DigitalInPin>
    abstract fun getAll(board: Board) : ArrayList<DigitalInPin>
    abstract fun updatePin(digitalInPins: DigitalInPin)
    abstract fun getPin(board: Board, pinNumber: Int) : DigitalInPin?
}
abstract  class   DigitalOutPinsDao  : Dao() {
    abstract fun save(digitalOutPins: DigitalOutPin)
    abstract fun getAll() : ArrayList<DigitalOutPin>
    abstract fun getAll(board: Board) : ArrayList<DigitalOutPin>
    abstract fun updatePin(digitalOutPins: DigitalOutPin)
    abstract fun getPin(board: Board, pinNumber: Int) : DigitalOutPin?
}