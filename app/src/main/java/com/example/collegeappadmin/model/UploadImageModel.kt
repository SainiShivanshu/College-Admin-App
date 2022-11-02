package com.example.collegeappadmin.model

data class  UploadImageModel(
    val category:String?="",
    val imageId:String?="",
    val image:ArrayList<String> = ArrayList(),
    val timestamp:Any?=null
)
