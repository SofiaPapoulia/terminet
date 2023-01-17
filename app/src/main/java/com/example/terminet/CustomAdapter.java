package com.example.terminet;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    Context context;
    ArrayList color_indicator,description, action_steps, color_id, isErrorMessage;

    CustomAdapter(Context context,
                  ArrayList color_id,
                  ArrayList color_indicator,
                  ArrayList description){
        this.context = context;
        this.color_indicator = color_indicator;
        this.description = description;
        this.color_id=color_id;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.colour_id_txt.setText(String.valueOf(color_id.get(position)));
        holder.color_indicator_btn.setText(String.valueOf(color_indicator.get(position)));
        holder.color_indicator_btn.setOnClickListener(v -> {
            // Start the new activity here and pass any necessary information as extras
            Intent intent = new Intent(v.getContext(), IndicatorSolutionActivity.class);
            intent.putExtra("indicatorID", String.valueOf(color_id.get(position)));
            v.getContext().startActivity(intent);
        });
        //holder.description_txt.setText(String.valueOf(description.get(position)));
    }

    @Override
    public int getItemCount() {
        return color_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView colour_id_txt, description_txt;
        Button color_indicator_btn;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            colour_id_txt = itemView.findViewById(R.id.color_id);
            color_indicator_btn = itemView.findViewById(R.id.color_indicator);
            //description_txt = itemView.findViewById(R.id.indicator_description);
        }
    }
}
