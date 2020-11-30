package com.johans.hotelelguadualapp.ui.listas.productos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johans.hotelelguadualapp.R
import com.johans.hotelelguadualapp.server.ProductosServer
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.productos_item.view.*
import java.text.NumberFormat
import java.util.*

class ProductosRVAdapter(var productosList: ArrayList<ProductosServer>) :
    RecyclerView.Adapter<ProductosRVAdapter.ProductosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductosViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.productos_item, parent, false)
        return ProductosViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductosViewHolder, position: Int) {
        val producto = productosList[position]
        holder.bindProductos(producto)
    }

    override fun getItemCount(): Int {
        return productosList.size
    }

    class ProductosViewHolder(itenView: View) : RecyclerView.ViewHolder(itenView) {


        @SuppressLint("SetTextI18n")
        fun bindProductos(producto: ProductosServer) {
            itemView.descripcionproducto.text = producto.descripcion
            itemView.cantidad.text = producto.cantidad.toString()
            val valor = producto.valor?.toInt()
            val str: String = NumberFormat.getNumberInstance(Locale.US).format(valor)
            itemView.valorprod.text = "$ $str"
            Picasso.get().load(producto.foto).into(itemView.foto_imagen_view_producto)
        }
    }

}