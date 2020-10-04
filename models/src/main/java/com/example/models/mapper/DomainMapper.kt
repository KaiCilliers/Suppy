package com.example.models.mapper

/**
 * Interface for converting database
 * model to domain model
 */
interface DomainMapper<T: Any> {
    fun asDomain(): T
}