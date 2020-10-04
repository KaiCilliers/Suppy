package com.example.models

import com.example.models.mapper.RoomMapper
import com.example.models.chat.EntityChat

data class RosterEntry(
    private val name: String,
    private val subType: String,
    private val bareJid: String,
    private val approved: Boolean,
    private val subPending: Boolean,
    private val commonGroups: ArrayList<RosterGroup>
) : RoomMapper<EntityChat>{
    fun add(group: RosterGroup) {
        commonGroups.add(group)
    }
    fun add(vararg groups: RosterGroup) {
        commonGroups.addAll(groups)
    }
    override fun asRoom(): EntityChat {
        return EntityChat(
            chatName = name,
            lastActivity = "Default Last Activity",
            mute = false,
            description = "Default Description",
            creator = "Default Creator",
            createdAt = "Default Created At",
            subType = subType,
            bareJid = bareJid,
            approved = approved,
            subPending = subPending,
            commonGroups = "$commonGroups"
        )
    }
}