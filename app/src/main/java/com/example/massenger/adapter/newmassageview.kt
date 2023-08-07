package com.example.massenger.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.massenger.R
import com.example.massenger.User
import com.squareup.picasso.Picasso

class newmassageview(val context: Context, var userList: ArrayList<User>) :
    RecyclerView.Adapter<newmassageview.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.user_card, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.userNameTextView.text = currentUser.username

        // Load the user photo using Picasso
        Picasso.get()
            .load(currentUser.imageUri) // Use the "imageUri" property for the user photo URL
            //.placeholder(R.drawable.placeholder_image) // Optional: show a placeholder image while loading
            //.error(R.drawable.error_image) // Optional: show an error image if loading fails
            .into(holder.userImageView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
        val userImageView: ImageView = itemView.findViewById(R.id.userImageView) // Add ImageView for user photo
    }
}
