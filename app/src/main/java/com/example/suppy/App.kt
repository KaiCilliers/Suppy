package com.example.suppy

import android.app.Application
import com.example.repository.ChatRepo
import com.example.repository.ContactRepo
import com.example.repository.MessageRepo
import com.example.repository.webservicemodule.Server
import org.jivesoftware.smack.SmackConfiguration
import timber.log.Timber

/**
 * Base class of the app
 *
 * Logging library initialised here
 */
class App : Application() {
    private val chatRepo: ChatRepo
        get() = ChatRepo()
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
        test()
    }
    fun test() {
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