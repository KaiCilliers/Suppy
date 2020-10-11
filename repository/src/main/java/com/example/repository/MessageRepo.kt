package com.example.repository

import androidx.lifecycle.LiveData
import com.example.database.LocalDatabase
import com.example.models.message.EntityMessage
import com.example.models.message.UpdatedReceived
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
     * Get all unreceived messages wrapped in LiveData
     */
    fun getAllUnreceivedLiveData(): LiveData<List<EntityMessage>> {
        return dao.getAllUnReceivedLiveData()
    }

    /**
     * Return all unreceived messages as just a list
     * TODO temp
     */
    fun getAllUnreceived(): List<EntityMessage> {
        return dao.getAllUnReceived()
    }

    /**
     * Return all the messages from a specific chat
     */
    fun allMessagesFrom(chatName: String): LiveData<List<EntityMessage>>{
        Timber.d("Repo fetching all messages form $chatName")
        return dao.allMessagesFrom(chatName)
    }

    /**
     * Update description of chat item using an
     * updated partial object entity
     */
    suspend fun updateReceivedValue(updated: UpdatedReceived) {
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

    /**
     * Set all messages in specific chat as received
     */
    suspend fun updateAllMessagesFromChatReceived(chatName: String) {
        Timber.d("Updating all messages from \"$chatName\" to received")
        dao.updateAllReceivedFromChat(chatName, true)
    }
}