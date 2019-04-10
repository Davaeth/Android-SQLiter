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
import java.sql.SQLDataException

class LoginActivity : AppCompatActivity() {

    private lateinit var db: UserHandler
    private var user: User ? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initDB()

        for(user in db.users) {
            println("USERS NAMES: ${user.username} AND USER ID: ${user.id} AND THE PASSWORD: ${user.password}")
        }
    }

    fun signing(v: View?) {
        if (checkIsBlank()) {

            try {
                this.user = db.getUser(login_usernameText.text.toString())
            } catch (e: SQLDataException) {
                println("Error: $e")
            }

            if (this.user != null) {

                if (user!!.password == login_passwordText.text.toString()) {
                    val intent = Intent(this, MainActivity::class.java).apply {}

                    //startActivity(intent)

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
