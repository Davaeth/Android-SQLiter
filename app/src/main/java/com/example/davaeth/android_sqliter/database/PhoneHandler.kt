package com.example.davaeth.android_sqliter.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.davaeth.android_sqliter.models.Phone

class PhoneHandler(context: Context) :
    SQLiteOpenHelper(context, PhoneHandler.DB_NAME, null, PhoneHandler.dbVersion) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($ID INTEGER PRIMARY KEY, REFERENCE VARCHAR(32) NOT NULL, $BRAND VARCHAR(45), $MODEL VARCHAR(45), $SYSTEM_VERSION FLOAT, $WEBSITE TEXT);"
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
        dbVersion += 1
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addPhone(phone: Phone): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(BRAND, phone.brand)
        values.put(MODEL, phone.model)
        values.put(SYSTEM_VERSION, phone.systemVersion)
        values.put(WEBSITE, phone.website)

        val success = db.insert(TABLE_NAME, null, values)

        db.close()

        return (Integer.parseInt("$success") != -1)
    }

    fun getPhone(id: Int): Phone? {
        val phone = Phone()
        val db = this.readableDatabase

        try {

            val selectQuery = "SELECT * FROM $TABLE_NAME WHERE id = '$id'"

            val cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    phone.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    phone.brand = cursor.getString(cursor.getColumnIndex(BRAND))
                    phone.model = cursor.getString(cursor.getColumnIndex(MODEL))
                    phone.systemVersion = cursor.getFloat(cursor.getColumnIndex(SYSTEM_VERSION))
                    phone.website = cursor.getString(cursor.getColumnIndex(WEBSITE))
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

        return phone
    }
//
//    fun getPhone(brand: String): Phone? {
//        val phone = Phone()
//        val db = this.readableDatabase
//
//        try {
//
//            val selectQuery = "SELECT * FROM $TABLE_NAME WHERE brand = '$brand'"
//
//            val cursor = db.rawQuery(selectQuery, null)
//
//            if (cursor.moveToFirst()) {
//                do {
//                    phone.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
//                    phone.brand = cursor.getString(cursor.getColumnIndex(BRAND))
//                    phone.model = cursor.getString(cursor.getColumnIndex(MODEL))
//                    phone.systemVersion = cursor.getFloat(cursor.getColumnIndex(SYSTEM_VERSION))
//                    phone.website = cursor.getString(cursor.getColumnIndex(WEBSITE))
//                } while (cursor.moveToNext())
//            } else {
//                cursor.close()
//                db.close()
//                return null
//            }
//
//            cursor.close()
//        } catch (e: SQLiteException) {
//            Log.w("Exception: ", e)
//        } finally {
//            db.close()
//        }
//
//        return phone
//    }

    val phones: List<Phone>
        get() {
            val phonesList = ArrayList<Phone>()
            val db = this.readableDatabase
            val selectQuery = "SELECT  * FROM $TABLE_NAME"
            val cursor = db.rawQuery(selectQuery, null)

            if (cursor != null) {
                cursor.moveToFirst()
                while (cursor.moveToNext()) {
                    val phone = Phone()

                    phone.id = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ID)))
                    phone.brand = cursor.getString(cursor.getColumnIndex(BRAND))
                    phone.model = cursor.getString(cursor.getColumnIndex(MODEL))
                    phone.systemVersion = cursor.getFloat(cursor.getColumnIndex(SYSTEM_VERSION))
                    phone.website = cursor.getString(cursor.getColumnIndex(WEBSITE))

                    phonesList.add(phone)
                }
            }

            cursor.close()

            return phonesList
        }

    fun updatePhone(phone: Phone): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(BRAND, phone.brand)
        values.put(MODEL, phone.model)
        values.put(SYSTEM_VERSION, phone.systemVersion)
        values.put(WEBSITE, phone.website)

        val success = db.update(TABLE_NAME, values, ID + "=?", arrayOf(phone.id.toString())).toLong()

        db.close()

        return Integer.parseInt("$success") != -1
    }

    fun deletePhone(id: Int): Boolean {
        val db = this.writableDatabase
        val success = db.delete(TABLE_NAME, ID + "=?", arrayOf(id.toString())).toLong()

        db.close()

        return Integer.parseInt("$success") != -1
    }

    companion object {
        private var dbVersion = 1
        private const val DB_NAME = "Severian"
        private const val TABLE_NAME = "Phones"
        private const val ID = "id"
        private const val BRAND = "brand"
        private const val MODEL = "model"
        private const val SYSTEM_VERSION = "system_version"
        private const val WEBSITE = "website"
    }
}