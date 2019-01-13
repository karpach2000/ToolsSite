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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "Users_roles",
            joinColumns = [JoinColumn(name = "user_id")],
            inverseJoinColumns = [JoinColumn(name = "role_id")])
    var roles: MutableList<Role> = ArrayList()

    override fun toString(): String {
        return "User(id=$id, login='$login', password='$password', deleted=$deleted, roles=$roles)"
    }


}