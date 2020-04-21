package com.parcel.tools.spy.database

import com.parcel.tools.Globals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.sql.SQLException

@Component
open class SpyLocationManager {
    @Autowired
    var jdbcTemplate: JdbcTemplate? = null



    private val logger = org.apache.log4j.Logger.getLogger(SpyLocationManager::class.java!!)

    init {
        logger.info("Init UserManager (database)")
        Globals.spyLocationManager = this
    }

    internal inner class GetLocationRowMapper : RowMapper<SpyLocation> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): SpyLocation {
            val spyLocation = SpyLocation()
            //user.id = rs.getInt("id")
            spyLocation.authorLogin = rs.getString("login")
            spyLocation.locationName= rs.getString("location")
            return spyLocation
        }
    }
    internal inner class GetLocationAsStringRowMapper : RowMapper<String> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): String {
            return rs.getString("location")
        }
    }
    internal inner class StringRowMapper : RowMapper<String> {
        @Throws(SQLException::class)
        override fun mapRow(rs: ResultSet, rowNum: Int): String {
            val spyLocation = SpyLocation()
            //user.id = rs.getInt("id")
            return "OK"
        }
    }

    fun getAllLocationsAsString(): List<String>
    {
        logger.info("getAllLocationsAsString()")
        return jdbcTemplate!!.query("SELECT location FROM get_spy_locations_and_login()",
                GetLocationAsStringRowMapper())
    }
    fun getAllLocations(): List<SpyLocation>
    {
        logger.info("getAllLocations()")
        return jdbcTemplate!!.query("SELECT * FROM get_spy_locations_and_login()",
                GetLocationRowMapper())
    }

    fun addLocation(location: String, userLogin: String):Boolean
    {
        logger.info("addLocation($location, $userLogin)")
        jdbcTemplate!!.query("SELECT * FROM add_spy_location('$location', '$userLogin')",
                StringRowMapper())
        return true
    }
    fun deleteLocation(location: String, login: String):Boolean
    {
        logger.info("deleteLocation($location, $login)")
        jdbcTemplate!!.query("SELECT * FROM delete_spy_location('$location', '$login')",
                StringRowMapper())
        return true
    }

}