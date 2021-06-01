package com.example.recuperacion_1a_evaluacion_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
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
    //Binding del layout de la actividad
    private lateinit var binding: MainActivityBinding
    //ViewModel con la logica detras de la actividad
    private val viewModel: MainActivityViewModel by viewModels {
        MainActivityViewModelFactory(
            LocalRepository(AppDatabase.getInstance(applicationContext).bookDao),
            application
        )
    }
    //Adaptador del RecyclerView de la actividad con la lista de libros
    private val adaptadorLista: MainActivityAdapter = MainActivityAdapter().apply {
        setOnItemClickListener {
            viewModel.setCurrentBook(currentList[it])
        }
    }
    //Metodo inflador de la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = MainActivityBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
    }
    //Metodo para preparar las vistas de la actividad
    private fun setupViews() {
        binding.lblCerrarPanel.setOnClickListener {
            viewModel.closePanel()
        }
        setupAdaptadorLista()
        observarViewModel()
    }

    //Metodo para preparar los datos a observar del viewmodel desde la actividad
    private fun observarViewModel() {
        //Observacion de la lista de libros
        viewModel.listaLibros.observe(this) { cargarLista(it) }
        //Observacion del mensaje de borrado de libros envuelto en clase Event
        viewModel.eventoMensaje.observeEvent(this) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
        }
        //Observacion del estado del panel que muestra la informacion del libro pulsado
        viewModel.mostrarPanel.observe(this) {
            binding.bookPanel.isVisible = it
        }
        //Observacion del ultimo libro pulsado para mostrar en el panel
        viewModel.currentBook.observe(this) { libro ->
            binding.lblBookTitle.text = libro.titulo
            binding.lblBookSinopsis.text = libro.sinopsis
        }
    }
    //Metodo para inicializar la lista de libros y esconder la vista flotante
    private fun cargarLista(libros: List<Libro>) {
        adaptadorLista.submitList(libros)
        binding.lblNoBooks.visibility = if (libros.isEmpty()) View.VISIBLE else View.INVISIBLE
    }
    //Metodo para preparar el adaptador de la lista para el recyclerview
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
    //Metodo para mostrar la informacion del libro que se pulsa
    private fun mostrarLibro(libro: Libro) {
        Toast.makeText(this, libro.toString(), Toast.LENGTH_SHORT).show()
    }
    //Metodo para eliminar un libro de la lista y mostrar un snackbar
    private fun borrarLibro(libro: Libro, posicion: Int) {
        mostrarSnackBar(libro, posicion)
    }

    private fun mostrarSnackBar(libro: Libro, posicion: Int) {
        Snackbar.make(binding.root,"Libro ${libro.titulo} eliminado", Snackbar.LENGTH_LONG)
            .setAction("DESHACER") {
                reinsertarLibro(posicion)
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
    //Metodo para reinsertar el libro que se acaba de eliminar a traves del snackbar
    private fun reinsertarLibro(posicion: Int) {
        adaptadorLista.notifyItemChanged(posicion)
    }

    //Inflar menu Appbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addBookOpt -> navigateToAddBookActivity()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun navigateToAddBookActivity() {
        TODO("Not yet implemented")

    }
}