package com.parcel.tools.mqtt.database.entities

class User
{
    var id :Int = 0
    var login = ""
    var pas = ""
    var deleted : Short = 0
}
class Board
{
    var id :Int = 0
    var devId = ""
    var user = User()
    var boardRsName = "i"
    var boardType: Short = 0
    var description = ""
    var deleted : Short = 0
}
class DigitalInPin
{
    var id :Int = 0
    var board = Board()
    var pinNumber: Short =0
    var value: Short = 0
    var description  = ""
    var deleted : Short = 0
}
class  DigitalOutPin
{
    var id :Int = 0
    var board = Board()
    var pinNumber: Short =0
    var value: Short = 0
    var description  = ""
    var deleted : Short = 0
}