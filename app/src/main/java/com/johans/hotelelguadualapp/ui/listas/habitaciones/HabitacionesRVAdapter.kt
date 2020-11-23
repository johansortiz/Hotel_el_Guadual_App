package com.johans.hotelelguadualapp.ui.listas.habitaciones

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johans.hotelelguadualapp.R
import com.johans.hotelelguadualapp.server.HabitacionServer
import kotlinx.android.synthetic.main.habitaciones_item.view.*

class HabitacionesRVAdapter(var habitacionesList: ArrayList<HabitacionServer>) :
    RecyclerView.Adapter<HabitacionesRVAdapter.HabitacionesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitacionesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.habitaciones_item, parent, false)
        return HabitacionesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HabitacionesViewHolder, position: Int) {
        val habitacion = habitacionesList[position]
        holder.bindHabitacion(habitacion)
    }

    override fun getItemCount(): Int {
        return habitacionesList.size
    }

    class HabitacionesViewHolder(itenView: View) : RecyclerView.ViewHolder(itenView) {

        //private val binding = DeudoresItemBinding.bind(itenView)

        fun bindHabitacion(habitacion: HabitacionServer) {
            itemView.id_habitacion.text = habitacion.id.toString()
            itemView.descripcion.text = habitacion.descripcion
            itemView.estado.text = habitacion.estado
            itemView.valorprod.text = habitacion.valor.toString()
        }
    }
}