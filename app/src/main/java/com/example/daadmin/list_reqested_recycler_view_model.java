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

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;

public class list_reqested_recycler_view_model extends RecyclerView.Adapter<list_reqested_recycler_view_model.MyViewHolder>{

    String DOC_EMAIL;
    int POSITION;
    String CLICKED_USER_NAME,CLICKED_USER_EMAIL,CLICKED_USER_PROBLEM;

    Context con;
    ArrayList<RequestingListCardRecyclerViewModel> card;

    public list_reqested_recycler_view_model(Context con, ArrayList<RequestingListCardRecyclerViewModel> card) {
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
        holder.list_requsted_recycle_view_doc_email.setText(card.get(holder.getAdapterPosition()).getDoc_email());
        holder.list_requsted_recycle_view_name.setText(card.get(holder.getAdapterPosition()).getName());
        holder.list_reqested_recycle_view_email.setText(card.get(holder.getAdapterPosition()).getEmail());
        holder.list_reqested_recycle_view_problem.setText(card.get(holder.getAdapterPosition()).getProblem());
        DOC_EMAIL = holder.list_requsted_recycle_view_doc_email.getText().toString();
        holder.list_requsted_recycle_view_accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CLICKED_USER_NAME = card.get(holder.getAdapterPosition()).name;
                CLICKED_USER_EMAIL = card.get(holder.getAdapterPosition()).email;
                CLICKED_USER_PROBLEM = card.get(holder.getAdapterPosition()).problem;
                POSITION = holder.getAdapterPosition();
                card.remove(POSITION);
                notifyItemRemoved(POSITION);
                handle_task_thread_accept(v);
                new Thread(() -> update_user_notification("accepted")).start();
            }
        });
        holder.list_requsted_recycle_view_reject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CLICKED_USER_NAME = card.get(holder.getAdapterPosition()).name;
                CLICKED_USER_EMAIL = card.get(holder.getAdapterPosition()).email;
                CLICKED_USER_PROBLEM = card.get(holder.getAdapterPosition()).problem;
                POSITION = holder.getAdapterPosition();
                card.remove(POSITION);
                notifyItemRemoved(POSITION);
                handle_task_thread_reject(v);
                new Thread(() -> update_user_notification("rejected")).start();
            }
        });
    }
    public void handle_task_thread_accept(View view){
        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(1000);
                }catch (Exception e){}
                finally {
                    handle_accept_btn(view);
                }
            }
        };thread.start();
    }

    public void handle_task_thread_reject(View view){
        Thread thread = new Thread(){
            public void run(){
                try {
                    sleep(2000);
                }catch (Exception e){}
                finally {
                    handled_reject_btn(view);
                }
            }
        };thread.start();
    }
    public void handle_accept_btn(View view){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

//        DocumentReference DR = db.collection("ADMINS").document(DOC_EMAIL).collection(DOC_EMAIL).document(DOC_EMAIL).collection("MY PATIENTS BOOK").document(CLICKED_USER_EMAIL);
//        MyHandlingPatientsBookModel patient = new MyHandlingPatientsBookModel(CLICKED_USER_NAME,CLICKED_USER_EMAIL,CLICKED_USER_PROBLEM,"handling");
//        DR.set(patient);
        DocumentReference HANDLING_DR = db.collection("ADMINS").document(DOC_EMAIL).collection(DOC_EMAIL).document(DOC_EMAIL).collection("MY HANDLING PATIENTS BOOK").document(CLICKED_USER_EMAIL);
        MyHandlingPatientsBookModel handling_patients = new MyHandlingPatientsBookModel(CLICKED_USER_NAME,CLICKED_USER_EMAIL,CLICKED_USER_PROBLEM,"handling");
        HANDLING_DR.set(handling_patients);

        DocumentReference HANDLED_DR = db.collection("ADMINS").document(DOC_EMAIL).collection(DOC_EMAIL).document(DOC_EMAIL).collection("MY HANDLED PATIENTS BOOK").document(CLICKED_USER_EMAIL);
        MyHandlingPatientsBookModel HANDLED_patients = new MyHandlingPatientsBookModel(CLICKED_USER_NAME,CLICKED_USER_EMAIL,CLICKED_USER_PROBLEM,"handling");
        HANDLED_DR.set(HANDLED_patients);

        DocumentReference APPOINTMENT_DR = db.collection("USERS").document(CLICKED_USER_EMAIL).collection(CLICKED_USER_EMAIL).document("APPOINTMENT");
//        DocumentReference NDR = db.collection("USERS").document(CLICKED_USER_EMAIL).collection(CLICKED_USER_EMAIL).document("NOTIFICATION");
        APPOINTMENT_DR.update("status","handling");
//        NDR.update("status","accepted");

        DocumentReference DEL_DR = db.collection("ADMINS").document(DOC_EMAIL).collection(DOC_EMAIL).document(DOC_EMAIL).collection("MY REQUESTING PATIENTS BOOK").document(CLICKED_USER_EMAIL);
        DEL_DR.delete();
        new Handler(Looper.getMainLooper()).post(() -> {
            Toast toast = Toast.makeText(con, "Request Accepted", Toast.LENGTH_SHORT);
            toast.show();
        });
//        Toast.makeText(view.getContext().getApplicationContext(), "Request Accepted", Toast.LENGTH_SHORT).show();
    }

//    public void handled_reject_btn_delayed(View view){
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
////                    Toast.makeText(view.getContext().getApplicationContext(), "Request Rejected Successfullyt", Toast.LENGTH_SHORT).show();
//                handled_reject_btn(view);
//            }
//        };
//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.postDelayed(runnable,1700);
//    }

//    public void handle_accept_btn_delayed(View view){
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
////                    Toast.makeText(view.getContext().getApplicationContext(), "Request Rejected Successfullyt", Toast.LENGTH_SHORT).show();
//                handle_accept_btn(view);
//            }
//        };
//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.postDelayed(runnable,1700);
//    }

    public void handled_reject_btn(View view){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Calendar calendar = Calendar.getInstance();
        String [] months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        String[] days = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT"};
        String hour = ""+calendar.get(Calendar.HOUR);
        String minute = ""+calendar.get(Calendar.MINUTE);
        String second = ""+calendar.get(Calendar.SECOND);
        String day = "dummyDay";
        String date = ""+ calendar.get(Calendar.DATE);
        String month = months[calendar.get(Calendar.MONTH)];
        String year = ""+calendar.get(Calendar.YEAR);

        DocumentReference DOC_DR = db.collection("ADMINS").document(DOC_EMAIL).collection(DOC_EMAIL).document(DOC_EMAIL).collection("MY REQUESTING PATIENTS BOOK").document(CLICKED_USER_EMAIL);
        DOC_DR.delete();

//        DocumentReference NDR = db.collection("USERS").document(CLICKED_USER_EMAIL).collection(CLICKED_USER_EMAIL).document("NOTIFICATION");
//        NDR.update("status","rejected");

        DocumentReference RDR = db.collection("USERS").document(CLICKED_USER_EMAIL).collection(CLICKED_USER_EMAIL).document("APPOINTMENT");
        RDR.update("status","rejected");

        DoctorRejectRequestModel reject = new DoctorRejectRequestModel(CLICKED_USER_NAME,CLICKED_USER_EMAIL,CLICKED_USER_PROBLEM,"rejected",day,date,month,year,hour,minute,second);
        DocumentReference REJECT_DR = db.collection("ADMINS").document(DOC_EMAIL).collection(DOC_EMAIL).document(DOC_EMAIL).collection("MY REJECTED PATIENTS BOOK").document(CLICKED_USER_EMAIL);
        REJECT_DR.set(reject);
        DocumentReference DEL_DR = db.collection("ADMINS").document(DOC_EMAIL).collection(DOC_EMAIL).document(DOC_EMAIL).collection("MY REQUESTING PATIENTS BOOK").document(CLICKED_USER_EMAIL);
        DEL_DR.delete();
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(con, "Request Rejected", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
//        Toast.makeText(view.getContext().getApplicationContext(), "Request Rejected", Toast.LENGTH_SHORT).show();
//        Toast.makeText(con, "Request Rejected", Toast.LENGTH_SHORT).show();

    }

    public void update_user_notification(String status){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        DocumentReference NOTIFICATION_DR = db.collection("USERS").document(CLICKED_USER_EMAIL).collection(CLICKED_USER_EMAIL).document("NOTIFICATION");
        DocumentReference NOTIFICATION_DR = db.collection("USERS").document(CLICKED_USER_EMAIL).collection(CLICKED_USER_EMAIL).document(CLICKED_USER_EMAIL).collection("NOTIFICATION").document("APPOINTMENT STATUS NOTIFICATION");
        NOTIFICATION_DR.update("status",status);
    }


    @Override
    public int getItemCount() {
        return card.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView list_requsted_recycle_view_name;
        TextView list_reqested_recycle_view_problem;
        TextView list_reqested_recycle_view_email;
        TextView list_requsted_recycle_view_doc_email;

        Button list_requsted_recycle_view_accept_btn,list_requsted_recycle_view_reject_btn;

        String DOC_EMAIL;
        String CLICKED_USER_NAME,CLICKED_USER_EMAIL,CLICKED_USER_PROBLEM;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            list_requsted_recycle_view_name = itemView.findViewById(R.id.list_requsted_recycle_view_name);
            list_reqested_recycle_view_email = itemView.findViewById(R.id.list_requsted_recycle_view_email);
            list_reqested_recycle_view_problem = itemView.findViewById(R.id.list_reqested_recycle_view_problem);
            list_requsted_recycle_view_doc_email = itemView.findViewById(R.id.list_requsted_recycle_view_doc_email);
            list_requsted_recycle_view_accept_btn = itemView.findViewById(R.id.list_requsted_recycle_view_accept_btn);
            list_requsted_recycle_view_reject_btn = itemView.findViewById(R.id.list_requsted_recycle_view_reject_btn);
            
//            list_requsted_recycle_view_accept_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    Toast.makeText(v.getContext().getApplicationContext(), "Accepted", Toast.LENGTH_SHORT).show();
//                    CLICKED_USER_NAME = list_requsted_recycle_view_name.getText().toString();
//                    CLICKED_USER_EMAIL = list_reqested_recycle_view_email.getText().toString();
//                    CLICKED_USER_PROBLEM = list_reqested_recycle_view_problem.getText().toString();
//                    DOC_EMAIL =  list_requsted_recycle_view_doc_email.getText().toString();
//                    handle_accept_btn_delayed(v);
//                }
//            });
//            list_requsted_recycle_view_reject_btn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                   DOC_EMAIL =  list_requsted_recycle_view_doc_email.getText().toString();
//                    CLICKED_USER_NAME = list_requsted_recycle_view_name.getText().toString();
//                    CLICKED_USER_EMAIL = list_reqested_recycle_view_email.getText().toString();
//                    CLICKED_USER_PROBLEM = list_reqested_recycle_view_problem.getText().toString();
//                    DOC_EMAIL =  list_requsted_recycle_view_doc_email.getText().toString();
////                    Toast.makeText(v.getContext().getApplicationContext(), "Rejected", Toast.LENGTH_SHORT).show();
//                    handled_reject_btn_delayed(v);
//                }
//            });
        }

//        public void handled_reject_btn(View view){
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            DocumentReference DOC_DR = db.collection("ADMINS").document(DOC_EMAIL).collection(DOC_EMAIL).document(DOC_EMAIL).collection("MY REQUESTING PATIENTS BOOK").document(CLICKED_USER_EMAIL);
//            DOC_DR.delete();
//            DocumentReference NDR = db.collection("USERS").document(CLICKED_USER_EMAIL).collection(CLICKED_USER_EMAIL).document("NOTIFICATION");
//            NDR.update("status","rejected");
//            Toast.makeText(view.getContext().getApplicationContext(), "Request Rejected", Toast.LENGTH_SHORT).show();
//
//        }
//        public void handled_reject_btn_delayed(View view){
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
////                    Toast.makeText(view.getContext().getApplicationContext(), "Request Rejected Successfullyt", Toast.LENGTH_SHORT).show();
//                    handled_reject_btn(view);
//                }
//            };
//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.postDelayed(runnable,3000);
//        }
//
//        public void handle_accept_btn_delayed(View view){
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
////                    Toast.makeText(view.getContext().getApplicationContext(), "Request Rejected Successfullyt", Toast.LENGTH_SHORT).show();
//                    handle_accept_btn(view);
//                }
//            };
//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.postDelayed(runnable,3000);
//        }
//
//        public void handle_accept_btn(View view){
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            DocumentReference DR = db.collection("ADMINS").document(DOC_EMAIL).collection(DOC_EMAIL).document(DOC_EMAIL).collection("MY PATIENTS BOOK").document(CLICKED_USER_EMAIL);
//            MyHandlingPatientsBookModel patient = new MyHandlingPatientsBookModel(CLICKED_USER_NAME,CLICKED_USER_EMAIL,CLICKED_USER_PROBLEM,"handling");
//            DocumentReference PDR = db.collection("USERS").document(CLICKED_USER_EMAIL).collection(CLICKED_USER_EMAIL).document("APPOINTMENT");
//            DocumentReference NDR = db.collection("USERS").document(CLICKED_USER_EMAIL).collection(CLICKED_USER_EMAIL).document("NOTIFICATION");
//            PDR.update("status","handling");
//            NDR.update("status","accepted");
//            DR.set(patient);
//            DocumentReference DEL_DR = db.collection("ADMINS").document(DOC_EMAIL).collection(DOC_EMAIL).document(DOC_EMAIL).collection("MY REQUESTING PATIENTS BOOK").document(CLICKED_USER_EMAIL);
//            DEL_DR.delete();
//            Toast.makeText(view.getContext().getApplicationContext(), "Request Accepted", Toast.LENGTH_SHORT).show();
//        }


    }


}
