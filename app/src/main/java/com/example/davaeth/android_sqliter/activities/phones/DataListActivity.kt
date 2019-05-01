package com.example.davaeth.android_sqliter.activities.phones

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.davaeth.android_sqliter.R
import com.example.davaeth.android_sqliter.adapters.PhoneAdapter
import com.example.davaeth.android_sqliter.database.PhoneHandler
import com.example.davaeth.android_sqliter.database.UserHandler
import com.example.davaeth.android_sqliter.models.Phone
import kotlinx.android.synthetic.main.activity_data_list.*

class DataListActivity : AppCompatActivity() {

    private lateinit var dbPhones: PhoneHandler
    private lateinit var dbUsers: UserHandler

    private lateinit var phonesList: Phone

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_list)

        initDB()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PhoneAdapter(phonesList, this)
        recyclerView.setHasFixedSize(true)

        setSupportActionBar(findViewById(R.id.dataList_phoneBar))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_phone, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?) = when (item!!.itemId) {

        R.id.action_add_phone -> {
            val intent: Intent = Intent(this, PhoneActivity::class.java).apply {
                putExtra("isNewPhone", true)
            }

            startActivity(intent)

            true
        }

        R.id.action_remove_phone -> {
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }


    private fun initDB() {
        dbPhones = PhoneHandler(this)
        dbUsers = UserHandler(this)

        phonesList = Phone("XIAOMI", "Mi MAX 3")
    }
}
