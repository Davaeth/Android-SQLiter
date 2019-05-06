package com.example.davaeth.android_sqliter.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.davaeth.android_sqliter.models.Phone
import com.example.davaeth.android_sqliter.models.User

class UserPhonesHandler(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, dbVersion) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($ID_USERS INTEGER, $ID_PHONES INTEGER, FOREIGN KEY($ID_USERS) REFERENCES Users(id), FOREIGN KEY($ID_PHONES) REFERENCES Phones(id));"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        dbVersion += 1
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addUserPhone(id_user: Int, id_phone: Int): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(ID_USERS, id_user)
        values.put(ID_PHONES, id_phone)

        val success = db.insert(TABLE_NAME, null, values)

        db.close()

        return (Integer.parseInt("$success") != -1)
    }

    fun getUsers(phone: Int): List<User>? {
        val usersList = ArrayList<User>()
        val db = this.readableDatabase

        try {

            val selectQuery = "SELECT DISTINCT * FROM $USERS WHERE $TABLE_NAME.id_phones = '$phone' AND $USERS.id = $TABLE_NAME.id_users"

            val cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    val user = User()

                    user.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    user.username = cursor.getString(cursor.getColumnIndex(USERNAME))
                    user.password = cursor.getString(cursor.getColumnIndex(PASSWORD))
                    user.email = cursor.getString(cursor.getColumnIndex(EMAIL))

                    usersList.add(user)
                } while (cursor.moveToNext())
            } else {
                cursor.close()
                db.close()
                return null
            }

            cursor.close()
        } catch (e: SQLiteException) {
            Log.w("Exception: ", e)
        } finally {
            db.close()
        }

        return usersList
    }

    fun getPhones(user: Int): List<Phone>? {
        val phonesList = ArrayList<Phone>()
        val db = this.readableDatabase

        try {

            val selectQuery = "SELECT DISTINCT * FROM $PHONES, $TABLE_NAME WHERE id_users = '$user'"

            val cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    val phone = Phone()

                    phone.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    phone.brand = cursor.getString(cursor.getColumnIndex(BRAND))
                    phone.model = cursor.getString(cursor.getColumnIndex(MODEL))
                    phone.systemVersion = cursor.getFloat(cursor.getColumnIndex(SYSTEM_VERSION))
                    phone.website = cursor.getString(cursor.getColumnIndex(WEBSITE))

                    phonesList.add(phone)
                } while (cursor.moveToNext())
            } else {
                cursor.close()
                db.close()
                return null
            }

            cursor.close()
        } catch (e: SQLiteException) {
            Log.w("Exception: ", e)
        } finally {
            db.close()
        }

        return phonesList
    }

    fun deletePhone(id: Int): Boolean {
        val db = this.writableDatabase
        val success = db.delete(TABLE_NAME, "$ID_PHONES=?", arrayOf(id.toString())).toLong()

        db.close()

        return Integer.parseInt("$success") != -1
    }

    companion object {
        private var dbVersion = 7
        private const val DB_NAME = "Severian"
        private const val TABLE_NAME = "UserPhones"
        private const val ID = "id"

        // UserPhones
        private const val ID_USERS = "id_users"
        private const val ID_PHONES = "id_phones"

        //User
        private const val USERS = "Users"
        private const val USERNAME = "username"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"

        //Phone
        private const val PHONES = "Phones"
        private const val BRAND = "brand"
        private const val MODEL = "model"
        private const val SYSTEM_VERSION = "system_version"
        private const val WEBSITE = "website"
    }
}