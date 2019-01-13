package com.parcel.tools.mqtt.database.entities

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "Roles")
class Role {
    @Id
    var id: Int = 0

    @Column(name = "role")
    var role: String = ""
}