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

class newmassageview(
    val context: Context,
    var userList: ArrayList<User>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<newmassageview.UserViewHolder>() {


    interface OnItemClickListener {
        fun onItemClick(user: User)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.user_card, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.userNameTextView.text = currentUser.username

        Picasso.get()
            .load(currentUser.imageUri) // Use the "imageUri" property for the user photo URL
            .into(holder.userImageView)

        // Set click listener for each item
        holder.itemView.setOnClickListener {
            listener.onItemClick(currentUser)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
        val userImageView: ImageView = itemView.findViewById(R.id.userImageView)
    }
}