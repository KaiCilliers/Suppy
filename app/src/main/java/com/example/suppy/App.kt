package com.example.suppy

import android.app.Application
import timber.log.Timber

/**
 * Base class of the app
 *
 * Logging library initialised here
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.v("App - onCreate...")
    }
}