package com.example.suppy.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.database.LocalDatabase
import com.example.repository.ChatRepo
import com.example.repository.webservicemodule.Server
import com.example.suppy.home.HomeActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Simple splash screen that immediately loads [HomeActivity]
 * The splash screen background/theme is set in Manifest
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val server = GlobalScope.async {
            Timber.d("Splash server init thread = ${Thread.currentThread().name}")
            Server.instance()
        }
        val db = GlobalScope.async {
            Timber.d("Splash db init thread = ${Thread.currentThread().name}")
            LocalDatabase.instance(applicationContext)
        }
        val x = MainScope().launch {
            Timber.d("server = ${server.await()} thread running on ${Thread.currentThread().name}")
            Timber.d("db = ${db.await()} thread running on ${Thread.currentThread().name}")
            val local = async {
                Timber.d("Splash fetch to check db chats thread = ${Thread.currentThread().name}")
                ChatRepo.instance(LocalDatabase.justgetinstance().chatDao()).chats()
            }
            Timber.d("chats = ${local.await()} thread running on ${Thread.currentThread().name}")
            Timber.d("OK cool - all done go launch the home activity!")
            startActivity(
                Intent(
                    applicationContext,
                    HomeActivity::class.java
                )
            )
            finish()
        }
    }
}