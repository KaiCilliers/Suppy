package com.example.database.contact

import androidx.room.Dao
import androidx.room.Query
import com.example.database.BaseDao
import com.example.models.contact.EntityContact

@Dao
abstract class ContactDao : BaseDao<EntityContact>{
    /**
     * Get all data from Contact table ordered by contact name
     */
    @Query("SELECT * FROM table_contact ORDER BY name DESC")
    abstract suspend fun all(): List<EntityContact>
    /**
     * Clear table of all records
     */
    @Query("DELETE FROM table_contact")
    abstract suspend fun clear()
    /**
     * Replace table data with new data
     */
    suspend fun repllaceAll(contacts: List<EntityContact>){
        clear()
        insert(*contacts.toTypedArray())
    }
}
