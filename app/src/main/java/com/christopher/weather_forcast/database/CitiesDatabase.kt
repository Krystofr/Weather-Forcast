package com.christopher.weather_forcast.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.christopher.weather_forcast.model.City

@Database(entities = [City::class], version = 1, exportSchema = false)
abstract class CitiesDatabase : RoomDatabase() {

    abstract fun CitiesDao(): CitiesDao

    companion object {

        @Volatile
        private var INSTANCE: CitiesDatabase? = null

        fun getDatabase(context: Context): CitiesDatabase? {
            if (INSTANCE == null) {
                synchronized(CitiesDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            CitiesDatabase::class.java,
                            "cities_database"
                        )
                            .build()
                    }
                }
            }
            return INSTANCE
        }


    }

}