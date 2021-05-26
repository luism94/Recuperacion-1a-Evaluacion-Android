package com.example.recuperacion_1a_evaluacion_android.data

import androidx.lifecycle.LiveData
import com.example.recuperacion_1a_evaluacion_android.data.entity.Libro

class Retrofit: DataSource {
    override fun obtenerLista(): LiveData<List<Libro>> {
        TODO("Not yet implemented")
    }

    override fun aniadirLibro(titulo: String, autor: String, fecha: Int, urlPortada: String, sinopsis: String) {
        TODO("Not yet implemented")
    }

    override fun eliminarLibro(libro: Libro): Boolean {
        TODO("Not yet implemented")
    }

    override fun insertarLibro(libro: Libro): Boolean {
        TODO("Not yet implemented")
    }
}