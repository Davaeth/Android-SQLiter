package com.example.davaeth.android_sqliter.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.davaeth.android_sqliter.R
import com.example.davaeth.android_sqliter.adapters.PhoneAdapter
import com.example.davaeth.android_sqliter.database.UserHandler
import com.example.davaeth.android_sqliter.models.User
import kotlinx.android.synthetic.main.activity_data_list.*

class DataListActivity : AppCompatActivity() {

    var db: UserHandler = UserHandler(this)
    private lateinit var usersList: User
    var user: User = User("Patryk", "Vege", "elo@gmail.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_list)

        initDB()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PhoneAdapter(usersList, this)
        recyclerView.setHasFixedSize(true)
    }


    private fun initDB() {
        db = UserHandler(this)
        db.addUser(this.user)
    }
}
