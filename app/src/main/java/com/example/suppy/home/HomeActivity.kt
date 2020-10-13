package com.example.suppy.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.suppy.R
import com.example.suppy.splash.ClassA
import com.example.suppy.splash.ClassB
import timber.log.Timber

/**
 * Activity used with navigation graph to
 * provide a UI flow with fragments. [HomeActivity]
 * is the main UI flow with [ChatsFragment] and
 * [ChatMessagesFragment]
 */
class HomeActivity : AppCompatActivity() {
    val parcel: ClassA by lazy { intent.getParcelableExtra("myparcel") as ClassA }
    val cereal: ClassB by lazy { intent.getSerializableExtra("mycereal") as ClassB }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Timber.d("Parcel: ${parcel.color}")
        Timber.d("Cereal: ${cereal.color}")
    }
}