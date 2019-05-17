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
import com.example.davaeth.android_sqliter.global.GlobalVariables
import com.example.davaeth.android_sqliter.models.Phone
import kotlinx.android.synthetic.main.user_phones_list.view.*

class PhoneAdapter(private var phonesList: List<Phone>, internal var context: Context, private var userID: Int) :
    RecyclerView.Adapter<PhoneAdapter.PhoneViewAdapter>() {

    private var position: Int = 0
    private var positionList: MutableList<Int> = mutableListOf()

    class PhoneViewAdapter(
        view: View,
        phone: List<Phone>,
        context: Context,
        userID: Int,
        position: Int,
        positionList: MutableList<Int>
    ) : RecyclerView.ViewHolder(view) {

        /**
         * Init of the ViewAdapter class.
         * Mostly it stores user touches events.
         */
        init {
            //region OnClickListener
            view.setOnClickListener {

                /**
                 * Allow phone edit option only when there is no element in multi selection stack.
                 */
                if (positionList.count() == 0) {
                    val intent: Intent = Intent(context, PhoneActivity::class.java).apply {
                        putExtra("isNewPhone", false)
                        putExtra("phoneID", phone[position].id)
                        putExtra("loggedUser", userID)
                    }

                    context.startActivity(intent)
                } else if (positionList.count() > 0) {
                    if (positionList.toList().contains(phone[position].id)) {
                        positionList.remove(phone[position].id)
                        GlobalVariables.removeSelectedPhone(phone[position].id)

                        view.setBackgroundColor(0)
                    } else {
                        positionList.add(phone[position].id)
                        GlobalVariables.addSelectedPhone(phone[position].id)

                        view.setBackgroundColor(Color.LTGRAY)
                    }
                }
            }
            //endregion

            //region OnHoldListener
            view.setOnLongClickListener {

                /**
                 * Put element at the multi selection stack
                 * and check if this element isn't already at stack.
                 */
                if (!positionList.toList().contains(phone[position].id)) {
                    positionList.add(phone[position].id)
                    GlobalVariables.addSelectedPhone(phone[position].id)

                    view.setBackgroundColor(Color.LTGRAY)
                } else {
                    positionList.remove(phone[position].id)
                    GlobalVariables.removeSelectedPhone(phone[position].id)

                    view.setBackgroundColor(0)
                }

                return@setOnLongClickListener true
            }
            //endregion
        }
    }

    /**
     * Method that creates content in the recycler view
     * and optionally does something with it.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhoneViewAdapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_phones_list, parent, false)

        return PhoneViewAdapter(view, this.phonesList, this.context, this.userID, position, this.positionList)
    }

    /**
     * Method that does something before anything is even shown,
     * but after creating.
     */
    override fun onBindViewHolder(holder: PhoneViewAdapter, position: Int) {
        this.position = position + 1

        holder.itemView.phoneModel.text = this.phonesList[position].model
        holder.itemView.phoneBrand.text = this.phonesList[position].brand
    }

    /**
     * Method that specifies how many rows in content will be shown.
     */
    override fun getItemCount() = phonesList.count()
}