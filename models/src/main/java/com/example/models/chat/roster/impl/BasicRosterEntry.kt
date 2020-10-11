package com.example.models.chat.roster.impl

import com.example.models.mapper.RoomMapper
import com.example.models.chat.EntityChat
import com.example.models.chat.roster.RosterEntry

/**
 * Represents a single entry in an account's
 * roster
 */
data class BasicRosterEntry(
    private val identification: Identification,
    private val subscriptionType: String,
    private val status: Status,
    private val commonGroups: ArrayList<BasicRosterGroup>
) : RoomMapper<EntityChat>,
    RosterEntry<BasicRosterGroup> {
    override fun addGroup(group: BasicRosterGroup) {
        commonGroups.add(group)
    }
    override fun addGroups(vararg groups: BasicRosterGroup) {
        commonGroups.addAll(groups)
    }
    override fun asRoom(): EntityChat {
        return EntityChat(
            chatName = identification.name,
            lastActivity = "Default Last Activity",
            mute = false,
            description = "Default Description",
            creator = "Default Creator",
            createdAt = "Default Created At",
            subType = subscriptionType,
            bareJid = identification.bareJid,
            approved = status.approved,
            subPending = status.subscriptionPending,
            commonGroups = "$commonGroups"
        )
    }
}