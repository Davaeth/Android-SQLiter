package com.example.davaeth.android_sqliter.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.davaeth.android_sqliter.R

class MainActivity : AppCompatActivity() {

//    var db: UserHandler = UserHandler(this)
//    var listUsers: List<Users> = ArrayList()
//    var user: Users = Users("Patryk", "Vege", "elo@gmail.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        initDB()
//
//        recyclerView_main.layoutManager = LinearLayoutManager(this)
//        recyclerView_main.adapter = MainAdapter(listUsers, this)
//        recyclerView_main.setHasFixedSize(true)

//        deleteUserBtn.setOnClickListener {
//            db.deleteUser(0)
//        }
    }

//    fun initDB() {
//        db = UserHandler(this)
//        db.addUser(this.user)
//        listUsers = db.users
//    }
}
