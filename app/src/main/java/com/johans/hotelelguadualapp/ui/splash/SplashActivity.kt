package com.johans.hotelelguadualapp.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.johans.hotelelguadualapp.R
import com.johans.hotelelguadualapp.ui.login.LoginActivity
import com.johans.hotelelguadualapp.ui.main_cliente.Main_Cliente_Activity
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val timer = Timer()
        timer.schedule(
            timerTask {
                val auth = FirebaseAuth.getInstance().currentUser
                if (auth == null) {
                    goToLoginActivity()
                } else {
                    goTomaincliente()
                }
            }, 2000
        )

    }

    fun goToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun goTomaincliente() {
        val intent = Intent(this, Main_Cliente_Activity::class.java)
        startActivity(intent)
        finish()
    }
}