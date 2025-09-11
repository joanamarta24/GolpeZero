package com.example.golpezero;

import android.telecom.Call;

public interface SmartLightApi {
@Put("lights/1/state")
    Call<Void>setLightState
        (@Body LightState lightState);
}
