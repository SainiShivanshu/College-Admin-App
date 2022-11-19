package com.example.collegeappadmin.model

data class LocalGatePass(
    val id:String?="",
    val name:String?="",
    val rollNo:String?="",
    val place:String?="",
    val programme:String?="",
    val branch:String?="",
    val mobileNo:String?="",
    val timestamp:Any?=null,
    val roomNo:String?="",
    var status:String?="",
    val date: String?="",
    val timeOut:String?="",
    val timeIn:String?=""
)
