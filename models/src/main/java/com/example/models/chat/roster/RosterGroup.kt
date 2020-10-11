package com.example.models.chat.roster

interface RosterGroup {
    fun add(member: String)
    fun add(vararg members: String)
}