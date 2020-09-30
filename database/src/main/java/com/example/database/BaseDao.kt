package com.example.database

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

/**
 * Interface to share common operations among all
 * the DAOs
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