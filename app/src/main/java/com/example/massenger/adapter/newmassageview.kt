package com.example.massenger.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.massenger.R
import com.example.massenger.User

class newmassageview(val context: Context, var userList: ArrayList<User>):
    RecyclerView.Adapter<newmassageview.UserViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            UserViewHolder {

        val view = LayoutInflater.from(context)
            .inflate(R.layout.user_card, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentusername = userList[position]
        holder.userNameTextView.text = currentusername.username
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)

    }
}


