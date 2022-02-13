package com.example.transport10v;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Toast;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.example.transport10v.adapter.AdapterCustomers;
import com.example.transport10v.database.DBHelperCustomers;
import com.example.transport10v.model.ModelCustomers;
import com.google.common.collect.Range;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import dialog.AdminDialogView;

public class MainActivity extends AppCompatActivity {
     AdapterCustomers viewCustomersS;
     ArrayList<ModelCustomers> modelCustomersList;
     RecyclerView lvCustomers;
     DBHelperCustomers db;
     SearchView searchView;
     ImageButton btnAdd;
     Button btnFirma, btnAddArt, btnIndiviual;
     EditText editName, editLastName, editPhone, editMiejscowosc, editTextpostcode, editUlica,
            editTextUlicaNr, editTextDate, editTextod, editTextdo, editTextParagon;
     AwesomeValidation awesomeValidation;
     LinearLayout linearLayoutArt,listaCustomers,linerLayoutBuisness;
     ScrollView SearchViewTemplet;
     DatePickerDialog.OnDateSetListener setListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //button
        btnAdd = findViewById(R.id.btnAdd); // Przejście do mainAddCustomers
        btnIndiviual = findViewById(R.id.btnIndiviual); //dodawanie klienta do template
        btnFirma = findViewById(R.id.btnFirma); // dodawanie firmy do template
        btnAddArt = findViewById(R.id.btnAddArt);

        //linerLayout
        listaCustomers = findViewById(R.id.listaCustomers);
        linearLayoutArt = findViewById(R.id.listaViewArt);
        linerLayoutBuisness = findViewById(R.id.buisnessView);
        //scroll
        SearchViewTemplet = findViewById(R.id.ViewTemplate);
        //Add Art

        // button dodawania art wyłączony
        btnAddArt.setEnabled(false);
        //wyłączenie generowanie pdf


        //funkcje
        ViewCustomerAdd();
        AddSearchBiss();




    //Activity dodające urzytkownika firmy do DB
    btnAdd.setOnClickListener(v -> {
        Intent intent = new Intent(MainActivity.this, AddCustomerBuisnessActivity.class);
        startActivity(intent);
    });
    }
    //Implementacja menu w toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Menu
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_admin:
                return openDialog();
            case R.id.item_view:
                return openActivityPdf();
            case R.id.item_pdf:

               if(awesomeValidation.validate()){
                    try {
                        createPdf();
                    } catch (IOException | DocumentException e) {
                        e.printStackTrace();
                    }
               }
        }
        return super.onOptionsItemSelected(item);
    }

    //Dialog admin
    public boolean openDialog() {
        AdminDialogView adminDialog = new AdminDialogView();
        adminDialog.show(getSupportFragmentManager(), "Admin");
        return false;
    }

    //Podgląd szablonu w pdf
    public boolean openActivityPdf() {
        Intent intent = new Intent(MainActivity.this, ViewPdfActivity.class);
        intent.putExtra("Viewtype", "code_cache");
        startActivity(intent);
        return false;
    }

    //Dodawanie klienta indywidualnego do LinearLayout za pomocą przycisku.
    private void ViewCustomerAdd(){

        btnIndiviual.setOnClickListener(v -> {
            viewIndivCustomers();
            addCalendar();
            ViewArtAdd();
            addValidation();
            btnIndiviual.setEnabled(false);
            btnAddArt.setEnabled(true);
            btnFirma.setEnabled(false);

        });
    }

    //usuwanie widoku szablonu art z template
    private void removeArtDel(View view){
        linearLayoutArt.removeView(view);
    }

    // usuwanie widoku szablonu urzytkowanika indywidualnego z template
    private void removeCustomerDel(View view){
        listaCustomers.removeView(view);
    }

    //dodawanie szablonu art do template
    private void ViewArtAdd() {

        btnAddArt.setOnClickListener(v -> {

            View addArtView = getLayoutInflater().inflate(R.layout.add_art_main,null,false);
            AutoCompleteTextView addNumerArtView =  addArtView.findViewById(R.id.addNumerArtView);
            EditText addNazwaArtView = addArtView.findViewById(R.id.addNazwaArtView);
            EditText addIloscArtView =  addArtView.findViewById(R.id.addIloscArtView);
            EditText addWagaArtView = addArtView.findViewById(R.id.addWagaArtView);
            ImageView imageViewDelate = addArtView.findViewById(R.id.ImageArtDelateView);


            imageViewDelate.setOnClickListener(v1 -> removeArtDel(addArtView));
            linearLayoutArt.addView(addArtView);
        });
    }

    //Dodawanie firmy do LinearLayout za pomocą przycisku.
    public void AddSearchBiss(){
        btnFirma.setOnClickListener(v -> {

            viewBuisnessRv();
            searchCustomers();
            viewCustomersdlist();
            ViewArtAdd();
            addValidation();
            addCalendar();

            btnIndiviual.setEnabled(false);
            btnAddArt.setEnabled(true);
            btnFirma.setEnabled(false);
        });
    }

    // Kalendarz
   private void addCalendar(){
        editTextDate = findViewById(R.id.editTextDate);
        Calendar calendar = Calendar.getInstance();
         int year = calendar.get(Calendar.YEAR);
         int month = calendar.get(Calendar.MONTH);
         int day = calendar.get(Calendar.DAY_OF_MONTH);

     editTextDate.setOnClickListener(v -> {

         DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, android.R.style.Widget_Holo_ActionBar_Solid,
                 setListener, year, month, day);
         datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         datePickerDialog.show();
     });
        setListener = (view, year1, month1, day1) -> {
            month1 = month1 + 1;
            String date = day1 + "/" + month1 + "/" + year1;
            editTextDate.setText(date);
        };
        editTextDate.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, (view, year12, month12, day12) -> {
                month12 = month12 + 1;
                String date = day12 + "/" + month12 + "/" + year12;
                editTextDate.setText(date);
            }, year, month, day);
            datePickerDialog.show();
        });
    }

    // tworzenie pdf
    public boolean createPdf() throws IOException, DocumentException {

        String editNameP = editName.getText().toString();
        String editLastNameP = editLastName.getText().toString();
        String editPhoneP = editPhone.getText().toString();
        String editMiejscowoscP = editMiejscowosc.getText().toString();
        String editUlicaP = editUlica.getText().toString();
        String editTextUlicaNrP = editTextUlicaNr.getText().toString();
        String editDateP = editTextDate.getText().toString();
        String editTextodP = editTextod.getText().toString();
        String editTextdoP = editTextdo.getText().toString();
        String editTextParagonP = editTextParagon.getText().toString();


        String path = MainActivity.this.getFilesDir().getAbsolutePath()+"/"+"Transport.pdf";

        BaseFont base = BaseFont.createFont("assets/arial.ttf", BaseFont.CP1250, BaseFont.EMBEDDED);
        Font myFont = new Font(base, 14, Font.NORMAL);

        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Document pdfDocument = new Document(PageSize.A4 , 15,15,5,25);

        try {
            PdfWriter.getInstance(pdfDocument, new FileOutputStream(file.getAbsoluteFile()));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pdfDocument.open();

        String name = "Imie i Nazwisko: " + editNameP+ "  " +editLastNameP+"\n"+"Numer telefonu: "
                + editPhoneP + "\n" + "Miejscowość: " + editMiejscowoscP + "\n" + "Ulica: "
                + editUlicaP+" "+editTextUlicaNrP ;
        try {

            PdfPTable table = new PdfPTable(1);
            PdfPCell cell = new PdfPCell(new Phrase());

            PdfPCell title = new PdfPCell(new Phrase("TRANSPORT                          Oryginał",myFont));
            title.getEffectivePaddingTop();
            title.setPadding(10f);
            title.setPaddingLeft(160f);

            table.addCell(title);
            cell.setColspan(1);
            //1
            PdfPCell cell1 = new PdfPCell(new Phrase("Dane klienta:"));
            cell1.setBackgroundColor(BaseColor.GRAY);
            table.addCell(cell1);
            //2
            PdfPCell cell2 = new PdfPCell(new Phrase(name, myFont));
            table.addCell(cell2);
            //3
            PdfPCell cell3 = new PdfPCell(new Phrase("Termin Transportu:"));
            cell3.setBackgroundColor(BaseColor.GRAY);
            table.addCell(cell3);

            table.addCell("Data: " + editDateP+" "+"Godzina: "+ editTextodP + " - " + editTextdoP);
            //4
            PdfPCell cell4 = new PdfPCell(new Phrase("Paragon:"));
            cell4.setBackgroundColor(BaseColor.GRAY);
            table.addCell(cell4);
            table.addCell("Dane systemowe z paragonu: "+ editTextParagonP);
            //5
            PdfPCell cell5 = new PdfPCell(new Phrase("Artykuły:"));
            cell5.setBackgroundColor(BaseColor.GRAY);
            table.addCell(cell5);
            //6
            PdfPCell cell6 = new PdfPCell(new Phrase("sdas"));
            cell6.setColspan(3);
            cell6.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell6);

            //ADD 1 TABELE
            pdfDocument.add(table);
            //Przerwa
            PdfPTable table1 = new PdfPTable(1);
            pdfDocument.add(new Paragraph(""+"\n"+"\n"+"\n"));

            //Kopia
            PdfPCell title1 = new PdfPCell(new Phrase("TRANSPORT                                Kopia"));
            title1.getEffectivePaddingTop();
            title1.setPadding(10f);
            title1.setPaddingLeft(160f);
            table1.addCell(title1);
            //7
            PdfPCell cell7 = new PdfPCell(new Phrase("Dane klienta:"));
            cell7.setBackgroundColor(BaseColor.GRAY);
            table1.addCell(cell7);
            //8
            PdfPCell cell8 = new PdfPCell(new Phrase(name, myFont));
            table1.addCell(cell8);
            //9
            PdfPCell cell9 = new PdfPCell(new Phrase("Termin Transportu:"));
            cell9.setBackgroundColor(BaseColor.GRAY);
            table1.addCell(cell9);
            table1.addCell("Data: " + editDateP+" "+"Godzina: "+ editTextodP + " - " + editTextdoP);
            //10
            PdfPCell cell10 = new PdfPCell(new Phrase("Paragon:"));
            cell10.setBackgroundColor(BaseColor.GRAY);
            table1.addCell(cell10);
            table1.addCell("Dane systemowe z paragonu: "+ editTextParagonP);
            //11
            PdfPCell cell11 = new PdfPCell(new Phrase("Artykuły:"));
            cell11.setBackgroundColor(BaseColor.GRAY);
            table.addCell(cell11);

            pdfDocument.add(table1);

        } catch (DocumentException e) {
            e.printStackTrace();

        }
        pdfDocument.close();

        Toast.makeText(MainActivity.this, "Utworzono plik PDF", Toast.LENGTH_LONG).show();

        return true;
    }

    //Walidacja
    public void addValidation(){
        awesomeValidation = new AwesomeValidation(ValidationStyle.COLORATION);
        awesomeValidation.setColor(Color.WHITE);
        awesomeValidation.addValidation(editName, "[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząęćżźńłóśĄĆĘŁŃÓŚŹŻ\\s]{1,}$", "Popraw pole");
        awesomeValidation.addValidation(editLastName, "[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząęćżźńłóśĄĆĘŁŃÓŚŹŻ\\s]{1,}$", "Popraw pole");
        awesomeValidation.addValidation(editPhone, "[5-9]{1}[0-9]{8}$", "Popraw pole");
        awesomeValidation.addValidation(editTextpostcode, "[0-9]{2}" + "-" + "[0-9]{3}$", "Popraw pole");
        awesomeValidation.addValidation(editMiejscowosc, "[A-ZĄĆĘŁŃÓŚŹŻ]{1}[a-ząęćżźńłóśĄĆĘŁŃÓŚŹŻ\\s]{1,}$", "Popraw pole");
        awesomeValidation.addValidation(editUlica, RegexTemplate.NOT_EMPTY, "Popraw pole");
        awesomeValidation.addValidation(editTextod, Range.closed(8, 22), "Popraw pole");
        awesomeValidation.addValidation(editTextdo, Range.closed(8, 22), "Popraw pole");
        awesomeValidation.addValidation(editTextParagon, RegexTemplate.NOT_EMPTY, "Popraw pole");
    }

    private void viewBuisnessRv(){
        View addBuisnessView = getLayoutInflater().inflate(R.layout.add_business_view,null,false);
        ImageView imageCustomerDelate = (ImageView)addBuisnessView.findViewById(R.id.ImageCustomersTemplet);
        listaCustomers.addView(addBuisnessView);
        searchView =(SearchView) findViewById(R.id.editSearchCustomers1);

        lvCustomers = (RecyclerView) findViewById(R.id.recrdView1);
        db = new DBHelperCustomers(MainActivity.this);
        modelCustomersList = db.getListCustomers();
        viewCustomersS = new AdapterCustomers(MainActivity.this, modelCustomersList);
        lvCustomers.setAdapter(viewCustomersS);

        lvCustomers.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(MainActivity.this, DividerItemDecoration.VERTICAL);
        lvCustomers.addItemDecoration(dividerItemDecoration);


        editName = addBuisnessView.findViewById(R.id.editTextName);
        editTextpostcode = addBuisnessView.findViewById(R.id.editTextpostcode);
        editLastName = addBuisnessView.findViewById(R.id.editTextLastName);
        editPhone = addBuisnessView.findViewById(R.id.editTextPhone);
        editMiejscowosc = addBuisnessView.findViewById(R.id.editTextMiejscowosc);
        editUlica =addBuisnessView.findViewById(R.id.editTextUlica);
        editTextUlicaNr = addBuisnessView.findViewById(R.id.editTextUlicaNr);
        editTextDate = findViewById(R.id.editTextDate);
        editTextod = addBuisnessView.findViewById(R.id.editTextod);
        editTextdo = addBuisnessView.findViewById(R.id.editTextdo);
        editTextParagon = addBuisnessView.findViewById(R.id.editTextParagon);

        imageCustomerDelate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCustomerDel(addBuisnessView);
                btnIndiviual.setEnabled(true);
                btnAddArt.setEnabled(false);
                btnFirma.setEnabled(true);
            }
        });
    }

    private void viewIndivCustomers(){
        View addCustomervView = getLayoutInflater().inflate(R.layout.add_customers_view, null, false);
        editName = addCustomervView.findViewById(R.id.editTextName);
        editTextpostcode = addCustomervView.findViewById(R.id.editTextpostcode);
        editLastName = addCustomervView.findViewById(R.id.editTextLastName);
        editPhone = addCustomervView.findViewById(R.id.editTextPhone);
        editMiejscowosc = addCustomervView.findViewById(R.id.editTextMiejscowosc);
        editUlica = addCustomervView.findViewById(R.id.editTextUlica);
        editTextUlicaNr = addCustomervView.findViewById(R.id.editTextUlicaNr);
        editTextDate = findViewById(R.id.editTextDate);
        editTextod = addCustomervView.findViewById(R.id.editTextod);
        editTextdo = addCustomervView.findViewById(R.id.editTextdo);
        editTextParagon = addCustomervView.findViewById(R.id.editTextParagon);
        ImageView imageCustomerDelate = (ImageView) addCustomervView.findViewById(R.id.ImageCustomersTemplet);
        listaCustomers.addView(addCustomervView);



        imageCustomerDelate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCustomerDel(addCustomervView);
                btnIndiviual.setEnabled(true);
                btnAddArt.setEnabled(false);
                btnFirma.setEnabled(true);
            }
        });
    }

    private void searchCustomers(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewCustomersS.getFilter().filter(newText);
                return true;
            }
        });

    }

    private void viewCustomersdlist() {
        modelCustomersList = db.getListCustomers();
        viewCustomersS = new AdapterCustomers(this, modelCustomersList);
        lvCustomers.setAdapter(viewCustomersS);
        lvCustomers.setLayoutManager(new LinearLayoutManager(this));
        viewCustomersS.notifyDataSetChanged();
    }
}
