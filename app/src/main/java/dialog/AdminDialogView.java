package dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.transport10v.PanelAdminActivity;
import com.example.transport10v.R;

public class AdminDialogView extends AppCompatDialogFragment {
     EditText editTextAdminName, editTextAdminPassword;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view);
        builder.setTitle("Login");

        builder.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setPositiveButton("OK",new DialogInterface.OnClickListener() {
            @Override
            public void onClick (DialogInterface dialog,int which){

                if (editTextAdminName.getText().toString().equals("admin")
                        && editTextAdminPassword.getText().toString().equals("admin")) {
                    Intent intent = new Intent(getContext(), PanelAdminActivity.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "Zalogowany jako Administrator", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Błąd logowania !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editTextAdminName =view.findViewById(R.id.editTextAdminName);
        editTextAdminPassword= view.findViewById(R.id.editTextAdminPassword);
        return builder.create();
    }
}
