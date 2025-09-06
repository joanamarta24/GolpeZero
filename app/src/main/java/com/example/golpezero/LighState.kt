package com.example.golpezero

import android.util.Log
import android.util.Log.e
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


data class LighState(
    val on: Boolean,
    val hue: Int
)

data class LightState(
    val on: Boolean,
    val hue: Int
)


class CouroutineScope(io: CoroutineDispatcher) {

}

fun triggerIoTAlert() {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://<IP_DA_LAMPADA>/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api = retrofit.create(SmartLightApi::class.java)
    CouroutineScope(Dispatchers.IO).launch {
        try {
            api.setLightState(LightState(true, 0))
            withContext(Dispatchers.Main) {
                Log.d("IoT", "Lâmpada ativada com sucesso")
            }catch (e: Exception) {
                e.printStackTrace()
            }

    }
}
