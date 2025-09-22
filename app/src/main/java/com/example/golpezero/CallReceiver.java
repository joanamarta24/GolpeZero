package com.example.golpezero;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public class CallReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String incomingNumber = null;
        if (intent.getAction().equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
        }
        if (isScam(incomingNumber)) {
            Toast.makeText(context, "Possivel golpe:" + incomingNumber,
                    Toast.LENGTH_LONG).show();
            notifyUserOfScam(context, incomingNumber);
            triggerIoTAlert(context, incomingNumber);
        }
    }

    private boolean isScam(String incomingNumber) {
        return number.startsWith("000") == true || number.length ==3;
        return false;
    }
    public interface SmartLightApi {
        @PUT("lights/1/state")
        void setLightState(@Body LightState state);
    }

    private void notifyUserOfScam(Context context, String number) {
        Toast.makeText(context, "Possivel golpe:" + number,
                Toast.LENGTH_LONG).show();
    }
    private fun triggerIoTAlert(context: Context, number: String Context context, String incomingNumber) {
        SmartLightApi api = Retrofit.Builder()
                .baseUrl("http://<IP_DA_LAMPADA>/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SmartLightApi.class);
        return null;
    }
}
