package com.example.cms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class showadapter extends RecyclerView.Adapter<showadapter.showviewholder>{

    ArrayList<mymodel>datalist;

    public showadapter(ArrayList<mymodel> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public showviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recviewcard,parent,false);
        return new showviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull showviewholder holder, int position) {
        holder.st1.setText(datalist.get(position).getUsername());
        holder.st2.setText(datalist.get(position).getDate());
        holder.st3.setText(datalist.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class showviewholder extends RecyclerView.ViewHolder{

        TextView st1,st2,st3;

        public showviewholder(@NonNull View itemView) {
            super(itemView);
            st1=itemView.findViewById(R.id.st1);
            st2=itemView.findViewById(R.id.st2);
            st3=itemView.findViewById(R.id.st3);
        }
    }
}
