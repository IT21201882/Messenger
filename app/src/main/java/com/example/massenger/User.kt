package com.example.massenger

class User {
    var uid: String? = null
    var username: String? = null
    var phoneNumber: String? = null
    var imageUri: String? = null

    constructor() {}

    constructor(uid: String, username: String, phoneNumber: String, imageUri: String) {
        this.uid = uid
        this.username = username
        this.phoneNumber = phoneNumber
        this.imageUri = imageUri
    }
}
