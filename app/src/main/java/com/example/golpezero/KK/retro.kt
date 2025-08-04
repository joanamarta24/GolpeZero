import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.PUT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// Interface da API com Retrofit
interface SmartLightApi {
    @PUT("lights/1/state")
    suspend fun setLightState(@Body state: LightState)
}

data class LightState(val on: Boolean, val hue: Int) // hue 0 = vermelho

// Função para acionar o alerta
fun triggerIoTAlert(context: Context) {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://<IP_DA_LAMPADA>/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(SmartLightApi::class.java)

    CoroutineScope(Dispatchers.IO).launch {
        try {
            // Liga a lâmpada e a deixa vermelha
            api.setLightState(LightState(true, 0))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}