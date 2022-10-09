package com.fathan.core2.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fathan.core2.databinding.ListItemUserBinding
import com.fathan.core2.domain.model.User

class UserAdapter(private val user: ArrayList<User>, private val clickListener: (String, View) -> Unit):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setList(items: List<User>?) {
        user.apply {
            clear()
            items?.let { addAll(it) }
        }
        notifyDataSetChanged()
    }
    inner class UserViewHolder(private val binding: ListItemUserBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: User, click: (String, View) -> Unit){
            binding.data = user
            binding.root.transitionName = user.login
            binding.root.setOnClickListener { user.login?.let { i -> click(i, binding.root) } }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(ListItemUserBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(user[position], clickListener)
    }

    override fun getItemCount(): Int {
        return user.size
    }
}