package com.example.testapp.adaptors

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.testapp.activities.data.MyItem
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.example.testapp.R
class MyAdapter(private val itemList: List<MyItem>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewItem: TextView = itemView.findViewById(R.id.textViewItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.generic_item_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemList[position]
        holder.textViewItem.text = item.name
    }

    override fun getItemCount(): Int = itemList.size
}