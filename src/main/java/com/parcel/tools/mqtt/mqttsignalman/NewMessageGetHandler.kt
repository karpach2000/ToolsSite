package com.parcel.tools.mqtt.mqttsignalman

interface NewMessageGetHandler {
    fun newRelePosition(topic: String, data: String)
}