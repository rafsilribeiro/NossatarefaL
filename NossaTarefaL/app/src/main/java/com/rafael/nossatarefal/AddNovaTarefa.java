package com.rafael.nossatarefal;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.rafael.nossatarefal.Model.TarefaModel;
import com.rafael.nossatarefal.Utils.DataBaseHelper;

public class AddNovaTarefa extends BottomSheetDialogFragment {


    public static final String TAG = "Adicionar Tarefa";


    private EditText mEditText;
    private Button mSalvaBtn;
    private DataBaseHelper myDb;
    public static AddNovaTarefa newInstance(){

        return new AddNovaTarefa();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_novatarefa, container, false );
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mEditText = view.findViewById(R.id.edittext);

        mSalvaBtn = view.findViewById(R.id.salva_btn);


        myDb = new DataBaseHelper(getActivity());

        boolean isUpdate = false;

        Bundle bundle = getArguments();


        if(bundle != null){
            isUpdate = true;
            String tarefa = bundle.getString("tarefa");
            mEditText.setText(tarefa);


         if(tarefa.length() > 0 ){


             mSalvaBtn.setEnabled(false);
         }

        }
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().equals("")){

                    mSalvaBtn.setEnabled(false);
                    mSalvaBtn.setBackgroundColor(Color.GREEN);

                }else{
                    mSalvaBtn.setEnabled(true);
                    mSalvaBtn.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        boolean finalIsUpdate = isUpdate;
        mSalvaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditText.getText().toString();


                if (finalIsUpdate){

                    myDb.updateTarefa(bundle.getInt("id") , text);


                }else{
                    TarefaModel item = new TarefaModel();

                    item.setTarefa(text);
                    item.setStatus(0);
                    myDb.insertTarefa(item);
                }
                dismiss();



            }
        });

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if(activity instanceof OndialogCloseListner){
            ((OndialogCloseListner) activity).onDialongClose(dialog);


        }








    }
}
















