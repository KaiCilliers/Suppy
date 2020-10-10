package com.example.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.database.chat.ChatDao
import com.example.database.contact.ContactDao
import com.example.database.message.MessageDao
import com.example.models.chat.EntityChat
import com.example.models.contact.EntityContact
import com.example.models.message.EntityMessage
import timber.log.Timber


/**
 * Local storage
 */
@Database(entities = [
    EntityChat::class, EntityMessage::class, EntityContact::class
], version = 5, exportSchema = true)
abstract class LocalDatabase  : RoomDatabase() {
    /**
     * Access points
     */
    abstract fun chatDao(): ChatDao
    abstract fun messageDao(): MessageDao
    abstract fun contactDao(): ContactDao

    /**
     * Local database instance access
     */
    companion object {
        @Volatile
        private lateinit var INSTANCE: LocalDatabase
        fun isInit(): Boolean{
            return ::INSTANCE.isInitialized
        }
        fun justgetinstance(): LocalDatabase{
            return INSTANCE
        }
        fun instance(context: Context): LocalDatabase {
            synchronized(LocalDatabase::class.java) {
                if(!::INSTANCE.isInitialized) {
                    Timber.d("Creating database...")
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        LocalDatabase::class.java,
                        "suppydatabase"
                    ).fallbackToDestructiveMigration().build()
                }
                Timber.d("Returning database instance...")
            }
            return INSTANCE
        }
    }
}