package com.example.davaeth.android_sqliter.global

import android.app.Application

/**
 * Class of global variables,
 * that are shared in ever class in application.
 * CAUTION! : It will be vanished after app reset!
 */
class GlobalVariables : Application() {

    /***
     * Companion object that holds static fields
     * of the global variables.
     */
    companion object {
        /**
         * List that tells the PhoneBar toolbar
         * which phones are going to be deleted.
         */
        private var selectedPhonesList: MutableList<Int> = mutableListOf()

        /**
         * Method that is just a selected phones list getter.
         */
        fun getSelectedPhones() = this.selectedPhonesList.toList()

        /**
         * Method that adds phone's id to the list.
         */
        fun addSelectedPhone(phoneID: Int) {
            this.selectedPhonesList.add(phoneID)
        }

        /**
         * Method that removes phone's id from the list.
         */
        fun removeSelectedPhone(phoneID: Int) {
            this.selectedPhonesList.remove(phoneID)
        }
    }
}