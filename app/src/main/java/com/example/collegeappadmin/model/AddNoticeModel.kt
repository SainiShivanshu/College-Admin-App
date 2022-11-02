package com.example.collegeappadmin.model

import com.google.firebase.firestore.model.ServerTimestamps

data class AddNoticeModel(
    val noticeId:String?="",
    val date:String?=null,
    val time:String?=null,
    val title:String?="",
    val noticeDesc:String?="",
    val image:ArrayList<String> = ArrayList(),
    val timestamp:Any?=null
)
