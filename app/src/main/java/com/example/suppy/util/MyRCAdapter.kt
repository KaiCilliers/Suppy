package com.example.suppy.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suppy.R
import com.example.suppy.SomeDataModel
import kotlinx.android.synthetic.main.row_chat.view.*

class MyRCAdapter(val items: ArrayList<SomeDataModel>, val context: Context) : RecyclerView.Adapter<MyViewHolder>(){
    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_chat,
                parent,
                false
            )
        )
    }

    // Gets the number of items in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Binds each item in the arraylist to a view
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder?.items?.text = items.get(position).name
    }
}
class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val items = view.item_chats
}