package com.example.massenger.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.massenger.adapter.newmassageview
import com.example.massenger.R
import com.example.massenger.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class NewMassageActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: newmassageview
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_massage)

        supportActionBar?.title = "Select User"

        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("user")

        userList = ArrayList()
        adapter = newmassageview(this,userList)

        recyclerView = findViewById(R.id.Recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter

        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val startTime = System.currentTimeMillis()
                userList.clear()
                for (postSnapshot in snapshot.children) {
                    val currentuser = postSnapshot.getValue(User::class.java)
                    userList.add(currentuser!!)
                    Log.d("UserList", "done")
                }
                adapter.notifyDataSetChanged() // Notify the adapter about the data change
                recyclerView.visibility = View.VISIBLE
                for (user in userList) {
                    Log.d("UserList", "Username: ${user.username}, UID: ${user.uid}")
                }
                val endTime = System.currentTimeMillis()
                Log.d("UserList", "Time taken: ${endTime - startTime}ms")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("NewMassageActivity", "Database operation cancelled: ${error.message}")
            }
        })

  }
}


