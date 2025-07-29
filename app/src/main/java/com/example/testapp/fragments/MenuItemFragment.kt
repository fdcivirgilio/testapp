package com.example.testapp.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.testapp.R
import com.example.testapp.activities.data.User
import com.example.testapp.fragments.placeholder.PlaceholderContent
import com.example.testapp.utils.SessionManager
import android.util.Log


/**
 * A fragment representing a list of Items.
 */
class MenuItemFragment : Fragment() {

    private var columnCount = 1
    lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        arguments?.let { bundle ->

            user = User(
                id = bundle.getString("user_id").toString().toInt(),
                name = bundle.getString("name").toString(),
                age = bundle.getString("age").toString().toInt(),
                token = bundle.getString("token").toString(),
                email_address = bundle.getString("email_address").toString(),
                password = bundle.getString("password").toString()
            )

            Log.d("MenuItemFragment", "User: $user")
            Log.d("MenuItemFragment", "User - Non-class: ${bundle.getString("user_id").toString()}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu_item_list, container, false)
        
        //
        


        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyItemRecyclerViewAdapter(PlaceholderContent.ITEMS, requireContext(), user)
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            MenuItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}