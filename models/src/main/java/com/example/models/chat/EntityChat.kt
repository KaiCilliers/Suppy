package com.example.models.chat

import android.media.MediaDescription
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import com.example.models.mapper.DomainMapper

@Entity(tableName = "table_chat")
data class EntityChat(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "chat_id")
    // Kotlin data classes require a default value
    val id: Int = 0,
    @ColumnInfo(name = "chat_name")
    val chatName: String,
    @ColumnInfo(name = "last_activity")
    val lastActivity: String,
    @ColumnInfo(name = "is_mute")
    val mute: Boolean,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "creator")
    val creator: String,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    @ColumnInfo(name = "subscription_type")
    val subType: String,
    @ColumnInfo(name = "bare_jid")
    val bareJid: String,
    @ColumnInfo(name = "approved")
    val approved: Boolean,
    @ColumnInfo(name = "subscription_pending")
    val subPending: Boolean,
    // TODO implement list values in database storage
    @ColumnInfo(name = "groups_in_common")
    val commonGroups: String
) : DomainMapper<DomainChat> {
    override fun asDomain(): DomainChat {
        return DomainChat(
            id = id,
            chatName = chatName,
            lastActivity = lastActivity,
            mute = mute,
            description = description,
            creator = creator,
            createdAt = createdAt
        )
    }
}
