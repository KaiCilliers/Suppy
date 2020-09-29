package com.example.suppy.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.RecyclerView
import com.example.suppy.move_out.SomeDataModel
import com.example.suppy.databinding.RowChatsBinding
import com.example.suppy.home.chatmessages.ChatMessagesFragment

/**
 * An adapter to bind data to a recyclerview
 * TODO add interfaces for ViewHolders
 */
class ChatsAdapter(val items: ArrayList<SomeDataModel>, val context: Context, val fragment: Fragment) : RecyclerView.Adapter<ChatItem>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItem = ChatItem(
        RowChatsBinding.inflate(
            LayoutInflater.from(parent.context)
        ),
        fragment
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ChatItem, position: Int) = holder.bind(items[position])
}

/**
 * Represents a single item in recylerview list
 * [fragment] is simply used in order for broadcasting
 * the clicked item's data
 *
 * TODO extract to own file with interface
 */
class ChatItem(val binding: RowChatsBinding, val fragment: Fragment) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SomeDataModel) {
        binding.item = item
        binding.root.setOnClickListener{
            fragment.setFragmentResult(
                "nav", bundleOf(
                    "to" to ChatMessagesFragment::class.java,
                    "conversation" to item.name
                )
            )
        }
        binding.executePendingBindings()
    }
}