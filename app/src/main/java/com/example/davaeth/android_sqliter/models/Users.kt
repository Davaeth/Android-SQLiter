package com.example.davaeth.android_sqliter.models

import android.provider.ContactsContract

public class Users(nickname: String = "", password: String = "", email: String = "") {
    var id: Int = 0
    var nickname: String = nickname
    var password: String = password
    var email: String = email
}