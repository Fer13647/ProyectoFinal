package com.ferr.tienda

import android.content.Context
import androidx.room.Room

class ComputadoraRepository(context: Context) {
    private val db = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, "computadora-db"
    ).build()

    private val computadoraDao = db.computadoraDao()

    fun getAllComputadoras(): List<Computadora> {
        return computadoraDao.getAllComputadoras()
    }

    fun insertComputadora(computadora: Computadora) {
        computadoraDao.insertComputadora(computadora)
    }
}