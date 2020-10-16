package com.example.suppy.home.messages

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.models.message.DomainMessage
import com.example.suppy.R
import com.example.suppy.databinding.ItemMessageReceivedBinding
import com.example.suppy.databinding.ItemMessageSentBinding
import com.example.suppy.util.onClick
import timber.log.Timber

/**
 * Adapter to bind chat messages to recyclerview
 */
class MessagesAdapter (
    private var items: ArrayList<DomainMessage> = arrayListOf()
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 0) {
            ReceivedMessageHolder(
                ItemMessageReceivedBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        } else {
            SentMessageHolder(
                ItemMessageSentBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }
    override fun getItemCount(): Int = items.size
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder.itemViewType) {
            0 -> (holder as ReceivedMessageHolder).bind(items[position])
            1 -> (holder as SentMessageHolder).bind(items[position])
        }
    }
    override fun getItemViewType(position: Int): Int {
        val item = items.get(position)
        if(item.fromName == "scyther") {
            return 1 // true
        }
        return 0 // false
    }
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
     * ViewHolder for messages sent
     */
    inner class SentMessageHolder(private val binding: ItemMessageSentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DomainMessage) {
            binding.apply {
                root.onClick {
                    Timber.d("Sent item clicked: \"${data.body}\"")
                }
                this.data = data
                executePendingBindings()
            }
        }
    }
    /**
     * ViewHolder for messages received
     */
    inner class ReceivedMessageHolder(private val binding: ItemMessageReceivedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DomainMessage) {
            binding.apply {
                imageMessageProfile.setImageResource(R.drawable.scyther)
                root.onClick {
                    Timber.d("Received item clicked: \"${data.body}\"")
                }
                this.data = data
                executePendingBindings()
            }
        }
    }
}