package com.johans.hotelelguadualapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.johans.hotelelguadualapp.R
import com.johans.hotelelguadualapp.ui.main_cliente.Main_Cliente_Activity
import com.johans.hotelelguadualapp.ui.registro.RegistroActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        registro_login_btn.setOnClickListener {
            goToResgistroActivity()
        }
        

        login_btn.setOnClickListener{
            val correo_login = email_login_edit_text.text.toString()
            val contrasena_login = passwor_login_edit_text.text.toString()

            when {
                correo_login.isEmpty() -> {
                    email_login_edit_text.error = getString(R.string.login_correo)
                    email_login_edit_text.requestFocus()
                }
                contrasena_login.isEmpty() -> {
                    passwor_login_edit_text.error = getString(R.string.login_contrasena)
                    passwor_login_edit_text.requestFocus()
                }
                else -> {
                    loginWithFirebase(correo_login, contrasena_login)
                }
            }
        }
    }

    private fun loginWithFirebase(correoLogin: String, contrasenaLogin: String) {
        auth.signInWithEmailAndPassword(correoLogin, contrasenaLogin)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    goMainActivity()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Usuario o contraseña incorrectos",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun goMainActivity() {
        val intent = Intent(this, Main_Cliente_Activity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToResgistroActivity() {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
        finish()
    }
}