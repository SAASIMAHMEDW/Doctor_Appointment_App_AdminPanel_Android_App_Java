package com.example.daadmin;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
        holder.home_recycle_view_email.setText(card.get(position).getEmail());
        holder.home_recycle_view_problem.setText(card.get(position).getProblem());
        holder.doctor_email.setText(card.get(position).getDoctor_email());
    }

    @Override
    public int getItemCount() {
        return card.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        String CLICKED_EMAIL,DOCTOR_EMAIL;

        TextView home_recycle_view_name;
        TextView home_recycle_view_problem;
        TextView home_recycle_view_email;
        TextView doctor_email;
        Button doctor_patients_finished_btn;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            doctor_patients_finished_btn = itemView.findViewById(R.id.doctor_patients_finished_btn);
            home_recycle_view_name = itemView.findViewById(R.id.home_recycle_view_name);
            home_recycle_view_email = itemView.findViewById(R.id.home_recycle_view_email);
            home_recycle_view_problem = itemView.findViewById(R.id.home_recycle_view_problem);
            doctor_email = itemView.findViewById(R.id.doctor_email);
            doctor_patients_finished_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CLICKED_EMAIL = home_recycle_view_email.getText().toString();
                    DOCTOR_EMAIL = doctor_email.getText().toString();
//                    Toast.makeText(v.getContext().getApplicationContext(), DOCTOR_EMAIL, Toast.LENGTH_SHORT).show();
                    update_status_dalayed(v);
//                    update_status(email,v);

                }
            });
        }

        public void update_status_dalayed(View view){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
//                    Toast.makeText(view.getContext().getApplicationContext(), EMAILX, Toast.LENGTH_SHORT).show();
                    update_status(view);
                }
            };
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(runnable,1500);
        }
        public void update_status(View view){
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference DR = db.collection("ADMINS").document(DOCTOR_EMAIL).collection(DOCTOR_EMAIL).document(DOCTOR_EMAIL).collection("MY PATIENTS BOOK").document(CLICKED_EMAIL);
            DocumentReference USER_DR = db.collection("USERS").document(CLICKED_EMAIL).collection(CLICKED_EMAIL).document("APPOINTMENT");
            USER_DR.update("email","NULL","name","NULL","phoneno","NULL","problem","NULL");
            DR.update("status","finished").addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(view.getContext().getApplicationContext(), "Finished Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
