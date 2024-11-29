package com.example.foregroundserviceexample

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.*

class MyForegroundService : Service() {

    companion object {
        const val CHANNEL_ID = "ForegroundServiceChannel"
        const val NOTIFICATION_ID = 1
        const val TAG = "MyForegroundService"
    }

    private var serviceJob: Job? = null
    private val serviceScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: Serviço criado")
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: Iniciando serviço em primeiro plano")
        val notification = buildNotification()

        startForeground(NOTIFICATION_ID, notification)
        Log.d(TAG, "onStartCommand: Serviço em primeiro plano iniciado")
        
        serviceJob = serviceScope.launch {
            while (isActive) {
                delay(1000)
                Log.d(TAG, "Serviço está ativo...")
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Serviço sendo destruído")
        serviceJob?.cancel()
        serviceScope.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind: Não é um serviço ligado")
        return null
    }

    private fun buildNotification(): Notification {
        Log.d(TAG, "Construindo a notificação")

        val manager = getSystemService(NotificationManager::class.java)
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Serviço em Primeiro Plano")
            .setContentText("O serviço está rodando...")
            .setSmallIcon(R.drawable.baseline_notifications_active_24)
            .setOngoing(true)
            .build()

        manager.notify(NOTIFICATION_ID, notification)
        return notification
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Canal do Serviço em Primeiro Plano",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
            Log.d(TAG, "Canal de notificação criado")
        }
    }
}
