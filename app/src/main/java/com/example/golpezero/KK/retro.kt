import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.PUT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Interface da API com Retrofit
interface SmartLightApi {
    @PUT("lights/1/state")
    suspend fun setLightState(@Body state: LightState)
}

data class LightState(val on: Boolean, val hue: Int)

// Função para acionar o alerta
fun triggerIoTAlert() {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://<IP_DA_LAMPADA>/api/") // precisa terminar com /
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(SmartLightApi::class.java)

    CoroutineScope(Dispatchers.IO).launch {
        try {
            // Liga a lâmpada e a deixa vermelha (hue=0)
            api.setLightState(LightState(true, 0))

            // Se quiser mostrar log na Main Thread
            withContext(Dispatchers.Main) {
                Log.d("IoT", "Lâmpada ativada com sucesso")
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Log.e("IoT", "Erro ao ativar lâmpada", e)
            }
        }
    }
}
