package com.security.insecurestoragapp
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etAccessToken: EditText = findViewById(R.id.etAccessToken)
        val btnStoreToken: Button = findViewById(R.id.btnStoreToken)
        val btnRetrieveToken: Button = findViewById(R.id.btnRetrieveToken)
        val tvToken: TextView = findViewById(R.id.tvToken)

        // Store User-Provided Access Token (Insecurely)
        btnStoreToken.setOnClickListener {
            val accessToken = etAccessToken.text.toString()
            if (accessToken.isNotEmpty()) {
                val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("accessToken", accessToken)
                editor.apply()
                Toast.makeText(this, "Access Token Stored", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Please enter an Access Token", Toast.LENGTH_SHORT).show()
            }
        }

        // Retrieve and Display Access Token
        btnRetrieveToken.setOnClickListener {
            val sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
            val token = sharedPreferences.getString("accessToken", "No Token Found")
            tvToken.text = token
        }
    }
}