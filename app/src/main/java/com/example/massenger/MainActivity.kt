package com.example.massenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.massenger.activities.SingInActivity
import com.example.massenger.activities.SingUpActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        // Rest of your activity code

        val signIn: Button = findViewById<Button>(R.id.index_singin_btn)
        signIn.setOnClickListener {
            val i = Intent(this, SingInActivity::class.java)
            startActivity(i)
        }
        val signUp: Button = findViewById<Button>(R.id.index_singup_btn)
        signUp.setOnClickListener {
            val i = Intent(this, SingUpActivity::class.java)
            startActivity(i)
        }
    }
}