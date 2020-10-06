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
    /**
     * Fetch chats without LiveData wrapper for
     * debugging purposes
     * TODO temp
     */
    suspend fun justChats(): List<EntityChat> {
        Timber.d("ChatRepo called to fetch local chats...")
        return dao.justAll()
    }
    /**
     * Delete a row by id (currently used for debugging)
     * TODO temp
     */
    suspend fun deleteRow(name: String) {
        Timber.d("REPO - going to delete chat record with name $name")
        dao.deleteByName(name)
    }
    suspend fun repopulate(chats: ArrayList<EntityChat>) {
        Timber.d("Repopulate with $chats")
        Timber.d("REPO - calling DAO replace all...")
        dao.replaceAll(chats)
    }
}