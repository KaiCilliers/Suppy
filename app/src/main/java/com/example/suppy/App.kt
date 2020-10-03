package com.example.suppy

import android.app.Application
import com.example.repository.ChatRepo
import com.example.repository.ContactRepo
import com.example.repository.MessageRepo
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
        Timber.v("Repo Testing bitches!")
        test()
    }
    fun test() {
        chatRepo.chats()
        messageRepo.messages()
        contactRepo.contacts()
    }
}