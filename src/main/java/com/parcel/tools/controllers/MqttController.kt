package com.parcel.tools.controllers

import com.parcel.tools.constructor.Page
import com.parcel.tools.constructor.content.mqtt.MqttPage
import com.parcel.tools.mqtt.database.dao.UsersDao
import com.parcel.tools.mqtt.database.entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import kotlin.random.Random

@Controller
@RequestMapping("/mqtt")
class MqttController {

    @Autowired
    lateinit var usersDao: UsersDao;

    @RequestMapping("/login")
    public fun index(model: Model): String {
        model.addAttribute("page", Page(MqttPage()))
        return "web/html/mqtt/login";
    }

    @RequestMapping("/users")
    public fun getUsers(): User {
        val user = User()

        user.login = "test" + Random.nextInt();
        user.password = "pass"

        usersDao.save(user)

        return user;
    }
}