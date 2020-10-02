package com.example.suppy.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.suppy.move_out.SomeDataModel
import com.example.suppy.databinding.RowChatsBinding
import com.example.suppy.home.chatlist.ChatsViewModel
import timber.log.Timber

/**
 * An adapter to bind data to a recyclerview
 * TODO add interfaces for ViewHolders
 */
class ChatsAdapter(val items: ArrayList<SomeDataModel>, val context: Context, val itemClicked: ChatsViewModel) : RecyclerView.Adapter<ChatItem>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItem = ChatItem(
        RowChatsBinding.inflate(
            LayoutInflater.from(parent.context)
        )
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ChatItem, position: Int) = holder.bind(items[position], itemClicked)
}

/**
 * Represents a single item in recylerview list
 * [fragment] is simply used in order for broadcasting
 * the clicked item's data
 *
 * TODO extract to own file with interface
 */
class ChatItem(val binding: RowChatsBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SomeDataModel, itemClicked: ChatsViewModel) {
        binding.item = item
        binding.root.setOnClickListener{
            itemClicked.bundle = item.name
            Timber.d("RC Chat Item clicked...with data ${itemClicked.bundle}")
            itemClicked.navigate()
        }
        binding.executePendingBindings()
    }
}