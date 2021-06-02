package com.example.recuperacion_1a_evaluacion_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.recuperacion_1a_evaluacion_android.data.AppDatabase
import com.example.recuperacion_1a_evaluacion_android.data.LocalRepository
import com.example.recuperacion_1a_evaluacion_android.data.entity.Libro
import com.example.recuperacion_1a_evaluacion_android.databinding.AddBookActivityBinding
import com.google.android.material.snackbar.Snackbar
import utils.hideSoftKeyboard


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

    private lateinit var titulo: String
    private lateinit var autor: String
    private lateinit var fecha: String
    private lateinit var portada: String
    private lateinit var sinopsis: String

    //Metodo inflador de la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = AddBookActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
    }

    private fun setupViews() {
//        setupTextInputs()
//        observarViewModel()
        titulo = binding.inputTitle.text.toString()
        autor = binding.inputAuthor.text.toString()
        fecha = binding.inputYear.text.toString()
        portada = binding.inputTitlePageURL.text.toString()
        sinopsis = binding.inputSynopsis.text.toString()
    }

//    private fun setupTextInputs() {
//        binding.inputTitle.addTextChangedListener(object {
//        })
//    }

//    private fun observarViewModel() {
//        viewModel.titulo.observe(this) {
//            titulo = it
//        }
//        viewModel.autor.observe(this) {
//            autor = it
//        }
//        viewModel.fecha.observe(this) {
//            fecha = it
//        }
//        viewModel.portada.observe(this) {
//            portada = it
//        }
//        viewModel.sinopsis.observe(this) {
//            sinopsis = it
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_book_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }
    // Metodo sobreescrito para que el icono de vuelta simule el pulsado del boton de atrás
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addBookToDDBB -> añadirLibro()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun añadirLibro() {
//        if (viewModel.comprobarFormulario()) {
//            viewModel.insertarLibro()
//            onBackPressed()
//            finish()
//        } else {
//            Snackbar.make(
//                binding.root,
//                R.string.form_error,
//                Snackbar.LENGTH_SHORT
//            )
//            .show()
//        }
        binding.inputSynopsis.hideSoftKeyboard()
        if (comprobarFormulario()) {
            viewModel.insertarLibro(
                Libro(0, titulo, autor, fecha.toInt(), portada, sinopsis)
            )
            onBackPressed()
            finish()
        } else {
            Snackbar.make(
                binding.root,
                R.string.form_error,
                Snackbar.LENGTH_SHORT
            )
                .show()
        }
    }

    fun comprobarFormulario(): Boolean {
        return comprobarTitulo() &&
                comprobarAutor() &&
                comprobarFecha() &&
                comprobarPortada() &&
                comprobarSinopsis()
    }

    private fun comprobarTitulo(): Boolean {
        return !TextUtils.isEmpty(binding.inputTitle.text.toString())
    }
    private fun comprobarAutor(): Boolean {
        return !TextUtils.isEmpty(binding.inputAuthor.text.toString())
    }
    private fun comprobarFecha(): Boolean {
        return !TextUtils.isEmpty(binding.inputYear.text.toString()) && TextUtils.isDigitsOnly(binding.inputYear.text.toString())
    }
    private fun comprobarPortada(): Boolean {
        return !TextUtils.isEmpty(binding.inputTitlePageURL.text.toString()) && (binding.inputTitlePageURL.text.toString().startsWith("http://") || binding.inputTitlePageURL.text.toString().startsWith("https://"))
    }
    private fun comprobarSinopsis(): Boolean {
        return !TextUtils.isEmpty(binding.inputSynopsis.text.toString())
    }

//    fun insertarLibro() {
//        dataSource.insertarLibro(libro)
//    }

    //Objeto compañero que define el objeto Intent para llamar a la actividad
    companion object {
        fun newIntent(context: Context) =
            Intent(context, AddBookActivity::class.java)
    }
}