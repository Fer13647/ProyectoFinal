package com.ferr.tienda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferr.tienda.databinding.FragmentCompuBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CompuFragment : Fragment(), OnModificarClickListener {

    private lateinit var binding : FragmentCompuBinding
    lateinit var computadoraDao: ComputadoraDao
    var computadoras : List<Computadora> = listOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getInstance(requireContext().applicationContext)
        computadoraDao = db.computadoraDao()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCompuBinding.inflate(inflater,container,false)
        refreshDataBase()
        cargarRecycler()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        refreshDataBase()
        cargarRecycler()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnAgregar.setOnClickListener {
            findNavController().navigate(R.id.formComputadoraFragment, Bundle().apply {
                putInt("idComputadora",-1)
            })
        }

        binding.btnRefrescar.setOnClickListener {
            refreshDataBase()
            cargarRecycler()
            Toast.makeText(context, "Refrescando...", Toast.LENGTH_SHORT).show()
        }

        refreshDataBase()
        cargarRecycler()
    }


    private fun cargarRecycler(){
        binding.rvComputadoras.layoutManager = LinearLayoutManager(binding.root.context)
        var adapter = ComputadoraAdapter(binding.root.context, computadoras)
        adapter.setOnModificarClickListener(this)
        binding.rvComputadoras.adapter = adapter;

    }

    private fun refreshDataBase(){
        GlobalScope.launch {
            computadoras = computadoraDao.getAllComputadoras()
        }
    }

    override fun onModificarClick(computadora: Computadora) {
        findNavController().navigate(R.id.formComputadoraFragment, Bundle().apply {
            putInt("idComputadora",computadora.idComputadora)
        })

        Toast.makeText(context, "${computadora.nombre}", Toast.LENGTH_SHORT).show()
    }
}