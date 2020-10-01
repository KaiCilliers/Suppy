package com.example.models.chat

import android.media.MediaDescription
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "table_chat")
data class EntityChat(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "chat_id")
    private val id: Int,
    @ColumnInfo(name = "chat_name")
    private val chatName: String,
    @ColumnInfo(name = "last_activity")
    private val lastActivity: String,
    @ColumnInfo(name = "is_mute")
    private val mute: Boolean,
    @ColumnInfo(name = "description")
    private val description: String,
    @ColumnInfo(name = "creator")
    private val creator: String,
    @ColumnInfo(name = "created_at")
    private val createdAt: String
)