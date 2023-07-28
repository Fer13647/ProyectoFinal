package com.ferr.tienda

import android.media.Image
import android.text.Editable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "computadora")
data class Computadora(
    @PrimaryKey(autoGenerate = true)
    val idComputadora: Int = 0,
    val nombre: String,
    val descripcion: String,
    val marca: String,
    val modelo: String,
    val procesador: String,
    val ram: Int,
    val almacenamiento: Long,
    val serviceTag: String,
    val noInventario: String,
    val ubicacion: Int,
    val urlImagen : String
)