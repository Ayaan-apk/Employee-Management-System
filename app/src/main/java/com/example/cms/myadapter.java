package com.example.cms;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class myadapter extends RecyclerView.Adapter<myadapter.myviewholder> {

    private  Context mctx;
    List<model> datalist;

    public myadapter(Context mctx, List<model> datalist) {
        this.mctx= mctx;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.singlerow,parent,false);
        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.t1.setText(datalist.get(position).getUsername());
        holder.t2.setText(datalist.get(position).getDate());
        holder.t3.setText(datalist.get(position).getReason());

                 /*holder.t1.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent intent = new Intent(holder.t1.getContext(),UpdateLeave.class);
                         intent.putExtra("uname",datalist.get(position).getUsername());
                         intent.putExtra("date",datalist.get(position).getDate());
                         intent.putExtra("reason",datalist.get(position).getReason());

                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                         holder.t1.getContext().startActivity(intent);
                     }
                 });*/

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView t1,t2,t3;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.t1);
            t2=itemView.findViewById(R.id.t2);
            t3=itemView.findViewById(R.id.t3);


            itemView.setOnClickListener(this);


        }


        @Override
        public void onClick(View v) {
            model product = datalist.get(getAdapterPosition());
            Intent intent = new Intent(mctx, UpdateLeave.class);
            intent.putExtra("product", product);
            mctx.startActivity(intent);
        }
    }
}
