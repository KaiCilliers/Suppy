package com.example.models.chat

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * Partial Entity to perform an update
 * on a [EntityChat] database entry
 *
 * This class updates the [description]
 * value
 */
@Entity
data class UpdateChatDescription(
    @ColumnInfo(name = "chat_id")
    val id: Int,
    @ColumnInfo(name = "description")
    val description: String
)