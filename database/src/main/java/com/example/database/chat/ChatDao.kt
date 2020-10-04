package com.example.database.chat

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.database.BaseDao
import com.example.models.chat.EntityChat
import timber.log.Timber

@Dao
abstract class ChatDao : BaseDao<EntityChat> {
    /**
     * Get all data from Chat table ordered by chat name
     */
    @Query("SELECT * FROM table_chat ORDER BY chat_name DESC")
    abstract fun all(): List<EntityChat>

    /**
     * Clear table of all records
     */
    @Query("DELETE FROM table_chat")
    abstract fun clear()

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
    open fun replaceAll(chats: List<EntityChat>){
        Timber.d("DAO - calling replace all transaction...")
        clear()
        // The [*] is the spread operator that converts arraylist to vararg
        insert(*chats.toTypedArray())
    }
}