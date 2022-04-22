package com.example.weatherappkotlin.business.room.dao

import androidx.room.*
import com.example.weatherappkotlin.business.room.entity.WeatherDataEntity

@Dao
interface WeatherDao {

    @Query("SELECT * from WeatherData Where id = 1")
    fun getWeatherData(): WeatherDataEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeatherData(data: WeatherDataEntity)

    @Delete
    fun deleteWeatherData(data: WeatherDataEntity)

    @Update
    fun updateWeatherData(data: WeatherDataEntity)
}