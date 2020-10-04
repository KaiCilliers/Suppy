package com.example.models.roster

data class RosterEntry(
    private val name: String,
    private val subType: String,
    private val semiJID: String,
    private val approved: Boolean,
    private val subPending: Boolean,
    private val commonGroups: ArrayList<RosterGroup>
) {
    fun add(group: RosterGroup) {
        commonGroups.add(group)
    }
    fun add(vararg groups: RosterGroup) {
        commonGroups.addAll(groups)
    }
}