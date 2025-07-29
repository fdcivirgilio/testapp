package com.example.testapp.activities

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.testapp.R
import com.example.testapp.activities.data.AppDatabase
import com.example.testapp.activities.data.DatabaseProvider
import com.example.testapp.activities.data.MyItem
import com.example.testapp.fragments.MyRecyclerViewFragment
import com.example.testapp.fragments.ToolbarFragment
import kotlinx.coroutines.launch

class UsersActivity : AppCompatActivity() {
    private lateinit var db: AppDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_users)

        // show toolbar

        var toolbarFragment = ToolbarFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.toolbar, toolbarFragment)
            .commit()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = DatabaseProvider.getDatabase(this)

        val userDao = db.userDao()

        // Example with coroutine
        lifecycleScope.launch {
            val users = userDao.getAllUsers()

            var usersFragment = MyRecyclerViewFragment()
            var bundle = Bundle()
            bundle.putInt("for_users_list", 1)
            bundle.putString("users", users.toString())
            usersFragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.user_list_ll, usersFragment)
                .commit()
        }

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }
}