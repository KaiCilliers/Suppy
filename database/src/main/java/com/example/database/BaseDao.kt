package com.example.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

/**
 * Interface for generic DAO operations
 * TODO make all dao functions suspend functions
 */
interface BaseDao<T> {
    @Insert
    fun insert(obj: T)
    @Insert
    fun insert(vararg obj: T)
    @Update
    fun update(obj: T)
    @Delete
    fun delete(obj: T)
}