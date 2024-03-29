package com.christopher.weather_forcast.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities_table")
data class City(

    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "city") var name: String

)