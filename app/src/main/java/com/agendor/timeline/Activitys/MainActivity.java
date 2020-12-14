package com.agendor.timeline.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.agendor.timeline.Dialogs.NovaTarefaDialog;
import com.agendor.timeline.R;

public class MainActivity extends AppCompatActivity implements NovaTarefaDialog.DialogListener {

    public TextView teste;
    public static final String SHARED_PREFS = "sharedPrefs";
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        teste = findViewById(R.id.textView3);

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        teste.setText(
                sharedPreferences.getString("01", "")
        );

    }

    public void openDialog(View v) {
        NovaTarefaDialog ntDialog = new NovaTarefaDialog();
        ntDialog.show(getSupportFragmentManager(), "Nova terefa");
    }

    @Override
    public void applyTexts(String clientetxt) {
        teste.setText(clientetxt);
    }
}