package com.agendor.timeline.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.agendor.timeline.Activitys.MainActivity;
import com.agendor.timeline.R;
import com.agendor.timeline.model.Tarefa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {

    private List<Tarefa> mData;
    private LayoutInflater mInflater;
    public ItemClickListener mClickListener;

    // data is passed into the constructor
    public CustomRecyclerViewAdapter(Context context, List<Tarefa> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }


    public void updateList (List<Tarefa> items) {
        if (items != null && items.size() > 0) {
            mData.clear();
            mData.addAll(items);
            notifyDataSetChanged();
        }else{
            notifyDataSetChanged();
        }
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_tarefa, parent, false);
        return new ViewHolder(view);
    }


    // binds the data to the TextView in each row
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tarefa tarefa = mData.get(position);

        holder.lt_lbl_cliente.setText(tarefa.getCliente());
        holder.lt_lbl_descricao.setText(tarefa.getDescricao());

        try {
            Date data = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tarefa.getData());
            Date datinha = new Date();
            Date hojeMenos5min = new Date(data.getTime() - 5 * 60000);
            Date dataMais10min = new Date(data.getTime() + 5 * 60000);

            String dia_semana = new SimpleDateFormat("EEE\nHH:mm", Locale.getDefault()).format(data);

            holder.lt_lbl_horario.setText(dia_semana);

            if (datinha.compareTo(hojeMenos5min) > 0 && datinha.compareTo(dataMais10min) < 0) {
                holder.lt_view_barra.setBackgroundResource(R.color.verde_agendor);
                holder.lt_view_barra2.setBackgroundResource(R.color.verde_agendor);
                holder.lt_lbl_horario.setTextColor(Color.argb(255, 81, 255, 144));

                int color = Color.parseColor("#80ffae");
                holder.lt_fabtn_icone.setBackgroundTintList(ColorStateList.valueOf(color));
                holder.lt_container_info.setBackgroundResource(R.drawable.container_borda_verde);


            } else if(datinha.compareTo(hojeMenos5min) > 0 && datinha.compareTo(dataMais10min) > 0) {
                holder.lt_view_barra.setBackgroundResource(R.color.vermelho);
                holder.lt_view_barra2.setBackgroundResource(R.color.vermelho);
                holder.lt_lbl_horario.setTextColor(Color.argb(255, 255, 0, 0));

                int color = Color.parseColor("#ff6666");
                holder.lt_fabtn_icone.setBackgroundTintList(ColorStateList.valueOf(color));
                holder.lt_container_info.setBackgroundResource(R.drawable.container_borda_vermelha);

            } else{

                holder.lt_view_barra.setBackgroundResource(R.color.azul_agendor);
                holder.lt_view_barra2.setBackgroundResource(R.color.azul_agendor);
                holder.lt_lbl_horario.setTextColor(Color.argb(255, 51, 45, 230));

                int color = Color.parseColor("#a6a3f5");
                holder.lt_fabtn_icone.setBackgroundTintList(ColorStateList.valueOf(color));

                holder.lt_container_info.setBackgroundResource(R.drawable.container_borda_azul);


            }

            switch(tarefa.getIdTipoTarefa()){
                case 1:
                    holder.lt_fabtn_icone.setImageResource(R.drawable.ic_mail_w);
                    break;
                case 2:
                    holder.lt_fabtn_icone.setImageResource(R.drawable.ic_telefone_w);
                    break;
                case 3:
                    holder.lt_fabtn_icone.setImageResource(R.drawable.ic_documento_w);
                    break;
                case 4:
                    holder.lt_fabtn_icone.setImageResource(R.drawable.ic_maleta_w);
                    break;
                case 5:
                    holder.lt_fabtn_icone.setImageResource(R.drawable.ic_location_w);
                    break;
                case 6:
                    holder.lt_fabtn_icone.setImageResource(R.drawable.ic_more_w);
                    break;

            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView lt_lbl_cliente, lt_lbl_descricao, lt_lbl_horario;
        FloatingActionButton lt_fabtn_icone;
        View lt_view_barra, lt_view_barra2;
        ConstraintLayout lt_container_info;

        ViewHolder(View itemView) {
            super(itemView);
            lt_lbl_cliente = itemView.findViewById(R.id.lt_lbl_cliente);
            lt_lbl_descricao = itemView.findViewById(R.id.lt_lbl_descricao);
            lt_lbl_horario = itemView.findViewById(R.id.lt_lbl_horario);
            lt_fabtn_icone = itemView.findViewById(R.id.lt_fabtn_icone);
            lt_view_barra = itemView.findViewById(R.id.lt_view_barra);
            lt_view_barra2 = itemView.findViewById(R.id.lt_view_barra2);
            lt_container_info = itemView.findViewById(R.id.lt_container_info);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null)
                mClickListener.onItemClick(view, getAdapterPosition());
        }

    }

    // convenience method for getting data at click position
    public Object getItem(int position) {
        return mData.get(position);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }


    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
         void onItemClick(View view, int position);
    }
}