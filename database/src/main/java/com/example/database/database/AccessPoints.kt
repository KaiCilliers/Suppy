package com.example.database.database

import com.example.database.chat.ChatDao
import com.example.database.message.MessageDao

/**
 * Interface that wraps the dao
 * access points of the server
 * which is forced to not be
 * a normal class due to
 * [Room] requirements, thus it
 * can not receive the same
 * refractor to remove the
 * singleton as [Server]
 * Thus the daos have been
 * wrapped and they will be
 * passed down to objects
 * that require them
 */
interface AccessPoints {
    fun chatDao(): ChatDao
    fun msgDao(): MessageDao
}