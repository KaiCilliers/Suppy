package com.example.models.mapper

/**
 * Interface for converting network
 * response model to a database model
 */
interface RoomMapper<T: Any> {
    fun asRoom(): T
}