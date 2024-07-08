package com.rafael.nossatarefal.Utils;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.rafael.nossatarefal.Model.TarefaModel;
import com.rafael.nossatarefal.Model.UsuarioModel;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper  extends SQLiteOpenHelper {


    private SQLiteDatabase db;


    private static final String DATABASE_NAME = " TAREFA_DATABASE";

    private static final  String TABLE_NAME = "TAREFA_DATABASE";
    private static final String COL_1 = "ID";

    private static final String COL_2 = "TAREFA";

    private static final String COL_3 = "STATUS";
    private static final  String TABLE_USER = "USUARIO_DATABASE";
    private static final String COLUM_USUARIO_ID = "ID";
    private static final String COLUM_NOME = "NOME";
    private static final String COLUM_SENHA = "SENHA";



    public DataBaseHelper(@Nullable Context context ) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "  + TABLE_NAME + " (ID INTEGER PRIMARY KEY  AUTOINCREMENT, TAREFA TEXT, STATUS INTEGER)");

        db.execSQL("CREATE TABLE IF NOT EXISTS "  + TABLE_USER + " (ID INTEGER PRIMARY KEY  AUTOINCREMENT, NOME TEXT, SENHA TEXT )");





    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        onCreate(db);


    }


    public void insertUsuario(UsuarioModel model){

        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUM_NOME, model.getNome());
        values.put(COLUM_SENHA,0);
        db.insert(TABLE_USER, null, values);


    }

    public void updateUsuario(int id, String nome){

        db = this.getWritableDatabase();


        ContentValues values = new ContentValues();
        values.put(COLUM_NOME, nome);

        db.update(TABLE_USER, values , "ID=?", new String[]{String.valueOf(id)});
    }

    public void updateSenha(int id, int senha){

        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_SENHA, senha);
        db.update(TABLE_USER, values , "ID=?", new String[]{String.valueOf(id)});


    }



    public void deleteUsuario(int id){
        db = this.getWritableDatabase();
        db.delete(TABLE_USER, "ID=?", new String[]{String.valueOf(id)});
    }




     public void insertTarefa(TarefaModel model){
        db = this.getWritableDatabase();


         ContentValues values = new ContentValues();
         values.put(COL_2, model.getTarefa());
         values.put(COL_3,0);
         db.insert(TABLE_NAME, null, values);



     }


     public void updateTarefa(int id, String tarefa){

         db = this.getWritableDatabase();


         ContentValues values = new ContentValues();
         values.put(COL_2, tarefa);

         db.update(TABLE_NAME, values , "ID=?", new String[]{String.valueOf(id)});




     }

     public void updateStatus(int id, int status){

         db = this.getWritableDatabase();
         ContentValues values = new ContentValues();
         values.put(COL_3, status);
         db.update(TABLE_NAME, values , "ID=?", new String[]{String.valueOf(id)});


     }

     public void deleteTarefa(int id){
         db = this.getWritableDatabase();
         db.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(id)});
     }

     @SuppressLint("Range")
     public List<TarefaModel> getAllTarefa(){


        db = this.getWritableDatabase();
         Cursor cursor = null;
         List<TarefaModel> modelList = new ArrayList<>();


        db.beginTransaction();

        try {

            cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
            if(cursor != null){

                if(cursor.moveToFirst()){
                    do{
                        TarefaModel tarefa = new TarefaModel();
                        tarefa.setId(cursor.getInt(cursor.getColumnIndex(COL_1)));
                        tarefa.setTarefa(cursor.getString(cursor.getColumnIndex(COL_2)));
                        tarefa.setStatus(cursor.getInt(cursor.getColumnIndex(COL_3)));

                        modelList.add(tarefa);



                    }while (cursor.moveToNext());

                }
            }
        }finally {
            db.endTransaction();
            cursor.close();
        }

        return modelList;

     }











}













