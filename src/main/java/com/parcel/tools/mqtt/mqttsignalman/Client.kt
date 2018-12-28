package com.parcel.tools.mqtt.mqttsignalman


import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.springframework.stereotype.Component
import java.util.*

@Component
class Client @Throws(MqttException::class)
private constructor() {

    protected var brokerUrlList = ArrayList<String>()
    protected var mqttId = "dev0"
    protected var qos = 2
    protected lateinit var mqttClient: MqttClient

    private val simpleMqttCallBack = SimpleMqttCallBack()



    init {
        brokerUrlList.add("tcp://5.200.53.139:1883")
        connect()
    }

    /******************ИНТЕРФЕЙСЫ *********************/
    fun sendMessage(messageText: String, topic: String) {
        try {
            connect()
            publishMessage(messageText, topic)
            //disconnect();
        } catch (me: MqttException) {
            println("reason " + me.reasonCode)
            println("msg " + me.message)
            println("loc " + me.localizedMessage)
            println("cause " + me.cause)
            println("excep $me")
            me.printStackTrace()
        }

    }

    fun subscribe(topic: String) {
        try {
            mqttClient.subscribe(topic)
        } catch (e: MqttException) {
            e.printStackTrace()
        }

    }

    fun subscribe(topics: ArrayList<String>)
    {
        for(t in topics)
            subscribe(t)
    }

    fun addNewMessageGetHandlers(handler: NewMessageGetHandler) {
        simpleMqttCallBack.addReleStateChangeHandlers(handler)

    }

    /******************ПРИВАТЫ */


    @Throws(MqttException::class)
    private fun connect() {
        val brokerUrl = brokerUrlList[0]
        //MemoryPersistence persistence = new MemoryPersistence();
        mqttClient = MqttClient(brokerUrl, mqttId)
        val connOpts = MqttConnectOptions()
        connOpts.isCleanSession = true
        connOpts.userName = "parcel2"
        connOpts.password = "parcel2".toCharArray()
        println("Connecting to broker: $brokerUrl\nlogin ${connOpts.userName}")
        mqttClient.connect(connOpts)
        println("Connected")


        mqttClient.setCallback(simpleMqttCallBack)

    }


    @Throws(MqttException::class)
    private fun publishMessage(messageText: String, topic: String) {
        println("Publishing message: $messageText")
        val message = MqttMessage(messageText.toByteArray())
        message.qos = qos
        mqttClient.publish(topic, message)
        println("Message published")
    }

    @Throws(MqttException::class)
    private fun disconnect() {
        mqttClient.disconnect()
        println("Disconnected")
        //System.exit(0);
    }

    companion object {


        /******************СИНГЕЛТОН */
        private var client: Client? = null

        @Throws(MqttException::class)
        fun getClient(): Client {
            if (client == null) {
                client = Client()
            }
            return client as Client
        }
    }

}
