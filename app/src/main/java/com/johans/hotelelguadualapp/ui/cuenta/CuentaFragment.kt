package com.johans.hotelelguadualapp.ui.cuenta

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.os.Bundle
import android.text.method.KeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.johans.hotelelguadualapp.R
import com.johans.hotelelguadualapp.server.usuarioserver
import com.johans.hotelelguadualapp.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_cuenta.*


class CuentaFragment : Fragment() {
    private lateinit var auth: FirebaseAuth
    private var idUsuario: String? = ""
    private var listenernombre: KeyListener? = null
    private var listeneridentificacion: KeyListener? = null
    private var listenernumero: KeyListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cuenta, container, false)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buscarenFirebase()
        button_cerrar_sesion.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        edtar_textview.setOnClickListener {
            nombre_edit_text.keyListener = listenernombre
            identificacion_edit_text.keyListener = listeneridentificacion
            numero_edit_text.keyListener = listenernumero
            nombre_edit_text.requestFocus()
            btnactualizar.visibility = View.VISIBLE
        }

        btnactualizar.setOnClickListener {
            val nombre = nombre_edit_text.text.toString()
            val identifiacion = identificacion_edit_text.text.toString()
            val numero = numero_edit_text.text.toString()
            val correo = correo_edit_text.text.toString()
            actualizarEnFirebase(nombre, identifiacion, numero, correo)
            btnactualizar.visibility = View.INVISIBLE
        }

    }

    private fun actualizarEnFirebase(
        nombre: String,
        identificacion: String,
        numero: String,
        correo: String
    ) {
        val database = FirebaseDatabase.getInstance()
        val myUsuarioRef = database.getReference("usuarios")

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data: DataSnapshot in snapshot.children) {
                    val uid = auth.currentUser?.uid
                    val Usuarioserver = data.getValue(usuarioserver::class.java)
                    if (Usuarioserver?.id.equals(uid)) {
                        idUsuario = Usuarioserver?.id
                    }
                    if (idUsuario == uid) {
                        val childUpdates = HashMap<String, Any>()
                        childUpdates["nombre"] = nombre
                        childUpdates["identificacion"] = identificacion
                        childUpdates["telefono"] = numero
                        childUpdates["email"] = correo
                        idUsuario?.let { myUsuarioRef.child(it).updateChildren(childUpdates) }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }


        myUsuarioRef.addValueEventListener(postListener)

    }

    private fun buscarenFirebase() {
        val database = FirebaseDatabase.getInstance()
        val myUsuarioRef = database.getReference("usuarios")

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data: DataSnapshot in snapshot.children) {
                    val uid = auth.currentUser?.uid
                    val Usuarioserver = data.getValue(usuarioserver::class.java)
                    if (Usuarioserver?.id.equals(uid)) {
                        nombre_edit_text.setText(Usuarioserver?.nombre)
                        listenernombre = nombre_edit_text.keyListener
                        nombre_edit_text.keyListener = null
                        identificacion_edit_text.setText(Usuarioserver?.identificacion)
                        listeneridentificacion = identificacion_edit_text.keyListener
                        identificacion_edit_text.keyListener = null
                        numero_edit_text.setText(Usuarioserver?.telefono)
                        listenernumero = numero_edit_text.keyListener
                        numero_edit_text.keyListener = null
                        correo_edit_text.setText(Usuarioserver?.email)
                        correo_edit_text.keyListener = null
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        myUsuarioRef.addValueEventListener(postListener)
    }


}