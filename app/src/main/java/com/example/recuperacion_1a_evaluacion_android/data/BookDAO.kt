package com.example.recuperacion_1a_evaluacion_android.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.recuperacion_1a_evaluacion_android.data.entity.Libro

@Dao
interface BookDAO {
    //Recibir la lista guardada de libros del viewModel
    @Query("SELECT * FROM libro")
    fun obtenerLista(): LiveData<List<Libro>>

    //Crear un libro nuevo y guardarlo en la lista
    @Insert
    fun insertarLibro(libro: Libro)

    //Eliminar libro con gesto swipe
    @Delete
    fun eliminarLibro(libro: Libro): Int

//    //Reinsertar libro desde Snackbar
//    @Insert
//    fun insertarLibro(libro: Libro): Long
}