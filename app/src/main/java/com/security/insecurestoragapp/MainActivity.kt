package com.security.insecurestoragapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etAccessToken: EditText = findViewById(R.id.etAccessToken)
        val btnStoreToken: Button = findViewById(R.id.btnStoreToken)
        val btnRetrieveToken: Button = findViewById(R.id.btnRetrieveToken)
        val tvToken: TextView = findViewById(R.id.tvToken)

        // Set up EncryptedSharedPreferences
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val encryptedSharedPreferences = EncryptedSharedPreferences.create(
            "SecurePrefs",
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        // Store User-Provided Access Token Securely
        btnStoreToken.setOnClickListener {
            val accessToken = etAccessToken.text.toString()
            if (accessToken.isNotEmpty()) {
                val editor = encryptedSharedPreferences.edit()
                editor.putString("accessToken", accessToken)
                editor.apply()
                Toast.makeText(this, "Access Token Stored Securely", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter an Access Token", Toast.LENGTH_SHORT).show()
            }
        }

        // Retrieve and Display Access Token
        btnRetrieveToken.setOnClickListener {
            val token = encryptedSharedPreferences.getString("accessToken", "No Token Found")
            tvToken.text = token
        }
    }
}