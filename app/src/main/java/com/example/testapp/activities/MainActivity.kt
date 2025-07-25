package com.example.testapp.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.testapp.R
import com.example.testapp.activities.ui.login.LoginActivity
import com.example.testapp.activities.ui.login.LoginResult
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.utils.SessionManager
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding  // Binding declaration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SessionManager.init(applicationContext)
        Log.d("MainActivity", "MainActivity is called with token ${SessionManager.token}")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d("MainActivity", "LoginResult Data: ${LoginResult().success}")
        binding.goToLoginBtn.setOnClickListener {
            if(SessionManager.token == null){
                var intent  = Intent(this, LoginActivity::class.java    )
                startActivity(intent)
            } else {
                    Log.d("MainActivity", "LoginResult Data: ${SessionManager.token}")

            }

        }

        var intent  = Intent(this, LoginActivity::class.java    )
        startActivity(intent)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }
}