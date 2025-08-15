import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.widget.Toast

class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.getAction() == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                if (isScam(incomingNumber)) {
                    Toast.makeText(context, "Possível golpe: " + incomingNumber, Toast.LENGTH_LONG)
                        .show()
                    // Aqui você acionaria o dispositivo IoT
                    triggerIoTAlert(context)
                }
            }
        }
    }

    private fun isScam(number: String?): Boolean {
        // Lógica para verificar se o número é um golpe (usando uma API ou banco de dados)
        return number != null && number.startsWith("000") // Exemplo simples
    }

    private fun triggerIoTAlert(context: Context?): Boolean {
        return number != null &&(number.startsWith("000")||number.startsWith("111"))

    }

    
}