package com.example.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.models.chat.EntityChat
import com.example.models.contact.EntityContact
import com.example.models.message.EntityMessage

/**
 * Local storage for chatrooms and messages
 */
//@Database(entities = [
//    EntityChat::class, EntityMessage::class, EntityContact::class
//], version = 1, exportSchema = true)
//abstract class LocalDatabase  : RoomDatabase() {
//    companion object {
//
//    }
//}