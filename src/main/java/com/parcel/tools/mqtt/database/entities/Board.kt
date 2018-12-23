package com.parcel.tools.mqtt.database.entities

import javax.persistence.*

@Entity
@Table(name = "Boards")
class Board
{
    @Id
    var id :Int = 0

    @Column(name = "devId")
    var devId = ""

    @ManyToOne
    @JoinColumn(name = "userId")
    var user = User()

    @Column(name = "boardRsName")
    var boardRsName = "i"

    @Column(name = "boardType")
    var boardType: Short = 0

    @Column(name = "description")
    var description = ""

    @Column(name = "deleted")
    var deleted : Short = 0
}