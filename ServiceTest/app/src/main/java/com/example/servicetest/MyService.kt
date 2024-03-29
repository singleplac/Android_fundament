package com.example.servicetest

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Button
import androidx.core.app.NotificationCompat

class MyService : Service() {

    private  val myBinder = DownloadBinder()

    class DownloadBinder:Binder(){
        fun startDownload(){
            Log.d("MyService","start download executed")
        }
        fun getProcess() : Int{
            Log.d("MyService", "getProgress executes")
            return 0
        }
    }
    override fun onBind(intent: Intent): IBinder {
        return myBinder
    }





    override fun onCreate() {
        super.onCreate()
        Log.d("MyService","onCreate executed")

        //前台Service
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel("my_service","forground_notify", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)

        }

        val intent = Intent(this, MainActivity::class.java)
        val pi = PendingIntent.getActivity(this,0,intent,0)

        val notification = NotificationCompat.Builder(this,"my_service")
            .setContentTitle("Title")
            .setContentText("Text")
            .setSmallIcon(R.drawable.small_icon)
            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.big_icon))
            .setContentIntent(pi)
            .build()

        startForeground(1,notification)

        //
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("MyService","onStartCommand executed")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MyService","onDestroy executed")
    }

}