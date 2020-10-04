package com.example.models.roster

data class RosterGroup(
    private val name: String,
    private val entries: ArrayList<String>,
    private val memberCount: Int
){
    fun add(member: String) {
        entries.add(member)
    }
    fun add(vararg members: String) {
        entries.addAll(members)
    }
}