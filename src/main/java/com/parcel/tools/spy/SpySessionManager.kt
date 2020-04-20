package com.parcel.tools.spy

class SpySessionManagerException(message: String): Exception(message)

object SpySessionManager {

    private val gameLifeTime = 60L*60L*1000L*2
    private val destructorPeriod = 60L*1000L

    private val logger = org.apache.log4j.Logger.getLogger(SpySessionManager::class.java!!)

    private val spySessions = ArrayList<SpySession>()

    init {
        Thread(Runnable {
            destructorAction()
        }).start()
    }

    fun getLocationList() :ArrayList<String>
    {
        return SpySession(-1, -1).locations
    }

    @Synchronized
    fun subscribeSpySessionEvent(sessionId: Long, sessionPas: Long, spyEvent: SpyEvent)
    {
        getSession(sessionId, sessionPas).subscribeSpyEvents(spyEvent)
    }

    fun deSubscribeSpySessionEvent(sessionId: Long, sessionPas: Long, spyEvent: SpyEvent)
    {
        if(isSessionExists(sessionId))
            getSession(sessionId, sessionPas).deSubscribeSpyEvents(spyEvent)
    }

    fun addSession(sessionId: Long, sessionPas: Long): Boolean
    {
        logger.info("addSession($sessionId, $sessionPas)")
        if(!isSessionExists(sessionId))
        {
            spySessions.add(SpySession(sessionId,sessionPas))
            return true
        }
        else
        {
            logger.warn("Session $sessionId is already exists.")
            return false
        }
    }

    fun startGame(sessionId: Long, sessionPas: Long): Boolean {
        logger.info("startGame($sessionId, $sessionPas)")
        if(isSessionExists(sessionId)) {
            getSession(sessionId, sessionPas).startGame()
            return true
        }
        else
        {
            logger.warn("Game $sessionId not exists.")
            return false
        }
    }

    fun isSpyUncovered(sessionId: Long, sessionPas: Long) : Boolean
    {
        logger.info("isSpyUncovered($sessionId, $sessionPas)")
        return getSession(sessionId, sessionPas).spyIsNotSecret

    }


    fun getUserInformation(sessionId: Long, sessionPas: Long, userName: String): UserInformation
    {
        logger.info("getUserInformation($sessionId, $sessionPas $userName)")
        return getSession(sessionId, sessionPas).getUserInformation(userName)
    }

    fun addUser(sessionId: Long, sessionPas: Long, userName: String):Boolean
    {
        logger.info("addUser($sessionId, $sessionPas $userName)")
        return getSession(sessionId, sessionPas).addUser(userName)
    }

    fun stopGame(sessionId: Long, sessionPas: Long):Boolean
    {
        logger.info("stopGame($sessionId, $sessionPas)")
        if (isSessionExists(sessionId)) {
            val session = getSession(sessionId, sessionPas)
            session.stopGame()
            spySessions.remove(session)
            return true
        }
        return false
    }

    fun getUsers(sessionId: Long, sessionPas: Long):String {
        logger.info("getUsers($sessionId, $sessionPas)")
        return getSession(sessionId, sessionPas).getAllUsers()
    }

    fun getSpy(sessionId: Long, sessionPas: Long, userName: String): String
    {
        logger.info("getSpy($sessionId, $sessionPas)")
        if(isSessionExists(sessionId))
            return getSession(sessionId, sessionPas).getSpy(userName)
        else
            throw SpySessionException("Session $sessionId does not exist. Maybe someone finished the game.")
    }

    private fun destructorAction()
    {
        while (true)
        {
            removeOldGames()
            Thread.sleep(destructorPeriod)
        }
    }

    private fun removeOldGames()
    {
        logger.debug("Removing old games.")
        val current = System.currentTimeMillis()
        spySessions.forEach {
            if(current- it.startTime> this.gameLifeTime) {
                logger.debug("Removing game: ${it.sessionId}")
                spySessions.remove(it)
            }
        }
    }


    private fun getSession(sessionId: Long, sessionPas: Long):SpySession
    {
        spySessions.forEach {
            if(it.sessionId == sessionId)
                if(it.sessionPas == sessionPas)
                {
                    return it
                }
                else
                    throw SpySessionManagerException("Id or password not correct!")

        }
        throw SpySessionManagerException("Id not correct!")
    }

    private fun isSessionExists(sessionId: Long): Boolean
    {
        spySessions.forEach {
            if(it.sessionId == sessionId)
                return true
        }
        return false
    }

}