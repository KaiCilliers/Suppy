package com.example.repository

import androidx.lifecycle.LiveData
import com.example.database.LocalDatabase
import com.example.models.message.EntityMessage
import com.example.models.message.UpdateMessageReceived
import timber.log.Timber

class MessageRepo {
    private val dao = LocalDatabase.justgetinstance().messageDao()
    fun messages(): LiveData<List<EntityMessage>>{
        Timber.d("Repo fetch all messages...")
        return dao.all()
    }
    suspend fun insert(message: EntityMessage) {
        dao.insert(message)
    }

    fun latestMessage(): LiveData<EntityMessage> {
      return dao.latestMessage()
    }

    /**
     * Update description of chat item using an
     * updated partial object entity
     */
    suspend fun updateReceivedValue(updated: UpdateMessageReceived) {
        Timber.d("Partial message object: $updated")
        dao.updateReceived(updated)
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