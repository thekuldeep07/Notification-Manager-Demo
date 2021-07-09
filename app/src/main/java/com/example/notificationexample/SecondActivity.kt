package com.example.notificationexample

import android.app.NotificationManager
import android.app.RemoteInput
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.app.NotificationCompat

class SecondActivity : AppCompatActivity()

{   private lateinit var inputTv:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        inputTv=findViewById(R.id.inputTv)
        recieveInput()
    }
    private fun recieveInput(){
        val KEY_REPLY = "key_reply"
        val intent = this.intent
        val remoteInput = RemoteInput.getResultsFromIntent(intent)
        if(remoteInput!=null){
            val inputString = remoteInput.getCharSequence(KEY_REPLY).toString()
            inputTv.text = inputString
             val channelID = "com.example.notificationexample.channel1"
            val notificationId =45

            val repliedNotification = NotificationCompat.Builder(this,channelID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentText("Your Reply recieved")
                .build()

            val notificationManager:NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.notify(notificationId,repliedNotification)


        }

    }
}