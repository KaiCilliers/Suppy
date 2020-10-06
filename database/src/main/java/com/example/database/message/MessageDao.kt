package com.example.database.message

import androidx.room.Dao
import androidx.room.Query
import com.example.database.BaseDao
import com.example.models.message.EntityMessage

@Dao
abstract class MessageDao : BaseDao<EntityMessage>{
    /**
     * Get all data from Message table ordered by contact name
     */
    @Query("SELECT * FROM table_message ORDER BY body DESC")
    abstract suspend fun all(): List<EntityMessage>
    /**
     * Clear table of all records
     */
    @Query("DELETE FROM table_message")
    abstract suspend fun clear()
    /**
     * Replace table data with new data
     */
    suspend fun repllaceAll(contacts: List<EntityMessage>){
        clear()
        insert(*contacts.toTypedArray())
    }
}