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
import com.example.davaeth.android_sqliter.database.PhoneHandler
import com.example.davaeth.android_sqliter.global.GlobalVariables
import com.example.davaeth.android_sqliter.models.Phone
import kotlinx.android.synthetic.main.activity_data_list.*

class DataListActivity : AppCompatActivity() {

    private lateinit var dbPhones: PhoneHandler

    private var phonesList: List<Phone>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDB()

        setContentView(R.layout.activity_data_list)

        initPhonesList()

        for (phone in dbPhones.phones) {
            println("DataList ::  all phones id: " + phone.id)
        }

//        for (phone in dbPhones.getUserPhones(intent.getIntExtra("loggedUser", 1))!!) {
//            println("DataList :: all user phones id: " + phone.id)
//        }

        setRecyclerView()

        setSupportActionBar(findViewById(R.id.dataList_phoneBar))
    }

    /**
     * Method that adds actions to the toolbar.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_phone, menu)

        return true
    }

    /**
     * Toolbar actions functionalities.
     */
    override fun onOptionsItemSelected(item: MenuItem?) = when (item!!.itemId) {

        /**
         * Action that adds new phone to the database.
         */
        R.id.action_add_phone -> {
            val intent: Intent = Intent(this, PhoneActivity::class.java).apply {
                putExtra("isNewPhone", true)
                putExtra("loggedUser", intent.getIntExtra("loggedUser", 0))
            }

            startActivity(intent)

            true
        }

        /**
         * Action that removes selected phones from the database
         */
        R.id.action_remove_phone -> {
            if (GlobalVariables.getSelectedPhones().count() > 0) {
                for (phoneID in GlobalVariables.getSelectedPhones()) {
                    this.dbPhones.deletePhone(phoneID)

                    GlobalVariables.removeSelectedPhone(phoneID)
                }
                reloadRecycler()
            } else {
                reloadRecycler()
            }

            true
        }

        /**
         * Action that logs out the user.
         * Also it removes every single extras
         * that was ever made in application.
         */
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

    private fun reloadRecycler() {
        val intent: Intent = Intent(this, DataListActivity::class.java).apply {
            putExtra("isNewPhone", true)
            putExtra("loggedUser", intent.getIntExtra("loggedUser", 1))
            putExtra("phoneID", intent.getIntExtra("phoneID,", 1))
        }

        startActivity(intent)
    }

    /**
     *  Print data about selected phone in the template form
     *  if it's and edit mode, not add.
     *  Or refresh the recycler view.
     */
    private fun setRecyclerView() {
        initPhonesList()


    }

    /**
     * Method that initialises list of phones.
     */
    private fun initPhonesList() {
        if (phonesList != null) {
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = PhoneAdapter(phonesList!!, this, intent.getIntExtra("loggedUser", 0))
            recyclerView.setHasFixedSize(true)
        }
    }

    /**
     * Method that initialise database.
     */
    private fun initDB() {
        this.dbPhones = PhoneHandler(this)

        if (intent.hasExtra("loggedUser")) {
            phonesList = dbPhones.getUserPhones(intent.getIntExtra("loggedUser", 1))
        } else {
            println("DataList :: user not logged")
        }
    }
}
