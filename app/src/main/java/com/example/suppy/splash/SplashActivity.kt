package com.example.suppy.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.database.LocalDatabase
import com.example.repository.ChatRepo
import com.example.suppy.App
import com.example.suppy.home.HomeActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import timber.log.Timber
import java.io.Serializable

/**
 * Simple splash screen that immediately loads [HomeActivity]
 * The splash screen background/theme is set in Manifest
 * TODO cleanup code
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Server and database setup is performed here while splash screen
         * is displayed to the user.
         */
        val currentServerInstance = (application as App).server
        Timber.d("Current server instance: $currentServerInstance")
        val server = GlobalScope.async { currentServerInstance.login() }
        val db = GlobalScope.async { LocalDatabase.instance(applicationContext) }
        MainScope().launch {
            currentServerInstance.monitor()
            Timber.d("server = ${server.await()} thread running on ${Thread.currentThread().name}")
            Timber.d("db = ${db.await()} thread running on ${Thread.currentThread().name}")
            val local = async {
                Timber.d("Splash fetch to check db chats thread = ${Thread.currentThread().name}")
                ChatRepo.instance(LocalDatabase.justgetinstance().chatDao()).chats()
            }
            Timber.d("chats = ${local.await()} thread running on ${Thread.currentThread().name}")
            Timber.d("OK cool - all done go launch the home activity!")
            val intent = Intent(applicationContext, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}