package com.example.projectcovid19;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import static android.content.ContentValues.TAG;


public class Request_Recycleradapter extends RecyclerView.Adapter<Request_Recycleradapter.ViewHolder> {

    public Context mcontext;
    public List<Request_pozo> mrequest;
    private FirebaseUser firebaseUser;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    String user = "User";
    String Uid = firebaseUser.getUid();

    public Request_Recycleradapter(Context mcontext, List<Request_pozo> mrequest) {
        this.mcontext = mcontext;
        this.mrequest = mrequest;
    }

    @NonNull
    @NotNull
    @Override
    public Request_Recycleradapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.request_item,parent,false);
        return new Request_Recycleradapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Request_Recycleradapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mrequest.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView Request;
        public TextView deadline;
        public TextView pno;
        public TextView address;
        public TextView Delete;


        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.username);
            Request = itemView.findViewById(R.id.request);
            deadline = itemView.findViewById(R.id.finaldate);
            pno = itemView.findViewById(R.id.phoneno);
            address = itemView.findViewById(R.id.address);
            Delete = itemView.findViewById(R.id.delete);
            db = FirebaseFirestore.getInstance();
            mAuth = FirebaseAuth.getInstance();
        }
    }
    private void requestinfo(TextView username, TextView Request,
            TextView deadline,
            TextView pno,
            TextView address,
            TextView Delete){
        db.collection("User").document("User profile").collection("Users").document(Uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().exists()) {
                        String account_type = task.getResult().getString("Account");
                        try {
                            if (account_type.equals(user)) {



                            }


                        } catch (Exception e) {
                            Log.d(TAG, "get failed with " + e);
                        }
                    }
                }
            }
        });

    }
}
