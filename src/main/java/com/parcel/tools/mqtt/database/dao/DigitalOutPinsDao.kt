package com.parcel.tools.mqtt.database.dao

import com.parcel.tools.mqtt.database.entities.Board
import com.parcel.tools.mqtt.database.entities.DigitalOutPin
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.sql.SQLException
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.PersistenceContext

@Component
open class DigitalOutPinsDaoImpl : DigitalOutPinsDao()
{
    @PersistenceContext
    private lateinit var em: EntityManager

    @Autowired
    private lateinit var boardsDao: BoardsDao;

    @Transactional
    override fun save(digitalOutPins: DigitalOutPin) {
        em.persist(em)
    }

    override  fun getAll() : List<DigitalOutPin> {
        val sql = "SELECT d FROM DigitalOutPin d"
        val query = em.createQuery(sql, DigitalOutPin::class.java)
        return query.resultList
    }

    override  fun getAll(board: Board) : List<DigitalOutPin> {
        val sql = "SELECT d FROM DigitalOutPin d WHERE d.board = :board"
        val query = em.createQuery(sql, DigitalOutPin::class.java)
        query.setParameter("board", board)
        return query.resultList
    }

    override fun getPin(board: Board, pinNumber: Int) : DigitalOutPin? {
        val sql = "SELECT d FROM DigitalOutPin d WHERE d.board = :board AND d.pinNumber = :pinNumber"
        val query = em.createQuery(sql, DigitalOutPin::class.java)
        query.setParameter("board", board)
        query.setParameter("pinNumber", pinNumber)

        try {
            return query.singleResult
        } catch (e: NoResultException) {
            return null;
        }
    }

    @Transactional
    override fun updatePin(digitalOutPin: DigitalOutPin)
    {
        em.merge(digitalOutPin)
    }
}



