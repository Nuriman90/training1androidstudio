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
        contentValues.put("id", siswa.getId());
        contentValues.put("namaDepan", siswa.getNamaDepan());
        contentValues.put("namaBelakang", siswa.getNamaBelakang());
        contentValues.put("phoneNumber", siswa.getPhoneNumber());
        contentValues.put("email", siswa.getEmail());
        contentValues.put("tglLahir", siswa.getTglLahir());
        contentValues.put("gender", siswa.getGender());
        contentValues.put("education", siswa.getEducation());
        contentValues.put("hoby", siswa.getHoby());
        contentValues.put("alamat", siswa.getAlamat());

        return contentValues;
    }
// Tahap Kelima
    private Siswa convertToSiswa(Cursor cursor) {
        Siswa siswa = new Siswa();

        siswa.setId(cursor.getLong(0));
        siswa.setNamaDepan(cursor.getString(1));
        siswa.setNamaBelakang(cursor.getString(2));
        siswa.setPhoneNumber(cursor.getString(3));
        siswa.setEmail(cursor.getString(4));
        siswa.setTglLahir(cursor.getString(5));
        siswa.setGender(cursor.getString(6));
        siswa.setEducation(cursor.getString(7));
        siswa.setHoby(cursor.getString(8));
        siswa.setAlamat(cursor.getString(9));

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

    public Siswa findById(long id) {
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT *FROM siswa WHERE id=?",
                new String[] { String.valueOf(id)});

        boolean found = cursor.getCount() > 0;

        if (found) {
            cursor.moveToNext();
            Siswa siswa = convertToSiswa(cursor);

            cursor.close();
            database.close();

            return siswa;
        } else {
            throw new RuntimeException("Data Siswa dengan id " + id + "tidak ditemukan");
        }
    }

    public void update(Siswa siswa) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        ContentValues contentValues = convertToContentValues(siswa);
        database.update("siswa", contentValues, "id=?", new String[] {
                String.valueOf(siswa.getId())
        });
        database.close();
    }

    public void remove (Siswa siswa) {
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        database.delete("siswa","id=?", new
                String[]{Long.toString(siswa.getId())});
        database.close();
    }

}
