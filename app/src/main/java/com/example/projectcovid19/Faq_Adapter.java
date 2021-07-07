package com.example.projectcovid19;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Faq_Adapter extends RecyclerView.Adapter<Faq_Adapter.PersonViewHolder> {
    private List<Faq_Pozo> personList;
    private Context context;
    private boolean visible;

    private static int currentPosition = 0;

    public Faq_Adapter(List<Faq_Pozo> personList, Context context) {
        this.personList = personList;
        this.context = context;
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new PersonViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final PersonViewHolder holder, final int position) {
        Faq_Pozo person = personList.get(position);
        holder.question.setText(person.getQuestion());
        holder.answer.setText(person.getAnswer());

        holder.linearLayout.setVisibility(View.GONE);

        //if the position is equals to the item position which is to be expanded


        holder.question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the position of the item to expand it
                currentPosition = position;

                //reloding the list
                notifyDataSetChanged();
            }
        });
        if (currentPosition == position) {

            holder.question.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (holder.linearLayout.getVisibility() == View.VISIBLE) {
                        holder.question.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                holder.linearLayout.setVisibility(View.GONE);

                            }
                        });

                    }
                    else{
                        holder.linearLayout.setVisibility(View.VISIBLE);
                    }



                }
            });

            //toggling visibility


        }


    }

    @Override
    public int getItemCount() {
        return personList.size();
    }

    class PersonViewHolder extends RecyclerView.ViewHolder {
        TextView question,answer;
        LinearLayout linearLayout;

        PersonViewHolder(View itemView) {
            super(itemView);

            question = (TextView) itemView.findViewById(R.id.textViewName);
            answer = (TextView) itemView.findViewById(R.id.answer);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
    }

}
