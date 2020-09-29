package com.example.suppy.util

import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suppy.databinding.RowMessagesBinding
import com.example.suppy.move_out.Message
import com.example.suppy.move_out.SomeMessages
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class RCMessagesAdapter (val items: ArrayList<Message>, val context: Context) : RecyclerView.Adapter<MessagesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessagesViewHolder {
        val infl = LayoutInflater.from(parent.context)
        val binding = RowMessagesBinding.inflate(infl)
        return MessagesViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) = holder.bind(items[position])
}
class MessagesViewHolder(val binding: RowMessagesBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Message) {
        binding.data = item
        binding.root.setOnClickListener{
            Timber.d("${item.content}")
        }
        binding.executePendingBindings()
    }
}