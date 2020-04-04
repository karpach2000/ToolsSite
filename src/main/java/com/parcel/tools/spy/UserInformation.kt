package com.parcel.tools.spy

import com.google.gson.GsonBuilder

class UserInformation(var spyIsNotSecret: Boolean) {

    constructor(user: User, location:String, usersCount: Int, spyIsNotSecret: Boolean, allUsers: String)
            :this(spyIsNotSecret)
    {
        name=user.name
        spy = user.spy
        this.location = location
        this.usersCount = usersCount
        this.allUsers = allUsers


    }
    constructor(error:String, spyIsNotSecret: Boolean)
            :this(spyIsNotSecret)
    {
        this.error = error
    }
    var allUsers = ""
    var usersCount = 0;
    var name = ""
    var spy = false
    var error = ""
    var location = ""
    get() {
        if(!spy)
            return field
        else
            return "ШПИОН(SPY)"
    }

    override fun toString() =
          "Name: $name\n"+
           "location: $location\n"+
           "Users in game: $usersCount\n"+
            "Users:\n$allUsers"



    fun toJson() :String
    {
        var builder =  GsonBuilder()
        var gson = builder.create()
        return gson.toJson(this)
    }
}