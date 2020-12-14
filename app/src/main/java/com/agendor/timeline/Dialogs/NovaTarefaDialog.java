package com.agendor.timeline.Dialogs;


import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.agendor.timeline.Activitys.MainActivity;
import com.agendor.timeline.R;

public class NovaTarefaDialog extends AppCompatDialogFragment {

    private EditText cliente;
    private DialogListener listener;
    private Button btnAdicionar;

    private View view;

    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_nova_tarefa, null);

        btnAdicionar = view.findViewById(R.id.nt_btn_cadastrar);
        cliente = view.findViewById(R.id.nt_edt_cliente);
        builder.setView(view);
        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ma.saveData(cliente.getText().toString());
                saveData(cliente.getText().toString());


                String clientetxt = cliente.getText().toString();
                listener.applyTexts(cliente.getText().toString());
                getDialog().dismiss();
            }
        });


        return builder.create();
    }
    public void saveData(String ed1) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("01", ed1);
        editor.apply();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Imlemente NovaTerefaDialog");
        }
    }

    public interface DialogListener{
        void applyTexts(String clientetxt);
    }
}
