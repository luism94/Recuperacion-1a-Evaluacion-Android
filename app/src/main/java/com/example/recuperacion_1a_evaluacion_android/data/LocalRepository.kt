package com.example.recuperacion_1a_evaluacion_android.data

import androidx.lifecycle.LiveData
import com.example.recuperacion_1a_evaluacion_android.data.entity.Libro

class LocalRepository(private val bookDAO: BookDAO) : Repository {
    override fun obtenerLista(): LiveData<List<Libro>> = bookDAO.obtenerLista()

    override fun aniadirLibro(libro: Libro) = bookDAO.aniadirLibro(libro)

    override fun eliminarLibro(libro: Libro): Int = bookDAO.eliminarLibro(libro)

//    override fun insertarLibro(libro: Libro): Int = bookDAO.insertarLibro(libro)

    //Inicializo la lista mutable de libros con una lista vacia
//    private var listaLibros: MutableList<Libro> = mutableListOf(
//        Libro(
//            1,
//            "Ofrenda a la tormenta",
//            "Dolores Redondo",
//            2014,
//            "https://loremflickr.com/320/240",
//            "sinopsis"
//        ),
//        Libro(
//            2,
//            "El codigo DaVinci",
//            "Dan Brown",
//            2010,
//            "https://loremflickr.com/320/240",
//            "sinopsis"
//        ),
//        Libro(
//            3,
//            "Angeles y Demonios",
//            "Dan Brown",
//            2012,
//            "https://loremflickr.com/320/240",
//            "sinopsis"
//        )
//    )
//
//    private var librosMutableLiveData: MutableLiveData<List<Libro>> =
//        MutableLiveData(actualizarLista())
//
//    override fun obtenerLista(): LiveData<List<Libro>> = librosMutableLiveData
//
//    private fun actualizarLiveData() {
//        librosMutableLiveData.value = actualizarLista()
//    }
//
//    private fun actualizarLista() = listaLibros.toList().sortedBy { it.id }
//
//    private var idLibroInicial: Int = 3
//
//    override fun aniadirLibro(
//        libro: Libro
//    ) {
//        idLibroInicial++
//        listaLibros.add(libro)
//        actualizarLiveData()
//    }
//
//    override fun eliminarLibro(libro: Libro): Boolean {
//        val eliminado = listaLibros.remove(libro)
//        if (eliminado) {
//            actualizarLiveData()
//        }
//        return eliminado
//    }
//
//    override fun insertarLibro(libro: Libro): Boolean {
//        val aniadido = listaLibros.add(libro)
//        if (aniadido) {
//            actualizarLiveData()
//        }
//        return aniadido
//    }


}