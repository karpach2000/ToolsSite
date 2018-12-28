package com.parcel.tools.mqtt.database.dao

import com.parcel.tools.mqtt.database.entities.Board
import com.parcel.tools.mqtt.database.entities.DigitalInPin
import org.jetbrains.annotations.Nullable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.sql.SQLException
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.PersistenceContext

@Component
open class DigitalInPinsDaoImpl : DigitalInPinsDao()
{

    @PersistenceContext
    private lateinit var em: EntityManager

    @Autowired
    private lateinit var boardsDao: BoardsDao;

    override fun save(digitalInPin: DigitalInPin) {
        em.persist(digitalInPin)
    }

    override  fun getPin(board: Board, pinNumber: Int) : DigitalInPin?
    {
        val sql = "SELECT d FROM DigitalInPin d WHERE d.board = :board AND d.pinNumber = :pinNumber"
        val query = em.createQuery(sql, DigitalInPin::class.java)

        query.setParameter("board", board)
        query.setParameter("pinNumber", pinNumber)

        try {
            return query.singleResult
        } catch (e : NoResultException) {
            return null
        }
    }

    override  fun getAll() : List<DigitalInPin>
    {
        val sql = "SELECT d FROM DigitalInPin d"
        val query = em.createQuery(sql, DigitalInPin::class.java)
        return query.resultList
    }

    override  fun getAll(board: Board) : List<DigitalInPin> {
        val pins = ArrayList<DigitalInPin>()
        val boardId = board.id
        val sql = "SELECT d FROM DigitalInPin d WHERE d.board = :board"
        val query = em.createQuery(sql, DigitalInPin::class.java)
        query.setParameter("board", board)

        return query.resultList
    }

    override fun updatePin(digitalInPin: DigitalInPin) {
        em.merge(digitalInPin)
    }
}
