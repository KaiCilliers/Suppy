package com.example.models.contact

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_contact")
data class EntityContact(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contact_id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "phone")
    val phone: String,
    @ColumnInfo(name = "last_seen")
    val last_seen: String,
    @ColumnInfo(name = "about")
    val about: String
)