package com.example.testapp.activities


import android.app.Activity
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
import com.example.testapp.activities.data.User
import com.example.testapp.activities.ui.login.LoginActivity
import com.example.testapp.activities.ui.login.LoginResult
import com.example.testapp.activities.utils.DialogUtils
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.fragments.MenuItemFragment
import com.example.testapp.utils.SessionManager
import kotlinx.coroutines.launch
private const val PROFILE_UPDATE_REQUEST_CODE = 1001

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding  // Binding declaration
    private lateinit var db: AppDatabase
    var userId = 0
    var user: User = User(
        id = 0,
        name = "",
        age = 0,
        token = "",
        email_address = "",
        password = ""
    )

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

        // show the login button

        if(SessionManager.token == null){
            //route the user to login page

            intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        } else{
            lifecycleScope.launch {
                user = userDao.getUserByToken(SessionManager.token.toString())!!
                if (user != null){
                    binding.greetingsTv.text = "Hi ${user?.email_address}"
                    userId = user.id.toInt()

                    if(user.age == 0 || user.name.isEmpty()){

                        //Incomplete profile dialog

                        var title = "Incomplete Profile"
                        var message = "Hi, we noticed that you didn't finish your profile. Let us help you!"
                        DialogUtils.showCustomDialog(
                            this@MainActivity,
                            title,
                            message,
                            {
                                val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                                intent.putExtra("user_id", user.id.toString())
                                intent.putExtra("name", user.name)
                                intent.putExtra("age", user.age.toString())
                                intent.putExtra("email_address", user.email_address)
                                startActivityForResult(intent, PROFILE_UPDATE_REQUEST_CODE)
                            }
                        )
                    }

                    //show menu

                    //bundle for Profile
                    var menuFragment = MenuItemFragment()
                    var menuBundle = Bundle()
                    menuBundle.putString("user_id", userId.toString())
                    menuBundle.putString("name", user.name)
                    menuBundle.putString("age", user.age.toString())
                    menuBundle.putString("email_address", user.email_address)
                    menuFragment.arguments = menuBundle
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.menu_ll, menuFragment)
                        .commit()
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
                }
            }
        }

        if(SessionManager.token == null || (SessionManager.token.toString()).length < 5){
            var intent  = Intent(this, LoginActivity::class.java    )
            startActivity(intent)
        }

        // logout user
        binding.logoutBtn.setOnClickListener {
            SessionManager.clearToken()
            intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            lifecycleScope.launch {
                user = db.userDao().getUserByToken(token = SessionManager.token.toString())!!
                Log.d("onActivityResult", "user: $user")
            }
        }
    }
}