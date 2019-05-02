package com.example.davaeth.android_sqliter.adapters

import android.annotation.SuppressLint
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

class PhoneAdapter(private var phonesList: List<Phone>, internal var context: Context, private var userID: Int) :
    RecyclerView.Adapter<PhoneAdapter.PhoneViewAdapter>() {

    private var position: Int = 1
    private var positionList: MutableList<Int> = mutableListOf()

    class PhoneViewAdapter(
        view: View,
        phone: List<Phone>,
        context: Context,
        userID: Int,
        position: Int,
        positionList: MutableList<Int>
    ) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {

                if (positionList.count() == 0) {
                    val intent: Intent = Intent(context, PhoneActivity::class.java).apply {
                        putExtra("isNewPhone", false)
                        putExtra("phoneID", phone[position].id)
                        putExtra("loggedUser", userID)
                    }

                    context.startActivity(intent)
                } else if (positionList.count() > 0) {
                    if (positionList.toList().contains(position)) {
                        positionList.remove(position)

                        view.setBackgroundColor(0)
                    } else {
                        positionList.add(position)

                        view.setBackgroundColor(Color.LTGRAY)
                    }
                }
            }

            view.setOnLongClickListener {

                if (!positionList.toList().contains(position)) {
                    positionList.add(position)

                    view.setBackgroundColor(Color.LTGRAY)
                } else {
                    positionList.remove(position)

                    view.setBackgroundColor(0)
                }

                return@setOnLongClickListener true
            }
        }

    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewAdapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_phones_list, parent, false)

        return PhoneViewAdapter(view, this.phonesList, this.context, this.userID, this.position, this.positionList)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: PhoneViewAdapter, @SuppressLint("RecyclerView") position: Int) {
        this.position = position

        holder.itemView.phoneModel.text = this.phonesList[position].model
        holder.itemView.phoneBrand.text = this.phonesList[position].brand


    }

    override fun getItemCount() = phonesList.count()
}