package com.example.recuperacion_1a_evaluacion_android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recuperacion_1a_evaluacion_android.data.entity.Libro
import com.example.recuperacion_1a_evaluacion_android.databinding.MainActivityBookBinding

class MainActivityAdapter(): ListAdapter<Libro, MainActivityAdapter.ViewHolder>(CallbackDiferenciasLibros) {

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    var listaLibros: List<Libro> = emptyList()

    inner class ViewHolder(private var binding: MainActivityBookBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = absoluteAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.invoke(position)
                }
            }
        }

        fun bind(libro: Libro) {
            binding.bookTitle.text = libro.titulo
            binding.bookAuthor.text = libro.autor
            binding.bookYear.text = libro.fecha.toString()
            Glide.with(itemView).load(libro.urlPortada).into(binding.titlePageBook)    //Recibir contexto de MainActivity de alguna forma
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainActivityAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MainActivityBookBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewholder: MainActivityAdapter.ViewHolder, posicion: Int) {
        viewholder.bind(currentList[posicion])
    }
}

object CallbackDiferenciasLibros: DiffUtil.ItemCallback<Libro>() {
    override fun areItemsTheSame(libroAnterior: Libro, libroNuevo: Libro): Boolean {
        return libroAnterior.id == libroNuevo.id
    }

    override fun areContentsTheSame(libroAnterior: Libro, libroNuevo: Libro): Boolean {
        return libroAnterior.titulo == libroNuevo.titulo &&
                libroAnterior.autor == libroNuevo.autor &&
                libroAnterior.fecha == libroNuevo.fecha
    }
}

typealias OnItemClickListener = (position: Int) -> Unit