package com.example.suppy.home.messages

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.models.message.DomainMessage
import com.example.suppy.databinding.RowMessagesBinding
import timber.log.Timber

/**
 * Adapter to bind chat messages to recyclerview
 */
class ChatMessagesAdapter (private var items: ArrayList<DomainMessage> = arrayListOf(), val context: Context) : RecyclerView.Adapter<MessageItem>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageItem = MessageItem(
        RowMessagesBinding.inflate(
            LayoutInflater.from(parent.context)
        )
    )

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MessageItem, position: Int) = holder.bind(items[position])

    /**
     * Return domain object at specific position
     */
    fun itemAtPosition(position: Int): DomainMessage {
        return items[position]
    }

    fun setData(data: List<DomainMessage>) {
        Timber.d("messages setData called with data $data")
        if(data.isNotEmpty()) {
            Timber.d("messages data set is not empty and is being replaced...")
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        } else {
            // first init
            Timber.d("messages data set is empty and is being populated now...")
            items = data.toArrayList()
        }
    }
    private fun <T> List<T>.toArrayList(): ArrayList<T> {
        Timber.d("List to arraylist conversion of $this to ${ArrayList(this)}")
        return ArrayList(this)
    }
}

/**
 * Represents a single item in recyclerview
 */
class MessageItem(val binding: RowMessagesBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: DomainMessage) {
        binding.apply {
            root.setOnClickListener {
                Timber.d("Message clicked with content \"${data.body}\"")
            }
            this.data = data
            executePendingBindings()
        }
    }
}