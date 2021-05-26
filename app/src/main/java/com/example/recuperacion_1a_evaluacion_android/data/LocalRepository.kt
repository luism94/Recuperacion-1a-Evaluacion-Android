package com.example.recuperacion_1a_evaluacion_android.data

import androidx.lifecycle.LiveData
import com.example.recuperacion_1a_evaluacion_android.data.entity.Libro

class LocalRepository(private val bookDAO: BookDAO) : Repository {
    override fun obtenerLista(): LiveData<List<Libro>> = bookDAO.obtenerLista()

    override fun aniadirLibro(titulo: String, autor: String, fecha: Int, urlPortada: String, sinopsis: String) = bookDAO.aniadirLibro(titulo, autor, fecha, urlPortada, sinopsis)

    override fun eliminarLibro(libro: Libro) = bookDAO.eliminarLibro(libro)


    override fun insertarLibro(libro: Libro): Long = bookDAO.insertarLibro(libro)

}