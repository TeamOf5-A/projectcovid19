package com.example.projectcovid19;


import android.annotation.SuppressLint;
import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class Request_Recycleradapter extends RecyclerView.Adapter<Request_Recycleradapter.MyViewHolder>{

             Context context;
             ArrayList<Request_pozo> userArraylist;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;


    public Request_Recycleradapter(Context context, ArrayList<Request_pozo> userArraylist) {
        this.context = context;
        this.userArraylist = userArraylist;
    }

    @NonNull
    @Override
    public Request_Recycleradapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.request_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Request_Recycleradapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Request_pozo user = userArraylist.get(position);


        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        holder.request.setText(user.request);
        holder.date.setText(String.valueOf(user.date));
        String Uid = firebaseUser.getUid();
        db.collection("User_profile").document("User").collection("Users").document(Uid).get()
                .addOnCompleteListener(
                        new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {

                                        String name = task.getResult().getString("nameofperson");
                                        String pn = task.getResult().getString("pn");
                                        String city1 = task.getResult().getString("CITY");
                                        holder.username.setText(name);
                                        holder.phoneno.setText(pn);
                                        holder.address.setText(city1);



                                    }
                                } else {
                                    Toast.makeText(context, "ERROR:" + task.getException()
                                            .getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );







        holder.Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("Request User")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                                if (task.isSuccessful() ){

                                   DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                                   String DocumentId = documentSnapshot.getId();
                                   db.collection("Request User").document(DocumentId).delete()
                                           .addOnSuccessListener(new OnSuccessListener<Void>() {
                                               @Override
                                               public void onSuccess(Void unused) {
                                                   try {
                                                       userArraylist.remove(position);
                                                       notifyDataSetChanged();
                                                       notifyItemRemoved(position);


                                                   }catch (Exception e){
                                                       Toast.makeText(context, (CharSequence) e, Toast.LENGTH_SHORT).show();
                                                       notifyDataSetChanged();
                                                       notifyItemRemoved(position);

                                                   }

                                               }
                                           }).addOnFailureListener(new OnFailureListener() {
                                       @Override
                                       public void onFailure(@NonNull Exception e) {

                                       }
                                   });




                                }else {

                                    Toast.makeText(context,"Failed",Toast.LENGTH_LONG).show();

                                }

                            }
                        });
            }
        });





    }

    @Override
    public int getItemCount() {
        return userArraylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView username;
        TextView request;
        TextView date;
        TextView phoneno;
        Button call;
        TextView Close;
        TextView address;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            request = itemView.findViewById(R.id.request);
            call = itemView.findViewById(R.id.call);
            call.setVisibility(View.GONE);
            phoneno = itemView.findViewById(R.id.phoneno);
            username = itemView.findViewById(R.id.username);
            Close = itemView.findViewById(R.id.delete);
            date = itemView.findViewById(R.id.finaldate);
            address = itemView.findViewById(R.id.address);




        }
    }
}
