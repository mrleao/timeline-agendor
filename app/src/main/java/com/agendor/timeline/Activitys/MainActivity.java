package com.agendor.timeline.Activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.agendor.timeline.Dialogs.NovaTarefaDialog;
import com.agendor.timeline.Adapter.CustomRecyclerViewAdapter;
import com.agendor.timeline.R;
import com.agendor.timeline.model.Tarefa;
import com.agendor.timeline.service.TarefaService;
import com.google.gson.JsonArray;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NovaTarefaDialog.DialogListener,
        CustomRecyclerViewAdapter.ItemClickListener{

    //public static final String SHARED_PREFS = "sharedPrefs";
    //public SharedPreferences sharedPreferences;

    public CustomRecyclerViewAdapter adapter;

    public TarefaService tarefaService;
    public List<Tarefa> tarefas;

    public RecyclerView recyclerView;

    public Integer
            email = 0,
            visitas = 0,
            ligacoes = 0,
            propostas = 0,
            reunioes = 0,
            outros = 0;

    public String
            email_,
            visitas_,
            ligacoes_,
            propostas_,
            reunioes_,
            outros_;

    public TextView tl_lbl_qtd_emails, tl_lbl_qtd_ligacoes, tl_lbl_qtd_outros, tl_lbl_qtd_propostas, tl_lbl_qtd_reunioes, tl_lbl_qtd_visitas;

    public ConstraintLayout container_vazio;

    public View lt_view_barra3;

    public interface DialogCallback {
        public void onDialogCallback();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        tl_lbl_qtd_emails = findViewById(R.id.tl_lbl_qtd_emails);
        tl_lbl_qtd_ligacoes = findViewById(R.id.tl_lbl_qtd_ligacoes);
        tl_lbl_qtd_outros = findViewById(R.id.tl_lbl_qtd_outros);
        tl_lbl_qtd_propostas = findViewById(R.id.tl_lbl_qtd_propostas);
        tl_lbl_qtd_reunioes = findViewById(R.id.tl_lbl_qtd_reunioes);
        tl_lbl_qtd_visitas = findViewById(R.id.tl_lbl_qtd_visitas);
        lt_view_barra3 = findViewById(R.id.lt_view_barra3);

        container_vazio = findViewById(R.id.container_vazio);

        tarefas = new ArrayList<>();

        recyclerView = findViewById(R.id.tl_recyler_tarefas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        getDados(tl_lbl_qtd_emails, tl_lbl_qtd_visitas, tl_lbl_qtd_reunioes, tl_lbl_qtd_propostas, tl_lbl_qtd_ligacoes, tl_lbl_qtd_outros);

    }

    public void getDados(TextView tl_lbl_qtd_emails, TextView tl_lbl_qtd_visitas, TextView tl_lbl_qtd_reunioes, TextView tl_lbl_qtd_propostas, TextView tl_lbl_qtd_ligacoes, TextView tl_lbl_qtd_outros){

        this.tarefaService = new TarefaService(this);

        this.tarefaService.getAll("").setCallback(new FutureCallback<JsonArray>() {
            @Override
            public void onCompleted(Exception e, JsonArray result) {
                if (e != null) {
                    Log.e("Error: ", e.getMessage());
                    return;
                }
                Log.d("result.toString()", result.toString());

                for (int i = 0; i < result.size(); i++) {
                    Tarefa item = new Tarefa(
                            result.get(i).getAsJsonObject().get("Id").getAsInt(),
                            result.get(i).getAsJsonObject().get("Cliente").getAsString(),
                            result.get(i).getAsJsonObject().get("Descricao").getAsString(),
                            result.get(i).getAsJsonObject().get("DataExecucao").getAsString(),
                            result.get(i).getAsJsonObject().get("IdTipoTarefa").getAsInt()
                    );

                    switch (result.get(i).getAsJsonObject().get("IdTipoTarefa").getAsInt()){
                        case 1:
                            email ++;
                            break;
                        case 2:
                            ligacoes ++;
                            break;
                        case 3:
                            propostas ++;
                            break;
                        case 4:
                            reunioes ++;
                            break;
                        case 5:
                            visitas ++;
                            break;
                        case 6:
                            outros ++;
                            break;
                    }

                    tarefas.add(item);

                    adapter = new CustomRecyclerViewAdapter( MainActivity.this, tarefas );
                    adapter.setClickListener(MainActivity.this);
                    recyclerView.setAdapter(adapter);
                }


                if (email < 10){
                    email_ = "0"+email;
                }
                if (ligacoes < 10){
                    ligacoes_ = "0"+ligacoes;
                }
                if (outros < 10){
                    outros_ = "0"+outros;
                }
                if (propostas < 10){
                    propostas_ = "0"+propostas;
                }
                if (reunioes < 10){
                    reunioes_ = "0"+reunioes;
                }
                if (visitas < 10){
                    visitas_ = "0"+visitas;
                }

                tl_lbl_qtd_emails.setText(email_);
                tl_lbl_qtd_ligacoes.setText(ligacoes_);
                tl_lbl_qtd_outros.setText(outros_);
                tl_lbl_qtd_propostas.setText(propostas_);
                tl_lbl_qtd_reunioes.setText(reunioes_);
                tl_lbl_qtd_visitas.setText(visitas_);

                Log.d("ddd", "kfpksodf"+result.toString());
                if (result.toString() == null || result.toString().trim().isEmpty() || result.toString().equals("[]")){
                    container_vazio.setVisibility(View.VISIBLE);
                    lt_view_barra3.setVisibility(View.GONE);
                }else{
                    container_vazio.setVisibility(View.GONE);
                    lt_view_barra3.setVisibility(View.VISIBLE);
                }


            }

        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }

    public void openDialog(View v) {

        NovaTarefaDialog ntDialog = new NovaTarefaDialog(new DialogCallback() {
            @Override
            public void onDialogCallback() {
                getDados(tl_lbl_qtd_emails, tl_lbl_qtd_visitas, tl_lbl_qtd_reunioes, tl_lbl_qtd_propostas, tl_lbl_qtd_ligacoes, tl_lbl_qtd_outros);
               // adapter.updateList(tarefas);
            }
        });

        ntDialog.show(getSupportFragmentManager(), "Nova terefa");
    }

    @Override
    public void applyTexts(String clientetxt) {
    }



}