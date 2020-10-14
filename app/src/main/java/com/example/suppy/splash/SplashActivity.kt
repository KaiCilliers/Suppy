package com.example.suppy.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.repository.impl.ChatRepo
import com.example.repository.impl.MessageRepo
import com.example.suppy.App
import com.example.suppy.home.HomeActivity
import com.example.webservice.ConnectionListener
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
        val currentServerInstance = (application as App).server
        val currentDatabaseInstance = (application as App).db
        Timber.d("Current server instance: $currentServerInstance")
        Timber.d("Current datbase instance: $currentDatabaseInstance")
        /**
         * Establish an XMPP connection before launching [HomeActivity]
         */
        val connection = GlobalScope.async { currentServerInstance.login() }
        MainScope().launch {
            connection.await()
            currentServerInstance.monitor(
                ConnectionListener(
                    MessageRepo(currentDatabaseInstance.msgDao()),
                    ChatRepo(currentDatabaseInstance.chatDao())
                )
            )
            startActivity(
                Intent(applicationContext, HomeActivity::class.java)
            )
            finish()
        }
    }
}