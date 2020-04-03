package com.parcel.tools.spy

import java.io.File
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Paths


object Spy {

    private val logger = org.apache.log4j.Logger.getLogger(Spy::class.java!!)

    private val folder = "spy/"
    private val fileName = "locations.txt"

    private val locations = ArrayList<String>()
    private var currentLocation = ""

    private val users = ArrayList<User>()
    private var spyName = ""

    var spyIsNotSecret = false

    private var started = false

    fun startGame()
    {

        if (!started) {
            started = true
            logger.info("startGame()...")
            updateLocations()

            //делаем локацию
            val locationIndex: Int = (Math.random() * (locations.size - 1)).toInt()
            currentLocation = locations[locationIndex]
            logger.info("Location: $currentLocation")

            //выбираем шпиона
            val spyIndex = (Math.random() * (users.size - 1)).toInt()
            users[spyIndex].spy = true
            spyName = users[spyIndex].name
            logger.info("Spy is: $spyName")
            logger.info("...Game started")
        }
    }

    fun getUserInformation(userName: String): UserInformation
    {
        logger.info("getUserInformation($userName)")
        users.forEach {
            if(it.name == userName)
            {
                return UserInformation(it, currentLocation, users.size, spyIsNotSecret)
            }
        }
        return UserInformation("User name not correct", spyIsNotSecret)
    }

    fun addUser(name: String): Boolean
    {
        logger.info("addUser($name)...")
        if(name.length>2) {
            users.forEach {
                if (it.name == name) {
                    logger.warn("User exist")
                    return false
                }
            }
            users.add(User(name))
            logger.info("...addUser()")
            return true
        }
        logger.warn("To short user name.")
        return false
    }
    fun stopGame()
    {
        logger.info("stopGame()")
        users.clear()
        spyIsNotSecret = false
        started = false
    }

    fun getSpy(): String
    {
        logger.info("getSpy():$spyName")
        spyIsNotSecret = true
        return spyName
    }




    private fun updateLocations() : Boolean
    {
        logger.info("updateLocations()")
        try {
            val file = File(folder + fileName)
            //проверяем, что если файл не существует то создаем его
            if (file.exists())
            {
                locations.clear()
                val lines =file.readText().split("\n")
                lines.forEach {
                    if(it.length>2) {
                    locations.add(it)}
                }
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