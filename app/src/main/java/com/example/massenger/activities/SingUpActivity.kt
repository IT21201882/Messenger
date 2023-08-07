package com.example.massenger.activities

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.massenger.R
import com.google.firebase.auth.FirebaseAuth
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.IOException
import java.util.UUID

class SingUpActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var email: EditText
    private lateinit var phonenumber: EditText
    private lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)
        supportActionBar?.hide()

        username = findViewById(R.id.reg_username)
        email = findViewById(R.id.reg_email)
        phonenumber = findViewById(R.id.reg_Phonenumber)
        password = findViewById(R.id.reg_password)

        val reg_btn:Button = findViewById<Button>(R.id.reg_register_btn)
        reg_btn.setOnClickListener {
            perform_register()
        }

        FirebaseAuth.getInstance()
        val singin:TextView = findViewById(R.id.already_have_an_account)
        singin.setOnClickListener{
            Log.d("SingInActivity","Loging Activity")

            val i = Intent(this,SingInActivity::class.java)
            startActivity(i)
        }
        val regselect_photo: Button = findViewById<Button>(R.id.reg_select_photo)
        // Without using override function we can handle select photo section using this function. but we have to use startForResult.lunch(i) code for in the reg_select_button setonclicklistner
//        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//            if (result.resultCode == Activity.RESULT_OK) {
//                val data: Intent? = result.data
//                if (data != null) {
//                    val selectedPhotoUri: Uri? = data.data
//                    if (selectedPhotoUri != null) {
//                        // Process the selected photo URI
//                        try {
//                            val inputStream = contentResolver.openInputStream(selectedPhotoUri)
//                            val bitmap = BitmapFactory.decodeStream(inputStream)
//                            val bitmapDrawable = BitmapDrawable(resources, bitmap)
//                            regselect_photo.background = bitmapDrawable
//                        } catch (e: IOException) {
//                            e.printStackTrace()
//                        }
//                    }
//                }
//            }
//        }

        regselect_photo.setOnClickListener {
            val i = Intent(Intent.ACTION_PICK)
            i.type = "image/*"
            startActivityForResult(i,0)
        }
    }

    var uri:Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val reg_btn: Button = findViewById<Button>(R.id.reg_select_photo)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            Log.d("SignUp","Photo was selected ")
            uri = data.data
            try {
                val inputStream = contentResolver.openInputStream(uri!!)
                val bitmap = BitmapFactory.decodeStream(inputStream)
                val circularBitmapDrawable = RoundedBitmapDrawableFactory.create(resources, bitmap)
                circularBitmapDrawable.isCircular = true
                reg_btn.background = circularBitmapDrawable
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    private fun perform_register() {
        val emailtext = email.text.toString()
        val passwordtext = password.text.toString()

        if(emailtext.isEmpty() || passwordtext.isEmpty()){
            Toast.makeText(this,"Email and Password empty",Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("SingUpActivity", "Email is:$emailtext")
        Log.d("SingUpActivity", "Password is:$passwordtext")

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(emailtext,passwordtext)
            .addOnCompleteListener{
                if(!it.isSuccessful) return@addOnCompleteListener

                //if success full
                Log.d("SingUpActivity","Successfully create user with Uid: ${it.result.user?.uid}")
                uploadImageToFirebase()


            }
            .addOnFailureListener{
                Log.d("SingUpActivity","Failed :${it.message}")
                Toast.makeText(this,"Failed to sign up. Try again :${it.message}",Toast.LENGTH_SHORT).show()

            }
    }

    private fun uploadImageToFirebase() {
        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images")

        ref.putFile(uri!!)
            .addOnSuccessListener { taskSnapshot ->
                // Get the download URL of the uploaded image
                ref.downloadUrl.addOnSuccessListener { uri ->
                    // Call saveUserToFirebaseDatabse with the download URL
                    saveUserToFirebaseDatabse(uri.toString())
                }
            }
            .addOnFailureListener {
                Log.e("Register", "Failed to upload photo: ${it.message}")
            }
    }

    private fun saveUserToFirebaseDatabse(profileImageUri: String) {
        val uid = FirebaseAuth.getInstance().uid?:""
        val ref = FirebaseDatabase.getInstance().getReference("/user/$uid")

        val uname = username.text.toString()
        val pnumber = phonenumber.text.toString()
        val user = User(uid,uname,pnumber,profileImageUri)
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("SingupActivity","Saved user details to firebase")
                val i = Intent(this,latest_chatsActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK )
                startActivity(i)
            }
    }

    class User(val uid:String,val username:String, val phoneNumber: String,val ImageUri:String)

}