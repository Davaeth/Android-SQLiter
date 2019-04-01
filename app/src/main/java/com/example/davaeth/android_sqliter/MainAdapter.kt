package com.example.davaeth.android_sqliter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.davaeth.android_sqliter.models.Users
import kotlinx.android.synthetic.main.users_list.view.*

class MainAdapter(userList: List<Users>, internal var context: Context) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    var userList: List<Users> = ArrayList()

    init {
        this.userList = userList
    }

    class MainViewHolder(view: View, userList: List<Users>) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                view.nickname.text = userList.get(2).nickname
            }
            view.setOnLongClickListener {
                view.email.text = userList.get(2).email
                return@setOnLongClickListener true
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MainViewHolder {
        val cellForRow = LayoutInflater.from(parent.context).inflate(R.layout.users_list, parent, false)

        return MainViewHolder(cellForRow, this.userList)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

    }

    override fun getItemCount() = this.userList.size
}