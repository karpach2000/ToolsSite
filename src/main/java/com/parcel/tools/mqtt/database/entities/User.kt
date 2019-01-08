package com.parcel.tools.mqtt.database.entities

import javax.persistence.*

@Entity
@Table(name = "Users")
class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "user_id_gen")
    @TableGenerator(name = "user_id_gen" ,table = "sqlite_sequence",
            pkColumnName = "name", pkColumnValue = "Users", valueColumnName = "seq")
    var id :Int = 0

    @Column(name = "login")
    var login = ""

    @Column(name = "pas")
    var password = ""

    @Column(name = "deleted")
    var deleted : Short = 0
}