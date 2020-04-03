package com.parcel.tools.controllers

import com.parcel.tools.constructor.Page
import com.parcel.tools.constructor.bodies.counter.Counter
import com.parcel.tools.spy.Spy
import com.parcel.tools.spy.UserInformation
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.io.IOException

@Controller
class SpyController {

    private val logger = org.apache.log4j.Logger.getLogger(SpyController::class.java!!)

    @RequestMapping("/spy")
    @Throws(IOException::class)
    internal fun spy(model: Model): String {
        val counter = Counter()
        val page = Page(counter)
        model.addAttribute("page", page)
        return "web/html/spy"
    }

    @RequestMapping("/spy_addUser")
    @ResponseBody
    @Throws(IOException::class)
    internal fun addUser(model: Model, @RequestParam("userName") userName: String = ""): String {
        logger.info("addUser($userName)")


        val counter = Counter()
        val page = Page(counter)
        model.addAttribute("page", page)
        return Spy.addUser(userName).toString()
    }

    @RequestMapping("/spy_start_game")
    @ResponseBody
    @Throws(IOException::class)
    internal fun startGame(model: Model, @RequestParam("userName") userName: String = ""): String {
        logger.info("startGame($userName)")
        Spy.startGame()
        val userInformation = Spy.getUserInformation(userName)
        return userInformation.toString()
    }

    @RequestMapping("/spy_stop_game")
    @ResponseBody
    @Throws(IOException::class)
    internal fun stopGame(model: Model, @RequestParam("userName") userName: String = ""): String {
        logger.info("stopGame($userName)")
        Spy.stopGame()
        return "true"
    }

    @RequestMapping("/spy_get_spy")
    @ResponseBody
    @Throws(IOException::class)
    internal fun getSpy(model: Model, @RequestParam("userName") userName: String = ""): String {
        logger.info("getSpy($userName)")
        return Spy.getSpy()
    }

    @RequestMapping("/spy_is_spy_showen")
    @ResponseBody
    @Throws(IOException::class)
    internal fun isSpyShowen(model: Model, @RequestParam("userName") userName: String = ""): String {
        logger.info("isSpyShowen($userName)")
        return Spy.spyIsNotSecret.toString()
    }


}