package com.example.cms;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class reqadapter  extends RecyclerView.Adapter<reqadapter.myviewholder> {

    ArrayList<reqmodel> datalist;

    public reqadapter(ArrayList<reqmodel> datalist) {
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.threerow,parent,false);
        return new reqadapter.myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.st11.setText(datalist.get(position).getHelp());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{

        TextView st11;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            st11=itemView.findViewById(R.id.st11);
        }
    }
}
