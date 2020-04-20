package com.parcel.tools.spy

import java.io.File
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.random.Random

class SpySessionException(message: String):Exception(message)

class SpySession(val sessionId: Long, val sessionPas: Long) {

    private val logger = org.apache.log4j.Logger.getLogger(SpySession::class.java!!)
    private val spyEvents = ArrayList<SpyEvent>()

    private val folder = "spy/"
    private val fileName = "locations.txt"

    val locations = ArrayList<String>()

    private var currentLocation = ""

    private val users = ArrayList<User>()
    private var spyName = ""

    var mainUserName = ""
    private set

    var spyIsNotSecret = false
        private set

    var started = false
        private set

    var startTime = 0L
    private set

    init {
        startTime = System.currentTimeMillis()
        updateLocations()
    }


    fun startGame()
    {



        if (!started) {
            started = true
            spyIsNotSecret = false
            logger.info("startGame()...")
            updateLocations()

            //делаем локацию
            val locationIndex: Int = GlobalRandomiser.getRundom(locations.size)
            currentLocation = locations[locationIndex]
            logger.info("Location: $currentLocation")

            //выбираем шпиона
            val spyIndex = GlobalRandomiser.getRundom(users.size)
            users[spyIndex].spy = true
            spyName = users[spyIndex].name
            logger.info("Spy is: $spyName")
            logger.info("...Game started")

            startGameEvent()
        }
    }

    fun getUserInformation(userName: String): UserInformation
    {
        logger.info("getUserInformation($userName)")
        users.forEach {
            if(it.name == userName)
            {
                return UserInformation(it, currentLocation, users.size, spyIsNotSecret,getAllUsers())
            }
        }
        return UserInformation("User name not correct", spyIsNotSecret)
    }

    fun addUser(name: String): Boolean
    {
        logger.info("addUser($name)...")
        val userExist = isUserExist(name)
        if(started && !userExist)
        {
            logger.warn("Game  started.")
            throw SpySessionException("The game is already running.")
        }
        else if(started && userExist)
        {
            logger.warn("User $name already exists. Access is allowed.")
            addUserEvent(getAllUsers())
            return true
        }
        else if(name.length<1) {
            logger.warn("To short user name.")
            throw SpySessionException("To short user name.")
        }
        else if(!started && userExist)
        {
            logger.warn("A user with the same name already exists.")
            throw SpySessionException("A user with the same name already exists.")
        }
        else
        {
            if(users.size == 0)
            {
                mainUserName = name
            }
            users.add(User(name))
            logger.info("...addUser()")
            addUserEvent(getAllUsers())
            return true
        }
    }
    fun stopGame()
    {
        logger.info("stopGame()")
        users.clear()
        spyIsNotSecret = false
        started = false
        stopGameEvent(spyName)
    }

    fun getSpy(userName: String): String
    {
        logger.info("getSpy($userName):$spyName")
        spyIsNotSecret = true
        spyIsNotSecret
        spyIsNotSecretEvent(spyName)
        return spyName

    }

    fun getAllUsers():String
    {
        var userList =""
        users.forEach {
            userList = userList+ "    " +it.name + "\n"
        }
        return userList
    }

    private fun isEvrybodyWantSeaSpy(): Boolean
    {
        users.forEach {
            if(!it.wantSeeSpy)
                return false
        }
        return true
    }

    private fun isUserExist(name: String): Boolean
    {
        users.forEach {
            if(it.name == name)
                return true
        }
        return false
    }

    private fun getUser(name: String): User
    {
        users.forEach {
            if(it.name == name)
                return it
        }
        throw SpySessionManagerException("Can`t finde user: $name")
    }


    /********EVENTS***********/
    fun subscribeSpyEvents(spyEvent: SpyEvent)
    {
        spyEvents.add(spyEvent)
    }

    fun deSubscribeSpyEvents(spyEvent: SpyEvent)
    {
        spyEvents.remove(spyEvent)
    }

    private fun addUserEvent(userList: String)
    {
        spyEvents.forEach {
            it.addUserEvent(userList)
        }
    }

    private fun startGameEvent()
    {
        spyEvents.forEach { it.startGameEvent() }
    }

    private fun stopGameEvent(spyName: String)
    {
        spyEvents.forEach { it.stopGameEvent(spyName) }
    }

    private fun spyIsNotSecretEvent(spyName: String)
    {
        spyEvents.forEach { it.spyIsNotSecretEvent(spyName) }
    }


    /*******SETTINGS*********/
    @Synchronized
    fun addLocation(location: String): Boolean
    {
        if(!locations.contains(location))
        {
            locations.add(location)
            create(locations)
            return true
        }
        else
            return false
    }


    @Synchronized
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

    private fun create(locations: ArrayList<String> = arrayListOf("пятерочка", "колайдер", "яблочково")) : Boolean
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
            var text = ""
            locations.forEach { text = text + it + "\n" }
            file.writeText(text)
            return true
        }
        catch (e: Exception)
        {
            e.printStackTrace()
            return false
        }
    }

}