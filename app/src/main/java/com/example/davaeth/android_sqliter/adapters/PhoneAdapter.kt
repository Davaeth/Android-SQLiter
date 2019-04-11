package com.example.davaeth.android_sqliter.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.davaeth.android_sqliter.R
import com.example.davaeth.android_sqliter.models.Phone

class PhoneAdapter(private var phone: Phone, internal var context: Context) :
    RecyclerView.Adapter<PhoneAdapter.PhoneViewAdapter>() {

    class PhoneViewAdapter(view: View, phone: Phone) : RecyclerView.ViewHolder(view),
        View.OnLongClickListener, View.OnClickListener {

        override fun onLongClick(v: View?): Boolean {
            TODO("Change activity to `PhoneEditActivity`")
            TODO("Or delete selected phone")
        }

        override fun onClick(v: View?) {
            TODO("Select multiple phones then change activity to `PhoneEditActivity`")
            TODO("Or delete selected phones")
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