package com.example.testapp.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.testapp.R
import com.example.testapp.activities.MainActivity
import com.example.testapp.activities.data.AppDatabase
import com.example.testapp.activities.data.DatabaseProvider
import com.example.testapp.activities.data.User
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.databinding.ActivityProfileBinding
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding  // Binding declaration
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        db = DatabaseProvider.getDatabase(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityProfileBinding.inflate(layoutInflater)

//        var emailAddressEt = binding.emailAddressEt
//        var ageEt = binding.ageEt
//        var nameEt = binding.nameEt

        var emailAddressEt = findViewById<EditText>(R.id.email_address_et)
        var ageEt = findViewById<EditText>(R.id.age_et)
        var nameEt = findViewById<EditText>(R.id.name_et)
        var userId = intent.getStringExtra("user_id")

        nameEt.setText(intent.getStringExtra("name"))
        ageEt.setText(intent.getStringExtra("age"))
        emailAddressEt.setText(intent.getStringExtra("email_address"))

        findViewById<Button>(R.id.update_profile_btn).setOnClickListener {

            var user2 = User(
                id = userId?.toInt() ?: 0,
                name = nameEt.text.toString(),
                age = ageEt.text.toString().toInt(),
                token = "",
                email_address = emailAddressEt.text.toString(),
                password = ""
            )
            Log.d("ProfileActivity", "user2: ${user2} user id: $userId")

            lifecycleScope.launch {
                val rowsUpdated = db.userDao().updateUserDetails(
                    emailAddress = user2.email_address,
                    id = user2.id,
                    age = user2.age,
                    name = user2.name
                )

                if (rowsUpdated > 0) {
                    Toast.makeText(this@ProfileActivity, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ProfileActivity, ProfileActivity::class.java)
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this@ProfileActivity, "Update failed or no changes made.", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }
}