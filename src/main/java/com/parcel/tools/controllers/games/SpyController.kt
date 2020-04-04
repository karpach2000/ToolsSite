package com.parcel.tools.controllers.games

import com.parcel.tools.constructor.Page
import com.parcel.tools.constructor.games.CounterGames
import com.parcel.tools.spy.SpyManager
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.io.IOException

@Controller
class SpyController {

    private val logger = org.apache.log4j.Logger.getLogger(SpyController::class.java!!)

    @RequestMapping("/games")
    @Throws(IOException::class)
    internal fun spy(model: Model): String {
        val counter = CounterGames()
        val page = Page(counter)
        model.addAttribute("page", page)
        return "web/html/games"
    }

    @RequestMapping("/games/spy_add_session")
    @ResponseBody
    @Throws(IOException::class)
    internal fun addSession(model: Model,
                         @RequestParam("userName") userName: String = "",
                         @RequestParam("sessionId") sessionId: String = "",
                         @RequestParam("sessionPas") sessionPas: String = ""): String {
        logger.info("addSession($userName, $sessionId, $sessionPas)")
        return SpyManager.addSession(sessionId.toLong(), sessionPas.toLong()).toString()
    }

    @RequestMapping("/games/spy_addUser")
    @ResponseBody
    @Throws(IOException::class)
    internal fun addUser(model: Model,
                         @RequestParam("userName") userName: String = "",
                         @RequestParam("sessionId") sessionId: String = "",
                         @RequestParam("sessionPas") sessionPas: String = ""): String {
        logger.info("addUser($userName, $sessionId, $sessionPas)")
        return SpyManager.addUser(sessionId.toLong(), sessionPas.toLong(), userName).toString()
    }

    @RequestMapping("/games/spy_start_game")
    @ResponseBody
    @Throws(IOException::class)
    internal fun startGame(model: Model,
                           @RequestParam("userName") userName: String = "",
                           @RequestParam("sessionId") sessionId: String = "",
                           @RequestParam("sessionPas") sessionPas: String = ""): String {
        logger.info("startGame($userName, $sessionId, $sessionPas)")
        SpyManager.startGame(sessionId.toLong(), sessionPas.toLong())
        val userInformation =
                SpyManager.getUserInformation(sessionId.toLong(), sessionPas.toLong(),userName)
        return userInformation.toString()
    }

    @RequestMapping("/games/spy_stop_game")
    @ResponseBody
    @Throws(IOException::class)
    internal fun stopGame(model: Model,
                          @RequestParam("userName") userName: String = "",
                          @RequestParam("sessionId") sessionId: String = "",
                          @RequestParam("sessionPas") sessionPas: String = ""): String {
        logger.info("stopGame($userName, $sessionId, $sessionPas)")
        SpyManager.stopGame(sessionId.toLong(), sessionPas.toLong())
        return "true"
    }

    @RequestMapping("/games/spy_get_spy")
    @ResponseBody
    @Throws(IOException::class)
    internal fun getSpy(model: Model,
                        @RequestParam("userName") userName: String = "",
                        @RequestParam("sessionId") sessionId: String = "",
                        @RequestParam("sessionPas") sessionPas: String = ""): String {
        logger.info("getSpy($userName, $sessionId, $sessionPas)")
        return SpyManager.getSpy(sessionId.toLong(), sessionPas.toLong(), userName)
    }

    @RequestMapping("/games/spy_is_spy_showen")
    @ResponseBody
    @Throws(IOException::class)
    internal fun isSpyShowen(model: Model,
                             @RequestParam("userName") userName: String = "",
                             @RequestParam("sessionId") sessionId: String = "",
                             @RequestParam("sessionPas") sessionPas: String = ""): String {
        logger.info("isSpyShowen($userName, $sessionId, $sessionPas)")
        return SpyManager.isSpyUncovered(sessionId.toLong(), sessionPas.toLong()).toString()
    }


}