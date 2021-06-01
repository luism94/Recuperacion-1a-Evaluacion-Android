package com.example.recuperacion_1a_evaluacion_android

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recuperacion_1a_evaluacion_android.data.Repository
import com.example.recuperacion_1a_evaluacion_android.data.entity.Libro
import utils.Event

class MainActivityViewModel(private val dataSource: Repository, private val application: Application): ViewModel() {
    private val _mostrarPanel: MutableLiveData<Boolean> = MutableLiveData(false)
    val mostrarPanel: LiveData<Boolean> get() = _mostrarPanel

    private val _currentBook: MutableLiveData<Libro> = MutableLiveData()
    val currentBook: LiveData<Libro> get() = _currentBook

    val listaLibros: LiveData<List<Libro>> = dataSource.obtenerLista()

    private val _eventoMensaje: MutableLiveData<Event<String>> = MutableLiveData()
    val eventoMensaje: LiveData<Event<String>> get() = _eventoMensaje

    fun setCurrentBook(libro: Libro) {
        _currentBook.value = libro
        _mostrarPanel.value = true
    }

    fun closePanel() {
        _mostrarPanel.value = false
    }

    fun eliminarLibro(libro: Libro) {
        dataSource.eliminarLibro(libro)
        _eventoMensaje.value = Event(application.getString(R.string.libro_eliminado))
    }

//    fun insertarLibro(libro: Libro) {
//        dataSource.insertarLibro(libro)
//    }
}