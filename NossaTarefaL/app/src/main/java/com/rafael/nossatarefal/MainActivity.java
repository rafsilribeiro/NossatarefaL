package com.rafael.nossatarefal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rafael.nossatarefal.Adapter.TarefaAdapter;
import com.rafael.nossatarefal.Model.TarefaModel;
import com.rafael.nossatarefal.Utils.DataBaseHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OndialogCloseListner {

    private RecyclerView mRecyclerview;
    private FloatingActionButton fab;
    private DataBaseHelper myDB;
    private List<TarefaModel> mLista;
    private TarefaAdapter adapter;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerview = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.fab);
        backButton = findViewById(R.id.back_button);

        myDB = new DataBaseHelper(MainActivity.this);

        mLista = new ArrayList<>();
        adapter = new TarefaAdapter(myDB, MainActivity.this);

        mRecyclerview.setHasFixedSize(true);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(adapter);

        mLista = myDB.getAllTarefa();
        Collections.reverse(mLista);
        adapter.setTarefa(mLista);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNovaTarefa.newInstance().show(getSupportFragmentManager(), AddNovaTarefa.TAG);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MainActivity", "Back button clicked"); // Adicionado log para depuração
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(mRecyclerview);
    }

    @Override
    public void onDialongClose(DialogInterface dialogInterface) {
        mLista = myDB.getAllTarefa();
        Collections.reverse(mLista);
        adapter.setTarefa(mLista);
        adapter.notifyDataSetChanged();
    }
}
