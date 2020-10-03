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
    abstract fun all(): List<EntityContact>
    /**
     * Clear table of all records
     */
    @Query("DELETE FROM table_contact")
    /**
     * Replace table data with new data
     */
    abstract fun clear()
    open fun repllaceAll(contacts: List<EntityContact>){
        clear()
        insert(*contacts.toTypedArray())
    }
}
