package com.johans.hotelelguadualapp.ui.registro

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.johans.hotelelguadualapp.R
import com.johans.hotelelguadualapp.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_registro.*
import java.text.SimpleDateFormat
import java.util.*

class RegistroActivity : AppCompatActivity() {

    companion object {
        private const val EMPTY = ""
        private const val SPACE = " "
        private const val CITY_S = "Seleccione una ciudad"
        private const val CITY_E = "Choose city"
    }

    private var fechaNacimiento: String = ""
    private var cal = Calendar.getInstance()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        Log.d("Método","onCreate")

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
            val cadena:Int = contrasena.length

            when {
                nombre.isEmpty() -> {
                    Toast.makeText(this, getString(R.string.nombres), Toast.LENGTH_LONG).show()
                    nombre_edit_text.requestFocus()
                }

                identificacion.isEmpty() -> {
                    Toast.makeText(this,getString(R.string.identificacion), Toast.LENGTH_LONG).show();
                    identificacion_edit_text.requestFocus();
                }
                correo.isEmpty() -> {
                    Toast.makeText(this, getString(R.string.correo), Toast.LENGTH_LONG).show()
                    correo_edit_text.requestFocus()
                }
                telefono.isEmpty() -> {
                    Toast.makeText(this, getString(R.string.telefono), Toast.LENGTH_LONG).show()
                    numero_edit_text.requestFocus()
                }
                contrasena.isEmpty() -> {
                    Toast.makeText(this,getString(R.string.contraseña), Toast.LENGTH_LONG).show();
                    contraseña_edit_text.requestFocus();
                }
                cadena < 6 -> {
                    Toast.makeText(this,getString(R.string.caracteres), Toast.LENGTH_LONG).show();
                    contraseña_edit_text.requestFocus();
                }
                fecha == getText(R.string.fecha_nacimiento)-> {
                    Toast.makeText(this,getString(R.string.fecha_nacimiento), Toast.LENGTH_LONG).show();
                    confirmar_edit_text.requestFocus();
                }
                contrasena != confirmar -> {
                    error_contrase_text_view.text = getText(R.string.contraseña_no_coincide)
                    confirmar_edit_text.requestFocus();
                }
                else -> {
                    error_contrase_text_view.text =
                        EMPTY
                    //respuesta_text_view.text = getString(R.string.respuesta, nombre, correo, contrasena, telefono, genero, pasatiempos,fecha, ciudaddenacimiento)

                    Toast.makeText(this,getString(R.string.exito),Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.putExtra("correo",correo)
                    intent.putExtra("contrasena",contrasena)
                    startActivity(intent)
                    finish()
                }
            }

        }
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

    fun showDatePickerDialog(view: View) {}
}