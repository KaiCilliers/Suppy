package com.example.database.chat

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.database.BaseDao
import com.example.models.chat.EntityChat
import com.example.models.chat.UpdateChatDescription
import timber.log.Timber

@Dao
abstract class ChatDao : BaseDao<EntityChat> {
    /**
     * Update chat table's description by using a
     * partial chat entity with updated fields
     */
    @Update(entity = EntityChat::class)
    abstract suspend fun updateChatDescription(update: UpdateChatDescription)

    /**
     * Return the id of the chat matching name parameter
     */
    @Query("SELECT chat_id FROM table_chat WHERE chat_name = :name")
    abstract suspend fun getIdOf(name: String): Int

    /**
     * Get all data from Chat table ordered by chat name
     */
    @Query("SELECT * FROM table_chat ORDER BY chat_name DESC")
    abstract fun all(): LiveData<List<EntityChat>>

    /**
     * Returns chats from database without LiveData wrapper
     * for debugging purposes
     * TODO temp
     */
    @Query("SELECT * FROM table_chat ORDER BY chat_name DESC")
    abstract suspend fun justAll(): List<EntityChat>
    /**
     * Delete a record by id, used for debugging currently
     * TODO temp
     */
    @Query("DELETE FROM table_chat WHERE chat_name = :name")
    abstract suspend fun deleteByName(name: String)
    /**
     * Clear table of all records
     */
    @Query("DELETE FROM table_chat")
    abstract suspend fun clear()

    /**
     * Replace table data with new data
     * Transaction will only succeed if all
     * operations are executed successfully
     *
     * This prevents leaving the table empty
     * if an error occurs when attempting to
     * add the new data
     */
    @Transaction
    open suspend fun replaceAll(chats: List<EntityChat>){
        Timber.d("DAO - calling replace all transaction... with data $chats")
        clear()
        // The [*] is the spread operator that converts arraylist to vararg
        insert(*chats.toTypedArray())
    }
}
