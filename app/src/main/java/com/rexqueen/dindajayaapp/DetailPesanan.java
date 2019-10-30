package com.rexqueen.dindajayaapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.rexqueen.dindajayaapp.model.DBHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailPesanan extends AppCompatActivity {

    // inisiasi variabel
    TextView id, nama, jenis, jumlah, hp, tglPesan, tglSelesai, tglAmbil, keterangan, harga, total, status;
    Button selesai, ambil, diproses, ambilFoto;
    ImageView foto;
    DBHelper dbHelper;
    String[] data;
    int getExtra;
    Intent intent;
    String query, tanggal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pesanan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // inisiasi class DBHelper
        dbHelper = new DBHelper(DetailPesanan.this);

        //inisiasi array untuk menyimpan data dari database
        data = new String[12];

        // mengambil nilai dari textview
        id = findViewById(R.id.idPesan);
        nama = findViewById(R.id.nama);
        jenis = findViewById(R.id.jenis);
        jumlah = findViewById(R.id.jumlah);
        hp = findViewById(R.id.hp);
        tglPesan = findViewById(R.id.tglPesan);
        tglSelesai = findViewById(R.id.tglSelesai);
        tglAmbil = findViewById(R.id.tglDiambil);
        keterangan = findViewById(R.id.keterangan);
        harga = findViewById(R.id.harga);
        total = findViewById(R.id.total);
        status = findViewById(R.id.status);

        // mengambil nilai dari button
        selesai = findViewById(R.id.selesai_bt);
        ambil = findViewById(R.id.diambil_bt);
        diproses = findViewById(R.id.proses_bt);
        ambilFoto = findViewById(R.id.foto_bt);

        // mengambil nilai dari imageview
        foto = findViewById(R.id.foto);

        // menyembunyikan Foto
        foto.setVisibility(View.GONE);

        // menambahkan listener pada floating button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ketika diklik maka akan beralih ke tammpilan Home
                startActivity(new Intent(DetailPesanan.this, Home.class));
            }
        });

        // mendapatkan nilai variabel extra dari intent
        Bundle extras = getIntent().getExtras();
        // mengecek apakah extra tidak kosong
        if (extras != null) {
            // mendapatkan nilai extra dari intent dengan kata kunci "page"
            getExtra = extras.getInt("idOrder");
            query = "SELECT * FROM orders where idPesan = "+getExtra;
        }

        // Mengambil database untuk dibaca
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Memulai transaksi dengan database
        db.beginTransaction();

        // mencoba menjalankan query
        try {
            // menyiapkan query
            String selectQuery = query;
            // menyiapkan cursor untuk membaca nilai
            Cursor cursor = db.rawQuery(selectQuery, null);
            // jika jumlah baris lebih dari 0
            if (cursor.getCount() != 0) {
                // memulai iterasi untuk mengambil data perbaris
                if (cursor.moveToFirst()) {
                    // mengambil nilai perkolom
                    int data_id = cursor.getInt(cursor.getColumnIndex("idPesan"));
                    data[0] = String.valueOf(data_id);
                    for (int i = 1; i < 12; i++) {
                        data[i] = cursor.getString(i);
                        System.out.println("DATA :");
                        System.out.println(data[i]);
                    }

                    // konversi nilai status
                    if (data[11].equals("1")) {
                        data[11] = "Menunggu";
                    } else
                    if (data[11].equals("2")) {
                        data[11] = "Diproses";
                    } else
                    if (data[11].equals("3")) {
                        data[11] = "Selesai";
                    } else
                    if (data[11].equals("4")) {
                        data[11] = "Diambil";
                    }

                    // konversi nilai jenis
                    if (data[2].equals("1")) {
                        data[2] = "Seragam";
                    } else
                    if (data[2].equals("2")) {
                        data[2] = "Atasan";
                    } else
                    if (data[2].equals("3")) {
                        data[2] = "Bawahan";
                    }

                    // menampilkan data dari array ke textview
                    id.setText(data[0]);
                    nama.setText(data[1]);
                    jenis.setText(data[2]);
                    jumlah.setText(data[3]);
                    hp.setText(data[4]);
                    tglPesan.setText(data[5]);
                    tglSelesai.setText(data[6]);
                    tglAmbil.setText(data[7]);
                    keterangan.setText(data[8]);
                    harga.setText(data[9]);
                    total.setText(data[10]);
                    status.setText(data[11]);

                    // cek status untuk menampilkan tombol yang sesuai
                    // status menunggu
                    if (data[11].equals("Menunggu")) {
                        diproses.setVisibility(View.VISIBLE);
                        selesai.setVisibility(View.GONE);
                        ambilFoto.setVisibility(View.GONE);
                        ambil.setVisibility(View.GONE);
                    } else
                        // status diproses
                    if (data[11].equals("Diproses")) {
                        diproses.setVisibility(View.GONE);
                        selesai.setVisibility(View.VISIBLE);
                        ambilFoto.setVisibility(View.GONE);
                        ambil.setVisibility(View.GONE);
                    } else
                        // status selesai
                    if (data[11].equals("Selesai")) {
                        diproses.setVisibility(View.GONE);
                        selesai.setVisibility(View.GONE);
                        ambilFoto.setVisibility(View.VISIBLE);
                        ambil.setVisibility(View.VISIBLE);
                    } else {
                        // status diambil
                        diproses.setVisibility(View.GONE);
                        selesai.setVisibility(View.GONE);
                        ambilFoto.setVisibility(View.GONE);
                        ambil.setVisibility(View.GONE);
                    }

                    // menambahkan listener untuk tombol diproses
                    diproses.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setStatus(Integer.parseInt(data[0]), "2");
                            startActivity(new Intent(DetailPesanan.this, Home.class));
                            finish();
                        }
                    });

                    // menambahkan listener untuk tombol selesai
                    selesai.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date());
                            setDate(Integer.parseInt(data[0]), "tglSelesai", date);
                            setStatus(Integer.parseInt(data[0]), "3");
                            startActivity(new Intent(DetailPesanan.this, Home.class));
                            finish();
                        }
                    });

                    // menambahkan listener untuk tombol ambilFoto
                    ambilFoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    // menambahkan listener untuk tombol ambil
                    ambil.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date());
                            setDate(Integer.parseInt(data[0]), "tglAmbil", date);
                            setStatus(Integer.parseInt(data[0]), "4");
                            startActivity(new Intent(DetailPesanan.this, Home.class));
                            finish();
                        }
                    });

                }

            }
        } catch (Exception e) {
            System.out.println(e);
            Toast.makeText(DetailPesanan.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
        finally
        {
            // Mengakhiri transaksi dengan database
            db.endTransaction();
            // dan menutup koneksi dengan database untuk menghemat resource yang digunakan
            db.close();
        }
    }

    private void setStatus(int idPesan, String status) {
        ContentValues cv = new ContentValues();
        cv.put("status", status);
        // Mengambil database untuk dibaca
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
        db.update("orders", cv, "idPesan="+idPesan, null);
            Toast.makeText(DetailPesanan.this, "Berhasil memperbarui status", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(DetailPesanan.this, "Berhasil memperbarui status", Toast.LENGTH_SHORT).show();
        }

    }

    private void setDate(int idPesan, String colName, String tanggal) {
        ContentValues cv = new ContentValues();
        cv.put(colName, tanggal);
        // Mengambil database untuk dibaca
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.update("orders", cv, "idPesan="+idPesan, null);
            Toast.makeText(DetailPesanan.this, "Berhasil memperbarui tanggal", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(DetailPesanan.this, "Berhasil memperbarui tanggal", Toast.LENGTH_SHORT).show();
        }

    }
}
