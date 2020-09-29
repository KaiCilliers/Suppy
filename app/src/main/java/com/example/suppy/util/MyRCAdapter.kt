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
import com.google.android.material.snackbar.Snackbar

class MyRCAdapter(val items: ArrayList<SomeDataModel>, val context: Context, val frag: Fragment) : RecyclerView.Adapter<MyViewHolder>(){
    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val infl = LayoutInflater.from(parent.context)
        val binding = RowChatsBinding.inflate(infl)
        return MyViewHolder(binding, frag)
    }

    // Gets the number of items in the list
    override fun getItemCount(): Int = items.size

    // Binds each item in the arraylist to a view
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(items[position])
    }
}
class MyViewHolder(val binding: RowChatsBinding, val frag: Fragment) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: SomeDataModel) {
        binding.item = item
        binding.root.setOnClickListener{
            frag.setFragmentResult(
                "nav", bundleOf(
                    "to" to ChatMessagesFragment::class.java,
                    "conversation" to item.name
                )
            )
        }
        binding.executePendingBindings()
    }
}