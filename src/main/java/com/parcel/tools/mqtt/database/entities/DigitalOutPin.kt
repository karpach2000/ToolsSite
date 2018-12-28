package com.parcel.tools.mqtt.database.entities

import javax.persistence.*

@Entity
@Table(name = "DigitalOutPins")
class  DigitalOutPin
{
    @Id
    var id :Int = 0

    @ManyToOne
    @JoinColumn(name = "boardId")
    var board = Board()

    @Column(name = "pinNumber")
    var pinNumber: Short =0

    @Column(name = "value")
    var value: Short = 0

    @Column(name = "description")
    var description  = ""

    @Column(name = "deleted")
    var deleted : Short = 0
}