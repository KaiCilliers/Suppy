package com.example.repository

import com.example.database.LocalDatabase
import com.example.models.chat.EntityChat
import com.example.models.message.EntityMessage
import timber.log.Timber

class MessageRepo {
    private val dao = LocalDatabase.justgetinstance().messageDao()
    fun messages(): List<EntityChat>{
        Timber.d("Repo fetch all messages...")
        return emptyList()
    }
    suspend fun insert(message: EntityMessage) {
        dao.insert(message)
    }

    /**
     * Fetch messages without LiveData wrapper for
     * debugging purposes
     * TODO temp
     */
    suspend fun justMessages(): List<EntityMessage> {
        Timber.d("Message REpo called to fetch local messages...")
        return dao.justAll()
    }
}