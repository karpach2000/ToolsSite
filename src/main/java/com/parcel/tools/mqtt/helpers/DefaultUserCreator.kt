package com.parcel.tools.mqtt.helpers

import com.parcel.tools.mqtt.database.dao.RolesDao
import com.parcel.tools.mqtt.database.dao.UsersDao
import com.parcel.tools.mqtt.database.entities.User
import com.sun.istack.internal.logging.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class DefaultUserCreator {

    @Autowired
    private lateinit var usersDao : UsersDao

    @Autowired
    private lateinit var rolesDao : RolesDao

    @Autowired
    private lateinit var passwordEncoder : PasswordEncoder

    private var logger = Logger.getLogger(DefaultUserCreator::class.java);

    @PostConstruct
    fun init() {
        val users = usersDao.getAll()
        if(users.isEmpty()) {
            createDefaultUser()
            logger.info("Default user admin with password 'admin' was created")
        }
    }

    private fun createDefaultUser() {
        val user = User();
        user.login = "admin";
        user.password = passwordEncoder.encode("admin");
        user.deleted = 0;
        val admin = rolesDao.getAdminRole()
        admin?.let { user.roles.add(it) }

        usersDao.save(user);
    }

}
