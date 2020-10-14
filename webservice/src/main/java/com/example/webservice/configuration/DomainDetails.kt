package com.example.webservice.configuration

/**
 * Represents a domain configuration
 */
data class DomainDetails(
    val xmpp: String,
    val host: String,
    val port: Int
)