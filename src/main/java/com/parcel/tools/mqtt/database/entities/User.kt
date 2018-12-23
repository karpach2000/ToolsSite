package com.parcel.tools.mqtt.database.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "Users")
class User
{
    @Id
    var id :Int = 0

    @Column(name = "login")
    var login = ""

    @Column(name = "pas")
    var password = ""

    @Column(name = "deleted")
    var deleted : Short = 0
}