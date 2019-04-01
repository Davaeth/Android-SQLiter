package com.example.davaeth.android_sqliter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.davaeth.android_sqliter.database.DatabaseHandler
import com.example.davaeth.android_sqliter.models.Users
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var db: DatabaseHandler = DatabaseHandler(this)
    var listUsers: List<Users> = ArrayList<Users>()
    var user: Users = Users("Patryk", "Vege", "elo@gmail.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initDB()

        recyclerView_main.layoutManager = LinearLayoutManager(this)
        recyclerView_main.adapter = MainAdapter(listUsers, this)
        recyclerView_main.setHasFixedSize(true)
    }

    fun initDB() {
        db = DatabaseHandler(this)
        db.addUser(this.user)
        listUsers = db.users
    }
}
