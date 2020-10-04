package com.example.models.message

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class DomainMessage(
    val id: String,
    val chatId: String,
    val author: String,
    val body: String,
    val createdAt: String
)