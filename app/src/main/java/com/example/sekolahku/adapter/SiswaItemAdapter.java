package com.example.sekolahku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sekolahku.R;
import com.example.sekolahku.model.Siswa;
import java.util.List;

public class SiswaItemAdapter extends ArrayAdapter<Siswa> {

    public SiswaItemAdapter(Context context, List<Siswa> objects) {
        super(context, R.layout.item_siswa, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        if (listItemView ==  null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            listItemView = inflater.inflate(R.layout.item_siswa, parent, false);
        }

        TextView etNamaDepan = listItemView.findViewById(R.id.etNamaDepan);
        TextView genderRb = listItemView.findViewById(R.id.genderRb);
        TextView listItem = listItemView.findViewById(R.id.listItem);
        TextView etNoHandphone = listItemView.findViewById(R.id.etNoHandphone);
        TextView etTglLahir = listItemView.findViewById(R.id.etTglLahir);

        Siswa siswa = getItem(position);

        etNamaDepan.setText(siswa.getNamaDepan() + " " + siswa.getNamaBelakang());
        genderRb.setText(siswa.getGender());
        etNoHandphone.setText(siswa.getPhoneNumber());
        listItem.setText(siswa.getEducation());
        etTglLahir.setText("Tanggal Lahir : " + siswa.getTglLahir());

        return listItemView;
    }
}
