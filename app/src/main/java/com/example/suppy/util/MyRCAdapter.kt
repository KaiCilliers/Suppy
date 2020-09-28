package com.example.suppy.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.suppy.R
import com.example.suppy.SomeDataModel
import com.google.android.material.snackbar.Snackbar
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
        holder?.name?.text = items.get(position).name
        holder?.desc?.text = items.get(position).description
        holder?.name.setOnClickListener{
            Snackbar.make(it,"${items.get(position).name}", 2000).show()
        }
    }
}
class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name = view.item_chats
    val desc = view.item_chats_desc
}