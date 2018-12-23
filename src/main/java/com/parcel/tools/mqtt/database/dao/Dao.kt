package com.parcel.tools.mqtt.database.dao


import com.parcel.tools.mqtt.database.entities.Board
import com.parcel.tools.mqtt.database.entities.DigitalInPin
import com.parcel.tools.mqtt.database.entities.DigitalOutPin
import com.parcel.tools.mqtt.database.entities.User
import java.sql.Connection


object Db
{
     lateinit var conn :Connection
}

abstract class Dao {

}


abstract class UsersDao : Dao() {
    abstract fun save(user: User)
    abstract fun getAll() : List<User>
    abstract fun get(login: String) : User
    abstract fun get(id: Int) : User
}
abstract class BoardsDao  : Dao() {
    abstract fun save(board: Board)
    abstract fun getAll(boardType : Int) : List<Board>
    abstract fun getAll(user : User) : List<Board>
    abstract fun get(id: Int) : Board
    abstract fun  getBoard(mqttTopic : String) : Board
    abstract fun setOutPins(mqttTopic:  String, pinVals: String)
    abstract fun setInPins(mqttTopic:  String, pinVals: String)
}
abstract  class  DigitalInPinsDao  : Dao() {
    abstract fun save(digitalInPin: DigitalInPin)
    abstract fun getAll() : List<DigitalInPin>
    abstract fun getAll(board: Board) : List<DigitalInPin>
    abstract fun updatePin(digitalInPin: DigitalInPin)
    abstract fun getPin(board: Board, pinNumber: Int) : DigitalInPin?
}
abstract  class   DigitalOutPinsDao  : Dao() {
    abstract fun save(digitalOutPins: DigitalOutPin)
    abstract fun getAll() : List<DigitalOutPin>
    abstract fun getAll(board: Board) : List<DigitalOutPin>
    abstract fun updatePin(digitalOutPin: DigitalOutPin)
    abstract fun getPin(board: Board, pinNumber: Int) : DigitalOutPin?
}