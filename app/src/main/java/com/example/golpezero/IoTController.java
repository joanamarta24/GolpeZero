package com.example.golpezero;

import android.content.Context;
import android.hardware.lights.LightState;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.lang.ref.Cleaner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IoTController {

    @RequiresApi(api = Build.VERSION_CODES.S)
    public void triggerIoTAlert(Context context) {
        Cleaner GsonConverterFactory;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://<IP_DA_LAMPADA>/api/") // Troque pelo IP real
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SmartLightApi api = retrofit.create(SmartLightApi.class);

        // Liga a lâmpada e muda a cor
        com.example.golpezero.Call<Void> call = api.setLightState(new LightState(true, 0)); // hue=0 → vermelho

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("IoT", "Lâmpada ativada com sucesso");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("IoT", "Erro ao ativar lâmpada", t);
            }
        });
    }
}
