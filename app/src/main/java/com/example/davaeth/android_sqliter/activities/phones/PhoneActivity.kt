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
import com.example.davaeth.android_sqliter.database.UserPhonesHandler
import com.example.davaeth.android_sqliter.models.Phone
import kotlinx.android.synthetic.main.activity_phone.*

class PhoneActivity : AppCompatActivity() {

    private lateinit var userPhonesDB: UserPhonesHandler
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
     * Method that searches Internet for a path sent by user
     */
    fun searchInternet(view: View) {
        if(phoneTemplate_website.text.startsWith("http://www.") || phoneTemplate_website.text.startsWith("https://www.")) {
            val uriURL: Uri = Uri.parse("${phoneTemplate_website.text}")

            val searchInWeb = Intent(Intent.ACTION_VIEW, uriURL)

            startActivity(searchInWeb)
        } else {
            Toast.makeText(this, "Wrong address!", Toast.LENGTH_LONG).show()
            view.setBackgroundColor(Color.RED)
        }
    }

    fun cancelPhoneTemplate(view: View) {
        backToPhonesList()
    }

    fun savePhoneTemplate(view: View) {
        if(intent.getBooleanExtra("isNewPhone", true)) {
            savePhone()

            Toast.makeText(this, "Phone added successfully!", Toast.LENGTH_LONG).show()

            backToPhonesList()
        } else {
            updatePhone()

            Toast.makeText(this, "Phone updated successfully!", Toast.LENGTH_LONG).show()

            backToPhonesList()
        }
    }

    private fun updatePhone() {
        val phone: Phone = getTemplateData()

        phonesDB.updatePhone(phone)
    }

    private fun savePhone() {
        val phone: Phone = getTemplateData()

        userPhonesDB.addUserPhone(intent.getIntExtra("loggedUser", 0), phone.id)
        phonesDB.addPhone(phone)
    }

    private fun initDB() {
        userPhonesDB = UserPhonesHandler(this)
        phonesDB = PhoneHandler(this)

        for (phone in phonesDB.phones) {
            println(phone)
        }

        if (!intent.getBooleanExtra("isNewPhone", true)) {
            if (intent.hasExtra("phoneID")) {
                phone = phonesDB.getPhone(intent.getIntExtra("phoneID", 0))
            } else {
                Toast.makeText(this, "Wrong phone id!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getTemplateData(): Phone {
        val phone = Phone()

        phone.brand = phoneTemplate_brand.text.toString()
        phone.model = phoneTemplate_model.text.toString()
        phone.systemVersion = phoneTemplate_systemVersion.text.toString().toFloat()
        phone.website = phoneTemplate_website.text.toString()

        return phone
    }

    private fun backToPhonesList() {
        val intent: Intent = Intent(this, DataListActivity::class.java).apply {
            putExtra("loggedUser", intent.getIntExtra("loggedUser", 0))
        }

        startActivity(intent)
    }

}
