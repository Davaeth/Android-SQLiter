package com.example.davaeth.android_sqliter.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.davaeth.android_sqliter.models.User

class UserHandler(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, dbVersion) {

    /**
     * Override method that initializes whole database.
     */
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_USERS_TABLE =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($ID INTEGER PRIMARY KEY,REFERENCE TEXT, $USERNAME TEXT, $PASSWORD TEXT, $EMAIL TEXT);"

        val CREATE_PHONES_TABLE =
            "CREATE TABLE IF NOT EXISTS Phones ($ID INTEGER PRIMARY KEY, REFERENCE TEXT, brand TEXT, model TEXT, system_version FLOAT, website TEXT, user_id INTEGER, FOREIGN KEY (user_id) REFERENCES Users(id));"

        db.execSQL(CREATE_USERS_TABLE)
        db.execSQL(CREATE_PHONES_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        dbVersion += 1
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addUser(user: User): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(USERNAME, user.username)
        values.put(PASSWORD, user.password)
        values.put(EMAIL, user.email)

        val success = db.insert(TABLE_NAME, null, values)

        db.close()

        return (Integer.parseInt("$success") != -1)
    }

    fun getUser(id: Int): User? {
        val user = User()
        val db = this.readableDatabase

        try {

            val selectQuery = "SELECT * FROM $TABLE_NAME WHERE id = '$id'"

            val cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    user.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    user.username = cursor.getString(cursor.getColumnIndex(USERNAME))
                    user.password = cursor.getString(cursor.getColumnIndex(PASSWORD))
                    user.email = cursor.getString(cursor.getColumnIndex(EMAIL))
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

        return user
    }

    fun getUserByUsername(username: String): User? {

        val user = User()
        val db = this.readableDatabase

        try {

            val selectQuery = "SELECT * FROM $TABLE_NAME WHERE username = '$username'"

            val cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    user.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    user.username = cursor.getString(cursor.getColumnIndex(USERNAME))
                    user.password = cursor.getString(cursor.getColumnIndex(PASSWORD))
                    user.email = cursor.getString(cursor.getColumnIndex(EMAIL))
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

        return user
    }

    fun getUserByEmail(email: String): User? {

        val user = User()
        val db = this.readableDatabase

        try {

            val selectQuery = "SELECT * FROM $TABLE_NAME WHERE email = '$email'"

            val cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    user.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    user.username = cursor.getString(cursor.getColumnIndex(USERNAME))
                    user.password = cursor.getString(cursor.getColumnIndex(PASSWORD))
                    user.email = cursor.getString(cursor.getColumnIndex(EMAIL))
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

        return user
    }

    val users: List<User>
        get() {
            val userList = ArrayList<User>()
            val db = this.readableDatabase
            val selectQuery = "SELECT  * FROM Users"
            val cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    val user = User()

                    user.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    user.username = cursor.getString(cursor.getColumnIndex(USERNAME))
                    user.password = cursor.getString(cursor.getColumnIndex(PASSWORD))
                    user.email = cursor.getString(cursor.getColumnIndex(EMAIL))

                    userList.add(user)
                } while (cursor.moveToNext())
            }

            cursor.close()
            db.close()

            return userList
        }

    fun updateUser(user: User): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(USERNAME, user.username)
        values.put(PASSWORD, user.password)
        values.put(EMAIL, user.email)

        val success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(user.id.toString())).toLong()

        db.close()

        return Integer.parseInt("$success") != -1
    }

    fun deleteUser(id: Int): Boolean {
        val db = this.writableDatabase
        val success = db.delete(TABLE_NAME, ID + "=?", arrayOf(id.toString())).toLong()

        //delete his phones too!

        db.close()

        return Integer.parseInt("$success") != -1
    }

    companion object {
        // GENERAL
        private var dbVersion = 1
        private const val DB_NAME = "Severian"
        private const val ID = "id"

        // USERS TABLE
        private const val TABLE_NAME = "Users"
        private const val USERNAME = "username"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
    }
}