package com.example.golpezero;

import android.hardware.lights.LightState;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

public class SmartLightApi {
    private int hue;
    private boolean on;

    @PUT("lights/1/state")
    Call<Void> seLighttState(@Body LightState state) {
        return null;
    }
    public void LightState(boolean on, int hue) {
        this.on = on;
        this.hue = hue;
    }


    public Call<Void> setLightState(LightState lightState) {
        return null;
    }

}
