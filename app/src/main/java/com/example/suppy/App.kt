package com.example.suppy

import android.app.Application
import com.example.webservice.impl.DefaultConfiguration
import com.example.webservice.impl.BaseServer
import com.example.webservice.Server
import org.jivesoftware.smack.SmackConfiguration
import timber.log.Timber

/**
 * Base class of the app
 *
 * Logging library initialised here
 * TODO modularise your application by layer until app is promising and shows promise, then migrate to a modularise by layer/feature hybrid
 */
class App : Application() {
    /**
     * The server object is created here to allow
     * activities and fragments to access the same
     * instance without having to create a singleton.
     * Passing the server object via [Parcelable]
     * or [Serializable] is not possible due to
     * [XMPPTCPConnection] does not implement
     * either interfaces
     */
    val server: Server by lazy {
        BaseServer(
            DefaultConfiguration()
        )
    }
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