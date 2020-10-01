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
class ChatsAdapter(val items: ArrayList<SomeDataModel>, val context: Context, val fragment: Fragment, val vm: ChatsViewModel) : RecyclerView.Adapter<ChatItem>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatItem = ChatItem(
        RowChatsBinding.inflate(
            LayoutInflater.from(parent.context)
        ),
        fragment
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ChatItem, position: Int) = holder.bind(items[position], vm)
}

/**
 * Represents a single item in recylerview list
 * [fragment] is simply used in order for broadcasting
 * the clicked item's data
 *
 * TODO extract to own file with interface
 */
class ChatItem(val binding: RowChatsBinding, val fragment: Fragment) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SomeDataModel, vm: ChatsViewModel) {
        binding.item = item
        binding.root.setOnClickListener{
            Timber.d("Cool")
            vm.bundle = item
            vm.navigate()
        }
        binding.executePendingBindings()
    }
}