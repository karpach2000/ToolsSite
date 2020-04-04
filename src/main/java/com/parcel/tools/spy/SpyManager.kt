package com.parcel.tools.spy

class SpyManagerException(message: String): Exception(message)

object SpyManager {

    private val gameLifeTime = 60L*60L*1000L*2

    private val logger = org.apache.log4j.Logger.getLogger(SpyManager::class.java!!)

    private val spySessions = ArrayList<SpySession>()

    init {
        Thread(Runnable {
            destructorAction()
        }).start()
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

    fun getSpy(sessionId: Long, sessionPas: Long, userName: String): String
    {
        logger.info("getSpy($sessionId, $sessionPas)")
        return getSession(sessionId, sessionPas).getSpy(userName)
    }

    private fun destructorAction()
    {
        while (true)
        {
            removeOldGames()
            Thread.sleep(gameLifeTime)
        }
    }

    private fun removeOldGames()
    {
        logger.warn("Removing old games.")
        val current = System.currentTimeMillis()
        spySessions.forEach {
            if(current -it.startTime> this.gameLifeTime) {
                logger.warn("Removing game: ${it.sessionId}")
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
                    throw SpyManagerException("Id or password not correct!")

        }
        throw SpyManagerException("Id not correct!")
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