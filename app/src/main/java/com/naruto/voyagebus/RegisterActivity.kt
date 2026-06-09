package com.naruto.voyagebus

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnSignup = findViewById<Button>(R.id.btnSignup)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        btnSignup.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Clear any existing data and save new user data to SharedPreferences
                val sharedPrefs = getSharedPreferences("VoyageBusPrefs", MODE_PRIVATE)
                sharedPrefs.edit {
                    clear() // This resets all data for the new account
                    putString("user_name", name)
                    putString("user_email", email)
                    putString("user_password", password)
                    putBoolean("is_logged_in", true)
                    putInt("user_points", 6524) // Starting voyage points for each new account
                }

                Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                // Navigate to HomeActivity after successful registration
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        
        tvLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
