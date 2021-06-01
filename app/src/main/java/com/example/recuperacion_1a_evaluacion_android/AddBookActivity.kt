package com.example.recuperacion_1a_evaluacion_android

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recuperacion_1a_evaluacion_android.data.AppDatabase
import com.example.recuperacion_1a_evaluacion_android.data.LocalRepository
import com.example.recuperacion_1a_evaluacion_android.databinding.AddBookActivityBinding


class AddBookActivity : AppCompatActivity() {
    //Binding del layout de la actividad
    private lateinit var binding: AddBookActivityBinding
    //ViewModel con la logica detras de la actividad
    private val viewModel: AddBookActivityViewModel by viewModels {
        AddBookActivityViewModelFactory(
            LocalRepository(AppDatabase.getInstance(applicationContext).bookDao),
            application
        )
    }
}