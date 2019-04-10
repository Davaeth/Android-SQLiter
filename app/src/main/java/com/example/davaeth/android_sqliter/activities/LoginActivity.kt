package com.example.davaeth.android_sqliter.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.davaeth.android_sqliter.R
import com.example.davaeth.android_sqliter.database.UserHandler
import com.example.davaeth.android_sqliter.models.User
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var db: UserHandler
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initDB()

        println("USERS NAMES: ${db.users[0].username} AND USER ID: ${db.users[0].id} AND THE PASSWORD: ${db.users[0].password}")

    }

    fun signing(v: View?) {
        if (checkIsBlank()) {

            try {
                this.user = db.getUser(login_usernameText.text.toString())
            } catch (e: NullPointerException) {
                println("Error: $e")
            }

            if (this.user != null) {

                if (user!!.password == login_passwordText.text.toString()) {
                    val intent = Intent(this, DataListActivity::class.java).apply {
                        putExtra("loggedUser", user!!.id)
                    }

                    startActivity(intent)

                    Toast.makeText(this, "Logged successfully!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Invalid password!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "User not found!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkIsBlank(): Boolean {
        return login_usernameText.text.isNotBlank() && login_passwordText.text.isNotBlank()
    }

    private fun initDB() {
        db = UserHandler(this)
    }
}
