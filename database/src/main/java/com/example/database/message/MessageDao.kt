package com.example.database.message

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.database.BaseDao
import com.example.models.message.EntityMessage
import com.example.models.message.UpdateMessageReceived

@Dao
abstract class MessageDao : BaseDao<EntityMessage>{
    /**
     * Returns all messages from a specific chat wrapped in LiveData
     */
    @Query("SELECT * FROM table_message WHERE from_name = :chatName ORDER BY counter_temp ASC")
    abstract fun allMessagesFrom(chatName: String): LiveData<List<EntityMessage>>

    /**
     * Update message received value with partial entity object
     */
    @Update(entity = EntityMessage::class)
    abstract suspend fun updateReceived(update: UpdateMessageReceived)

    /**
     * Get all data from Message table ordered by contact name
     */
    @Query("SELECT * FROM table_message ORDER BY body DESC")
    abstract fun all(): LiveData<List<EntityMessage>>
    /**
     * Clear table of all records
     */
    @Query("DELETE FROM table_message")
    abstract suspend fun clear()

    /**
     * Returns messages from database without LiveData wrapper
     * for debugging purposes
     * TODO temp updated for debugging the order by timestamp results
     */
    @Query("SELECT * FROM table_message ORDER BY counter_temp DESC")
    abstract suspend fun justAll(): List<EntityMessage>

    /**
     * TODO update query to get latest entry to database - this implementation does not work
     * The sort is based on text and not actual time - thus consider
     * adding a autogenerated ID that can just be an easy indication
     * of what message was added last
     */
    @Query("SELECT * FROM table_message ORDER BY counter_temp DESC LIMIT 1")
    abstract fun latestMessage(): LiveData<EntityMessage>
    /**
     * Replace table data with new data
     */
    @Transaction
    open suspend fun repllaceAll(contacts: List<EntityMessage>){
        clear()
        insert(*contacts.toTypedArray())
    }
}