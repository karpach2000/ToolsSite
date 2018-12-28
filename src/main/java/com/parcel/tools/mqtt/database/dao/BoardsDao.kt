package com.parcel.tools.mqtt.database.dao

import com.parcel.tools.mqtt.database.entities.Board
import com.parcel.tools.mqtt.database.entities.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Component
open class BoardsDaoImpl : BoardsDao()
{

    @PersistenceContext
    private lateinit var em : EntityManager

    @Autowired
    private lateinit var usersDao: UsersDaoImpl

    @Autowired
    private lateinit var digitalInPinDao : DigitalInPinsDao

    @Autowired
    private lateinit var digitalOutPinsDao: DigitalOutPinsDao

    @Transactional
    override fun save(board: Board) {
        em.persist(board)
    }

    override  fun getAll(boardType : Int) : List<Board>
    {
        val sql = "SELECT b FROM Board WHERE b.boardType = :boardType"
        val query = em.createQuery(sql, Board::class.java)
        query.setParameter("boardType", boardType)

        return query.resultList
    }
    override  fun getBoard(mqttTopic : String) : Board
    {
        val login = getLoginFromMqttTopic(mqttTopic)
        val user = usersDao.get(login)
        val boardRsName = getBoardRsNameFromMqttTopic(mqttTopic)

        val sql = "SELECT b FROM Board WHERE b.boardRsName = :boardRsName AND b.user = :user"
        val query = em.createQuery(sql, Board::class.java)
        query.setParameter("user", user)
        query.setParameter("boardRsName", boardRsName)

        return query.singleResult
    }

    override fun getAll(user : User) : List<Board>
    {
        val sql = "SELECT b FROM Board WHERE b.user = :user"
        val query = em.createQuery(sql, Board::class.java)

        query.setParameter("user", user)

        return query.resultList

    }

    override  fun get(id: Int) : Board
    {
        return em.find(Board::class.java, id)
    }

    override fun setInPins(mqttTopic:  String, pinVals: String)
    {
        val board = getBoard(mqttTopic)
        var j=0
        for(i in pinVals)
        {
            val pin = digitalInPinDao.getPin(board, j)
            if(pin!=null) {
                pin.value ="$i".toShort()
                digitalInPinDao.updatePin(pin)
            }
            j++
        }
    }

    override fun setOutPins(mqttTopic:  String, pinVals: String)
    {
        val board = getBoard(mqttTopic)
        for(i in 0..pinVals.length)
        {
            val pin = digitalOutPinsDao.getPin(board, "$i".toInt())
            if(pin!=null) {
                println("Updating pin: $pin")
                digitalOutPinsDao.updatePin(pin)
            }
        }
    }

    private fun getLoginFromMqttTopic(mqttTopic: String) : String
    {
        var args =mqttTopic.split("/")
        return args[1]

    }
    private fun getBoardRsNameFromMqttTopic(mqttTopic: String) :String
    {
        var args =mqttTopic.split("/")
        return args[args.size-2]
    }
}

