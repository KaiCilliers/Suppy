package com.example.models.message

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
 * Partial Entity to perform an update
 * on [EntityMessage] database entry
 *
 * This class updates the [rerecived]
 * boolean
 */
@Entity
data class UpdatedReceived(
    @ColumnInfo(name = "message_id")
    val id: String,
    @ColumnInfo(name = "received")
    val rerecived: Boolean,
    @ColumnInfo(name = "counter_temp")
    val counter_temp: Int = 0
)