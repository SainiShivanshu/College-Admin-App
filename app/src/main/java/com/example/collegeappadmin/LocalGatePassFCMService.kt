package com.example.collegeappadmin

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.collegeappadmin.activity.LocalGatePassActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

class LocalGatePassFCMService : FirebaseMessagingService() {

    private val channelId  = "Local Gate Pass"

    override fun onMessageReceived(message: RemoteMessage) {

        if(message.data.isNotEmpty()){
            Log.d("FCM", "Message data payload: ${message.data}")
        }

        message.notification?.let {
            Log.d("FCM","Message Notification Body:${it.body} ")
        }

        val intent = Intent(this,LocalGatePassActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val manager=getSystemService(Context.NOTIFICATION_SERVICE)
        createNotificationChannel(manager as NotificationManager)

        val intent1 = PendingIntent.getActivities(this,0,
            arrayOf(intent), PendingIntent.FLAG_IMMUTABLE)

        val notification= NotificationCompat.Builder(this,channelId)
            .setContentTitle(message.data["title"])
            .setContentText(message.data["body"])
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setAutoCancel(true)
            .setContentIntent(intent1)
            .build()


        manager.notify(Random.nextInt(),notification)

    }

    private fun createNotificationChannel(manager: NotificationManager){

        // only if min sdk is 26

        val channel = NotificationChannel(channelId,"Local Gate Pass",
            NotificationManager.IMPORTANCE_HIGH)

        channel.description="New Gate Pass"
        channel.enableLights(true)

        manager.createNotificationChannel(channel)

    }

}