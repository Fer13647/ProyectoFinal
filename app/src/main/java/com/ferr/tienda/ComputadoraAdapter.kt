package com.ferr.tienda

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import kotlin.collections.List
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

interface OnModificarClickListener {
    fun onModificarClick(computadora: Computadora)
}


class ComputadoraAdapter(var context : Context, var computadoras: List<Computadora>):
    RecyclerView.Adapter<ComputadoraAdapter.eventHolder>() {
    private var modificarClickListener: OnModificarClickListener? = null

    inner class eventHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var nombre : TextView
        var marca : TextView
        var descripcion : TextView
        var almacenamiento : TextView
        var modelo : TextView
        var noInventario : TextView
        var ram : TextView
        var serviceTag : TextView
        var ubicacion : TextView
        var procesador : TextView
        var btnEliminar : ImageButton
        var btnModificar : ImageButton

        init {
            nombre = itemView.findViewById(R.id.txtNombre)
            marca = itemView.findViewById(R.id.txtMarca)
            descripcion = itemView.findViewById(R.id.txtDescripcion)
            almacenamiento = itemView.findViewById(R.id.txtAlmacenamiento)
            modelo = itemView.findViewById(R.id.txtModelo)
            noInventario = itemView.findViewById(R.id.txtNoInventario)
            ram = itemView.findViewById(R.id.txtRam)
            serviceTag = itemView.findViewById(R.id.txtServiceTag)
            ubicacion = itemView.findViewById(R.id.txtUbicacion)
            procesador = itemView.findViewById(R.id.txtProcesador)

            btnEliminar = itemView.findViewById(R.id.btnEliminar)
            btnModificar = itemView.findViewById(R.id.btnEditar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): eventHolder {
        var itemView =  LayoutInflater.from(context).inflate(R.layout.card_computadora, parent, false)
        return eventHolder(itemView);
    }

    override fun onBindViewHolder(holder: eventHolder, position: Int) {
        var event = computadoras[position]

        holder.nombre.text = event.nombre
        holder.descripcion.text = event.descripcion
        holder.marca.text = "Marca: ${event.marca}"
        holder.almacenamiento.text = "Almacenamiento: ${event.almacenamiento.toString()}GB";
        holder.modelo.text = "Modelo: ${event.modelo}";
        holder.noInventario.text = "No inventario: ${event.noInventario}";
        holder.ram.text = "RAM: ${event.ram.toString()}GB";
        holder.serviceTag.text = "Service Tag: ${event.serviceTag}";
        holder.ubicacion.text = "Ubicación: ${event.ubicacion.toString()}";

        holder.btnEliminar.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(context)
            alertDialogBuilder.setTitle("Eliminar")
            alertDialogBuilder.setMessage("¿Estás seguro de que deseas eliminar esta computadora?")

            alertDialogBuilder.setPositiveButton("Eliminar") { _, _ ->
                lateinit var computadoraDao: ComputadoraDao
                val db = AppDatabase.getInstance(context)
                computadoraDao = db.computadoraDao()
                GlobalScope.launch {
                    computadoraDao.deleteComputadora(computadoras[position])
                }
                notifyDataSetChanged()
            }
            alertDialogBuilder.setNegativeButton("Cancelar", null)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }

        holder.btnModificar.setOnClickListener {
            modificarClickListener?.onModificarClick(computadoras[position])
        }

    }

    override fun getItemCount(): Int {
        return computadoras.size
    }

    public fun setOnModificarClickListener(listener: CompuFragment) {
        modificarClickListener = listener
    }
}