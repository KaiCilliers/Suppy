package com.example.suppy.home.chats

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.models.chat.DomainChat
import com.example.suppy.databinding.RowChatsBinding
import com.example.suppy.util.onClick
import timber.log.Timber

/**
 * An adapter to bind data to a recyclerview
 * TODO add interfaces for ViewHolders
 * TODO replace vars with immutable objects
 * TODO consider making items an ArrayList of EntityChats and convert to Domain as late as possible to provide access to the entity object for as long as possible
 */
class ChatsAdapter(
    private var items: ArrayList<DomainChat> = arrayListOf(),
    val context: Context,
    val itemClicked: ChatsViewModel
) : RecyclerView.Adapter<ChatsAdapter.ChatItem>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItem = ChatItem(
        RowChatsBinding.inflate(
            LayoutInflater.from(parent.context)
        )
    )
    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: ChatItem, position: Int) = holder.bind(items[position], itemClicked)
    /**
     * Return domain object at specific position
     */
    fun item(position: Int): DomainChat = items[position]
    /**
     * Update the adapter's data
     */
    fun populate(data: List<DomainChat>) {
        Timber.v("Populating ChatAdapter's data to $data")
        if(data.isNotEmpty()) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        } else {
            items = ArrayList(data)
        }
    }
    /**
     * Represents a single item in recylerview list
     * [fragment] is simply used in order for broadcasting
     * the clicked item's data
     */
    inner class ChatItem(private val binding: RowChatsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DomainChat, itemClicked: ChatsViewModel) {
            binding.apply{
                root.onClick {
                    itemClicked.bundle = item
                    Timber.d("RC Chat Item clicked...with data ${itemClicked.bundle}")
                    itemClicked.navigate()
                }
                this.item = item
                executePendingBindings()
            }
        }
    }
}