package com.example.daadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class homeCardRecylerViewAdapter extends RecyclerView.Adapter<homeCardRecylerViewAdapter.MyViewHolder>{
    Context con;
    ArrayList<homeCardRecycler> card;
    public homeCardRecylerViewAdapter(Context con, ArrayList<homeCardRecycler> card){
        this.con = con;
        this.card = card;

    }
    @NonNull
    @Override
    public homeCardRecylerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(con);
        View view = inflater.inflate(R.layout.home_recyler_view,parent,false);
        return new homeCardRecylerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull homeCardRecylerViewAdapter.MyViewHolder holder, int position) {
        holder.home_recycle_view_name.setText(card.get(position).getName());
        holder.home_recycle_view_problem.setText(card.get(position).getProblem());
    }

    @Override
    public int getItemCount() {
        return card.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView home_recycle_view_name;
        TextView home_recycle_view_problem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            home_recycle_view_name = itemView.findViewById(R.id.home_recycle_view_name);
            home_recycle_view_problem = itemView.findViewById(R.id.home_recycle_view_problem);
        }
    }
}
