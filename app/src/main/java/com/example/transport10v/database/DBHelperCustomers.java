package com.example.transport10v.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.transport10v.model.ModelCustomers;

import java.util.ArrayList;

public class DBHelperCustomers extends SQLiteOpenHelper {

    private final Context context;
    public static final String DATE_NAME = "Customer";
    public static final String TABLE_NAME = "Custamers";

    public DBHelperCustomers(Context context) {
        super(context, DATE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table  " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, IMIE TEXT, NAZWISKO TEXT, MIEJSCOWOSC TEXT, ADRESP TEXT, ULICA TEXT, NUMERD INTEGER, TELEFON INTEGER, NIP TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Dodawanie urzytkownika do bazydanych "Customers"
    public void AddCustomers(ModelCustomers modelCustomers) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("IMIE", modelCustomers.getImie());
        cv.put("NAZWISKO", modelCustomers.getNazwisko());
        cv.put("MIEJSCOWOSC", modelCustomers.getMiejscowosc());
        cv.put("ADRESP", modelCustomers.getKodpocztowy());
        cv.put("ULICA", modelCustomers.getUlica());
        cv.put("NUMERD", modelCustomers.getNumerDomu());
        cv.put("TELEFON", modelCustomers.getTelefon());
        cv.put("NIP", modelCustomers.getNip());

        long wynik = db.insert(TABLE_NAME, null, cv);
        if (wynik == -1) {
            Toast.makeText(context, "Błąd", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Dodano klienta", Toast.LENGTH_SHORT).show();
        }
    }

    // Usuwanie z klientów z bazy danych
    public int delateCustomers(int id) {
        SQLiteDatabase db = getWritableDatabase();

        return db.delete( TABLE_NAME,"ID =?", new String[]{String.valueOf(id)});

    }

    // Wyciąganie z bazy danych
    public ArrayList<ModelCustomers> getListCustomers() {
        ArrayList<ModelCustomers> modelCustomersList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCustomers = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursorCustomers.moveToNext()) {
            ModelCustomers modelCustomers = new ModelCustomers(
                    cursorCustomers.getString(1),
                    cursorCustomers.getString(2),
                    cursorCustomers.getString(3),
                    cursorCustomers.getString(4),
                    cursorCustomers.getString(5),
                    cursorCustomers.getString(6),
                    cursorCustomers.getString(7),
                    cursorCustomers.getString(8));
            modelCustomersList.add(modelCustomers);

        }
        cursorCustomers.close();
        return modelCustomersList;
    }
}
