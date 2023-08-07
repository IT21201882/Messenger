package com.example.massenger.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.massenger.MainActivity
import com.example.massenger.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SingInActivity : AppCompatActivity() {

    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var create_new_account: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_in)
        supportActionBar?.hide()

        email = findViewById(R.id.log_email)
        password = findViewById(R.id.log_password)
        create_new_account = findViewById(R.id.sing_in_new_account)
        auth = FirebaseAuth.getInstance()

        create_new_account.setOnClickListener {
            var i = Intent(this,SingUpActivity::class.java)
            startActivity(i)
        }

       val login_btn: Button = findViewById<Button>(R.id.log_btn)
       login_btn.setOnClickListener {

           val email_signIn = email.text.toString()
           val password_signIn = password.text.toString()


           auth.signInWithEmailAndPassword(email_signIn, password_signIn)
               .addOnCompleteListener(this) { task ->
                   if (task.isSuccessful) {
                       // Sign in success, update UI with the signed-in user's information
                       Log.d("SingInActivity", "signInWithEmail:success")
                       val user = auth.currentUser
                       updateUI(user)
                   } else {
                       // If sign in fails, display a message to the user.
                       Log.w("SingInActivity", "signInWithEmail:failure", task.exception)

                       val errorCode = (task.exception as? FirebaseAuthException)?.errorCode
                       val errorMessage = when (errorCode) {
                           "ERROR_INVALID_EMAIL" -> "Invalid email address."
                           "ERROR_WRONG_PASSWORD" -> "Invalid password."
                           "ERROR_USER_DISABLED" -> "User account has been disabled."
                           else -> "Authentication failed."
                       }

//                       val eerrorMessage = when {
//                           task.exception is FirebaseAuthInvalidCredentialsException -> "Invalid email or password."
//                           task.exception is FirebaseAuthInvalidUserException -> "User not found."
//                           else -> "Authentication failed."
//                       }

                       Toast.makeText(baseContext, errorMessage, Toast.LENGTH_SHORT).show()
                       updateUI(null)
                   }
               }

       }
    }
    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            // User is signed in, navigate to the desired activity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // User is not signed in, update UI accordingly
            Toast.makeText(this,"User not Sing up. Please sign up firstly",Toast.LENGTH_SHORT).show()
        }
    }
}