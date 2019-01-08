package com.parcel.tools.controllers

import com.parcel.tools.mqtt.database.dao.UsersDao
import com.parcel.tools.mqtt.database.entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random

@RestController
@RequestMapping("/mqtt")
class MqttController {

    @Autowired
    lateinit var usersDao: UsersDao;

    @RequestMapping("/users")
    public fun getUsers(): User {
        val user = User()

        user.login = "test" + Random.nextInt();
        user.password = "pass"

        usersDao.save(user)

        return user;
    }
}