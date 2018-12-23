package com.parcel.tools.mqtt

import com.parcel.tools.mqtt.database.dao.BoardsDaoImpl
import com.parcel.tools.mqtt.database.dao.DigitalInPinsDaoImpl
import com.parcel.tools.mqtt.database.dao.DigitalOutPinsDaoImpl
import com.parcel.tools.mqtt.mqttsignalman.Client
import com.parcel.tools.mqtt.mqttsignalman.NewMessageGetHandler

class Mqtt{

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




    fun start()
    {
        val dopdi = DigitalOutPinsDaoImpl()
        val dipdi = DigitalInPinsDaoImpl()
        val dopdiTopics = arrayListOf<String>()
        val dipdiTopics = arrayListOf<String>()
        var relePositionGetEvent = NewPositionGetEvent()

        var client = Client.getClient()

        dipdi.getAll().forEach{
            val topic = "/"+it.board.user.login+"/"+it.board.boardRsName+"/"+"digitalIn"
            System.out.println(topic)
            client.subscribe(topic)
        }
        dopdi.getAll().forEach{
            val topic = "/"+it.board.user.login+"/"+it.board.boardRsName+"/"+"digitalOut"
            System.out.println(topic)
            client.subscribe(topic)
        }
        client.addNewMessageGetHandlers(relePositionGetEvent)


        //client.subscribe(dopdiTopics)
        //client.subscribe(dipdiTopics)



    }
}