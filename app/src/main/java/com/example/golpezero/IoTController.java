package com.example.golpezero;


import android.content.Context;
import android.hardware.lights.LightState;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IoTController {

    private static final String BASE_URL = "http://192.168.0.10/api/"; // coloque o IP real da lâmpada

    public void triggerIoTAlert(Context context) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SmartLightApi api = retrofit.create(SmartLightApi.class);

        Call<Void> call = api.setLightState(new LightState(true, 0)); // 0 = vermelho
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("IoT", "Lâmpada ativada com sucesso");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("IoT", "Erro ao enviar comando para a lâmpada", t);
            }
        });
    }
}

