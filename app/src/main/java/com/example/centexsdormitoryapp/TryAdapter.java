package com.example.centexsdormitoryapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class TryAdapter extends FirestoreRecyclerAdapter <OutingList, TryAdapter.TryViewHolder> {

    NoteListener noteListener;

    public TryAdapter(@NonNull FirestoreRecyclerOptions<OutingList> options, NoteListener noteListener) {
        super(options);
        this.noteListener = noteListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull TryViewHolder holder, int position, @NonNull OutingList outingList) {

        holder.textviewname.setText(outingList.getOutingname());
        holder.textviewnumber.setText(outingList.getOutingnumber());
        holder.textviewdestination.setText(outingList.getOutingdestination());
        holder.textviewreason.setText(outingList.getOutingreason());
        holder.textviewcomein.setText(outingList.getComeindate());
        holder.textviewgoout.setText(outingList.getGooutdate());
        holder.textviewstatus.setText(outingList.getOutingstatus());

    }

    @NonNull
    @Override
    public TryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.try_adapter_form, parent, false);
        return new TryViewHolder(view);

    }

    @SuppressWarnings("deprecation")
    class TryViewHolder extends RecyclerView.ViewHolder {

        TextView textviewname, textviewnumber, textviewdestination, textviewreason, textviewcomein, textviewgoout, textviewstatus;

        public TryViewHolder(@NonNull View itemView) {
            super(itemView);

            textviewname = itemView.findViewById(R.id.textoutingname);
            textviewnumber = itemView.findViewById(R.id.textoutingnumber);
            textviewdestination = itemView.findViewById(R.id.textoutingdestination);
            textviewreason = itemView.findViewById(R.id.textoutingreason);
            textviewcomein = itemView.findViewById(R.id.textoutingcomein);
            textviewgoout = itemView.findViewById(R.id.textoutinggoout);
            textviewstatus = itemView.findViewById(R.id.textoutingstatus);

            itemView.setOnClickListener(v -> {
                DocumentSnapshot snapshot = getSnapshots().getSnapshot(getAdapterPosition());
                noteListener.viewapplicationstatus(snapshot);
            });
        }
    }

    interface NoteListener {
        void viewapplicationstatus(DocumentSnapshot snapshot);
    }
}
