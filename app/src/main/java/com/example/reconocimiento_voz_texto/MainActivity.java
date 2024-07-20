package com.example.reconocimiento_voz_texto;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
  private static final int PERMISO_SOLICITAR_GRABAR_AUDIO = 100;
  private static final int SOLICITAR_PERMISO = 1;
  private TextView textoResultadoTextView;
  private Button grabarVozButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    EdgeToEdge.enable(this);
    setContentView(R.layout.activity_main);
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
      Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
      return insets;
    });

    grabarVozButton = findViewById(R.id.grabarVozButton);
    textoResultadoTextView = findViewById(R.id.textoResultadoTextView);

    grabarVozButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        IniciarReconocimientoVoz();
      }
    });
  }

  private void IniciarReconocimientoVoz() {
    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

    try {
      startActivityForResult(intent, SOLICITAR_PERMISO);
    } catch (Exception ex) {
      Toast.makeText(this, "No se puede realizar reconomiento de voz", Toast.LENGTH_LONG).show();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == SOLICITAR_PERMISO && resultCode == RESULT_OK && data != null) {
      ArrayList<String> resultado = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

      if (resultado != null && !resultado.isEmpty()) {
        String texto = resultado.get(0);
        TextView textViewResult = findViewById(R.id.textoResultadoTextView);
        textViewResult.setText(texto);
      }
    }
  }
}