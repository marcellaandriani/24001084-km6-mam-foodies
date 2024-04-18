package com.example.foodiesapp.data.model

import com.google.firebase.auth.FirebaseUser

data class User(
    val id : String,
    val username : String,
    val email : String,
   // val password : String,
   // val numberPhone : String,
   // val imgUrl : String
)

fun FirebaseUser?.toUser() = this?.let {
    User(
        id = this.uid,
        username = this.displayName.orEmpty(),
        email = this.email.orEmpty()
    )
}

