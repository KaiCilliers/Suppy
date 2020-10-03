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
    abstract fun all(): List<EntityMessage>
    /**
     * Clear table of all records
     */
    @Query("DELETE FROM table_message")
    /**
     * Replace table data with new data
     */
    abstract fun clear()
    open fun repllaceAll(contacts: List<EntityMessage>){
        clear()
        insert(*contacts.toTypedArray())
    }
}