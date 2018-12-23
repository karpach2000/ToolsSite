package com.parcel.tools.mqtt.mqttsignalman


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttMessage

import java.util.ArrayList

class SimpleMqttCallBack : MqttCallback {

    private val messageGetHandlers = ArrayList<NewMessageGetHandler>()


    init {

        println("publick SimpleMqttCallBack")
    }

    override fun connectionLost(throwable: Throwable) {
        println("Connection to MQTT broker lost!!!")
    }

    @Throws(Exception::class)
    override fun messageArrived(topic: String, mqttMessage: MqttMessage) {
        println("Message received:\nmessage: " + String(mqttMessage.payload) + "\ntopic: " + topic)
        handleStateChangeEvent(topic, String(mqttMessage.payload))

    }

    override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {
        println("deliveryComplete")
    }


    fun addReleStateChangeHandlers(handler: NewMessageGetHandler) {
        this.messageGetHandlers.add(handler)
    }


    private fun handleStateChangeEvent(topic: String, data: String) {
        for (h in messageGetHandlers) {
            h.newRelePosition(topic, data)
        }
    }
}
