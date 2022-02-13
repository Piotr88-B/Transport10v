package com.example.transport10v;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.transport10v.database.DBHelperCustomers;
import com.example.transport10v.model.ModelCustomers;
import com.google.common.collect.Range;

public class AddCustomerBuisnessActivity extends AppCompatActivity {
    EditText editTextAddName;
    EditText editTextAdd2Name;
    EditText editTextAddCity;
    EditText editTextAddCod;
    EditText editTextAddStreet;
    EditText editTextAddNumberH;
    EditText editTextPhone;
    EditText editTextNip;

    Button buttonAddCustomer;
    AwesomeValidation cawesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_buisness);

        editTextAddName = findViewById(R.id.editTextAddName);
        editTextAdd2Name = findViewById(R.id.editTextAdd2Name);
        editTextAddCity =  findViewById(R.id.editTextAddCity);
        editTextAddCod = findViewById(R.id.editTextAddCod);
        editTextAddStreet =  findViewById(R.id.editTextAddStreet);
        editTextAddNumberH = findViewById(R.id.editTextAddNumberH);
        editTextPhone = findViewById(R.id.editTextAddPhone);
        editTextNip =  findViewById(R.id.editTextAddNip);

        buttonAddCustomer =  findViewById(R.id.buttonAddCustomer);

    //Rodzaj wyświetlania walidacji
        cawesomeValidation = new AwesomeValidation(ValidationStyle.COLORATION);
        cawesomeValidation.setColor(Color.WHITE);
        // Sprawdzanie poprawności pola w imieniu
        cawesomeValidation.addValidation( editTextAddName,
                "[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząęćżźńłóśĄĆĘŁŃÓŚŹŻ\\s]{1,}$","Popraw pole");
        // Sprawdzanie poprawności pola w nazwisku
        cawesomeValidation.addValidation(editTextAdd2Name,
                "[A-ZĄĆĘŁŃÓŚŹŻ]{1,}[a-ząęćżźńłóśĄĆĘŁŃÓŚŹŻ\\s]{1,}$","Popraw pole");
        // Sprawdzanie poprawności pola w wprowadzaniu miasta
        cawesomeValidation.addValidation(editTextAddCity,
                "[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząęćżźńłóśĄĆĘŁŃÓŚŹŻ\\s]{1,}$", "Popraw pole");
        // Sprawdzanie poprawności pola w wprowadzaniu kodu pocztowegu
        cawesomeValidation.addValidation(editTextAddCod,
                "[0-9]{2}"+"-"+"[0-9]{3}$", "Popraw pole");
        // Sprawdzanie poprawności pola w wprowadzaniu nazwie ulicy
        cawesomeValidation.addValidation(editTextAddStreet,
                RegexTemplate.NOT_EMPTY,"Popraw pole");
        // Sprawdzanie poprawności pola w wprowadzaniu numerze domu/mieszkania
        cawesomeValidation.addValidation(editTextAddNumberH,
                Range.closed(0,9999), "Popraw pole");
        // Sprawdzanie poprawności pola w wprowadzaniu numeru telefonu
        cawesomeValidation.addValidation(editTextPhone,
                "[5-9]{1}[0-9]{8}$", "Popraw pole");
        // Sprawdzanie poprawności pola w wprowadzaniu Nip-u firmy
        cawesomeValidation.addValidation(editTextNip,
                "[0-9]{10}$", "Popraw pole");

        //Przycisk dodający urzytkownika do DB
        buttonAddCustomer.setOnClickListener(v -> {
            if (cawesomeValidation.validate()) {
                ModelCustomers modelCustomers = null;
                try {
                    modelCustomers = new ModelCustomers(
                            editTextAddName.getText().toString(),
                            editTextAdd2Name.getText().toString(),
                            editTextAddCity.getText().toString(),
                            editTextAddCod.getText().toString(),
                            editTextAddStreet.getText().toString(),
                            editTextAddNumberH.getText().toString(),
                            editTextPhone.getText().toString(),
                            editTextNip.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(AddCustomerBuisnessActivity.this, "Błąd", Toast.LENGTH_LONG).show();
                }

                DBHelperCustomers dbHelperCustomers = new DBHelperCustomers(AddCustomerBuisnessActivity.this);
                dbHelperCustomers.AddCustomers(modelCustomers);
                Toast.makeText(AddCustomerBuisnessActivity.this, "Dodano kilenta", Toast.LENGTH_LONG).show();
            }
        });


    }

}