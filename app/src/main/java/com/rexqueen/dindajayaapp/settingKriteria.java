package com.rexqueen.dindajayaapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.rexqueen.dindajayaapp.model.DBHelper;

public class settingKriteria extends AppCompatActivity {

    int[] criterias;
    DBHelper dbHelper;
    RadioButton c01,c02,c03,c04,c05,c11,c12,c13,c14,c15,c21,c22,c23,c24,c25,c31,c32,c33,c34,c35,c41,c42,c43,c44,c45,c51,c52,c53,c54,c55,c61,c62,c63,c64,c65;
    Button simpan, batal;
    int[] crit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_kriteria);

        crit = new int[7];

        simpan = findViewById(R.id.saveCrit);
        batal = findViewById(R.id.batalCrit);

        c01 = findViewById(R.id.c01);
        c02 = findViewById(R.id.c02);
        c03 = findViewById(R.id.c03);
        c04 = findViewById(R.id.c04);
        c05 = findViewById(R.id.c05);

        c11 = findViewById(R.id.c11);
        c12 = findViewById(R.id.c12);
        c13 = findViewById(R.id.c13);
        c14 = findViewById(R.id.c14);
        c15 = findViewById(R.id.c15);

        c21 = findViewById(R.id.c21);
        c22 = findViewById(R.id.c22);
        c23 = findViewById(R.id.c23);
        c24 = findViewById(R.id.c24);
        c25 = findViewById(R.id.c25);

        c31 = findViewById(R.id.c31);
        c32 = findViewById(R.id.c32);
        c33 = findViewById(R.id.c33);
        c34 = findViewById(R.id.c34);
        c35 = findViewById(R.id.c35);

        c41 = findViewById(R.id.c41);
        c42 = findViewById(R.id.c42);
        c43 = findViewById(R.id.c43);
        c44 = findViewById(R.id.c44);
        c45 = findViewById(R.id.c45);

        c51 = findViewById(R.id.c51);
        c52 = findViewById(R.id.c52);
        c53 = findViewById(R.id.c53);
        c54 = findViewById(R.id.c54);
        c55 = findViewById(R.id.c55);

        c61 = findViewById(R.id.c61);
        c62 = findViewById(R.id.c62);
        c63 = findViewById(R.id.c63);
        c64 = findViewById(R.id.c64);
        c65 = findViewById(R.id.c65);

//        mengambil nilai kriteria untuk ditampilkan
        try {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            db.beginTransaction();

            String query = "select * from criterias;";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.getCount()>0){
                int count = 0;
                while (cursor.moveToNext()) {
                    criterias [count] = Integer.parseInt(cursor.getString(cursor.getColumnIndex("criteriaValue")));
                }
            }
            db.endTransaction();

//            C0
            int x = criterias[0];
            switch (x) {
                case 1:
                    c01.setSelected(true);
                    break;
                case 3:
                    c02.setSelected(true);
                    break;
                case 5:
                    c03.setSelected(true);
                    break;
                case 7:
                    c04.setSelected(true);
                    break;
                case 9:
                    c05.setSelected(true);
                    break;
                default:
                    break;
            }

            //            C1
            int y = criterias[1];
            switch (y) {
                case 1:
                    c11.setSelected(true);
                    break;
                case 3:
                    c12.setSelected(true);
                    break;
                case 5:
                    c13.setSelected(true);
                    break;
                case 7:
                    c14.setSelected(true);
                    break;
                case 9:
                    c15.setSelected(true);
                    break;
                default:
                    break;
            }

            //            C2
            int z = criterias[2];
            switch (z) {
                case 1:
                    c21.setSelected(true);
                    break;
                case 3:
                    c22.setSelected(true);
                    break;
                case 5:
                    c23.setSelected(true);
                    break;
                case 7:
                    c24.setSelected(true);
                    break;
                case 9:
                    c25.setSelected(true);
                    break;
                default:
                    break;
            }

            //            C3
            int xx = criterias[3];
            switch (xx) {
                case 1:
                    c31.setSelected(true);
                    break;
                case 3:
                    c32.setSelected(true);
                    break;
                case 5:
                    c33.setSelected(true);
                    break;
                case 7:
                    c34.setSelected(true);
                    break;
                case 9:
                    c35.setSelected(true);
                    break;
                default:
                    break;
            }

            //            C4
            int xy = criterias[4];
            switch (xy) {
                case 1:
                    c41.setSelected(true);
                    break;
                case 3:
                    c42.setSelected(true);
                    break;
                case 5:
                    c43.setSelected(true);
                    break;
                case 7:
                    c44.setSelected(true);
                    break;
                case 9:
                    c45.setSelected(true);
                    break;
                default:
                    break;
            }

            //            C5
            int xz = criterias[5];
            switch (xz) {
                case 1:
                    c51.setSelected(true);
                    break;
                case 3:
                    c52.setSelected(true);
                    break;
                case 5:
                    c53.setSelected(true);
                    break;
                case 7:
                    c54.setSelected(true);
                    break;
                case 9:
                    c55.setSelected(true);
                    break;
                default:
                    break;
            }

            //            C6
            int xxx = criterias[6];
            switch (xxx) {
                case 1:
                    c61.setSelected(true);
                    break;
                case 3:
                    c62.setSelected(true);
                    break;
                case 5:
                    c63.setSelected(true);
                    break;
                case 7:
                    c64.setSelected(true);
                    break;
                case 9:
                    c65.setSelected(true);
                    break;
                default:
                    break;
            }

        }catch (Exception e) {}

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // membuat dialog konfirmasi
                AlertDialog.Builder builder = new AlertDialog.Builder(settingKriteria.this);
                builder.setTitle("Simpan");
                builder.setMessage("Apakah Anda yakin untuk menyimpan data?");
                builder.setCancelable(false);
                // membuat tombol "Ya" dengan listenernya
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // mendapatkan database dari class DBHelper
                        dbHelper = new DBHelper(settingKriteria.this);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        // lakukan percobaan

                        try {
                            // mengeksekusi query menambahkan data ke database pada tabel "orders"
                            db.execSQL("update criterias set `criteriaValue`="+crit[0]+" where `criteriaName`=\"waktuPesan\";");
                            db.execSQL("update criterias set `criteriaValue`="+crit[1]+" where `criteriaName`=\"jenisKelamin\";");
                            db.execSQL("update criterias set `criteriaValue`="+crit[2]+" where `criteriaName`=\"jenisPakaian\";");
                            db.execSQL("update criterias set `criteriaValue`="+crit[3]+" where `criteriaName`=\"model\";");
                            db.execSQL("update criterias set `criteriaValue`="+crit[4]+" where `criteriaName`=\"ukuran\";");
                            db.execSQL("update criterias set `criteriaValue`="+crit[5]+" where `criteriaName`=\"jumlah\";");
                            db.execSQL("update criterias set `criteriaValue`="+crit[6]+" where `criteriaName`=\"status\";");

                            // jika berhasil tampilkan toast
                            Toast.makeText(settingKriteria.this, "Berhasil menyimpan", Toast.LENGTH_SHORT).show();
                            // dan berpindah ke tampilan Home dengan imbuhan variabel extra dengan nilai 1 untuk fragment "home"
                            startActivity(new Intent(settingKriteria.this, Home.class).putExtra("page", 1));
                            // tutup activity AddOrders
                            finish();
                            db.close();
                        } catch (Exception e) {
                            // jika gagal tampilkan toast dengan pesan error dari consol
                            System.out.println(e);
                            Toast.makeText(settingKriteria.this, "Gagal mengyimpan, error: "+e, Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                // membuat tombol "Tidak" dengan listener
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // jika tombol "Tidak" dipilih, tampilkan toast
                        Toast.makeText(settingKriteria.this, "Silahkan periksa data sebelum menyimpan", Toast.LENGTH_SHORT).show();
                    }
                });

                // menampilkan dialog konfirmasi
                builder.show();

            }
        });

    }


    public void c0click(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.c01:
                if (checked)
                    crit[0] = 1;
                    break;
            case R.id.c02:
                if (checked)
                    crit[0] = 3;
                    break;
            case R.id.c03:
                if (checked)
                    crit[0] = 5;
                break;
            case R.id.c04:
                if (checked)
                    crit[0] = 7;
                break;
            case R.id.c05:
                if (checked)
                    crit[0] = 9;
                break;
        }
    }

    public void c1click(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.c11:
                if (checked)
                    crit[1] = 1;
                break;
            case R.id.c12:
                if (checked)
                    crit[1] = 3;
                break;
            case R.id.c13:
                if (checked)
                    crit[1] = 5;
                break;
            case R.id.c14:
                if (checked)
                    crit[1] = 7;
                break;
            case R.id.c15:
                if (checked)
                    crit[1] = 9;
                break;
        }
    }

    public void c2click(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.c21:
                if (checked)
                    crit[2] = 1;
                break;
            case R.id.c22:
                if (checked)
                    crit[2] = 3;
                break;
            case R.id.c23:
                if (checked)
                    crit[2] = 5;
                break;
            case R.id.c24:
                if (checked)
                    crit[2] = 7;
                break;
            case R.id.c25:
                if (checked)
                    crit[2] = 9;
                break;
        }
    }

    public void c3click(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.c31:
                if (checked)
                    crit[3] = 1;
                break;
            case R.id.c32:
                if (checked)
                    crit[3] = 3;
                break;
            case R.id.c33:
                if (checked)
                    crit[3] = 5;
                break;
            case R.id.c34:
                if (checked)
                    crit[3] = 7;
                break;
            case R.id.c35:
                if (checked)
                    crit[3] = 9;
                break;
        }
    }

    public void c4click(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.c41:
                if (checked)
                    crit[4] = 1;
                break;
            case R.id.c42:
                if (checked)
                    crit[4] = 3;
                break;
            case R.id.c43:
                if (checked)
                    crit[4] = 5;
                break;
            case R.id.c44:
                if (checked)
                    crit[4] = 7;
                break;
            case R.id.c45:
                if (checked)
                    crit[4] = 9;
                break;
        }
    }

    public void c5click(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.c51:
                if (checked)
                    crit[5] = 1;
                break;
            case R.id.c52:
                if (checked)
                    crit[5] = 3;
                break;
            case R.id.c53:
                if (checked)
                    crit[5] = 5;
                break;
            case R.id.c54:
                if (checked)
                    crit[5] = 7;
                break;
            case R.id.c55:
                if (checked)
                    crit[5] = 9;
                break;
        }
    }

    public void c6click(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.c61:
                if (checked)
                    crit[6] = 1;
                break;
            case R.id.c62:
                if (checked)
                    crit[6] = 3;
                break;
            case R.id.c63:
                if (checked)
                    crit[6] = 5;
                break;
            case R.id.c64:
                if (checked)
                    crit[6] = 7;
                break;
            case R.id.c65:
                if (checked)
                    crit[6] = 9;
                break;
        }
    }

}