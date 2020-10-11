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
data class UpdatedDescription(
    @ColumnInfo(name = "chat_id")
    val id: Int,
    @ColumnInfo(name = "description")
    val description: String
)
/**
 * Partial Entity to perform an update
 * on an [EntityChat] database entry
 *
 * This class updates the [lastActivity]
 * field which is currently being used
 * as the unread counter.
 * TODO it seems you can ommit the @Entity annotation for partial objects
 */
data class UpdatedReceived(
    @ColumnInfo(name = "chat_id")
    val id: Int,
    @ColumnInfo(name = "last_activity")
    val lastActivity: String
)