package com.johans.hotelelguadualapp.ui.listas.habitaciones

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.johans.hotelelguadualapp.R
import com.johans.hotelelguadualapp.server.HabitacionServer
import kotlinx.android.synthetic.main.fragment_lista_habitaciones.*

class ListaHabitacionesFragment : Fragment(), HabitacionesRVAdapter.OnItemClickListener {

    var listhabitaciones: MutableList<HabitacionServer> = mutableListOf()
    private lateinit var habitacionesRVAdapter: HabitacionesRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_habitaciones, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //binding = FragmentListaBinding.bind(view)

        habitaciones_recycler_view.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        habitaciones_recycler_view.setHasFixedSize(true)

        //cargaDesdeDatabase()


        habitacionesRVAdapter =
            HabitacionesRVAdapter(
                listhabitaciones as ArrayList<HabitacionServer>,
                this@ListaHabitacionesFragment
            )

        habitaciones_recycler_view.adapter = habitacionesRVAdapter

        cargardesdeFirebase()

        habitacionesRVAdapter.notifyDataSetChanged()
    }

    fun cargardesdeFirebase() {
        val database = FirebaseDatabase.getInstance()
        val myHabitacionesRef = database.getReference("habitaciones")

        listhabitaciones.clear()

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dato: DataSnapshot in snapshot.children) {
                    val habitacionServer = dato.getValue(HabitacionServer::class.java)
                    habitacionServer?.let { listhabitaciones.add(it) }
                }
                habitacionesRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        }
        myHabitacionesRef.addValueEventListener(postListener)
    }

    override fun onItemClick(habitacion: HabitacionServer) {
        val action = ListaHabitacionesFragmentDirections.actionNavInfoToReservaFragment(habitacion)
        findNavController().navigate(action)
    }
}