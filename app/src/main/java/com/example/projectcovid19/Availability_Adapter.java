package com.example.projectcovid19;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Availability_Adapter extends RecyclerView.Adapter<Availability_Adapter.MyViewHolder> {
    Context context;
    ArrayList<Availability_pozo> userArraylist;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore db;
    String pn;

    public Availability_Adapter(Context context, ArrayList<Availability_pozo> userArraylist) {
        this.context = context;
        this.userArraylist = userArraylist;
    }


    @NonNull
    @Override
    public Availability_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.request_item,parent,false);
        return new Availability_Adapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Availability_Adapter.MyViewHolder holder, int position) {
        Availability_pozo user = userArraylist.get(position);
        mAuth = FirebaseAuth.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        holder.request.setText(user.request);
        holder.phoneno.setText(user.PhoneNumber);
        holder.date.setText(String.valueOf(user.date));
        holder.username.setText(user.username);
        String Uid = firebaseUser.getUid();

        holder.call.setOnClickListener(new View.OnClickListener() {


            public String PhoneNumberWithoutCountryCode(String phoneNumberWithCountryCode){//+91 7698989898
                Pattern compile = Pattern.compile("\\+(?:998|996|995|994|993|992|977|976|975|974|973|972|971|970|968|967|966|965|964|963|962|961|960|886|880|856|855|853|852|850|692|691|690|689|688|687|686|685|683|682|681|680|679|678|677|676|675|674|673|672|670|599|598|597|595|593|592|591|590|509|508|507|506|505|504|503|502|501|500|423|421|420|389|387|386|385|383|382|381|380|379|378|377|376|375|374|373|372|371|370|359|358|357|356|355|354|353|352|351|350|299|298|297|291|290|269|268|267|266|265|264|263|262|261|260|258|257|256|255|254|253|252|251|250|249|248|246|245|244|243|242|241|240|239|238|237|236|235|234|233|232|231|230|229|228|227|226|225|224|223|222|221|220|218|216|213|212|211|98|95|94|93|92|91|90|86|84|82|81|66|65|64|63|62|61|60|58|57|56|55|54|53|52|51|49|48|47|46|45|44\\D?1624|44\\D?1534|44\\D?1481|44|43|41|40|39|36|34|33|32|31|30|27|20|7|1\\D?939|1\\D?876|1\\D?869|1\\D?868|1\\D?849|1\\D?829|1\\D?809|1\\D?787|1\\D?784|1\\D?767|1\\D?758|1\\D?721|1\\D?684|1\\D?671|1\\D?670|1\\D?664|1\\D?649|1\\D?473|1\\D?441|1\\D?345|1\\D?340|1\\D?284|1\\D?268|1\\D?264|1\\D?246|1\\D?242|1)\\D?");
                String number = phoneNumberWithCountryCode.replaceAll(compile.pattern(), "");
                //Log.e(tag, "number::_>" +  number);//OutPut::7698989898
                return number;
            }
            String tel = PhoneNumberWithoutCountryCode(holder.phoneno.getText().toString());
            String p = "tel:" + tel ;
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);

                i.setData(Uri.parse(p));
                context.startActivity(i);
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
        TextView city;
        TextView Close;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            request = itemView.findViewById(R.id.request);
            call = itemView.findViewById(R.id.call);

            phoneno = itemView.findViewById(R.id.phoneno);
            username = itemView.findViewById(R.id.username);
            Close = itemView.findViewById(R.id.delete);
            Close.setVisibility(View.GONE);
            date = itemView.findViewById(R.id.finaldate);
            city = itemView.findViewById(R.id.address);
        }
    }
}
