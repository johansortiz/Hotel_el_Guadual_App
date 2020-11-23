package com.johans.hotelelguadualapp.ui.listas.productos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.johans.hotelelguadualapp.R
import com.johans.hotelelguadualapp.server.ProductosServer
import kotlinx.android.synthetic.main.fragment_lista_productosk.*


class ListaProductoskFragment : Fragment() {

    var listproducto: MutableList<ProductosServer> = mutableListOf()
    private lateinit var productoRVAdapter: ProductosRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_productosk, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        producto_recycler_view.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        producto_recycler_view.setHasFixedSize(true)


        productoRVAdapter = ProductosRVAdapter(listproducto as ArrayList<ProductosServer>)

        producto_recycler_view.adapter = productoRVAdapter

        cargardesdeFirebase()

        productoRVAdapter.notifyDataSetChanged()
    }

    fun cargardesdeFirebase() {
        val database = FirebaseDatabase.getInstance()
        val myproductosRef = database.getReference("productos")

        listproducto.clear()

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dato: DataSnapshot in snapshot.children) {
                    val productoServer = dato.getValue(ProductosServer::class.java)
                    productoServer?.let { listproducto.add(it) }
                }
                productoRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        }
        myproductosRef.addValueEventListener(postListener)
    }

}