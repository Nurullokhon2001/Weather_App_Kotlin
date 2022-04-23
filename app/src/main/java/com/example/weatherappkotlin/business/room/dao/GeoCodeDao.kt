package com.example.weatherappkotlin.business.room.dao

import androidx.room.*
import com.example.weatherappkotlin.business.room.entity.GeoCodeEntity

@Dao
interface GeoCodeDao {

    @Query("SELECT * FROM GeoCode")
    fun getAll() : List<GeoCodeEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(item : GeoCodeEntity)

    @Delete
    fun remove(item : GeoCodeEntity)

}