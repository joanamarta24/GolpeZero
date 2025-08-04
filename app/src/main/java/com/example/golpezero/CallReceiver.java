package com.example.golpezero;

import static com.example.golpezero.InterfaceKt.triggerIoTAlert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        }
        if (isScam(incomingNumber)) {
            Toast.makeText(context, "Possivel golpe:" + incomingNumber,
                    Toast.LENGTH_LONG).show();
            triggerIoTAlert(context);
        }
    }
   private boolean isScam(String number) {
        return number != null && number.startsWith("000");
   }
   private void triggerIoTAlert(Context context) {

   }
}
