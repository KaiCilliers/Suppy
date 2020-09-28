package com.example.suppy.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suppy.R
import com.example.suppy.SomeDataModel
import com.example.suppy.databinding.RowChatBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.row_chat.view.*

class MyRCAdapter(val items: ArrayList<SomeDataModel>, val context: Context) : RecyclerView.Adapter<MyViewHolder>(){
    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val infl = LayoutInflater.from(parent.context)
        val binding = RowChatBinding.inflate(infl)
        return MyViewHolder(binding)
    }

    // Gets the number of items in the list
    override fun getItemCount(): Int = items.size

    // Binds each item in the arraylist to a view
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
class MyViewHolder(val binding: RowChatBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SomeDataModel) {
        binding.item = item
        binding.executePendingBindings()
    }
}