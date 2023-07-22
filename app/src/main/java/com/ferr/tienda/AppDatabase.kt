package com.ferr.tienda

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Computadora::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun computadoraDao(): ComputadoraDao

    companion object {
        private var instance: AppDatabase? = null
        private const val dbname = "tiendita_db"

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, AppDatabase::class.java, dbname).build()
            }
            return instance!!
        }
    }
}