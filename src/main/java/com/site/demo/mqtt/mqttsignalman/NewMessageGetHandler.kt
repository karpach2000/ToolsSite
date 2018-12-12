package com.site.demo.mqtt.mqttsignalman

interface NewMessageGetHandler {
    fun newRelePosition(topic: String, data: String)
}