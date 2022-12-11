package com.example.collegeappadmin.notification.api

import com.example.collegeappadmin.notification.PushNotification

import retrofit2.Call

import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    @Headers("Content-Type:application/json" ,
            "Authorization:key=AAAATeqe-bo:APA91bH0OGiJde836lppqf5MSSerFg-Mdfa18NgdSPbQfsAoK6sfBcLgGhzkpqvNZ9B3FOBVOdOHyR_cGqjS1NpKT-4UsZ9B8553SHArVye2JhMZyY_DqqBucwkASqHFVDpsbdsy0gB3")

@POST("fcm/send")
fun sendNotification(@Body notification: PushNotification)
: Call<PushNotification>

}