package com.example.suppy.home.messages

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.models.message.DomainMessage
import com.example.suppy.databinding.RowMessagesBinding
import com.example.suppy.util.onClick
import timber.log.Timber

/**
 * Adapter to bind chat messages to recyclerview
 */
class MessagesAdapter (
    private var items: ArrayList<DomainMessage> = arrayListOf(),
    val context: Context
) : RecyclerView.Adapter<MessagesAdapter.MessageItem>() {
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
    fun item(position: Int): DomainMessage = items[position]
    /**
     * Populate the adapter's data
     */
    fun populate(data: List<DomainMessage>) {
        Timber.v("Populating MessageAdapter's data to $data")
        if(data.isNotEmpty()) {
            items.clear()
            items.addAll(data)
            notifyDataSetChanged()
        } else {
            items = ArrayList(data)
        }
    }
    /**
     * Represents a single item in recyclerview
     */
    class MessageItem(val binding: RowMessagesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DomainMessage) {
            binding.apply {
                root.onClick {
                    Timber.v("Message clicked with content \"${data.body}\"")
                }
                this.data = data
                executePendingBindings()
            }
        }
    }
}