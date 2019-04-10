package com.example.davaeth.android_sqliter.models

class User(username: String = "", password: String = "", email: String = "") {
    var id: Int = 0
    var username: String = username
    var password: String = password
    var email: String = email
}