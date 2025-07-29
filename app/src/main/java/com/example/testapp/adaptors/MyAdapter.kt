package com.example.testapp.adaptors

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import com.example.testapp.activities.data.MyItem
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import androidx.core.content.ContextCompat.startActivity
import com.example.testapp.R
import com.example.testapp.activities.UserDetailsActivity
import com.google.gson.Gson
import com.example.testapp.activities.data.User

class MyAdapter(
    private val itemList: List<MyItem>,
    private val context: Context,
) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewItem: TextView = itemView.findViewById(R.id.textViewItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.generic_item_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemList[position]
        val gson = Gson()
        val userString = item.name
        val user = gson.fromJson(userString, User::class.java)
        holder.textViewItem.text = user.name
        holder.textViewItem.setOnClickListener {
            var intent = Intent(context, UserDetailsActivity::class.java)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = itemList.size
}