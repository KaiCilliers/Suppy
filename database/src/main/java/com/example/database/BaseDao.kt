package com.example.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

/**
 * Interface for generic DAO operations
 */
interface BaseDao<T> {
    @Insert
    suspend fun insert(obj: T)
    @Insert
    suspend fun insert(vararg obj: T)
    @Update
    suspend fun update(obj: T)
    @Delete
    suspend fun delete(obj: T)
}