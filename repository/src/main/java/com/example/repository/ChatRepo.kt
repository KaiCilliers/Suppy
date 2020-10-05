package com.example.repository

import androidx.lifecycle.LiveData
import com.example.database.LocalDatabase
import com.example.models.chat.EntityChat
import timber.log.Timber

class ChatRepo() {
    private val dao = LocalDatabase.justgetinstance().chatDao()
    suspend fun insert(chat: EntityChat) {
        dao.insert(chat)
    }
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