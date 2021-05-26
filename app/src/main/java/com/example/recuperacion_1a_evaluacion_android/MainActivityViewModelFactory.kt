package com.example.recuperacion_1a_evaluacion_android

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.recuperacion_1a_evaluacion_android.data.Repository

@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory(private val database: Repository, private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = MainActivityViewModel(database, application) as T
}
