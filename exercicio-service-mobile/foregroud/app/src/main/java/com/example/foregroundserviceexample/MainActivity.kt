package com.example.foregroundserviceexample

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {

    private lateinit var startServiceButton: Button
    private lateinit var stopServiceButton: Button
    private lateinit var serviceStatusTextView: TextView

    private val POST_NOTIFICATION_PERMISSION_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startServiceButton = findViewById(R.id.button_start_service)
        stopServiceButton = findViewById(R.id.button_stop_service)
        serviceStatusTextView = findViewById(R.id.textview_service_status)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    POST_NOTIFICATION_PERMISSION_REQUEST_CODE
                )
            }
        }

        startServiceButton.setOnClickListener {
            startForegroundServiceCompat()
        }

        stopServiceButton.setOnClickListener {
            stopForegroundService()
        }
    }

    override fun onRequestPermissionsResult (
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions as Array<String>, grantResults)
        if (requestCode == POST_NOTIFICATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permissão de Notificações Concedida", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permissão de Notificações Negada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startForegroundServiceCompat() {
        val serviceIntent = Intent(this, MyForegroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
        Toast.makeText(this, "Serviço Iniciado", Toast.LENGTH_SHORT).show()
        serviceStatusTextView.text = "Serviço Ativo"
    }

    private fun stopForegroundService() {
        val serviceIntent = Intent(this, MyForegroundService::class.java)
        stopService(serviceIntent)
        Toast.makeText(this, "Serviço Parado", Toast.LENGTH_SHORT).show()
        serviceStatusTextView.text = "Serviço Inativo"
    }
}
