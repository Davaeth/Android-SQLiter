package com.example.davaeth.android_sqliter.activities.signing

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.davaeth.android_sqliter.R
import com.example.davaeth.android_sqliter.activities.phones.DataListActivity
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

    }

    fun signing(v: View?) {
        /**
         * Check if fields are not blank.
         */
        if (checkIsNotBlank()) {

            try {
                this.user = db.getUserByUsername(login_usernameText.text.toString())
            } catch (e: NullPointerException) {
                println("Error: $e")
            }

            // Check if user with sent nickname was found
            if (this.user != null) {
                if (this.user!!.password == login_passwordText.text.toString()) {
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

    private fun checkIsNotBlank(): Boolean {
        return login_usernameText.text.isNotBlank() && login_passwordText.text.isNotBlank()
    }

    private fun initDB() {
        db = UserHandler(this)
    }
}
