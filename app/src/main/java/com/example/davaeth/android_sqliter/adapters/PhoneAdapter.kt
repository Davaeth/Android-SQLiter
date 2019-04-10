package com.example.davaeth.android_sqliter.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.davaeth.android_sqliter.R
import com.example.davaeth.android_sqliter.models.Phone
import kotlinx.android.synthetic.main.users_list.view.*

class PhoneAdapter(private var phone: Phone, internal var context: Context) :
    RecyclerView.Adapter<PhoneAdapter.PhoneViewAdapter>() {

    class PhoneViewAdapter(view: View, phone: Phone) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                view.user_Nickname.text = phone.brand
            }
            view.setOnLongClickListener {
                view.user_Email.text = phone.model
                return@setOnLongClickListener true
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewAdapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.users_list, parent, false)

        return PhoneViewAdapter(view, this.phone)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: PhoneViewAdapter, position: Int) {
    }

    override fun getItemCount() = 1
}