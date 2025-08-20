package com.example.golpezero;

import android.hardware.lights.LightState;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public interface SmartLightApi {
    @PUT("lights/1/state")
    Call<Void> setLightState(@Body LightState lightState);
}
