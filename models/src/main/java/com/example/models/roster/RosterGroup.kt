package com.example.models.roster

data class RosterGroup(
    private val name: String,
    private val entries: List<String>,
    private val memberCount: Int
)