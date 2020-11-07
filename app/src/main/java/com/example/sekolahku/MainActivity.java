package com.example.sekolahku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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
    private EditText etTglLahir;
    private RadioGroup genderRb;
    private Spinner educationSp;
    private CheckBox cbMembaca;
    private CheckBox cbMenulis;
    private CheckBox cbMenggambar;
    private TextInputEditText etAlamat;
    private Button btnUpdates;

    private Siswa receivedSiswa;

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void loadDetailDataSiswa(long idSiswa) {
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            SiswaDatasource datasource = new SiswaDatasource(databaseHelper);
            Siswa siswa = datasource.findById(idSiswa);
            receivedSiswa = datasource.findById(idSiswa);

            etNamaDepan.setText(siswa.getNamaDepan());
            etNamaBelakang.setText(siswa.getNamaBelakang());
            etNoHandphone.setText(siswa.getPhoneNumber());
            etAlamat.setText(siswa.getAlamat());
            etEmail.setText(siswa.getEmail());
            etTglLahir.setText(siswa.getTglLahir());

            String gender = siswa.getGender();
            if (gender.equals("Pria")) {
                genderRb.check(R.id.priaRb);
            } else {
                genderRb.check(R.id.wanitaRb);
            }
            ;
            // Tenari Operator hanya berlaku jika logika nya adalah if dan else
            // int selectedId = (gender.equals("Pria")) ? R.id.priaRb : R.id.wanitaRb;
            // genderRb.check(selectedId);

            String hobi = receivedSiswa.getHoby();
            cbMembaca.setChecked(hobi.contains("Membaca"));
            cbMenulis.setChecked(hobi.contains("Menulis"));
            cbMenggambar.setChecked(hobi.contains("Menggambar"));

            SpinnerAdapter adapter = educationSp.getAdapter();
            if (adapter instanceof ArrayAdapter) {
                int position = ((ArrayAdapter) adapter).getPosition(siswa.getEducation());
                educationSp.setSelection(position);
            }

            btnUpdates.setText("Updates");

            showToast("Data Siswa Berhasil Di Load");
        } catch (Exception e) {
            showToast(e.getMessage());
        }
    }

    private void initDataSiswa(Siswa siswa) {
        String inputNamaDepan = etNamaDepan.getText().toString().trim();
        String inputNamaBelakang = etNamaBelakang.getText().toString().trim();
        String inputPhoneNumber = etNoHandphone.getText().toString().trim();
        String inputEmail = etEmail.getText().toString().trim();
        String inputAlamat = etAlamat.getText().toString().trim();
        //
        String inputTglLahir = etTglLahir.getText().toString().trim();

        String selectedGender;
        if (genderRb.getCheckedRadioButtonId() == R.id.priaRb) {
            selectedGender = "Pria";
        } else {
            selectedGender = "Wanita";
        }

        List<String> selectedHobies = new ArrayList<>();
        if (cbMembaca.isChecked()) {
            selectedHobies.add("Membaca");
        }
        if (cbMenulis.isChecked()) {
            selectedHobies.add("Menulis");
        }
        if (cbMenggambar.isChecked()) {
            selectedHobies.add("Membaca");
        }
        String joinHobi = TextUtils.join(",", selectedHobies);
        String selectedEducation = educationSp.getSelectedItem().toString();

        siswa.setNamaDepan(inputNamaDepan);
        siswa.setNamaBelakang(inputNamaBelakang);
        siswa.setPhoneNumber(inputPhoneNumber);
        siswa.setEmail(inputEmail);
        //
        siswa.setTglLahir(inputTglLahir);
        siswa.setGender(selectedGender);
        siswa.setEducation(selectedEducation);
        siswa.setHoby(joinHobi);
        siswa.setAlamat(inputAlamat);

    }

    private void addNewSiswa() {
        Siswa siswa = new Siswa();
        initDataSiswa(siswa);

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

    private void updateSiswa() {
        initDataSiswa(receivedSiswa);

        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            SiswaDatasource datasource = new SiswaDatasource(databaseHelper);
            datasource.update(receivedSiswa);
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

    //  Tahap Keempat
    private void save() {
        if (receivedSiswa != null) {
            updateSiswa();
        } else {
            addNewSiswa();
        }
    }

    //  Action agar icon back fungsi Step 2
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
        etTglLahir = findViewById(R.id.etTglLahir);
        genderRb = findViewById(R.id.genderRb);
        educationSp = findViewById(R.id.listItem);
        cbMembaca = findViewById(R.id.cbMembaca);
        cbMenulis = findViewById(R.id.cbMenulis);
        cbMenggambar = findViewById(R.id.cbMenggambar);
        etAlamat = findViewById(R.id.etAlamat);
        btnUpdates = findViewById(R.id.saveBtn);
        MaterialButton saveBtn = findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String etNoHandphone1 = etNoHandphone.getText().toString();
                String etEmail1 = etEmail.getText().toString();

                if (etNoHandphone1.equals("")) {
                    etNoHandphone.setError("qweqweqw");
                } else if (etEmail1.equals("")) {
                    Toast.makeText(MainActivity.this, "Pass daskdaisn", Toast.LENGTH_SHORT).show();
                }
                {
                    save();
                }
            }
        });

        etTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        long receivedIdSiswa = getIntent().getLongExtra("id siswa", -1);
        if (receivedIdSiswa == -1) {
            showToast("Tidak menerima id siswa");
        } else {
            loadDetailDataSiswa(receivedIdSiswa);
        }
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