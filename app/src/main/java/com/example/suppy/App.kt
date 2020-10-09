package com.example.suppy

import android.app.Application
import com.example.database.LocalDatabase
import com.example.repository.ChatRepo
import com.example.repository.ContactRepo
import com.example.repository.MessageRepo
import com.example.repository.webservicemodule.Server
import kotlinx.coroutines.*
import org.jivesoftware.smack.SmackConfiguration
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/**
 * Base class of the app
 *
 * Logging library initialised here
 * TODO modularise your application by layer until app is promising and shows promise, then migrate to a modularise by layer/feature hybrid
 */
class App : Application() {
    private val chatRepo: ChatRepo
        get() = ChatRepo.instance(LocalDatabase.justgetinstance().chatDao())
    private val messageRepo: MessageRepo
        get() = MessageRepo()
    private val contactRepo: ContactRepo
        get() = ContactRepo()
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        Timber.v("App - onCreate...")
        smackDebugger()
        Timber.v("Smack debugger setup...")
    }
    fun test() {
        /**
         * Database is init here for testing purposes (passing context is a pain)
         */
        val db = LocalDatabase.instance(applicationContext)
        Timber.d("Database setup complete - ${LocalDatabase.isInit()} && $db")
        chatRepo.chats()
        messageRepo.messages()
        contactRepo.contacts()
    }
    fun smackDebugger(){
        // Smack Debugger?
        System.setProperty("smack.debuggerClass","org.jivesoftware.smack.debugger.ConsoleDebugger")
        System.setProperty("smack.debugEnabled", "true")
        SmackConfiguration.DEBUG = true
    }
}