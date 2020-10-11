package com.example.suppy.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.database.LocalDatabase
import com.example.repository.ChatRepo
import com.example.suppy.home.HomeActivity
import com.example.webservice.Server
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
        /**
         * Server and database setup is performed here while splash screen
         * is displayed to the user.
         */
        val server = GlobalScope.async { Server.instance() }
        val db = GlobalScope.async { LocalDatabase.instance(applicationContext) }
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