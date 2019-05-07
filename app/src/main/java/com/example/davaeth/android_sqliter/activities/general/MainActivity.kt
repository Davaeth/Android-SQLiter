package com.example.davaeth.android_sqliter.activities.general

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.davaeth.android_sqliter.R
import com.example.davaeth.android_sqliter.activities.signing.LoginActivity
import com.example.davaeth.android_sqliter.activities.signing.RegisterActivity
import com.example.davaeth.android_sqliter.database.UserHandler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * Initializing whole database.
         */
        val dbUsers = UserHandler(this)

        for (user in dbUsers.users) {
            println(user)
        }
    }

    fun switchToLoginActivity(v: View?) {
        val intent = Intent(this, LoginActivity::class.java).apply {
        }

        startActivity(intent)
    }

    fun switchToRegisterActivity(v: View?) {
        val intent = Intent(this, RegisterActivity::class.java).apply {
        }

        startActivity(intent)
    }


}
