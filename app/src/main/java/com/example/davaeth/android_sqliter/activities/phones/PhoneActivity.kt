package com.example.davaeth.android_sqliter.activities.phones

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.davaeth.android_sqliter.R
import com.example.davaeth.android_sqliter.database.PhoneHandler
import com.example.davaeth.android_sqliter.models.Phone
import kotlinx.android.synthetic.main.activity_phone.*

class PhoneActivity : AppCompatActivity() {

    private lateinit var phonesDB: PhoneHandler

    private var phone: Phone? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone)

        initDB()

        if (!intent.getBooleanExtra("isNewPhone", true) && phone != null) {
            phoneTemplate_brand.setText(phone!!.brand)
            phoneTemplate_model.setText(phone!!.model)
            phoneTemplate_systemVersion.setText(phone!!.systemVersion.toString())
            phoneTemplate_website.setText(phone!!.website)
        }
    }

    /**
     * Method that searches Internet for a path sent by user.
     */
    fun searchInternet(view: View) {
        if (phoneTemplate_website.text.isNotBlank()) {
            if (phoneTemplate_website.text.startsWith("http://www.") || phoneTemplate_website.text.startsWith("https://www.")) {
                val uriURL: Uri = Uri.parse("${phoneTemplate_website.text}")

                val searchInWeb = Intent(Intent.ACTION_VIEW, uriURL)

                startActivity(searchInWeb)
            } else {
                Toast.makeText(this, "Address should start with https://www.", Toast.LENGTH_LONG).show()
                phoneTemplate_website.setBackgroundColor(Color.RED)
            }
        } else {
            Toast.makeText(this, "Website field cannot be blank!", Toast.LENGTH_LONG).show()
            phoneTemplate_website.setBackgroundColor(Color.RED)
        }
    }

    fun cancelPhoneTemplate(view: View) {
        backToPhonesList()
    }

    //region Save/Update phone

    /**
     * Method that saves OR updates the particular phone in database.
     */
    fun savePhoneTemplate(view: View) {
        if (getTemplateData() != null) {
            if (intent.getBooleanExtra("isNewPhone", true)) {
                if (phoneTemplate_website.text.startsWith("http://www.") || phoneTemplate_website.text.startsWith("https://www.")) {
                    savePhone()

                    phoneTemplate_website.setBackgroundColor(0)
                    Toast.makeText(this, "Phone added successfully!", Toast.LENGTH_LONG).show()

                    backToPhonesList()
                } else {
                    Toast.makeText(this, "Address should start with https://www.", Toast.LENGTH_LONG).show()
                    phoneTemplate_website.setBackgroundColor(Color.RED)
                }
            } else {
                if (phoneTemplate_website.text.startsWith("http://www.") || phoneTemplate_website.text.startsWith("https://www.")) {
                    updatePhone()

                    phoneTemplate_website.setBackgroundColor(0)
                    Toast.makeText(this, "Phone updated successfully!", Toast.LENGTH_LONG).show()

                    backToPhonesList()
                } else {
                    Toast.makeText(this, "Address should start with https://www.", Toast.LENGTH_LONG).show()
                    phoneTemplate_website.setBackgroundColor(Color.RED)
                }
            }
        }
    }

    private fun updatePhone() {
        if (getTemplateData() != null) {
            val phone: Phone = getTemplateData()!!
            phone.id = intent.getIntExtra("phoneID", 0)

            phonesDB.updatePhone(phone)
        }
    }

    private fun savePhone() {
        if (getTemplateData() != null) {
            val phone: Phone = getTemplateData()!!

            phonesDB.addPhone(phone)
        }
    }

    //endregion

    private fun initDB() {
        phonesDB = PhoneHandler(this)

        if (!intent.getBooleanExtra("isNewPhone", true)) {
            if (intent.hasExtra("phoneID")) {
                phone = phonesDB.getPhone(intent.getIntExtra("phoneID", 0))
            } else {
                Toast.makeText(this, "Wrong phone id!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Method that receives data posted by user.
     */
    private fun getTemplateData(): Phone? {
        val phone = Phone()

        if (checkIsNotBlank()) {

            phoneTemplate_brand.setBackgroundColor(0)
            phoneTemplate_model.setBackgroundColor(0)
            phoneTemplate_systemVersion.setBackgroundColor(0)
            phoneTemplate_website.setBackgroundColor(0)

            phone.brand = phoneTemplate_brand.text.toString()
            phone.model = phoneTemplate_model.text.toString()
            phone.systemVersion = phoneTemplate_systemVersion.text.toString().toFloat()
            phone.website = phoneTemplate_website.text.toString()
            phone.userID = intent.getIntExtra("loggedUser", 0)

            return phone
        } else {
            Toast.makeText(this, "Fields cannot be blank!", Toast.LENGTH_LONG).show()

            phoneTemplate_brand.setBackgroundColor(Color.RED)
            phoneTemplate_model.setBackgroundColor(Color.RED)
            phoneTemplate_systemVersion.setBackgroundColor(Color.RED)
            phoneTemplate_website.setBackgroundColor(Color.RED)

            return null
        }
    }

    private fun backToPhonesList() {
        val intent: Intent = Intent(this, DataListActivity::class.java).apply {
            putExtra("loggedUser", intent.getIntExtra("loggedUser", 0))
        }

        startActivity(intent)
    }

    /**
     * Method that check if template form inputs are blank.
     */
    private fun checkIsNotBlank(): Boolean =
        phoneTemplate_brand.text.isNotBlank()
                && phoneTemplate_model.text.isNotBlank()
                && phoneTemplate_systemVersion.text.isNotBlank()
                && phoneTemplate_website.text.isNotBlank()

}
