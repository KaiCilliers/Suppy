package com.example.models.chat

import androidx.room.ColumnInfo

/**
 * Partial Entity to perform an update
 * on an [EntityChat] database entry
 *
 * This class updates the [lastActivity]
 * field which is currently being used
 * as the unread counter.
 * TODO it seems you can ommit the @Entity annotation for partial objects
 */
data class UpdateChatUnRead(
    @ColumnInfo(name = "chat_id")
    val id: Int,
    @ColumnInfo(name = "last_activity")
    val lastActivity: String
)