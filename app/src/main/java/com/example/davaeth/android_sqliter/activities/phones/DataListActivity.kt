package com.example.davaeth.android_sqliter.activities.phones

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.example.davaeth.android_sqliter.R
import com.example.davaeth.android_sqliter.activities.general.MainActivity
import com.example.davaeth.android_sqliter.adapters.PhoneAdapter
import com.example.davaeth.android_sqliter.database.UserPhonesHandler
import com.example.davaeth.android_sqliter.models.Phone
import kotlinx.android.synthetic.main.activity_data_list.*

class DataListActivity : AppCompatActivity() {

    private lateinit var dbUserPhones: UserPhonesHandler

    private var phonesList: List<Phone>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDB()

        setContentView(R.layout.activity_data_list)

        // Print data about selected phone in the template form
        // if it's and edit mode, not add
        if (phonesList != null) {
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = PhoneAdapter(phonesList!!, this, intent.getIntExtra("loggedUser", 0))
                recyclerView.setHasFixedSize(true)
        }

        setSupportActionBar(findViewById(R.id.dataList_phoneBar))
    }

    /**
     * Method that adds actions to the toolbar
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_phone, menu)

        return true
    }

    /**
     * Toolbar actions functionalities
     */
    override fun onOptionsItemSelected(item: MenuItem?) = when (item!!.itemId) {

        R.id.action_add_phone -> {
            val intent: Intent = Intent(this, PhoneActivity::class.java).apply {
                putExtra("isNewPhone", true)
                putExtra("loggedUser", intent.getIntExtra("loggedUser", 0))
            }

            startActivity(intent)

            true
        }

        R.id.action_remove_phone -> {
            true
        }

        R.id.action_logout -> {
            val intent: Intent = Intent(this, MainActivity::class.java).apply {
                removeExtra("loggedUser")
                removeExtra("phoneID")
                removeExtra("isNewPhone")
            }

            startActivity(intent)

            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }


    private fun initDB() {
        val dbUserPhones = UserPhonesHandler(this)

        if (intent.hasExtra("loggedUser")) {
            phonesList = dbUserPhones.getPhones(intent.getIntExtra("loggedUser", 0))

        } else {
            println("DataList :: user not logged")
        }
    }
}
