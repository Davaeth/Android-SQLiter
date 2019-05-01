package com.example.davaeth.android_sqliter.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.davaeth.android_sqliter.R
import com.example.davaeth.android_sqliter.activities.phones.PhoneActivity
import com.example.davaeth.android_sqliter.models.Phone
import kotlinx.android.synthetic.main.user_phones_list.view.*

class PhoneAdapter(private var phone: Phone, internal var context: Context) :
    RecyclerView.Adapter<PhoneAdapter.PhoneViewAdapter>() {

    class PhoneViewAdapter(view: View, phone: Phone, context: Context) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                val intent: Intent = Intent(context, PhoneActivity::class.java).apply {
                    putExtra("isNewPhone", false)
                    putExtra("phoneID", phone.id)
                }

                context.startActivity(intent)

                view.setBackgroundColor(0)
            }

            view.setOnLongClickListener {

                view.setBackgroundColor(Color.LTGRAY)

                return@setOnLongClickListener true
            }
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewAdapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_phones_list, parent, false)

        view.phoneModel.text = this.phone.model
        view.phoneBrand.text = this.phone.brand

        return PhoneViewAdapter(view, this.phone, this.context)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: PhoneViewAdapter, position: Int) {
    }

    override fun getItemCount() = 10
}