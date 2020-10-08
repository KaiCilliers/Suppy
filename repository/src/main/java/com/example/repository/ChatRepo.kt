package com.example.repository

import androidx.lifecycle.LiveData
import com.example.database.LocalDatabase
import com.example.models.chat.EntityChat
import com.example.models.chat.UpdateChatDescription
import timber.log.Timber

class ChatRepo {
    private val dao = LocalDatabase.justgetinstance().chatDao()

    /**
     * Checks if the chat table contains
     * any data
     */
    suspend fun isEmpty(): Boolean {
        dao.getAnyRow()?.let { return false }
        return true
    }

    /**
     * Insert a chat records
     */
    suspend fun insert(chat: EntityChat) {
        dao.insert(chat)
    }

    /**
     * Get chat ID of a chat based on name
     * used primarily to update that chat
     */
    suspend fun getChatIdOf(name: String): Int {
        Timber.d("Cool Cool, Cool, Cool Cool")
        return dao.getIdOf(name)
    }

    /**
     * Return all chats wrapped in LiveData
     */
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

    /**
     * Update description of chat item using an
     * updated partial object
     */
    suspend fun updateDescriptionOf(chatId: Int, message: String) {
        val updateChat = UpdateChatDescription(chatId, message)
        Timber.d("Partial chat object: $updateChat")
        dao.updateChatDescription(updateChat)
    }
}