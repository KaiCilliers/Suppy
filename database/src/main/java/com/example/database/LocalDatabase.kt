package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Local storage for chatrooms and messages
 */
@Database()
abstract class LocalDatabase  : RoomDatabase() {
    companion object {

    }
}