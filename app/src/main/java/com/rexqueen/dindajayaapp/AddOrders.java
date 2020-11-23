package com.rexqueen.dindajayaapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rexqueen.dindajayaapp.model.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static androidx.appcompat.app.AlertDialog.*;

public class AddOrders extends AppCompatActivity {

    // inisiasi class DBHelper
    DBHelper dbHelper;

    //inisiasi variabel dari View
    EditText nama, keterangan, noHp, harga;
    RadioButton seragam, atasan, bawahan, anak, dewasa, xtra;
    Button tanggal_bt, pesanan_bt, kembali;
    TextView tgl, jumlah, total;
    String tanggal;
    int jns, ukuran;

    // inisiasi nilai standar jumlah
    int jum = 1;

    // inisiasi variabel pendukung
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_orders);

        // membuat format tanggal
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        // inisiasi nilai awal variabel
        jns = 0;
        tanggal = "x";

        // mendapatkan nilai dari view
        // edittext
        nama = findViewById(R.id.nama);
        keterangan = findViewById(R.id.keterangan);
        noHp = findViewById(R.id.hp);
        harga = findViewById(R.id.harga);
        // button
        tanggal_bt = findViewById(R.id.tanggal_bt);
        pesanan_bt = findViewById(R.id.addPesanan);
        kembali = findViewById(R.id.kembali);
        // textview
        tgl = findViewById(R.id.tanggal);
        jumlah = findViewById(R.id.jumlah);
        total = findViewById(R.id.total);
        // radio button
        seragam = findViewById(R.id.seragam);
        atasan = findViewById(R.id.atasan);
        bawahan = findViewById(R.id.bawahan);
        anak = findViewById(R.id.anak);
        dewasa = findViewById(R.id.dewasa);
        xtra = findViewById(R.id.extra);

        // harga onchange listener
        harga.addTextChangedListener(new TextWatcher() {

            // ketika konten edittext harga berubah
            public void afterTextChanged(Editable s) {
                // dapatkan nilai harga
                int hrg = Integer.parseInt(harga.getText().toString());
                // kalikan jumlah dengan harga yang diinputkan
                // lalu tampilkan ke textview total
                total.setText(String.valueOf(jum*hrg));
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // menambahkan listener pada tombol tanggal
        tanggal_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mendapatkan kalender
                Calendar newCalendar = Calendar.getInstance();

                // inisiasi datePickerDialog untuk menampilkan pemilihan tanggal
                datePickerDialog = new DatePickerDialog(AddOrders.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        // method ini dijalankan ketika tanggal sudah dipilih
                        // mengambil nilai tanggal yang sudah dipilih
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        // menampilkan nilai tanggal pada textview
                        tgl.setText(dateFormatter.format(newDate.getTime()));
                        // menyimpan nilai tanggal pada variabel tanggal
                        tanggal = dateFormatter.format(newDate.getTime());
                    }

                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                // menampilkan dialog pemilihan tanggal
                datePickerDialog.show();
            }
        });

        // menambahkan listener pada tombol tambah pesanan
        pesanan_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // melakukan pengecekan
                // apakah nama kosong?
                if (nama.getText().length() <= 0){
                    nama.setError("Nama tidak boleh kosong");
                } else
                    // apakah jenis sudah dipilih?
                    if (jns == 0) {
                        Toast.makeText(AddOrders.this, "Silahkan pilih jenis pesanan", Toast.LENGTH_SHORT).show();
                    } else
                        // apakah tanggal sudah dipilih?
                        if (tanggal.length() <= 1){
                            Toast.makeText(AddOrders.this, "Silahkan pilih tanggal pemesanan", Toast.LENGTH_SHORT).show();
                        } else
                            // apakah nomor hp sesuai format?
                            if (noHp.getText().length() < 10 || noHp.getText().length() > 13) {
                                Toast.makeText(AddOrders.this, "Nomor HP salah", Toast.LENGTH_SHORT).show();
                                noHp.setError("Nomor HP salah");
                            } else
                                // apakah keterangan diisi?
                                if (keterangan.getText().length() <= 0){
                                    keterangan.setError("Silahkan isi keterangan");
                                } else
                                    // apakah harga kosong?
                                    if (harga.getText().length() <= 0) {
                                        Toast.makeText(AddOrders.this, "Harga Kosong", Toast.LENGTH_SHORT).show();
                                        harga.setError("Silahkan mengisi Harga");
                                    } else {
                                    // jika sudah lengkap
                                    // membuat dialog konfirmasi
                                    Builder builder = new Builder(AddOrders.this);
                                    builder.setTitle("Tambah Pesanan");
                                    builder.setMessage("Apakah Anda yakin untuk menambah data?");
                                    builder.setCancelable(false);
                                    // membuat tombol "Ya" dengan listenernya
                                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            // mendapatkan database dari class DBHelper
                                            dbHelper = new DBHelper(AddOrders.this);
                                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                                            // lakukan percobaan
                                            try {
                                                // mengeksekusi query menambahkan data ke database pada tabel "orders"
                                                db.execSQL("insert into orders(nama, jenis, ukuran, jumlah, noHp, tglPesan, tglSelesai, tglAmbil, keterangan, harga, total, urlFoto, status) " +
                                                        "values(" +
                                                        "'"+nama.getText()+"', " +
                                                        ""+jns+", " +
                                                        ""+ukuran+", " +
                                                        ""+jum+", " +
                                                        "'"+noHp.getText()+"', " +
                                                        "'"+tanggal+"', " +
                                                        "'-', " +
                                                        "'-', " +
                                                        "'"+keterangan.getText()+"', " +
                                                        "'"+harga.getText()+"', " +
                                                        "'"+total.getText()+"', '-', '1')");

                                                // jika berhasil tampilkan toast
                                                Toast.makeText(AddOrders.this, "Berhasil menambahkan pesanan", Toast.LENGTH_SHORT).show();
                                                // dan berpindah ke tampilan Home dengan imbuhan variabel extra dengan nilai 2 untuk fragment "daftar"
                                                startActivity(new Intent(AddOrders.this, Home.class).putExtra("page", 1));
                                                // tutup activity AddOrders
                                                finish();
                                                db.close();
                                            } catch (Exception e) {
                                                // jika gagal tampilkan toast dengan pesan error dari consol
                                                System.out.println(e);
                                                Toast.makeText(AddOrders.this, "Gagal menambah pesanan, error: "+e, Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });

                                    // membuat tombol "Tidak" dengan listener
                                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // jika tombol "Tidak" dipilih, tampilkan toast
                                            Toast.makeText(AddOrders.this, "Silahkan periksa data sebelum menyimpan", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                    // menampilkan dialog konfirmasi
                                    builder.show();
                                }
            }
        });

        // menambahkan listener pada tombol kembali
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // beralih ke halaman Home
                startActivity(new Intent(AddOrders.this, Home.class));
                // dan mengakhiri activity AddOrders
                finish();
            }
        });

    }

    // method untuk mengambil nilai dari radio button
    public void onRadioButtonClicked(View view) {
        // apakah radio button ada yang dipilih?
        boolean checked = ((RadioButton) view).isChecked();

        // jika iya cari radio button yang dipilih untuk diambil nilainya
        switch(view.getId()) {
            // seragam dipilih
            case R.id.seragam:
                if (checked)
                    // ubah nilai variabel jns menjadi 1
                    this.jns = 1;
                // tampilkan toast untuk radio button yang dipilih
                Toast.makeText(AddOrders.this, "Seragam", Toast.LENGTH_SHORT).show();
                break;
            case R.id.atasan:
                if (checked)
                    // ubah nilai variabel jns menjadi 2
                    this.jns = 2;
                // tampilkan toast untuk radio button yang dipilih
                Toast.makeText(AddOrders.this, "Atasan", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bawahan:
                if (checked)
                    // ubah nilai variabel jns menjadi 3
                    this.jns = 3;
                // tampilkan toast untuk radio button yang dipilih
                Toast.makeText(AddOrders.this, "Bawahan", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public void increment(View view) {
        int hrg = Integer.parseInt(harga.getText().toString());
        jumlah.setText(String.valueOf(jum+1));
        jum += 1;
        total.setText(String.valueOf(jum*hrg));
    }

    public void decrement(View view) {
        int hrg = Integer.parseInt(harga.getText().toString());
        if (jum > 1) {
            jumlah.setText(String.valueOf(jum-1));
            jum -= 1;
            total.setText(String.valueOf(jum*hrg));
        } else {
            Toast.makeText(AddOrders.this, "Minimal pemesanan adalah 1", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRadioButtonClickedd(View view) {
        // apakah radio button ada yang dipilih?
        boolean checked = ((RadioButton) view).isChecked();

        // jika iya cari radio button yang dipilih untuk diambil nilainya
        switch(view.getId()) {
            // seragam dipilih
            case R.id.anak:
                if (checked)
                    // ubah nilai variabel jns menjadi 1
                    this.ukuran = 1;
                // tampilkan toast untuk radio button yang dipilih
                Toast.makeText(AddOrders.this, "anak dipilih", Toast.LENGTH_SHORT).show();
                break;
            case R.id.dewasa:
                if (checked)
                    // ubah nilai variabel jns menjadi 2
                    this.ukuran = 2;
                // tampilkan toast untuk radio button yang dipilih
                Toast.makeText(AddOrders.this, "dewasa dipilih", Toast.LENGTH_SHORT).show();
                break;
            case R.id.extra:
                if (checked)
                    // ubah nilai variabel jns menjadi 3
                    this.ukuran = 3;
                // tampilkan toast untuk radio button yang dipilih
                Toast.makeText(AddOrders.this, "extra dipilih", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
