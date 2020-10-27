package com.example.models.chat.roster.impl

import com.example.models.chat.roster.RosterGroup

data class BasicRosterGroup(
    private val name: String,
    private val entries: ArrayList<String>,
    private val memberCount: Int
): RosterGroup {
    override fun add(member: String) {
        entries.add(member)
    }
    override fun add(vararg members: String) {
        entries.addAll(members)
    }
}