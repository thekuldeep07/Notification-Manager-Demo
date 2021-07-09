package com.example.notificationexample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput

class MainActivity : AppCompatActivity() {

    private val channelID = "com.example.notificationexample.channel1"
    private var notificationManager :NotificationManager?=null
    private  val KEY_REPLY = "key_reply"
    private lateinit var btn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationManager =getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel(channelID,"DemoChannel","this is a demo notification")

        btn=findViewById(R.id.button)

        btn.setOnClickListener {
            displayNotification()

        }
    }

    private fun displayNotification(){
        val notificationId =45
        val tapResultIntent = Intent(this,SecondActivity::class.java)
        val pendingIntent :PendingIntent = PendingIntent.getActivity(
                this,
                0,
                tapResultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        val detailsIntent = Intent(this,ThirdActivity::class.java)
        val detailsPendingIntent :PendingIntent = PendingIntent.getActivity(
            this,
            0,
            detailsIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        //reply action
        val remoteInput: RemoteInput = RemoteInput.Builder(KEY_REPLY).run {
            setLabel("Insert  your name Here")
            build()
        }

        val replyAction:NotificationCompat.Action = NotificationCompat.Action.Builder(
            0,
            "REPLY",
            pendingIntent,
        ).addRemoteInput(remoteInput)
            .build()



        val action2: NotificationCompat.Action = NotificationCompat.Action.Builder(0,"Details",detailsPendingIntent).build()

        val settingsIntent = Intent(this,SettingsActivity::class.java)
        val settingsPendingIntent :PendingIntent = PendingIntent.getActivity(
            this,
            0,
            settingsIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val action3: NotificationCompat.Action = NotificationCompat.Action.Builder(0,"Details",settingsPendingIntent).build()

        val notification = NotificationCompat.Builder(this@MainActivity,channelID)
                .setContentTitle("Demo Title")
                .setContentText("this is a demo text")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .addAction(replyAction)
                .addAction(action2)
                .addAction(action3)
                .build()
        notificationManager?.notify(notificationId,notification)


    }

    private fun createNotificationChannel(id:String,name:String,channelDescription:String){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id,name,importance).apply {
                description = channelDescription
            }

            notificationManager?.createNotificationChannel(channel)
        }

        else{
            Log.d("tag","not workable")
        }

    }
}