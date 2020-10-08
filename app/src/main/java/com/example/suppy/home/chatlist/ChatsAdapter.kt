package com.example.suppy.home.chatlist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.models.chat.DomainChat
import com.example.models.chat.EntityChat
import com.example.suppy.databinding.RowChatsBinding
import com.example.suppy.util.onClick
import timber.log.Timber

/**
 * An adapter to bind data to a recyclerview
 * TODO add interfaces for ViewHolders
 * TODO replace vars with immutable objects
 * TODO consider making items an ArrayList of EntityChats and convert to Domain as late as possible to provide access to the entity object for as long as possible
 */
class ChatsAdapter(private var items: ArrayList<DomainChat> = arrayListOf(), val context: Context, val itemClicked: ChatsViewModel) : RecyclerView.Adapter<ChatItem>(){
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
    fun itemAtPosition(position: Int): DomainChat {
        return items[position]
    }

    fun setData(data: List<DomainChat>) {
        Timber.d("setDAta called with data $data")
        Timber.d("setData called...")
        if(data.isNotEmpty()) {
            Timber.d("data set is not empty and is being replaced...")
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        } else {
            // first init
            Timber.d("data set is empty and is being populated now...")
            items = data.toArrayList()
        }
    }
    private fun <T> List<T>.toArrayList(): ArrayList<T> {
        Timber.d("List to arraylist conversion of $this to ${ArrayList(this)}")
        return ArrayList(this)
    }
}

/**
 * Represents a single item in recylerview list
 * [fragment] is simply used in order for broadcasting
 * the clicked item's data
 *
 * TODO extract to own file with interface
 */
class ChatItem(val binding: RowChatsBinding) : RecyclerView.ViewHolder(binding.root) {
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