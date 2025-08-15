package com.example.golpezero

import android.Manifest
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.registerReceiver

class MainActivity :AppCompatActivity() {
    companion object {
        private const val TAG = "CallMonitor"
        private const val PREFS_NAME = "CallMonitorPrefs"
        private const val KEY_IS_MONITORING = "permissions_granted"
    }
    private lateinit var btnToggleMonitoring: Button
    private lateinit var tvStatus: TextView
    private var callReceiver: CallReceiver? = null

    private val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.all { it.value }

        if (allGranted) {
            enableMonitoring()
        } else {
            Toast.makeText(this, "Permissões negadas.", Toast.LENGTH_SHORT).show()
            updateUI(false)
        }
    }

    private fun registerForActivityResult(
        requestMultiplePermissions: ActivityResultContracts.RequestMultiplePermissions,
        function: Any
    ) {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.golpezero.R.layout.activity_main)

        btnToggleMonitor = findViewById(com.example.golpezero.R.id.btnToggleMonitor)
        tvStatus = findViewById(com.example.golpezero.R.id.tvStatus)

        btnToggleMonitoring.setOnClickListener {
            toggleMonitoring()
        }
        checkInitialState()
    }
   private fun checkInitialState() {
       if (hasRequiredPermissions()) {
           enableMonitoring()
       }else{
           updateUI(false)
       }
   }
    private fun hasRequiredPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.PROCESS_OUTGOING_CALLS) ==
                PackageManager.PERMISSION_GRANTED
    }
    private fun requestRequiredPermissions() {
        val permissionsToResquest = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG
        ).filter {
            ContextCompat.checkSelfPermission
            (this, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()
        if (permissionsToResquest.isNotEmpty()) {
            requestPermissionsLauncher.launch(permissionsToResquest)
        }else{
            enableMonitoring()

        }
    }
    private fun MainActivity.enableMonitoring() {
        callReceiver = CallReceiver()
        val filter = IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        registerReceiver(callReceiver, filter)
        updateUI(true)
        Toast.makeText(this, "Monitoramento ativado", Toast.LENGTH_SHORT).show())
    }
private fun disableMonitoring() {
    callReceiver?.let{
        unregisterReceiver(it)
       callReceiver = null
    }
    updateUI(false)
    Toast.makeText(this, "Monitoramento desativado", Toast.LENGTH_SHORT).show())
}
    private fun updateUI(isMonitoring: Boolean) {
        if (isMonitoring) {
            tvStatus.text = "Monitoramento ativado"
            btnToggleMonitoring.text = "Desativar Monitoramento"
        } else {
            tvStatus.text = "Monitoramento desativado"
            btnToggleMonitoring.text = "Ativar Monitoramento"
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        callReceiver?.let {
            unregisterReceiver(it)
        }
    }

}


