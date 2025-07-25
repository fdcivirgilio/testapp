package com.example.testapp.activities


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.transition.Visibility
import com.example.testapp.R
import com.example.testapp.activities.data.AppDatabase
import com.example.testapp.activities.data.DatabaseProvider
import com.example.testapp.activities.ui.login.LoginActivity
import com.example.testapp.activities.ui.login.LoginResult
import com.example.testapp.activities.utils.DialogUtils
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.utils.SessionManager
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding  // Binding declaration
    private lateinit var db: AppDatabase

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
        db = DatabaseProvider.getDatabase(this)
        val userDao = db.userDao()

        // Example with coroutine
        lifecycleScope.launch {
            val users = userDao.getAllUsers()
            Log.d("RoomExample", "Users: $users")
        }
        // show the login button

        if(SessionManager.token == null){
            binding.goToLoginBtn.visibility = View.VISIBLE
        } else{
            lifecycleScope.launch {
                var user = userDao.getUserByToken(SessionManager.token.toString())
                if (user != null){
                    binding.greetingsTv.text = "Hi ${user?.email_address}"
                } else {
                    var title = "Session Expired"
                    var message = "Your expired. Please re-login. ${SessionManager.token.toString()}"
                    DialogUtils.showCustomDialog(
                        this@MainActivity,
                        title,
                        message,
                        {
                            val intent = Intent(this@MainActivity, LoginActivity::class.java)
                            startActivity(intent)
                        }
                    )
                    SessionManager.clearToken()
                    binding.goToLoginBtn.visibility = View.GONE
                }
            }
        }

        //Incomplete profile dialog

        var title = "Incomplete Profile"
        var message = "Hi, we noticed that you didn't finish your profile. Let us help you!"
        DialogUtils.showCustomDialog(
            this@MainActivity,
            title,
            message,
            {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        )

        binding.goToLoginBtn.setOnClickListener {
            if(SessionManager.token == null){
                var intent  = Intent(this, LoginActivity::class.java    )
                startActivity(intent)
            } else {
                    Log.d("MainActivity", "LoginResult Data: ${SessionManager.token}")

            }

        }
        if(SessionManager.token == null || (SessionManager.token.toString()).length < 5){
            var intent  = Intent(this, LoginActivity::class.java    )
            startActivity(intent)
        }

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }
}