package com.johans.hotelelguadualapp.ui.listas.habitaciones

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johans.hotelelguadualapp.R
import com.johans.hotelelguadualapp.server.HabitacionServer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.habitaciones_item.view.*
import java.text.NumberFormat
import java.util.*

class HabitacionesRVAdapter(
    var habitacionesList: ArrayList<HabitacionServer>,
    val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<HabitacionesRVAdapter.HabitacionesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitacionesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.habitaciones_item, parent, false)
        return HabitacionesViewHolder(itemView, onItemClickListener)
    }

    override fun onBindViewHolder(holder: HabitacionesViewHolder, position: Int) {
        val habitacion = habitacionesList[position]
        holder.bindHabitacion(habitacion)
    }

    override fun getItemCount(): Int {
        return habitacionesList.size
    }

    class HabitacionesViewHolder(
        itenView: View,
        var onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itenView) {

        //private val binding = DeudoresItemBinding.bind(itenView)

        @SuppressLint("SetTextI18n")
        fun bindHabitacion(habitacion: HabitacionServer) {
            itemView.id_habitacion.text = habitacion.id.toString()
            itemView.descripcion.text = habitacion.descripcion
            itemView.estado.text = habitacion.estado
            val valor = habitacion.valor?.toInt()
            val str: String = NumberFormat.getNumberInstance(Locale.US).format(valor)
            itemView.valorprod.text = "$ $str"
            itemView.estado_text_view.text = habitacion.estado.toString()
            itemView.btn_reserva.setOnClickListener {
                onItemClickListener.onItemClick(habitacion)
            }

            if (itemView.estado_text_view.text == "Disponible" || itemView.estado_text_view.text == "disponible") {
                itemView.estado_text_view.setTextColor(Color.parseColor("#088513"))
            } else {
                itemView.estado_text_view.setTextColor(Color.RED)
            }
            Picasso.get().load(habitacion.foto).into(itemView.foto_image_view)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(habitacion: HabitacionServer)
    }
}