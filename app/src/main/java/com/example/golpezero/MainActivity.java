package com.example.golpezero;

import static android.content.Context.MODE_PRIVATE;
import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;
import static androidx.core.content.ContextCompat.registerReceiver;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

public class MainActivity {

    private static final String TAG = "PermissaoApp";
    private static final String PREFS_NAME = "AppPrefs";
    private static final String PERMISSIONS_GRANTED_KEY = "PermissionsGranted";

    private BroadcastReceiver callReceiver;
    private Button btnAtivar;
    private TextView tvStatus;

    // Launcher que recebe o resultado da solicitação
    private final ActivityResultLauncher<String[]> requestPermissionsLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                // Verificamos se as permissões essenciais foram concedidas
                boolean allGranted = permissions.getOrDefault(android.Manifest.permission.READ_PHONE_STATE, false) &&
                        permissions.getOrDefault(android.Manifest.permission.READ_CALL_LOG, false);

                if (allGranted) {
                    Log.i(TAG, "Todas as permissões essenciais foram concedidas!");
                    ativarMonitoramento();
                } else {
                    Log.w(TAG, "Permissões necessárias foram negadas.");
                    Toast.makeText(this, "A proteção não pode ser ativada sem as permissões.", Toast.LENGTH_LONG).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAtivar = findViewById(R.id.btn_ativar_protecao);
        tvStatus = findViewById(R.id.tv_status_protecao);

        // LÓGICA DE PERSISTÊNCIA: Verificar no início se as permissões já foram dadas antes.
        if (verificarSePermissoesJaConcedidas()) {
            ativarMonitoramento();
        } else {
            // Prepara a UI para solicitar permissão
            tvStatus.setText("Proteção Inativa");
            btnAtivar.setText("Ativar Proteção Agora");
            btnAtivar.setOnClickListener(v -> verificarEsolicitarPermissoes());
        }
    }

    private void verificarEsolicitarPermissoes() {
        // Lista de permissões que nosso app precisa para a funcionalidade principal
        String[] permissoesNecessarias = {
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.READ_CALL_LOG
        };

        // Lança a solicitação para as permissões que ainda não foram concedidas
        requestPermissionsLauncher.launch(permissoesNecessarias);
    }

    /**
     * ESTA É A FUNÇÃO MAIS IMPORTANTE PÓS-PERMISSÃO
     */
    private void ativarMonitoramento() {
        Log.d(TAG, "Ativando o monitoramento de chamadas...");

        // 1. REGISTRAR O BROADCASTRECEIVER:
        // Agora que temos permissão, podemos registrar nosso "ouvido" para escutar as chamadas do sistema.
        // Sem permissão, o registro falharia ou o app quebraria.
        callReceiver = new CallReceiver();
        IntentFilter filter = new IntentFilter(android.telephony.TelephonyManager.ACTION_PHONE_STATE_CHANGED);
        registerReceiver(callReceiver, filter);

        // 2. ATUALIZAR A INTERFACE DO USUÁRIO (UI):
        // Fornecer feedback visual de que a proteção está ativa.
        tvStatus.setText("Proteção Ativa ");
        btnAtivar.setText("Monitoramento Ativado");
        btnAtivar.setEnabled(false);


        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PERMISSIONS_GRANTED_KEY, true);
        editor.apply();

        Toast.makeText(this, "Monitoramento de chamadas ativado!", Toast.LENGTH_SHORT).show();
    }

    private boolean verificarSePermissoesJaConcedidas() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        // Também verificamos a permissão real, caso o usuário a tenha revogado nas configurações do Android.
        boolean permissionsPreviouslyGranted = prefs.getBoolean(PERMISSIONS_GRANTED_KEY, false);
        boolean readPhoneStateGranted = ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;

        return permissionsPreviouslyGranted && readPhoneStateGranted;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (callReceiver != null) {
            unregisterReceiver(callReceiver);
        }
    }

}