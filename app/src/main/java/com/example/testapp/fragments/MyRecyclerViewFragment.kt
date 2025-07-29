package com.example.testapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.activities.data.AppDatabase
import com.example.testapp.activities.data.MyItem
import com.example.testapp.adaptors.MyAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyRecyclerViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyRecyclerViewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    private lateinit var db: AppDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_recycler_view, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val items = mutableListOf<MyItem>()
        if(arguments?.getInt("for_users_list") == 1){
            val usersString = arguments?.getString("users").toString()
            val regex = Regex("""User\(.*?\)""")
            val userEntries = regex.findAll(usersString)

            userEntries.forEach { matchResult ->
                val userData = matchResult.value

                val nameMatch = Regex("""name=([^,]*)""").find(userData)
                val emailMatch = Regex("""email_address=([^,]*)""").find(userData)
                val userIdMatch = Regex("""id\s*=\s*([^,]*)""").find(userData)
                val name = nameMatch?.groupValues?.get(1)?.trim() ?: ""
                val email = emailMatch?.groupValues?.get(1)?.trim() ?: ""
                val userId = userIdMatch?.groupValues?.get(1)?.trim() ?: ""
                val display = if (name.isNotEmpty()) name else email
                items.add(MyItem(display))
            }
        }

        adapter = MyAdapter(items)
        recyclerView.adapter = adapter

        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyRecyclerViewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyRecyclerViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}