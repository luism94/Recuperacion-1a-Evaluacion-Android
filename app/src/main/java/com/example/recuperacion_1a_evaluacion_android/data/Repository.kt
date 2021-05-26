package com.example.recuperacion_1a_evaluacion_android.data

import androidx.lifecycle.LiveData
import com.example.recuperacion_1a_evaluacion_android.data.entity.Libro


interface Repository {
    //Recibir la lista guardada de libros del viewModel
    fun obtenerLista(): LiveData<List<Libro>>

    //Crear un libro nuevo y guardarlo en la lista
    fun aniadirLibro(titulo: String, autor: String, fecha: Int, urlPortada: String, sinopsis: String)

    //Eliminar libro con gesto swipe
    fun eliminarLibro(libro: Libro): Int

    //Reinsertar libro desde Snackbar
    fun insertarLibro(libro: Libro): Long
}