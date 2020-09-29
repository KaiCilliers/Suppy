package com.example.suppy.util

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suppy.move_out.SomeDataModel
import com.example.suppy.databinding.RowChatsBinding
import com.google.android.material.snackbar.Snackbar

class MyRCAdapter(val items: ArrayList<SomeDataModel>, val context: Context) : RecyclerView.Adapter<MyViewHolder>(){
    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val infl = LayoutInflater.from(parent.context)
        val binding = RowChatsBinding.inflate(infl)
        return MyViewHolder(binding)
    }

    // Gets the number of items in the list
    override fun getItemCount(): Int = items.size

    // Binds each item in the arraylist to a view
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
class MyViewHolder(val binding: RowChatsBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SomeDataModel) {
        binding.item = item
        binding.root.setOnClickListener{
            Snackbar.make(it, "${item.name}", 2000).show()
            /**
             * NAVIGATE HERE
             */
        }
        binding.executePendingBindings()
    }
}