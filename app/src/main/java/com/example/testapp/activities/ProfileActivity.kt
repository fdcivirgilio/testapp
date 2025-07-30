package com.example.testapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.testapp.R
import com.example.testapp.activities.data.AppDatabase
import com.example.testapp.activities.data.DatabaseProvider
import com.example.testapp.activities.data.User
import com.example.testapp.activities.utils.DialogUtils
import com.example.testapp.databinding.ActivityProfileBinding
import com.example.testapp.fragments.ToolbarFragment
import com.example.testapp.utils.SessionManager
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding  // Binding declaration
    private lateinit var db: AppDatabase
    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        db = DatabaseProvider.getDatabase(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityProfileBinding.inflate(layoutInflater)
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



        var emailAddressEt = binding.emailAddressEt
        var ageEt = binding.ageEt
        var nameEt = binding.nameEt
        var userId = intent.getStringExtra("user_id")

        lifecycleScope.launch{
            val userDb = db.userDao().getUserByToken(SessionManager.token.toString())
            user = User(
                id = userDb?.id ?: 0,
                name = userDb?.name ?: "",
                age = userDb?.age ?: 0,
                token = userDb?.token ?: "",
                email_address = userDb?.email_address ?: "",
                password = userDb?.password ?: ""
            )

            nameEt.setText(user.name)
            ageEt.setText(user.age.toString())
            emailAddressEt.setText(user.email_address)
        }

        binding.updateProfileBtn.setOnClickListener {

            var user2 = User(
                id = userId?.toInt() ?: 0,
                name = nameEt.text.toString(),
                age = ageEt.text.toString().toInt(),
                token = "",
                email_address = emailAddressEt.text.toString(),
                password = ""
            )

            lifecycleScope.launch {
                val rowsUpdated = db.userDao().updateUserDetails(
                    emailAddress = user2.email_address,
                    id = user2.id,
                    age = user2.age,
                    name = user2.name
                )

                if (rowsUpdated > 0) {
                    var title = "Success!"
                    var message = "Profile is updated."
                    DialogUtils.showCustomDialog(
                        this@ProfileActivity,
                        title,
                        message,
                        {
                            finish()
                        }
                    )
                    setResult(RESULT_OK)
                } else {
                    Toast.makeText(this@ProfileActivity, "Update failed or no changes made.", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}