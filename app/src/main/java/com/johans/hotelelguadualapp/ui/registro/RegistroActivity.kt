package com.johans.hotelelguadualapp.ui.registro

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.johans.hotelelguadualapp.R
import com.johans.hotelelguadualapp.server.usuarioserver
import com.johans.hotelelguadualapp.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_registro.*
import java.text.SimpleDateFormat
import java.util.*

class RegistroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    companion object {
        private const val EMPTY = ""
        private const val SPACE = " "
        private const val TIPO_S = "Tipo documento"
        private const val TIPO_E = "document type"
    }

    private var fechaNacimiento: String = ""
    private var cal = Calendar.getInstance()
    private var corroaux = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        auth = FirebaseAuth.getInstance()

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfmonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfmonth)

                val format = "MM/dd/yyyy"
                val sdf = SimpleDateFormat(format, Locale.US)
                fechaNacimiento = sdf.format(cal.time).toString()
                btn_show.text = fechaNacimiento
            }
        btn_show.setOnClickListener{
            DatePickerDialog(
                this,
                dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }


        registrar_button.setOnClickListener {

            val nombre = nombre_edit_text.text.toString()
            val identificacion = identificacion_edit_text.text.toString()
            val correo = correo_edit_text.text.toString()
            val telefono = numero_edit_text.text.toString()
            val contrasena = contraseña_edit_text.text.toString()
            val confirmar = confirmar_edit_text.text.toString()

            val fecha = btn_show.text.toString()
            val cadena: Int = contrasena.length
            val tipodocumento = tipo_documento_spinner.selectedItem.toString()
            val tipo_usuario = "Cliente"
            buscarenFirebase(correo)

            when {
                nombre.isEmpty() -> {
                    nombre_edit_text.error = getString(R.string.nombres)
                    nombre_edit_text.requestFocus()
                }

                tipodocumento == TIPO_S || tipodocumento == TIPO_E -> {
                    Toast.makeText(this, getString(R.string.tipo_documento), Toast.LENGTH_LONG)
                        .show()
                    tipo_documento_spinner.requestFocus()
                }

                identificacion.isEmpty() -> {
                    identificacion_edit_text.error = getString(R.string.identificacion)
                    identificacion_edit_text.requestFocus()
                }
                telefono.isEmpty() -> {
                    numero_edit_text.error = getString(R.string.telefono)
                    numero_edit_text.requestFocus()
                }
                correo.isEmpty() -> {
                    correo_edit_text.error = getString(R.string.correo)
                    correo_edit_text.requestFocus()
                }
                contrasena.isEmpty() -> {
                    contraseña_edit_text.error = getString(R.string.contraseña)
                    contraseña_edit_text.requestFocus()
                }
                cadena < 6 -> {
                    Toast.makeText(this, getString(R.string.caracteres), Toast.LENGTH_LONG).show()
                    contraseña_edit_text.requestFocus()
                }
                fecha == getText(R.string.fecha_nacimiento) -> {
                    Toast.makeText(this, getString(R.string.fecha_nacimiento), Toast.LENGTH_LONG)
                        .show()
                    confirmar_edit_text.requestFocus()
                }
                contrasena != confirmar -> {
                    error_contrase_text_view.text = getText(R.string.contraseña_no_coincide)
                    confirmar_edit_text.requestFocus()
                }
                corroaux == correo -> {
                    Toast.makeText(
                        baseContext, "Ya existe un usuario con este correo en base de datos",
                        Toast.LENGTH_SHORT
                    ).show()
                    correo_edit_text.requestFocus()
                }
                else -> {
                    error_contrase_text_view.text = EMPTY
                    registroEnFirebase(
                        correo,
                        contrasena,
                        nombre,
                        tipodocumento,
                        identificacion,
                        telefono,
                        tipo_usuario
                    )
                }
            }

        }
    }

    private fun registroEnFirebase(
        correo: String,
        contrasena: String,
        nombre: String,
        tipodocumento: String,
        identificacion: String,
        telefono: String,
        tipo_usuario: String
    ) {
        auth.createUserWithEmailAndPassword(correo, contrasena)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    crearUsuarioenBaseDatos(
                        uid,
                        nombre,
                        tipodocumento,
                        identificacion,
                        telefono,
                        correo,
                        tipo_usuario
                    )

                } else {
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun crearUsuarioenBaseDatos(
        uid: String?,
        nombre: String?,
        tipodocumento: String?,
        identificacion: String?,
        telefono: String?,
        correo: String,
        tipo_usuario: String?
    ) {
        val database = FirebaseDatabase.getInstance()
        val myUserReference = database.getReference("usuarios")

        val Usuarioserver = usuarioserver(
            uid,
            nombre,
            tipodocumento,
            identificacion,
            telefono,
            correo,
            tipo_usuario
        )
        uid?.let {
            myUserReference.child(uid).setValue(Usuarioserver)
        }
        goToLoginActivity()
    }

    private fun buscarenFirebase(correo: String) {
        val database = FirebaseDatabase.getInstance()
        val myUsuarioRef = database.getReference("usuarios")

        val postListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data: DataSnapshot in snapshot.children) {
                    val Usuarioserver = data.getValue(usuarioserver::class.java)
                    if (Usuarioserver?.email.equals(correo)) {
                        corroaux = (Usuarioserver?.email.toString())
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        myUsuarioRef.addValueEventListener(postListener)
    }

    private fun goToLoginActivity() {
        onBackPressed()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        Log.d("Método","onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Método","onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Método","onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Método","onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Método","onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("Método","onRestart")
    }
}