package com.example.sekolahku.datasource;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sekolahku.model.Siswa;

import java.util.ArrayList;
import java.util.List;

public class SiswaDatasource {
//    Tahap Satu
    private final DatabaseHelper databaseHelper;
// Tahap Kedua
    public SiswaDatasource(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }
// Tahap Ketiga
    private ContentValues convertToContentValues(Siswa siswa) {
        ContentValues contentValues = new ContentValues();

        contentValues.put("namaDepan", siswa.getNamaDepan());
        contentValues.put("namaBelakang", siswa.getNamaBelakang());
        contentValues.put("phoneNumber", siswa.getPhoneNumber());
        contentValues.put("email", siswa.getEmail());
//        contentValues.put("tglLahir", siswa.getTglLahir());
        contentValues.put("gender", siswa.getGender());
        contentValues.put("education", siswa.getEducation());
        contentValues.put("hoby", siswa.getHoby());
        contentValues.put("alamat", siswa.getAlamat());

        return contentValues;
    }
// Tahap Kelima
    private Siswa convertToSiswa(Cursor cursor) {
        Siswa siswa = new Siswa();

        siswa.setNamaDepan(cursor.getString(0));
        siswa.setNamaBelakang(cursor.getString(1));
        siswa.setPhoneNumber(cursor.getString(2));
        siswa.setEmail(cursor.getString(3));
//        siswa.setTglLahir(cursor.getString(4));
        siswa.setGender(cursor.getString(5));
        siswa.setEducation(cursor.getString(6));
        siswa.setHoby(cursor.getString(7));
        siswa.setAlamat(cursor.getString(8));

        return siswa;
    }
// Tahap Keempat
    public void save(Siswa siswa) {
        ContentValues contentValues = convertToContentValues(siswa);

        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.insertOrThrow("siswa", null,contentValues);
        database.close();
    }
// Tahap Keenam
    public List<Siswa> getAll() {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT *FROM siswa", null);

        List<Siswa> foundSiswaList = new ArrayList<>();

        while (cursor.moveToNext()) {
            Siswa siswa = convertToSiswa(cursor);
            foundSiswaList.add(siswa);
        }
        cursor.close();
        database.close();

        return foundSiswaList;
    }
}
