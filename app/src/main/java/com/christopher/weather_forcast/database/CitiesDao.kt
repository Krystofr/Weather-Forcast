package com.christopher.weather_forcast.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.christopher.weather_forcast.model.City

@Dao
interface CitiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCity(city: City)

    @Query("SELECT * from cities_table ORDER BY id DESC")
    fun getCities(): LiveData<List<City>>

    @Delete
    fun deleteCity(city: City)

}