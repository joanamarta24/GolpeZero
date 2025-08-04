import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    // Cria um launcher para solicitar múltiplas permissões
    private val requestPermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val readPhoneStateGranted = permissions[Manifest.permission.READ_PHONE_STATE] ?: false
            val readCallLongGranted = permissions[Manifest.permission.READ_CALL_LOG] ?: false
            if (readPhoneStateGranted && readCallLongGranted) {
                Toast.makeText(this, "Permissões concedidas.", Toast.LENGTH_SHORT).show()
                iniciarFuncionalidadePrincipal()
            } else {
                Toast.makeText(this, "Permissões negadas.", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        verificarEsolicitarPermissoes()
    }

    private fun verificarEsolicitarPermissoes() {
        val permissoesNecessarias = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_CALL_LOG
        )

        val permissoesParaSolicitar =
            permissoesNecessarias.filter {
                ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
            } if (permissoesParaSolicitar.isNotEmpty()) {
            // Lança a solicitação de permissão
            requestPermissionsLauncher.launch(permissoesParaSolicitar.toTypedArray())
        } else {
            // Permissões já concedidas
            Toast.makeText(this, "Permissões já concedidas.", Toast.LENGTH_SHORT).show()
            iniciarFuncionalidadePrincipal()
        }
    }


    private fun iniciarFuncionalidadePrincipal() {
        // Coloque aqui o código que precisa das permissões para funcionar
        // Ex: registrar o BroadcastReceiver de chamadas
    }
}