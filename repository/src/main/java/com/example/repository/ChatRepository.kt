package com.example.repository

// TODO methods of course
interface ChatRepository {
}
// TODO i dont know how to simplify the repositories without having it consist of a huge amount of methods to override
//interface ARepo {
//    suspend fun empty(): Boolean
//    suspend fun insert(chat: EntityChat)
//    suspend fun chats(): LiveData<List<EntityChat>>
//    suspend fun cleanChats(): List<EntityChat>
//    suspend fun delete(name: String)
//    suspend fun repopulate(chats: ArrayList<EntityChat>)
//    suspend fun updateDescription(id: Int, message: String)
//    suspend fun updateUnread(id: Int, counter: Int)
//    class Smart(private val origin: ARepo) {
//        suspend fun idOf(name: String): Int = // cant access dao here :/
//    }
//}