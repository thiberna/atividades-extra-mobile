package com.example.notification
import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : ComponentActivity() {

    val ACTION_REPLY = "REPLY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        val button = findViewById<Button>(R.id.button)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel : NotificationChannel = NotificationChannel(
                "notification",
                "My Notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val  manager : NotificationManager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        button.setOnClickListener {
            val builder : NotificationCompat.Builder = NotificationCompat.Builder(this, "notification")
            .setContentTitle("Olha a notificação aee")
            .setContentText("essa notificação tem um texto realmente longo")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("não é possivel que uma notificação seja tão longa assim mas fazer o que né")
            )
            .setSmallIcon(R.drawable.baseline_notifications_active_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .addAction(R.drawable.baseline_reply_16, ACTION_REPLY, null)

            val managerCompat : NotificationManagerCompat = NotificationManagerCompat.from(this)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return@setOnClickListener
            }
            managerCompat.notify(1, builder.build())
        }
    }
}
