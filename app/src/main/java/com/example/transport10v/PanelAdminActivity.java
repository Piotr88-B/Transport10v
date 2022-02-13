package com.example.transport10v;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.transport10v.adapter.AdapterArt;
import com.example.transport10v.database.BDHelperArt;
import com.example.transport10v.model.ModelArt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class PanelAdminActivity extends AppCompatActivity {
   private ListView lvArt;
   private AdapterArt adapterArt;
    private List<ModelArt> modelArtList;
    private BDHelperArt databaseHelperArt;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panel_admin);

        lvArt = (ListView) findViewById(R.id.listaPanelArt);
        databaseHelperArt = new BDHelperArt(this);

        File db = getApplicationContext().getDatabasePath(BDHelperArt.ARTNAME);
        if (!db.exists()) {
            databaseHelperArt.getReadableDatabase();
            if (copydbArt(PanelAdminActivity.this)) {
                Toast.makeText(this, "db skopiowany", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "db bład", Toast.LENGTH_SHORT).show();
                return;
            }
        }

      modelArtList = databaseHelperArt.getListArt();

      adapterArt = new AdapterArt(this, modelArtList);

      lvArt.setAdapter(adapterArt);

    }
    private boolean copydbArt(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(databaseHelperArt.ARTNAME);
            String outfname = databaseHelperArt.ART_PATH + databaseHelperArt.ARTNAME;
            OutputStream outputStream = new FileOutputStream(outfname);
            byte[] buff = new byte[1024];
            int length;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("PanelAdminActivity", "DB kopia");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}