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

    @RequestMapping("/spy")
    @Throws(IOException::class)
    internal fun spy(model: Model, @RequestParam("userName") userName: String = ""): String {
        Spy.addUser(userName)
        val userInformation = Spy.getUserInformation(userName)

        val counter = Counter()
        val page = Page(counter)

        model.addAttribute("userInformation", userInformation)
        model.addAttribute("page", page)
        return "web/html/spy"
    }

    @RequestMapping("/spy_start_game")
    @ResponseBody
    @Throws(IOException::class)
    internal fun startGame(model: Model, @RequestParam("userName") userName: String = ""): String {
        Spy.startGame()
        val userInformation = Spy.getUserInformation(userName)
        return userInformation.toJson()
    }

    @RequestMapping("/spy_stop_game")
    @ResponseBody
    @Throws(IOException::class)
    internal fun stopGame(model: Model, @RequestParam("userName") userName: String = ""): String {
        Spy.stopGame()
        return "web/html/spy"
    }

    @RequestMapping("/spy_stop_game")
    @ResponseBody
    @Throws(IOException::class)
    internal fun getSpy(model: Model, @RequestParam("userName") userName: String = ""): String {
        return Spy.getSpy()
    }


}