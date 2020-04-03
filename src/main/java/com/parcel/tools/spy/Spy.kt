package com.parcel.tools.spy

import java.io.File
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Paths


object Spy {

    private val folder = "spy"
    private val fileName = "locations.txt"
    private val locations = ArrayList<String>()
    private var currentLocation = ""

    private val users = ArrayList<User>()
    private var spyName = ""

    private var spyIsNotSecret = false

    fun startGame()
    {
        updateLocations()

        //делаем локацию
        val locationIndex :Int = (Math.random()* (locations.size - 1)).toInt()
        currentLocation = locations[locationIndex]

        //выбираем шпиона
        val spyIndex = (Math.random()* (users.size - 1)).toInt()
        users[spyIndex].spy = true
    }

    fun getUserInformation(userName: String): UserInformation
    {
        users.forEach {
            if(it.name == userName)
            {
                return UserInformation(it, currentLocation, users.size, spyIsNotSecret)
            }
        }
        return UserInformation("User name not correct", spyIsNotSecret)
    }

    fun addUser(name: String)
    {
        if(name.length>2) {
            users.forEach {
                if (it.name == name) {
                    println("User exist")
                    return
                }
            }
            users.add(User(name))
        }
        println("To short user name.")
    }
    fun stopGame()
    {
        users.clear()
        spyIsNotSecret = false
    }

    fun getSpy(): String
    {
        spyIsNotSecret = true
        return spyName
    }




    private fun updateLocations() : Boolean
    {
        try {
            val file = File(folder + fileName)
            //проверяем, что если файл не существует то создаем его
            if (file.exists())
            {
                locations.clear()
                val lines =file.readLines()
                lines.forEach { if(it.length>2) {
                    locations.add(it)} }
                return true
            }
            else
            {
                println("File not found")
                create()
                return false
            }
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            return false
        }
    }

    private fun create() : Boolean
    {
        try {
            val file = File(folder + fileName)
            val path = Paths.get(folder)
            //проверяем наличие каталога, если катлона нет, создаем его
            if (!Files.exists(path)) {
                Files.createDirectories(path)
            }

            //проверяем, что если файл не существует то создаем его
            if (file.exists())
                file.delete()
            file.createNewFile()

            file.writeText("колайдер\nпятерочка\nяблочково\n")
            return true
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            return false
        }
    }

}