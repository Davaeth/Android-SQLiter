package com.example.davaeth.android_sqliter.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.davaeth.android_sqliter.R
import com.example.davaeth.android_sqliter.adapters.PhoneAdapter
import com.example.davaeth.android_sqliter.database.PhoneHandler
import com.example.davaeth.android_sqliter.database.UserHandler
import com.example.davaeth.android_sqliter.models.Phone
import kotlinx.android.synthetic.main.activity_data_list.*

class DataListActivity : AppCompatActivity() {

    lateinit var dbPhones: PhoneHandler
    lateinit var dbUsers: UserHandler

    private lateinit var phonesList: Phone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_list)

        initDB()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PhoneAdapter(phonesList, this)
        recyclerView.setHasFixedSize(true)
    }


    private fun initDB() {
        dbPhones = PhoneHandler(this)
        dbUsers = UserHandler(this)
    }
}
