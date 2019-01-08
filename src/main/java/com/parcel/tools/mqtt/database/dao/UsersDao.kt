package com.parcel.tools.mqtt.database.dao

import com.parcel.tools.mqtt.database.entities.User
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.Query

@Component
open class UsersDaoImpl : UsersDao()
{
    @PersistenceContext
    private lateinit var em: EntityManager

    @Transactional
    override fun save(user: User) {
        em.persist(user)
    }

    override  fun getAll() : List<User>
    {
        val sql = "SELECT u FROM User u"
        val query = em.createQuery(sql, User::class.java)

        return query.resultList;
    }

    override  fun get(login: String) : User
    {
        val sql = "SELECT u FROM User u WHERE u.login = :login"
        val query = em.createQuery(sql, User::class.java)
        query.setParameter("login", login)

        return query.singleResult
    }

    override  fun get(id: Int) : User
    {
        return em.find(User::class.java, id)
    }

}
