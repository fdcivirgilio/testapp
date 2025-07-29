package com.example.testapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.testapp.activities.ProfileActivity

import com.example.testapp.fragments.placeholder.PlaceholderContent.PlaceholderItem
import com.example.testapp.databinding.FragmentMenuItemBinding
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.testapp.activities.data.AppDatabase
import com.example.testapp.activities.data.DatabaseProvider
import com.example.testapp.activities.data.User
import com.example.testapp.utils.SessionManager
import kotlinx.coroutines.launch

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(
    private val values: List<PlaceholderItem>,
    private val context: Context,
    private val user: User
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentMenuItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item.content
        holder.itemView.setOnClickListener {

            if(holder.contentView.text.toString() == "Profile"){
                val intent = Intent(context, ProfileActivity::class.java)
                intent.putExtra("user_id", user.id)
                intent.putExtra("name", user.name)
                intent.putExtra("age", user.age.toString())
                intent.putExtra("email_address", user.email_address)
                context.startActivity(intent)

            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentMenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}