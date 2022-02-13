package com.example.transport10v.database;

import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Environment;
import androidx.annotation.Nullable;

import com.example.transport10v.model.ModelArt;

import java.util.ArrayList;
import java.util.List;

public class BDHelperArt extends SQLiteOpenHelper {

    public static String ART_PATH = "";
    public static final String ARTNAME = "Art_bd.db";
     final Context context;
     SQLiteDatabase msqLiteDatabase;

    public BDHelperArt(@Nullable Context context) {
        super(context, ARTNAME, null, 1);
        if (Build.VERSION.SDK_INT >= 17) {
            ART_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            ART_PATH = Environment.getDataDirectory() +context.getFilesDir().getPath()+ context.getPackageName() + "/databases/";
        }
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDbArt(){
        String dbArtPath = context.getDatabasePath(ARTNAME).getPath();
        if (msqLiteDatabase != null && msqLiteDatabase.isOpen()){
            return;
        }
        msqLiteDatabase = SQLiteDatabase.openDatabase(dbArtPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    private void closeDbArt(){
        if (msqLiteDatabase !=null){
            msqLiteDatabase.close();
        }
    }

    public List<ModelArt> getListArt(){
        ModelArt modelArt = null;
        List<ModelArt> listArt = new ArrayList<>();
        openDbArt();
        Cursor mcursor = msqLiteDatabase.rawQuery("SELECT * FROM ART",null);
        mcursor.moveToFirst();
        while (!mcursor.isAfterLast()){
             modelArt = new ModelArt(
                    mcursor.getInt(0),
                    mcursor.getInt(1),
                    mcursor.getString(2),
                    mcursor.getInt(3));
            listArt.add(modelArt);
            mcursor.moveToNext();
        }
        mcursor.close();
        closeDbArt();
        return listArt;
    }

}
