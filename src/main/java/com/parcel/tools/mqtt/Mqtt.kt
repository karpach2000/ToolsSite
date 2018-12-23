package com.parcel.tools.mqtt

import com.parcel.tools.mqtt.database.dao.BoardsDaoImpl
import com.parcel.tools.mqtt.database.dao.DigitalInPinsDao
import com.parcel.tools.mqtt.database.dao.DigitalOutPinsDao
import com.parcel.tools.mqtt.mqttsignalman.Client
import com.parcel.tools.mqtt.mqttsignalman.NewMessageGetHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class Mqtt {

    @Autowired
    private lateinit var client : Client

    @Autowired
    private lateinit var digitalOutPinsDao : DigitalOutPinsDao

    @Autowired
    private lateinit var digitalInPinsDao : DigitalInPinsDao

    /**
     * Класс обработки изменения знаений входов.
     * AHTUNG дублирование данных.     */
    private class NewPositionGetEvent(): NewMessageGetHandler
    {

        override fun newRelePosition(topicHave: String, data: String) {

                System.out.println("New value $data")
            if(topicHave.contains("digitalOut")) {
                val dbi = BoardsDaoImpl()
                dbi.setOutPins(topicHave, data)
            }
            else if(topicHave.contains("digitalIn")) {
                val dbi = BoardsDaoImpl()

                dbi.setInPins(topicHave, data)
            }

        }
    }

    @PostConstruct
    fun start()
    {
        val dopdiTopics = arrayListOf<String>()
        val dipdiTopics = arrayListOf<String>()
        var relePositionGetEvent = NewPositionGetEvent()

        digitalInPinsDao.getAll().forEach{
            val topic = "/"+it.board.user.login+"/"+it.board.boardRsName+"/"+"digitalIn"
            System.out.println(topic)
            client.subscribe(topic)
        }
        digitalOutPinsDao.getAll().forEach{
            val topic = "/"+it.board.user.login+"/"+it.board.boardRsName+"/"+"digitalOut"
            System.out.println(topic)
            client.subscribe(topic)
        }
        client.addNewMessageGetHandlers(relePositionGetEvent)


        println("Mqtt started!");
        //client.subscribe(dopdiTopics)
        //client.subscribe(dipdiTopics)

    }
}