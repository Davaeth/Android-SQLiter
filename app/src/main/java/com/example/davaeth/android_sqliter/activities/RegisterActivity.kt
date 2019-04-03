package com.example.davaeth.android_sqliter.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.davaeth.android_sqliter.R
import com.example.davaeth.android_sqliter.database.UserHandler
import com.example.davaeth.android_sqliter.models.Users
import kotlinx.android.synthetic.main.activity_register.*
import java.sql.SQLDataException

class RegisterActivity : AppCompatActivity() {

    lateinit var db: UserHandler

    lateinit var user: Users

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initDB()

        register_usernameText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
    }

    fun signUp(v: View?) {
        if (checkIsBlank() && checkIsEmail()) {

            user = Users(
                register_usernameText.text.toString(),
                register_passwordText.text.toString(),
                register_emailText.text.toString()
            )

            try {
                db.addUser(this.user)
            } catch (e: SQLDataException) {
                println(e.message)
            }

            val intent = Intent(this, MainActivity::class.java).apply {}

            startActivity(intent)

            Toast.makeText(this, "Registered successfully!", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Check your data!", Toast.LENGTH_SHORT).show()
        }

        if(!checkIsEmail()) {
            register_emailText.setBackgroundColor(Color.RED)
            Toast.makeText(this, "This is not an email!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initDB() {
        db = UserHandler(this)
    }

    private fun checkIsBlank(): Boolean {
        return register_usernameText.text.isNotBlank() && register_passwordText.text.isNotBlank() && register_emailText.text.isNotBlank()
    }

    private fun checkIsEmail(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(register_emailText.text).matches()
    }
}
