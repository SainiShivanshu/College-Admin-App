package com.example.collegeappadmin.model


data class UserModel(
    val emailId:String?="",
    val name:String?="",
    val mobileNo:String?="",
    val imageUrl:String?="",
    val resumeUrl:String?="",
    val address:String?="",
    val programme:String?="",
    val branch:String?="",
    val rollNo:String?="",
    var token:String?=""
)
