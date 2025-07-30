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
import com.bumptech.glide.Glide
import com.example.testapp.R
import com.example.testapp.activities.data.AppDatabase
import com.example.testapp.activities.data.DatabaseProvider
import com.example.testapp.activities.data.User
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.databinding.ActivityUserDetailsBinding
import com.example.testapp.fragments.ToolbarFragment
import kotlinx.coroutines.launch

class UserDetailsActivity : AppCompatActivity() {
    private var userId = 0
    private lateinit var binding: ActivityUserDetailsBinding  // Binding declaration
    private lateinit var user: User
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        var userDao = db.userDao()
        userId = intent.getIntExtra("user_id", 0)
        lifecycleScope.launch {
            user = userDao.getUserById(user_id = userId)!!
            binding.nameTv.text = user.name.ifBlank { user.email_address }

            val imageUrl = "https://images.pexels.com/photos/31559069/pexels-photo-31559069.jpeg"
            Glide.with(this@UserDetailsActivity)
                .load(imageUrl)
                .circleCrop()
                .into(binding.userProfileIv)

            binding.ageTv.text = user.age.toString()
            binding.emailAddressTv.text = user.email_address
            binding.nameActualTv.text = user.name
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }
}