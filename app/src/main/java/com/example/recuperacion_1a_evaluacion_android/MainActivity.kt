package com.example.recuperacion_1a_evaluacion_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.recyclerview.widget.*
import com.example.recuperacion_1a_evaluacion_android.data.AppDatabase
import com.example.recuperacion_1a_evaluacion_android.data.LocalRepository
import com.example.recuperacion_1a_evaluacion_android.data.entity.Libro
import com.example.recuperacion_1a_evaluacion_android.databinding.MainActivityBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import utils.observeEvent
import utils.setOnSwipeListener

private const val TAG_DETAIL_FRAGMENT = "TAG_DETAIL_FRAGMENT"

class MainActivity : AppCompatActivity() {
    // Binding del layout de la actividad
    private lateinit var binding: MainActivityBinding
    // ViewModel con la logica detras de la actividad
    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory(
            LocalRepository(AppDatabase.getInstance(applicationContext).bookDao),
            application
        )
    }
    // Adaptador del RecyclerView de la actividad con la lista de libros
    private val adaptadorLista: MainActivityAdapter = MainActivityAdapter().apply {
        setOnItemClickListener {
            viewModel.setCurrentBook(currentList[it])
        }
    }
    // Metodo inflador de la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = MainActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
    }
    // Metodo para preparar las vistas de la actividad
    private fun setupViews() {
        binding.lblCerrarPanel.setOnClickListener {
            viewModel.closeBookPanel()
        }
        setupAdaptadorLista()
        observarViewModel()
    }

    //Metodo para preparar los datos a observar del viewmodel desde la actividad
    private fun observarViewModel() {
        //Observacion de la lista de libros, esconde el icono y textview relevantes si la lista no esta vacia
        viewModel.listaLibros.observe(this) { cargarLista(it) }
        // Observacion del mensaje de borrado de libros envuelto en clase Event
        viewModel.eventoMensaje.observeEvent(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        }
        // Observacion del estado del panel que muestra la informacion del libro pulsado
        viewModel.mostrarPanel.observe(this) {
            binding.bookPanel.isVisible = it
        }
        // Observacion del ultimo libro pulsado para mostrar en el panel
        viewModel.currentBook.observe(this) { libro ->
            binding.lblBookTitle.text = libro.titulo
            binding.lblBookSinopsis.text = libro.sinopsis
        }
    }
    // Metodo para inicializar la lista de libros y esconder la vista flotante
    private fun cargarLista(libros: List<Libro>) {
        adaptadorLista.submitList(libros)
        binding.lblNoBooks.visibility = if (libros.isEmpty()) View.VISIBLE else View.INVISIBLE
        binding.iconNoBooks.visibility = if (libros.isEmpty()) View.VISIBLE else View.INVISIBLE
    }
    // Metodo para preparar el adaptador de la lista para el recyclerview
    private fun setupAdaptadorLista() {
        binding.bookList.apply {
            setHasFixedSize(true)
            adapter = adaptadorLista
            addItemDecoration(DividerItemDecoration(this@MainActivity, RecyclerView.VERTICAL))
            itemAnimator = DefaultItemAnimator()
            layoutManager =
                GridLayoutManager(this@MainActivity, resources.getInteger(R.integer.numColumnas))
            setOnSwipeListener { viewHolder, direction ->
                borrarLibro(
                    adaptadorLista.currentList[viewHolder.bindingAdapterPosition],
                    viewHolder.bindingAdapterPosition
                )
            }
        }
    }
    // Metodo para mostrar la informacion del libro que se pulsa con un Toast
    private fun mostrarLibro(libro: Libro) {
        Toast.makeText(this, libro.toString(), Toast.LENGTH_SHORT).show()
    }
    // Metodo para eliminar un libro de la lista al arrastrarlo a un lateral de la pantalla
    // y mostrar un snackbar
    private fun borrarLibro(libro: Libro, posicion: Int) {
        mostrarSnackBar(libro, posicion)
    }
    // Metodo que muestra el snackbar que avisa sobre el borrado del libro arrastrado
    private fun mostrarSnackBar(libro: Libro, posicion: Int) {
        Snackbar.make(
            binding.root,
            getString(R.string.libro_eliminado, libro.titulo),
            Snackbar.LENGTH_LONG
        )
        .setAction(R.string.deshacer_borrado) {
            deshacerBorrarLibro(posicion)
        }
        .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                    viewModel.eliminarLibro(libro)
                }
            }
        })
        .show()
    }
    //Metodo para deshacer el borrado del libro que se acaba de arrastrar
    private fun deshacerBorrarLibro(posicion: Int) {
        adaptadorLista.notifyItemChanged(posicion)
    }

    //Inflar menu Appbar de la actividad
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }
    //Metodo para establecer las acciones de los iconos contenidos en el menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.goToAddBookActivity -> navigateToAddBookActivity()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
    // Metodo para crear el objeto Intent que navega a la segunda actividad
    private fun navigateToAddBookActivity() {
        val intent = AddBookActivity.newIntent(applicationContext)
        startActivity(intent)
    }
}