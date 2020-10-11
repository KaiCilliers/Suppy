package com.example.models.chat.roster

/**
 * Represents a single group with
 * a list of members of that group
 */
interface RosterGroup {
    fun add(member: String)
    fun add(vararg members: String)
}