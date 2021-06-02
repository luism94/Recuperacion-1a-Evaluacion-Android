package com.example.recuperacion_1a_evaluacion_android.data.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

//Entidad de la bbdd para los libros
@Entity(
    tableName = "libro",
    indices = [
        Index(
            name = "INDICE_AUTOR_ANIO",
            value = ["autor", "fecha_publicacion"]
        )
    ],
)
data class Libro(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="ID", defaultValue = "0")
    val id: Int,
    @ColumnInfo(name="titulo")
    val titulo: String,
    @ColumnInfo(name="autor", collate = ColumnInfo.NOCASE)
    val autor: String,
    @ColumnInfo(name="fecha_publicacion")
    val fecha: Int,
    @ColumnInfo(name="url_portada")
    val urlPortada: String,
    @ColumnInfo(name="descripcion_sinopsis")
    val sinopsis: String
)