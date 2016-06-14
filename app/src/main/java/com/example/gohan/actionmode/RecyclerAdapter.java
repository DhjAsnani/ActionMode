package com.example.gohan.actionmode;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Freeware Sys on 6/14/2016.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {

    ArrayList<Contact> adapter_list = new ArrayList<Contact>();
    MainActivity mainActivity;
    Context ctx;
    public RecyclerAdapter(ArrayList<Contact> adapter_list,Context ctx){
        this.adapter_list = adapter_list;
        this.ctx = ctx;
        mainActivity = (MainActivity) ctx;

    }
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_layout,parent,false);
        RecyclerViewHolder recyclerViewHolder= new RecyclerViewHolder(view,mainActivity);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.textView.setText(adapter_list.get(position).getName());
        if(!mainActivity.is_in_action_mode)
        {
            holder.checkBox.setVisibility(View.GONE);
        }
        else
        {
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return adapter_list.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        CheckBox checkBox;
        MainActivity mainActivity;
        public RecyclerViewHolder(View itemview, MainActivity mainActivity)
        {
            // mainActivity is used for handling the click events of the checkboxes
            super(itemview);
            textView = (TextView) itemview.findViewById(R.id.textView);
            checkBox= (CheckBox) itemview.findViewById(R.id.checkBox);
            this.mainActivity = mainActivity;
        }
    }
}
