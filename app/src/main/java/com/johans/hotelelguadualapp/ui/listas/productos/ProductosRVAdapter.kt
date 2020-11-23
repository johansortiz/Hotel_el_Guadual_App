package com.johans.hotelelguadualapp.ui.listas.productos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.johans.hotelelguadualapp.R
import com.johans.hotelelguadualapp.server.ProductosServer
import kotlinx.android.synthetic.main.productos_item.view.*

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


        fun bindProductos(producto: ProductosServer) {
            itemView.id_producto.text = producto.id.toString()
            itemView.descripcionproducto.text = producto.descripcion
            itemView.cantidad.text = producto.cantidad.toString()
            itemView.valorprod.text = producto.valor.toString()
        }
    }

}