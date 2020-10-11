package com.example.suppy

import android.app.Application
import org.jivesoftware.smack.SmackConfiguration
import timber.log.Timber

/**
 * Base class of the app
 *
 * Logging library initialised here
 * TODO modularise your application by layer until app is promising and shows promise, then migrate to a modularise by layer/feature hybrid
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.v("App - onCreate...")
        smackDebugger()
        Timber.v("Smack debugger setup...")
    }
    private fun smackDebugger(){
        // Smack Debugger?
        System.setProperty("smack.debuggerClass","org.jivesoftware.smack.debugger.ConsoleDebugger")
        System.setProperty("smack.debugEnabled", "true")
        SmackConfiguration.DEBUG = true
    }
}