package com.ferr.tienda

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ComputadoraDao {
    @Query("SELECT * FROM computadora")
    fun getAllComputadoras(): List<Computadora>

    @Query("SELECT * FROM computadora WHERE idComputadora = :idComputadora")
    fun getDirectComputadora(idComputadora: Int): Computadora

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertComputadora(computadora: Computadora)

    @Delete
    fun deleteComputadora(computadora: Computadora)

    @Update
    fun updateComputadora(computadora: Computadora)
}