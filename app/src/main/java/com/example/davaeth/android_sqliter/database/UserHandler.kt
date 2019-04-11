package com.example.davaeth.android_sqliter.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.davaeth.android_sqliter.models.User

class UserHandler(context: Context) :
    SQLiteOpenHelper(context, UserHandler.DB_NAME, null, UserHandler.dbVersion) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE =
            "CREATE TABLE $TABLE_NAME ($ID INTEGER PRIMARY KEY, $USERNAME TEXT, $PASSWORD TEXT, $EMAIL VARCHAR(45));"
        db.execSQL(CREATE_TABLE)
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

    fun getUser(username: String): User? {

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

    val users: List<User>
        get() {
            val userList = ArrayList<User>()
            val db = this.readableDatabase
            val selectQuery = "SELECT  * FROM Users"
            val cursor = db.rawQuery(selectQuery, null)

            if (cursor != null) {
                cursor.moveToFirst()
                while (cursor.moveToNext()) {
                    val user = User()

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

        db.close()

        return Integer.parseInt("$success") != -1
    }

    companion object {
        private var dbVersion = 1
        private const val DB_NAME = "Severian"
        private const val TABLE_NAME = "Users"
        private const val ID = "id"
        private const val USERNAME = "username"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
    }
}