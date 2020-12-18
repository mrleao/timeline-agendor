package com.agendor.timeline.Dialogs;


import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import com.agendor.timeline.Activitys.MainActivity;
import com.agendor.timeline.Adapter.CustomRecyclerViewAdapter;
import com.agendor.timeline.R;
import com.agendor.timeline.model.Tarefa;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.Calendar;
import java.util.List;

public class NovaTarefaDialog extends AppCompatDialogFragment {

    private EditText cliente,descricao,dataExecucao, horaExecucao;
    private DialogListener listener;
    private Button btnAdicionar;

    private String sCliente, sDescricao, sDataExecucao;
    public Integer idTarefa = 0;

    private ImageButton btn_unfocus;

    private View view;

    DatePickerDialog dpd;

    TimePickerDialog timeDialog;


    private static final int[] idArray = {
            R.id.nt_img_btn_email,
            R.id.nt_img_btn_telefone,
            R.id.nt_img_btn_documento,
            R.id.nt_img_btn_localizacao,
            R.id.nt_img_btn_mais,
            R.id.nt_img_btn_negociacao
    };

    public static final String SHARED_PREFS = "sharedPrefs";

    int i;

    private ImageButton[] imageButton = new ImageButton[idArray.length];

    public MainActivity.DialogCallback callback;

    public NovaTarefaDialog (MainActivity.DialogCallback callback){
        this.callback = callback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_nova_tarefa, null);

        btnAdicionar = view.findViewById(R.id.nt_btn_cadastrar);

        cliente = view.findViewById(R.id.nt_edt_cliente);
        descricao = view.findViewById(R.id.nt_edt_descricao);
        dataExecucao = view.findViewById(R.id.nt_edt_data);
        horaExecucao = view.findViewById(R.id.nt_edt_hora);

        horaExecucao = view.findViewById(R.id.nt_edt_hora);

        builder.setView(view);

        dataExecucao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view,  boolean hasFocus) {
                if (hasFocus){
                    Calendar c = Calendar.getInstance();
                    int dia = c.get(Calendar.DAY_OF_MONTH);
                    int mes =  c.get(Calendar.MONTH);
                    int ano = c.get(Calendar.YEAR);

                    Log.d("MES", ""+mes);

                    dpd = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                            int mMes = mMonth + 1;
                            dataExecucao.setText(mDay+"-"+mMes+"-"+mYear);
                        }
                    }, ano, mes, dia);
                    dpd.show();
                }
            }
        });

        horaExecucao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view,  boolean hasFocus) {
                if (hasFocus){
                    Calendar c = Calendar.getInstance();
                    int hora = c.get(Calendar.HOUR_OF_DAY);
                    int min = c.get(Calendar.MINUTE);

                    timeDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timepicker, int mHour, int mMin) {
                            horaExecucao.setText(mHour+":"+mMin+":00");
                        }
                    }, hora , min, true);
                    timeDialog.show();
                }
            }
        });

        for (i = 0; i < idArray.length; i++){

            imageButton[i] =  view.findViewById(idArray[i]);

            imageButton[i].setBackgroundResource(R.drawable.borda_redonda);

            btn_unfocus = imageButton[0];

            imageButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()){
                        case R.id.nt_img_btn_email:
                            setFocus(btn_unfocus, imageButton[0]);
                            idTarefa = 1;
                            break;
                        case R.id.nt_img_btn_telefone:
                            setFocus(btn_unfocus, imageButton[1]);
                            idTarefa = 2;
                            break;
                        case R.id.nt_img_btn_documento:
                            setFocus(btn_unfocus, imageButton[2]);
                            idTarefa = 3;
                            break;
                        case R.id.nt_img_btn_localizacao:
                            setFocus(btn_unfocus, imageButton[3]);
                            idTarefa = 5;
                            break;
                        case R.id.nt_img_btn_mais:
                            setFocus(btn_unfocus, imageButton[4]);
                            idTarefa = 6;
                            break;
                        case R.id.nt_img_btn_negociacao:
                            setFocus(btn_unfocus, imageButton[5]);
                            idTarefa = 4;
                            break;
                    }
                }
            });
        }

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (idTarefa > 0) {

                    sCliente = cliente.getText().toString();
                    sDescricao = descricao.getText().toString();
                    sDataExecucao = dataExecucao.getText() + " " + horaExecucao.getText();

                    if(sCliente == null || sCliente.trim().isEmpty() ||
                    sDescricao == null || sDescricao.trim().isEmpty() ||
                    sDataExecucao == null || sDataExecucao.trim().isEmpty()
                    ){
                        Toast.makeText(getActivity(), "Você precisa preencher todos os campos.",
                                Toast.LENGTH_LONG).show();
                    }else{

                        salvar(sCliente, idTarefa, sDescricao, sDataExecucao);
                        callback.onDialogCallback();
                        getDialog().dismiss();
                    }

                }else{
                    Toast.makeText(getActivity(), "Você precisa selecionar o tipo da tarefa.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return builder.create();
    }

   /* public void saveData(String ed1) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, getContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("01", ed1);
        editor.apply();
    }*/

   private void setFocus(ImageButton btn_unfocus, ImageButton btn_focus){
       btn_unfocus.setBackgroundResource(R.drawable.borda_redonda);
       btn_focus.setBackgroundResource(R.drawable.background_redondo_azul);
       this.btn_unfocus = btn_focus;
   }

    public void salvar(String cliente, Integer idTipoTarefa, String descricao, String dataExecucao){
        JsonObject json = new JsonObject();
        json.addProperty("idUsuario", 1);
        json.addProperty("cliente", cliente);
        json.addProperty("idTipoTarefa", idTipoTarefa);
        json.addProperty("descricao", descricao);
        json.addProperty("dataExecucao", dataExecucao);

        Ion.with(getContext())
                .load("http://senhoraoferta.com/timeline/api/tarefa")
                .setJsonObjectBody(json)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // Log.d("testepost", "ok"+result);
                    }
                });

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
