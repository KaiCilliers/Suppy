package com.example.models.chat

import android.media.MediaDescription
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "table_chat")
data class EntityChat(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "chat_id")
    // Kotlin data classes require a default value
    private val id: Int = 0,
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
    private val createdAt: String,
    @ColumnInfo(name = "subscription_type")
    private val subType: String,
    @ColumnInfo(name = "bare_jid")
    private val bareJid: String,
    @ColumnInfo(name = "approved")
    private val approved: Boolean,
    @ColumnInfo(name = "subscription_pending")
    private val subPending: Boolean,
    // TODO implement list values in database storage
    @ColumnInfo(name = "groups_in_common")
    private val commonGroups: String
)