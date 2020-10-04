package com.example.repository

import android.content.Context
import com.example.database.LocalDatabase
import com.example.models.RosterEntry
import com.example.models.chat.EntityChat
import com.example.repository.webservicemodule.Server
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import timber.log.Timber

class ChatRepo() {
    private val dao = LocalDatabase.justgetinstance().chatDao()
    fun chats(): List<EntityChat>{
        Timber.d("Repo fetch all chats...")
        return dao.all()
    }
    fun repopulate(chats: ArrayList<EntityChat>) {
        Timber.d("REPO - calling DAO replace all...")
        dao.replaceAll(chats)
    }
}