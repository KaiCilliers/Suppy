package com.example.database.message

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
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
     * Returns messages from database without LiveData wrapper
     * for debugging purposes
     * TODO temp
     */
    @Query("SELECT * FROM table_message ORDER BY from_name DESC")
    abstract suspend fun justAll(): List<EntityMessage>
    /**
     * Replace table data with new data
     */
    @Transaction
    open suspend fun repllaceAll(contacts: List<EntityMessage>){
        clear()
        insert(*contacts.toTypedArray())
    }
}