package com.example.models.chat.roster

/**
 * Represents a single contact known to the
 * user - obtained from the XMPP account's
 * roster - hence the naming choice
 */
interface RosterEntry<T: RosterGroup>  {
    fun addGroup(group: T)
    fun addGroups(vararg groups: T)
}