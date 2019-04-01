package com.example.davaeth.android_sqliter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.phones_list.view.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                view.phoneType.text = "Elo"
            }
            view.setOnLongClickListener {
                view.phoneMark.text = "elo2"
                return@setOnLongClickListener true
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MainAdapter.MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.phones_list, parent, false)

        return MainViewHolder(cellForRow)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {

    }

    override fun getItemCount() = 2
}