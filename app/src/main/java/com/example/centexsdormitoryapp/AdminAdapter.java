package com.example.centexsdormitoryapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewHolder> {

    private final Context display;
    private final List<OutingList> outingLists;

    public AdminAdapter(Context display, List<OutingList> outingLists) {
        this.display = display;
        this.outingLists = outingLists;
    }

    @NonNull
    @Override
    public AdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AdminViewHolder(
                LayoutInflater.from(display).inflate(R.layout.layout_outingform, parent, false)
        );
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdminViewHolder holder, int position) {

        OutingList outingList = outingLists.get(position);

        holder.textviewname.setText(outingList.getOutingname());
        holder.textviewnumber.setText(outingList.getOutingnumber());
        holder.textviewdestination.setText(outingList.getOutingdestination());
        holder.textviewreason.setText(outingList.getOutingreason());
        holder.textviewcomein.setText(outingList.getComeindate());
        holder.textviewgoout.setText(outingList.getGooutdate());
        holder.textviewstatus.setText(outingList.getOutingstatus());

    }

    @Override
    public int getItemCount() {
        return outingLists.size();
    }

    public class AdminViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textviewname, textviewnumber, textviewdestination, textviewreason, textviewcomein, textviewgoout, textviewstatus;

        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);

            textviewname = itemView.findViewById(R.id.textoutingname);
            textviewnumber = itemView.findViewById(R.id.textoutingnumber);
            textviewdestination = itemView.findViewById(R.id.textoutingdestination);
            textviewreason = itemView.findViewById(R.id.textoutingreason);
            textviewcomein = itemView.findViewById(R.id.textoutingcomein);
            textviewgoout = itemView.findViewById(R.id.textoutinggoout);
            textviewstatus = itemView.findViewById(R.id.textoutingstatus);

            //click on individual application to see details
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            OutingList outingList = outingLists.get(getAdapterPosition());
            Intent intent = new Intent(display, StatusApproval.class);
            intent.putExtra("outingList", outingList);
            display.startActivity(intent);
        }
    }
}

