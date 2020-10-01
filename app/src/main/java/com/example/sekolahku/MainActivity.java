package com.example.sekolahku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sekolahku.datasource.DatabaseHelper;
import com.example.sekolahku.datasource.SiswaDatasource;
import com.example.sekolahku.model.Siswa;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

// Implements = action ketika tanggal dalam dialog di pilih user
public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    // Tahap Kedua
    private TextInputEditText etNamaDepan;
    private TextInputEditText etNamaBelakang;
    private TextInputEditText etNoHandphone;
    private TextInputEditText etEmail;
    //
    private TextInputEditText etTglLahir;
    private RadioGroup genderRb;
    private Spinner educationSp;
    private CheckBox cbMembaca;
    private CheckBox cbMenulis;
    private CheckBox cbMenggambar;
    private TextInputEditText etAlamat;

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    // Tahap Keempat
    private void save() {
        String inputNamaDepan = etNamaDepan.getText().toString().trim();
        String inputNamaBelakang = etNamaBelakang.getText().toString().trim();
        String inputPhoneNumber = etNoHandphone.getText().toString().trim();
        String inputEmail = etEmail.getText().toString().trim();
        String inputAlamat = etAlamat.getText().toString().trim();
        //
        String selectedTglLahir = etTglLahir.getText().toString().trim();

        String selectedGender;
        if (genderRb.getCheckedRadioButtonId() == R.id.priaRb) {
            selectedGender = "Pria";
        } else {
            selectedGender= "Wanita";
        }
        
        List<String> selectedHobies = new ArrayList<>();
        if ( cbMembaca.isChecked()) {
            selectedHobies.add("Membaca");
        }
        if ( cbMenulis.isChecked()) {
            selectedHobies.add("Menulis");
        }
        if ( cbMenggambar.isChecked()) {
            selectedHobies.add("Membaca");
        }
        String joinHobi = TextUtils.join(",", selectedHobies);
        String selectedEducation = educationSp.getSelectedItem().toString();

        Siswa siswa = new Siswa();
        siswa.setNamaDepan(inputNamaDepan);
        siswa.setNamaBelakang(inputNamaBelakang);
        siswa.setPhoneNumber(inputPhoneNumber);
        siswa.setEmail(inputEmail);
        //
        siswa.setTglLahir(selectedTglLahir);
        siswa.setGender(selectedGender);
        siswa.setEducation(selectedEducation);
        siswa.setHoby(joinHobi);
        siswa.setAlamat(inputAlamat);


        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            SiswaDatasource datasource = new SiswaDatasource(databaseHelper);
            datasource.save(siswa);
            showToast("SUKSES");
//            Agar automatic ke form
            finish();
        } catch (Exception e) {
            showToast(e.getMessage());
        }
//
//        showToast("Hi, " + siswa.getNamaDepan() + " " + siswa.getNamaBelakang() +"\n" +
//                "Phone Number :" + siswa.getPhoneNumber() + "\n" +
//                "Email :" + siswa.getEmail() + "\n" +
//                "Gender :" + siswa.getGender() + "\n" +
//                "Education :" + siswa.getEducation() + "\n" +
//                "Hobi :" + siswa.getHoby() + "\n" +
//                "Alamat :" + siswa.getAlamat());
    }

    //        Action agar icon back fungsi Step 2

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selectedMenuId = item.getItemId();

        if (selectedMenuId == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    // Tahap Pertama
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Agar muncul icon back Step 1
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    // Tahap Ketiga
        etNamaDepan = findViewById(R.id.etNamaDepan);
        etNamaBelakang = findViewById(R.id.etNamaBelakang);
        etNoHandphone = findViewById(R.id.etNoHandphone);
        etEmail = findViewById(R.id.etEmail);
        //
        etTglLahir = findViewById(R.id.etTglLahir);
        genderRb = findViewById(R.id.genderRb);
        educationSp = findViewById(R.id.listItem);
        cbMembaca = findViewById(R.id.cbMembaca);
        cbMenulis = findViewById(R.id.cbMenulis);
        cbMenggambar = findViewById(R.id.cbMenggambar);
        etAlamat= findViewById(R.id.etAlamat);
        MaterialButton saveBtn = findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });

        etTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        Calendar today = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        String selectedDate = String.format("%s-%s-%s",
                dayOfMonth,
                (month + 1),
                year);

        etTglLahir.setText(selectedDate);
    }
}