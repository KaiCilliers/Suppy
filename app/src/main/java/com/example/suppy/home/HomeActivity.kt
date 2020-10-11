package com.example.suppy.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.suppy.R

/**
 * Activity used with navigation graph to
 * provide a UI flow with fragments. [HomeActivity]
 * is the main UI flow with [ChatsFragment] and
 * [ChatMessagesFragment]
 */
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}