package com.example.sekolahku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sekolahku.datasource.DatabaseHelper;
import com.example.sekolahku.datasource.SiswaDatasource;
import com.example.sekolahku.model.Siswa;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    private TextView fullnameTv;
    private TextView etNoHandphone;
    private TextView genderRb;
    private TextView jenjang;
    private TextView ethobi;
    private TextView etAlamat;

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
                .show();
    }
    private void loadDetailDataSiswa(long idSiswa) {
     try {
         DatabaseHelper databaseHelper = new DatabaseHelper(this);
         SiswaDatasource datasource = new SiswaDatasource(databaseHelper);
         Siswa siswa = datasource.findById(idSiswa);

         String fullname = siswa.getNamaDepan() + " " + siswa.getNamaBelakang();
         fullnameTv.setText(fullname);

         etNoHandphone.setText(siswa.getPhoneNumber());
         genderRb.setText(siswa.getGender());
         jenjang.setText(siswa.getEducation());
         ethobi.setText(siswa.getHoby());
         etAlamat.setText(siswa.getAlamat());

         showToast("Data Siswa Berhasil Di Load");
     } catch (Exception e) {
         showToast(e.getMessage());
        }
    }
    // Action agar icon back fungsi Step 2
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectedMenuId = item.getItemId();

        if (selectedMenuId == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Agar muncul icon back Step 1
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        fullnameTv = findViewById(R.id.etNama);
        etNoHandphone = findViewById(R.id.etNoHandphone);
        genderRb = findViewById(R.id.genderRb);
        jenjang = findViewById(R.id.jenjang);
        ethobi = findViewById(R.id.ethobi);
        etAlamat = findViewById(R.id.etAlamat);

        long receivedIdSiswa = getIntent().getLongExtra("id siswa", -1);
        if (receivedIdSiswa == -1) {
            showToast("Tidak menerima id siswa");
        } else {
            loadDetailDataSiswa(receivedIdSiswa);
        }
    }
}