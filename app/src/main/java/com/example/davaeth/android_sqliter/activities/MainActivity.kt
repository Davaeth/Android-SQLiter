package com.example.davaeth.android_sqliter.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.example.davaeth.android_sqliter.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
