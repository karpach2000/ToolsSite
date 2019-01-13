package com.parcel.tools.mqtt.database.dao

import com.parcel.tools.mqtt.database.entities.Role
import org.springframework.stereotype.Component
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.PersistenceContext

@Component
open class RolesDao {

    @PersistenceContext
    private lateinit var em: EntityManager;

    fun getAdminRole(): Role? {
        val sql = "SELECT r FROM Role r WHERE r.role = :role"
        val q = em.createQuery(sql, Role::class.java)
        q.setParameter("role", "ADMIN")

        try {
            return q.singleResult
        } catch (e: NoResultException) {
            return null;
        }
    }
}