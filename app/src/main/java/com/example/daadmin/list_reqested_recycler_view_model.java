package com.example.daadmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class list_reqested_recycler_view_model extends RecyclerView.Adapter<list_reqested_recycler_view_model.MyViewHolder>{

    Context con;
    ArrayList<homeCardRecycler> card;

    public list_reqested_recycler_view_model(Context con, ArrayList<homeCardRecycler> card) {
        this.con = con;
        this.card = card;
    }

    @NonNull
    @Override
    public list_reqested_recycler_view_model.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(con);
        View view = inflater.inflate(R.layout.list_requested_recycler_view_layout,parent,false);
        return new list_reqested_recycler_view_model.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull list_reqested_recycler_view_model.MyViewHolder holder, int position) {
        holder.list_requsted_recycle_view_name.setText(card.get(position).getName());
        holder.list_reqested_recycle_view_problem.setText(card.get(position).getProblem());
    }

    @Override
    public int getItemCount() {
        return card.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView list_requsted_recycle_view_name;
        TextView list_reqested_recycle_view_problem;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            list_requsted_recycle_view_name = itemView.findViewById(R.id.list_requsted_recycle_view_name);
            list_reqested_recycle_view_problem = itemView.findViewById(R.id.list_reqested_recycle_view_problem);

        }
    }
}
