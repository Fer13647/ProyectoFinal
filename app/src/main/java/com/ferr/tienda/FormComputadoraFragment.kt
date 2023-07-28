package com.ferr.tienda

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ferr.tienda.databinding.FragmentFormComputadoraBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.squareup.picasso.Picasso

class FormComputadoraFragment : Fragment() {

    lateinit var binding : FragmentFormComputadoraBinding;
    private lateinit var computadoraDao : ComputadoraDao
    private var idComputadora : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFormComputadoraBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        idComputadora = requireArguments().getInt("idComputadora")
        Toast.makeText(context, "ID: ${idComputadora}", Toast.LENGTH_SHORT).show()

        if(idComputadora != -1) recrearDatos(idComputadora);

        with(binding){
            btnAgregarComputadora.setOnClickListener {
                if(!verificarCampos()){
                    Toast.makeText(binding.root.context, "Uno o más campos están vacíos ¡rellene por favor!", Toast.LENGTH_LONG).show()
                    return@setOnClickListener;
                }

                MandarBaseDatosLocal();

            }
        }
    }

    fun verificarCampos() : Boolean{
        with(binding){
            var items = listOf<TextInputEditText>(editTextNombre,
                editTextAlmacenamiento,
                editTextDescripcion,
                editTextMarca,
                editTextModelo,
                editTextNoInventario,
                editTextProcesador,
                editTextRam,
                editTextServiceTag,
                editTextUbicacion,
                editTextImagen);

            if(items.any{it.text.isNullOrEmpty()}){
                return false;
            }
        }
        return true;
    }

    fun recrearDatos(idComputadora: Int){
        val db = AppDatabase.getInstance(requireContext().applicationContext)
        computadoraDao = db.computadoraDao()
        var computadora : Computadora

        GlobalScope.launch {
            computadora = computadoraDao.getDirectComputadora(idComputadora)

            withContext(Dispatchers.Main) {
                with(binding){
                    editTextNombre.setText(computadora.nombre)
                    editTextDescripcion.setText(computadora.descripcion)
                    editTextMarca.setText(computadora.marca)
                    editTextProcesador.setText(computadora.procesador)
                    editTextRam.setText(computadora.ram.toString())
                    editTextAlmacenamiento.setText(computadora.almacenamiento.toString())
                    editTextServiceTag.setText(computadora.serviceTag)
                    editTextNoInventario.setText(computadora.noInventario)
                    editTextUbicacion.setText(computadora.ubicacion.toString())
                    editTextModelo.setText(computadora.modelo)
                    editTextImagen.setText(computadora.urlImagen)
                    Picasso.get().load(computadora.urlImagen).into(imgCompu)
                }
                Toast.makeText(context, "Instancia recuperada", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnAgregarComputadora.setText("Modificar Computadora");

    }

    fun MandarBaseDatosLocal(){
        val db = AppDatabase.getInstance(requireContext().applicationContext)
        computadoraDao = db.computadoraDao()

        val nuevaComputadora = retornarComputadora()

            GlobalScope.launch {
                if(idComputadora == -1 ) computadoraDao.insertComputadora(nuevaComputadora) else computadoraDao.updateComputadora(nuevaComputadora)
            }
            Toast.makeText(context, "Se ha guardado correctamente", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack()
    }

    fun retornarComputadora() : Computadora {
        with(binding) {

            val nombre = editTextNombre.text.toString()
            val descripcion = editTextDescripcion.text.toString()
            val marca = editTextMarca.text.toString()
            val modelo = editTextModelo.text.toString()
            val procesador = editTextProcesador.text.toString()
            val ram = editTextRam.text.toString().toInt()
            val almacenamiento = editTextAlmacenamiento.text.toString().toLong()
            val serviceTag = editTextServiceTag.text.toString()
            val noInventario = editTextNoInventario.text.toString()
            val ubicacion = editTextUbicacion.text.toString().toInt()
            val urlImagen = editTextImagen.text.toString()

            if (idComputadora != -1) {
                return Computadora(
                    idComputadora = idComputadora,
                    nombre = nombre,
                    descripcion = descripcion,
                    marca = marca,
                    modelo = modelo,
                    procesador = procesador,
                    ram = ram,
                    almacenamiento = almacenamiento,
                    serviceTag = serviceTag,
                    noInventario = noInventario,
                    ubicacion = ubicacion,
                    urlImagen = urlImagen
                );
            }

            return Computadora(
                nombre = nombre,
                descripcion = descripcion,
                marca = marca,
                modelo = modelo,
                procesador = procesador,
                ram = ram,
                almacenamiento = almacenamiento,
                serviceTag = serviceTag,
                noInventario = noInventario,
                ubicacion = ubicacion,
                urlImagen = urlImagen
            )
        }
    }
}