package com.example.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.database.LocalDatabase
import com.example.models.RosterEntry
import com.example.models.chat.EntityChat
import com.example.repository.webservicemodule.Server
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import timber.log.Timber

class ChatRepo() {
    private val dao = LocalDatabase.justgetinstance().chatDao()
    fun chats(): LiveData<List<EntityChat>>{
        Timber.d("Repo fetch all chats...")
        return dao.all()
    }
    fun repopulate(chats: ArrayList<EntityChat>) {
        Timber.d("Repopulate with $chats")
        Timber.d("REPO - calling DAO replace all...")
        dao.replaceAll(chats)
    }
}