package com.example.suppy.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.suppy.App
import com.example.suppy.R

/**
 * Activity used with navigation graph to
 * provide a UI flow with fragments. [HomeActivity]
 * is the main UI flow with [ChatsFragment] and
 * [ChatMessagesFragment]
 */
class HomeActivity : AppCompatActivity() {
    /**
     * Represents the current server instance
     * initialized in [App]. Allows fragments
     * to access the same server instance
     */
    val server by lazy { (application as App).server }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}