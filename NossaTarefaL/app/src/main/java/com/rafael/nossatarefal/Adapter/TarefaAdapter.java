package com.rafael.nossatarefal.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rafael.nossatarefal.AddNovaTarefa;
import com.rafael.nossatarefal.MainActivity;
import com.rafael.nossatarefal.Model.TarefaModel;
import com.rafael.nossatarefal.R;
import com.rafael.nossatarefal.Utils.DataBaseHelper;

import java.util.List;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.MyViewHolder> {


    private List<TarefaModel> mLista;

    private MainActivity activity;

    private DataBaseHelper myDB;

     public TarefaAdapter(DataBaseHelper myDB, MainActivity activity){

         this.activity = activity;

         this.myDB = myDB;



     }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarefa_layout, parent, false);
        return new MyViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


         final TarefaModel item = mLista.get(position);
         holder.mCheckBox.setText(item.getTarefa());
         holder.mCheckBox.setChecked(toBoolean(item.getStatus()));
         holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                 if(isChecked){

                    myDB.updateStatus(item.getId() , 1);

                 }else{
                     myDB.updateStatus(item.getId(), 0 );
                 }

             }
         });


    }

    public boolean toBoolean(int num){

        return num!=0;

    }



    public Context getContext(){

         return activity;

    }

    public void setTarefa(List<TarefaModel>  mLista){


         this.mLista = mLista;

         notifyDataSetChanged();

    }

    public void deleteTarefa(int position){

         TarefaModel item = mLista.get(position);

         myDB.deleteTarefa(item.getId());
         mLista.remove(position);
         notifyItemRemoved(position);


    }


    public void editItem (int position ){

         TarefaModel item = mLista.get(position);

        Bundle bundle = new Bundle();

        bundle.putInt("id" , item.getId());
        bundle.putString("tarefa" , item.getTarefa());

        AddNovaTarefa tarefa = new AddNovaTarefa();

        tarefa.setArguments(bundle);
        tarefa.show(activity.getSupportFragmentManager() , tarefa.getTag());










    }

    @Override
    public int getItemCount() {
        return mLista.size();
    }

    public  static class  MyViewHolder extends RecyclerView.ViewHolder{



        CheckBox mCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            mCheckBox = itemView.findViewById(R.id.mcheckbox);

        }
    }



}
