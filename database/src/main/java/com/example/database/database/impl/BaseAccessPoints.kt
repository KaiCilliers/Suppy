package com.example.database.database.impl

import com.example.database.database.AccessPoints
import com.example.database.database.LocalDatabase

/**
 * Represents a set of database access points
 */
class BaseAccessPoints(private val db: LocalDatabase) : AccessPoints {
    private val chatDao by lazy { db.chatDao() }
    private val msgDao by lazy { db.messageDao() }
    override fun chatDao() = chatDao
    override fun msgDao() = msgDao
}