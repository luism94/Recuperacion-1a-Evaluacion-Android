package com.example.recuperacion_1a_evaluacion_android.data.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = "libro",
    indices = [
        Index(
            name = "INDICE_AUTOR_ANIO",
            value = ["Autor", "Fecha publicacion"]
        )
    ],
)
data class Libro(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="ID libro", defaultValue = "0")
    val id: Int,
    @ColumnInfo(name="Titulo libro")
    val titulo: String,
    @ColumnInfo(name="Autor", collate = ColumnInfo.NOCASE)
    val autor: String,
    @ColumnInfo(name="Fecha publicacion")
    val anio: Int,
    @ColumnInfo(name="URL portada")
    val urlPortada: String,
    @ColumnInfo(name="Descripcion sinopsis")
    val sinopsis: String
)