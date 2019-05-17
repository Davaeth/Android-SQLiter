package com.example.davaeth.android_sqliter.activities.signing

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.InputType
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.davaeth.android_sqliter.R
import com.example.davaeth.android_sqliter.activities.general.MainActivity
import com.example.davaeth.android_sqliter.database.UserHandler
import com.example.davaeth.android_sqliter.models.User
import kotlinx.android.synthetic.main.activity_register.*
import java.sql.SQLDataException

class RegisterActivity : AppCompatActivity() {

    lateinit var db: UserHandler

    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initDB()

        register_usernameText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
    }

    fun signUp(v: View?) {

        /**
         * If template was completed properly add user to the database.
         */
        if (checkIsNotBlank() && checkIsEmail() && !checkIsUniqueEmail() && !checkUsername() && checkPassword()) {
            user = User(
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

            /**
             * Check if every field is correctly filled.
             */
            //region Checking fields
            if (checkIsNotBlank()) {
                register_usernameText.setBackgroundColor(0)
                register_emailText.setBackgroundColor(0)
                register_passwordText.setBackgroundColor(0)

                /**
                 * Checking username field.
                 */
                if (checkUsername()) {
                    register_usernameText.setBackgroundColor(Color.RED)

                    Toast.makeText(this, "This is username is already taken!", Toast.LENGTH_LONG).show()
                } else {
                    register_usernameText.setBackgroundColor(0)
                }

                /**
                 * Checking email field.
                 */
                if (!checkIsEmail()) {
                    register_emailText.setBackgroundColor(Color.RED)

                    Toast.makeText(this, "This is not an email!", Toast.LENGTH_LONG).show()
                } else {
                    if (checkIsUniqueEmail()) {
                        register_emailText.setBackgroundColor(Color.RED)

                        Toast.makeText(this, "This email is already taken!", Toast.LENGTH_LONG).show()
                    } else {
                        register_emailText.setBackgroundColor(0)
                    }
                }

                /**
                 * Checking password field.
                 */
                if (!checkPassword()) {
                    register_passwordText.setBackgroundColor(Color.RED)

                    Toast.makeText(this, "At least 5 characters!", Toast.LENGTH_LONG).show()
                } else {
                    register_passwordText.setBackgroundColor(0)
                }
            } else {
                register_usernameText.setBackgroundColor(Color.RED)
                register_emailText.setBackgroundColor(Color.RED)
                register_passwordText.setBackgroundColor(Color.RED)

                Toast.makeText(this, "Fields cannot be blank!", Toast.LENGTH_LONG).show()
            }
            //endregion
        }
    }

    private fun initDB() {
        db = UserHandler(this)
    }

    //region Fields checking methods
    /**
     * Method that check if template form inputs are blank.
     */
    private fun checkIsNotBlank(): Boolean = register_usernameText.text.isNotBlank()
            && register_passwordText.text.isNotBlank()
            && register_emailText.text.isNotBlank()

    /**
     * Method that checks if email input contains exactly email.
     */
    private fun checkIsEmail(): Boolean = Patterns.EMAIL_ADDRESS.matcher(register_emailText.text).matches()

    /**
     * Method that checks if specific email is already taken.
     */
    private fun checkIsUniqueEmail(): Boolean = db.getUserByEmail(register_emailText.text.toString()) != null

    /**
     * Method that checks if username is unique.
     */
    private fun checkUsername(): Boolean = db.getUserByUsername(register_usernameText.text.toString()) != null

    /**
     * Method that checks if password is validate.
     */
    private fun checkPassword(): Boolean = register_passwordText.text.length >= 5
    //endregion
}
