package com.example.transport10v.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.transport10v.R;
import com.example.transport10v.database.DBHelperCustomers;
import com.example.transport10v.model.ModelCustomers;

import java.util.ArrayList;

public class AdapterCustomers extends RecyclerView.Adapter<AdapterCustomers.ViewHolder> implements Filterable {
    private final Context context;
    ArrayList<ModelCustomers> modelCustomersArrayList1;
    ArrayList<ModelCustomers> modelSearchCustomers1;

    public AdapterCustomers(Context context, ArrayList<ModelCustomers> modelCustomersArrayList1) {
        this.context = context;
        this.modelCustomersArrayList1 = modelCustomersArrayList1;
        this.modelSearchCustomers1 = modelCustomersArrayList1;
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults filterResults = new FilterResults();
                if (charSequence == null | charSequence.length() == 0){
                    filterResults.count = modelSearchCustomers1.size();
                    filterResults.values = modelSearchCustomers1;
                }else{
                    String searchCust = charSequence.toString().toLowerCase();
                    ArrayList<ModelCustomers> resultCust = new ArrayList<>();
                    for (ModelCustomers modelCustomers: modelSearchCustomers1){
                        if (modelCustomers.getNip().toLowerCase().contains(searchCust)){
                            resultCust.add(modelCustomers);
                        }
                    }
                    filterResults.count = resultCust.size();
                    filterResults.values = resultCust;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                modelCustomersArrayList1 = (ArrayList<ModelCustomers>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_customers_search, parent, false);
        return new AdapterCustomers.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModelCustomers modelCustomers = modelCustomersArrayList1.get(position);
        holder.Name_c.setText(modelCustomers.getImie());
        holder.LastN_c.setText(String.valueOf(modelCustomers.getNazwisko()));
        holder.City_c.setText(modelCustomers.getMiejscowosc());
        holder.CodeC_c.setText(String.valueOf(modelCustomers.getKodpocztowy()));
        holder.Street_c.setText(modelCustomers.getUlica());
        holder.NumberS_c.setText(String.valueOf(modelCustomers.getNumerDomu()));
        holder.NumberP_c.setText(String.valueOf(modelCustomers.getTelefon()));
        holder.NumberN_c.setText(String.valueOf(modelCustomers.getNip()));
        holder.btnAddCustomerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.btnEditBuisnes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.btnDelBuis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Usunięcie !");
                builder.setMessage("Czy napewno chesz usunąć "+ "\n"+ modelCustomers.getImie()+" "+modelCustomers.getNazwisko()+ "?");
                builder.setPositiveButton("Tak", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                       DBHelperCustomers databaseHelper = new DBHelperCustomers(context);
                        int dcustomers = databaseHelper.delateCustomers(modelSearchCustomers1.indexOf(0));
                        if (dcustomers >0){
                            Toast.makeText(context,"Klient usunięty", Toast.LENGTH_SHORT).show();
                            modelCustomersArrayList1.remove(modelCustomers);
                            notifyDataSetChanged();
                        }else{
                            Toast.makeText(context,"Błąd", Toast.LENGTH_SHORT).show();
                        }
                        delateCustomerPosition(position);
                    }
                });
                builder.setNegativeButton("Nie",null);
                builder.show();

            }

        });
    }
    private void delateCustomerPosition(int position){
        modelCustomersArrayList1.remove(position);
        notifyItemRangeRemoved(position, position);
        notifyItemRangeChanged(position, modelCustomersArrayList1.size());
    }
    @Override
    public int getItemCount() {
        return modelCustomersArrayList1.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public  TextView Name_c, LastN_c, City_c, CodeC_c,Street_c, NumberS_c,NumberP_c,NumberN_c;
        public ImageButton btnAddCustomerText, btnEditBuisnes, btnDelBuis;
        public LinearLayout recyclerView;


        public ViewHolder(View itemView) {
            super(itemView);

            recyclerView = itemView.findViewById(R.id.buisnessView);
            name = itemView.findViewById(R.id.editTextName);
            Name_c = itemView.findViewById(R.id.c_name_c1);
            LastN_c = itemView.findViewById(R.id.c_last_c1);
            City_c = itemView.findViewById(R.id.c_town_c1);
            CodeC_c = itemView.findViewById(R.id.c_cod1);
            Street_c = itemView.findViewById(R.id.c_street_c1);
            NumberS_c = itemView.findViewById(R.id.c_nrd_c1);
            NumberP_c = itemView.findViewById(R.id.c_tel_c1);
            NumberN_c = itemView.findViewById(R.id.c_nip_c1);

            btnAddCustomerText = itemView.findViewById(R.id.btnOpcja1);
            btnDelBuis = itemView.findViewById(R.id.btmDelBuis);
            btnEditBuisnes = itemView.findViewById(R.id.btnEditBuis);
        }
    }
}
