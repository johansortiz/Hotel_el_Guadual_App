@file:Suppress("UNNECESSARY_NOT_NULL_ASSERTION")

package com.johans.hotelelguadualapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.johans.hotelelguadualapp.R
import com.johans.hotelelguadualapp.ui.login.LoginActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var datos_recibidos_principal = intent.extras
        val correo_recibido = datos_recibidos_principal?.getString("correo_main")
        val correo=correo_recibido
        respuesta_main_text_view.text=correo.toString()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.overflow_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean{
       when(item.itemId){
            R.id.salir_overflow -> {
                var datos_recibidos_ppal = intent.extras
                val correo_r = datos_recibidos_ppal?.getString("correo_main")
                val contras_r = datos_recibidos_ppal?.getString("contra_main")
                val correo_aux=correo_r
                val conta_aux=contras_r
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("correo",correo_aux)
                intent.putExtra("contrasena",conta_aux)
                startActivity(intent)
                finish()
            }

       }
        return super .onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        var datos_recibidos_ppal = intent.extras
        val correo_pressed = datos_recibidos_ppal?.getString("correo_main")
        val contras_pressed = datos_recibidos_ppal?.getString("contra_main")
        val correo_aux_p=correo_pressed
        val conta_aux_p=contras_pressed
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("correo",correo_aux_p)
        intent.putExtra("contrasena",conta_aux_p)
        startActivity(intent)
        finish()
    }

    }



