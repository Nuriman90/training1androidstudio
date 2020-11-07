package com.example.sekolahku;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.sekolahku.adapter.SiswaItemAdapter;
import com.example.sekolahku.datasource.DatabaseHelper;
import com.example.sekolahku.datasource.SiswaDatasource;
import com.example.sekolahku.model.Siswa;

import java.util.ArrayList;
import java.util.List;

public class ListMainActivity extends AppCompatActivity {
    protected Cursor cursor;
    DatabaseHelper databaseHelper;
    // Tahap Pertama
    private ListView siswaLv;
    private SiswaItemAdapter adapter;

    // Tahap Kedua
    private void showToast (String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG)
                .show();
    }

    // Code Pindah Ke Detail
    private void starDetailActivity(int position ) {
        Intent intent = new Intent(this, DetailActivity.class);

        Siswa selectedSiswa = adapter.getItem(position);
        intent.putExtra("id siswa", selectedSiswa.getId());

        startActivity(intent);
    }
    // Code Pindah Ke Detail
    private void starFormDetailActivity(int position ) {
        Intent intent = new Intent(this, MainActivity.class);

        Siswa selectedSiswa = adapter.getItem(position);
        intent.putExtra("id siswa", selectedSiswa.getId());

        startActivity(intent);
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

            siswaLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    starDetailActivity(position);
                }
            });

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


    // Action Tambah
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectedMenuId = item.getItemId();

        if (selectedMenuId == R.id.addSiswaMenu) {
            startMainActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void delete(Siswa siswa) {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SiswaDatasource datasource = new SiswaDatasource(databaseHelper);
        datasource.remove(siswa);
        adapter.notifyDataSetChanged();
    }

    // Edit and Delete
    @Override
    public void onCreateContextMenu (ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            getMenuInflater().inflate(R.menu.menu_context, menu);
            super.onCreateContextMenu(menu, v, menuInfo);
}
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int selectedPosition = info.position;

        switch (id) {
            case R.id.action_delete:
                delete(adapter.getItem(selectedPosition));
                Intent intent = new Intent(ListMainActivity.this, ListMainActivity.class);
                startActivity(intent);
                Toast.makeText(this,"Delete Sukses", Toast.LENGTH_SHORT).show();
                break;
            case R.id. action_edit:
                starFormDetailActivity(selectedPosition);
                break;
        }

        return super.onContextItemSelected(item);
    }

    // Tambahin Menu Pojok Kanan
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Icon Search
        getMenuInflater().inflate(R.menu.menu_search, menu);
        // Icon Add
        getMenuInflater().inflate(R.menu.menu_list_activity, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // Tahap Keempat
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_main);

        siswaLv = findViewById(R.id.siswaLv);
        registerForContextMenu(siswaLv);
    }

    // Back pencet sendiri
    @Override
    protected void onResume() {
        super.onResume();
        loadDataSiswa();
    }
}