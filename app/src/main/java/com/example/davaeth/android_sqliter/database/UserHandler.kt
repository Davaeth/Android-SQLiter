package com.example.davaeth.android_sqliter.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.davaeth.android_sqliter.models.Users

class UserHandler(context: Context) :
    SQLiteOpenHelper(context, UserHandler.DB_NAME, null, UserHandler.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                ID + " INTEGER PRIMARY KEY," +
                USERNAME + " TEXT," + PASSWORD + " TEXT," + EMAIL + " VARCHAR(45));"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addUser(user: Users): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(USERNAME, user.username)
        values.put(PASSWORD, user.password)
        values.put(EMAIL, user.email)

        val success = db.insert(TABLE_NAME, null, values)

        db.close()

        return (Integer.parseInt("$success") != -1)
    }

    fun getUser(id: Int): Users {
        val user = Users()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE $ID = $id"
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.moveToNext()) {
                user.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                user.username = cursor.getString(cursor.getColumnIndex(USERNAME))
                user.password = cursor.getString(cursor.getColumnIndex(PASSWORD))
                user.email = cursor.getString(cursor.getColumnIndex(EMAIL))
            }
        }

        cursor.close()

        return user
    }

    fun getUser(username: String): Users? {
        val user = Users()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE $USERNAME = $username"
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.getColumnIndex(USERNAME) != null) {

            if (cursor != null) {
                cursor.moveToFirst()
                while (cursor.moveToNext()) {
                    user!!.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    user!!.username = cursor.getString(cursor.getColumnIndex(USERNAME))
                    user!!.password = cursor.getString(cursor.getColumnIndex(PASSWORD))
                    user!!.email = cursor.getString(cursor.getColumnIndex(EMAIL))
                }
            }

            cursor.close()

            return user
        } else {
            return null
        }
    }

    val users: List<Users>
        get() {
            val userList = ArrayList<Users>()
            val db = writableDatabase
            val selectQuery = "SELECT  * FROM $TABLE_NAME"
            val cursor = db.rawQuery(selectQuery, null)

            if (cursor != null) {
                cursor.moveToFirst()
                while (cursor.moveToNext()) {
                    val user = Users()
                    user.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    user.username = cursor.getString(cursor.getColumnIndex(USERNAME))
                    user.password = cursor.getString(cursor.getColumnIndex(PASSWORD))
                    user.email = cursor.getString(cursor.getColumnIndex(EMAIL))
                    userList.add(user)
                }
            }

            cursor.close()

            return userList
        }

    fun updateUser(user: Users): Boolean {
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

        db.close()

        return Integer.parseInt("$success") != -1
    }

    companion object {

        private const val DB_VERSION = 1
        private const val DB_NAME = "Severian"
        private const val TABLE_NAME = "Users"
        private const val ID = "Id"
        private const val USERNAME = "Username"
        private const val EMAIL = "Email"
        private const val PASSWORD = "Password"
    }
}