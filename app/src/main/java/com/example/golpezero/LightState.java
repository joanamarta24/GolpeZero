package com.example.golpezero;

import android.content.Context;

public class LightState2 {
    private boolean on;
    private int hue;

    public void SmartLightApi(boolean on, int hue) {
        this.on = on;
        this.hue = hue;
    }

    public LightState2(boolean on, int hue) {
        this.on = on;
        this.hue = hue;
    }

    public boolean isOn() {
        return on;
    }

    public int getHue() {
        return hue;
    }
    public Call<Void> setLightState(LightState2 lightState) {
        return null;
    }
    public void triggerIoTAlert(Context context) {

    }


}
