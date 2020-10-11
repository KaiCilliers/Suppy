package com.example.models.chat.roster

interface RosterEntry<T: RosterGroup>  {
    fun addGroup(group: T)
    fun addGroups(vararg groups: T)
}