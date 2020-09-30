package com.example.models.message

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_message")
data class EntityMessage(
    @PrimaryKey
    @ColumnInfo(name = "message_id")
    val id: String,
    @ColumnInfo(name = "chat_id")
    val chatId: String,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "body")
    val body: String,
    @ColumnInfo(name = "created_at")
    val createdAt: String
)