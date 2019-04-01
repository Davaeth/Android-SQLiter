package com.example.davaeth.android_sqliter.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.davaeth.android_sqliter.models.Users

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DatabaseHandler.DB_NAME, null, DatabaseHandler.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
                ID + " INTEGER PRIMARY KEY," +
                NICKNAME + " TEXT," + PASSWORD + " TEXT," + EMAIL + " VARCHAR(45));"
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
        values.put(NICKNAME, user.nickname)
        values.put(PASSWORD, user.password)
        values.put(EMAIL, user.email)
        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

    fun getTask(_id: Int): Users {
        val users = Users()
        val db = writableDatabase
        val selectQuery = "SELECT  * FROM $TABLE_NAME WHERE $ID = $_id"
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor != null) {
            cursor.moveToFirst()
            while (cursor.moveToNext()) {
                users.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                users.nickname = cursor.getString(cursor.getColumnIndex(NICKNAME))
                users.password = cursor.getString(cursor.getColumnIndex(PASSWORD))
                users.email = cursor.getString(cursor.getColumnIndex(EMAIL))
            }
        }
        cursor.close()
        return users
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
                    user.nickname = cursor.getString(cursor.getColumnIndex(NICKNAME))
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
        values.put(NICKNAME, user.nickname)
        values.put(PASSWORD, user.password)
        values.put(EMAIL, user.email)
        val _success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(user.id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    fun deleteUser(_id: Int): Boolean {
        val db = this.writableDatabase
        val _success = db.delete(TABLE_NAME, ID + "=?", arrayOf(_id.toString())).toLong()
        db.close()
        return Integer.parseInt("$_success") != -1
    }

    companion object {

        private val DB_VERSION = 1
        private val DB_NAME = "Severian"
        private val TABLE_NAME = "Users"
        private val ID = "Id"
        private val NICKNAME = "Nickname"
        private val EMAIL = "Email"
        private val PASSWORD = "Password"
    }
}