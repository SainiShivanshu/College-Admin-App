package com.example.collegeappadmin.model

data class Complain(
    val id:String?="",
    val name:String?="",
    val rollNo:String?="",
    val programme:String?="",
    val branch:String?="",
    val mobileNo:String?="",
    val timestamp:Any?=null,
    val roomNo:String?="",
    val category:String?="",
    val description:String?="",
    var status:String?="",
)
