package com.example.suppy.home.chatmessages

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suppy.databinding.RowMessagesBinding
import com.example.suppy.move_out.Message
import timber.log.Timber

/**
 * Adapter to bind chat messages to recyclerview
 */
class ChatMessagesAdapter (val items: ArrayList<Message>, val context: Context) : RecyclerView.Adapter<MessageItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageItem {
        val infl = LayoutInflater.from(parent.context)
        val binding = RowMessagesBinding.inflate(infl)
        return MessageItem(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MessageItem, position: Int) = holder.bind(items[position])
}

/**
 * Represents a single item in recyclerview
 */
class MessageItem(val binding: RowMessagesBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: Message) {
        binding.apply {
            root.setOnClickListener {
                Timber.d("Message item clicked with data: ${data.content}")
            }
            this.data = data
            executePendingBindings()
        }
    }
}