package com.example.recuperacion_1a_evaluacion_android

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recuperacion_1a_evaluacion_android.data.Repository
import com.example.recuperacion_1a_evaluacion_android.data.entity.Libro
import com.google.android.material.snackbar.Snackbar

class AddBookActivityViewModel(private val dataSource: Repository, private val application: Application) : ViewModel() {
    private var _titulo: MutableLiveData<String> = MutableLiveData()
    val titulo: LiveData<String> get() = _titulo
    private val _autor: MutableLiveData<String> = MutableLiveData()
    val autor: LiveData<String> get() = _autor
    private val _fecha: MutableLiveData<String> = MutableLiveData()
    val fecha: LiveData<String> get() = _fecha
    private val _portada: MutableLiveData<String> = MutableLiveData()
    val portada: LiveData<String> get() = _portada
    private val _sinopsis: MutableLiveData<String> = MutableLiveData()
    val sinopsis: LiveData<String> get() = _sinopsis

    fun insertarLibro(libro: Libro) {
        val libro: Libro = Libro(
            0,
            titulo.value!!,
            autor.value!!,
            fecha.value!!.toInt(),
            portada.value!!,
            sinopsis.value!!
        )

        dataSource.insertarLibro(libro)
    }

    fun comprobarFormulario(): Boolean {
        return comprobarTitulo() &&
                comprobarAutor() &&
                comprobarFecha() &&
                comprobarPortada() &&
                comprobarSinopsis()
    }

    private fun comprobarTitulo(): Boolean {
        return !TextUtils.isEmpty(titulo.value)
    }
    private fun comprobarAutor(): Boolean {
        return !TextUtils.isEmpty(autor.value)
    }
    private fun comprobarFecha(): Boolean {
        return !TextUtils.isEmpty(fecha.value) && TextUtils.isDigitsOnly(fecha.value)
    }
    private fun comprobarPortada(): Boolean {
        return !TextUtils.isEmpty(portada.value) && (portada.value!!.startsWith("http://") || portada.value!!.startsWith("https://"))
    }
    private fun comprobarSinopsis(): Boolean {
        return !TextUtils.isEmpty(sinopsis.value)
    }
}
