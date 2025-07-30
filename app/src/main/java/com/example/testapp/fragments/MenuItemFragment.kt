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
import androidx.lifecycle.lifecycleScope
import com.example.testapp.activities.data.AppDatabase
import com.example.testapp.activities.data.DatabaseProvider
import kotlinx.coroutines.launch


/**
 * A fragment representing a list of Items.
 */
class MenuItemFragment : Fragment() {

    private var columnCount = 1
    private lateinit var user: User
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu_item_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }

                arguments?.let { bundle ->
                    lifecycleScope.launch {
                        db = DatabaseProvider.getDatabase(requireContext())
                        val userDao = db.userDao()
                        val userDb = userDao.getUserByToken(SessionManager.token.toString())
                        user = User(
                            id = userDb?.id ?: 0,
                            name = userDb?.name ?: "",
                            age = userDb?.age ?: 0,
                            token = userDb?.token ?: "",
                            email_address = userDb?.email_address ?: "",
                            password = userDb?.password ?: ""
                        )

                        adapter = MyItemRecyclerViewAdapter(PlaceholderContent.ITEMS, requireContext(), user)

                    }
                }
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