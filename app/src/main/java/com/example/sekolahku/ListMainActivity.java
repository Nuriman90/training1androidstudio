package com.example.sekolahku;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sekolahku.adapter.SiswaItemAdapter;
import com.example.sekolahku.datasource.DatabaseHelper;
import com.example.sekolahku.datasource.SiswaDatasource;
import com.example.sekolahku.model.Siswa;

import java.util.ArrayList;
import java.util.List;

public class ListMainActivity extends AppCompatActivity {
//    Tahap Pertama
    private ListView siswaLv;
    private SiswaItemAdapter adapter;
// Tahap Kedua
    private void showToast (String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
                .show();
    }
// Tahap Ketiga
    private void loadDataSiswa() {
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            SiswaDatasource datasource = new SiswaDatasource(databaseHelper);
            List<Siswa> foundSiswaList = datasource.getAll();

            adapter = new SiswaItemAdapter(this, foundSiswaList);
//
//            List<String> siswaNames = new ArrayList<>();
//            for (Siswa siswa : foundSiswaList) {
//                siswaNames.add(siswa.getNamaDepan() + " " + siswa.getNamaBelakang());
//            }
//
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, siswaNames);
            siswaLv.setAdapter(adapter);

            showToast("Data Loaded Successfully");
        } catch (Exception e) {
            e.printStackTrace();
            showToast("Failed to load data caused by " + e.getMessage());
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectedMenuId = item.getItemId();

        if (selectedMenuId == R.id.addSiswaMenu) {
            startMainActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    // Tambahin Menu Java
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Tahap Keempat
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);

        siswaLv = findViewById(R.id.siswaLv);
    }

//    Back pencet sendiri
    @Override
    protected void onResume() {
        super.onResume();
        loadDataSiswa();
    }
}