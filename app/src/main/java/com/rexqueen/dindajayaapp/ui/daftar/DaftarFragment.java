package com.rexqueen.dindajayaapp.ui.daftar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rexqueen.dindajayaapp.AddOrders;
import com.rexqueen.dindajayaapp.DetailPesanan;
import com.rexqueen.dindajayaapp.Home;
import com.rexqueen.dindajayaapp.R;
import com.rexqueen.dindajayaapp.model.DBHelper;
import com.rexqueen.dindajayaapp.ui.home.HomeViewModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.widget.GridLayout.spec;

public class DaftarFragment extends Fragment {

    // inisiasi variabel
    DBHelper dbHelper;
    TableRow tr;
    TextView[] col;
    Intent intent;
    Home home;
    TableLayout tableLayout;
    String tgl, nowDate;
    TextView tanggal;
    int count;

    private HomeViewModel homeViewModel;

    // inisiasi variabel pendukung
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);

        // membuat variabel root untuk mengakses view
        final View root = inflater.inflate(R.layout.fragment_daftar, container, false);

        // inisiasi class DBHelper
        dbHelper = new DBHelper(root.getContext());
        // inisiasi variabel col untuk menyimpan textview sebanyak 6
        col = new TextView[6];
        // inisiasi tableLayout pada view
        tableLayout = root.findViewById(R.id.tb_daftar);

        // insiasi class Home
        home = new Home();

        // membuat format tanggal
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

//        inisiasi variabel tanggal
        tanggal = root.findViewById(R.id.dates);

//        mengisi nilai tanggal
        nowDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        tanggal.setText("Tanpa filter");

        FloatingActionButton fab = root.findViewById(R.id.fab);
        // menambahkan listener pada tombol tanggal
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableLayout.removeAllViewsInLayout();
                tanggal.setText("Tanpa filter");
                getList(1);
            }
        });

        FloatingActionButton fac = root.findViewById(R.id.fac);
        // menambahkan listener pada tombol tanggal
        fac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mendapatkan kalender
                Calendar newCalendar = Calendar.getInstance();

                // inisiasi datePickerDialog untuk menampilkan pemilihan tanggal
                datePickerDialog = new DatePickerDialog(root.getContext(), new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        // method ini dijalankan ketika tanggal sudah dipilih
                        // mengambil nilai tanggal yang sudah dipilih
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        // menampilkan nilai tanggal pada textview
                        tanggal.setText(dateFormatter.format(newDate.getTime()));
                        // menyimpan nilai tanggal pada variabel tanggal
                        tgl = dateFormatter.format(newDate.getTime());

                        tableLayout.removeAllViewsInLayout();
                        getList(0);

                    }

                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                // menampilkan dialog pemilihan tanggal
                datePickerDialog.show();
            }
        });

        getList(1);

        return root;
    }

    void getList(int z){
        count = 0;
        // menyiapkan query
        String selectQuery;
        // konversi tanggal
        String a = String.valueOf(tanggal.getText());
        if (z==0){
            selectQuery = "SELECT * FROM orders where `status`=4 and `tglPesan`='"+a+"'";
        } else {
            selectQuery = "SELECT * FROM orders where `status`=4";
        }
        // mebuat header pada TableLayout
        // mendapatkan nilai TableRow dari TableLayout
        TableRow rowHeader = new TableRow(getContext());
        // mengubah warna pada header
        rowHeader.setBackgroundColor(Color.parseColor("#f6c89f"));
        // mengubah parameter layout pada view untuk Header
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        // menyiapkan judul pada Header
        String[] headerText={"ID","Nama","Jenis","Status"};
        // memulai iterasi untuk membuat Header per-kolom
        for(String c:headerText) {
            TextView tv = new TextView(getContext());
            tv.setTextColor(Color.parseColor("#000000"));
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);

        // Mengambil database untuk dibaca
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Memulai transaksi dengan database
        db.beginTransaction();

        // mencoba menjalankan query
        try
        {

            // menyiapkan cursor untuk membaca nilai
            Cursor cursor = db.rawQuery(selectQuery,null);
            // jika jumlah baris lebih dari 0
            if(cursor.getCount() >0)
            {
                // memulai iterasi untuk mengambil data perbaris
                while (cursor.moveToNext()) {
                    // mengambil nilai perkolom
                    // mengambil nilai dari kolom "no" untuk ID
                    final int outlet_id= cursor.getInt(cursor.getColumnIndex("idPesan"));
                    // mengambil nilai dari kolom "nama"
                    final String outlet_name= cursor.getString(cursor.getColumnIndex("nama"));
                    // mengambil nilai dari kolom "jenis"
                    String outlet_jenis= cursor.getString(cursor.getColumnIndex("jenis"));
                    // mengambil nilai dari kolom "status"
                    String outlet_status= cursor.getString(cursor.getColumnIndex("status"));

                    // konversi nilai dari jenis
                    // jika nilai 1
                    if (outlet_jenis.equals("1")) {
                        // dikonversi menjadi "Seragam"
                        outlet_jenis = "Seragam";
                    } else
                        // jika nilai 2
                        if (outlet_jenis.equals("2")) {
                            // dikonversi menjadi "Atasan"
                            outlet_jenis = "Atasan";
                        } else
                            // jika nilai 3
                            if (outlet_jenis.equals("3")) {
                                // dikonversi menjadi "Bawahan"
                                outlet_jenis = "Bawahan";
                            }

                    // konversi nilai dari status
                    // jika nilai 1
                    if (outlet_status.equals("1")){
                        // dikonversi menjadi "Menunggu"
                        outlet_status = "Menunggu";
                    } else
                        // jika nilai 2
                        if (outlet_status.equals("2")){
                            // dikonversi menjadi "Diproses"
                            outlet_status = "Diproses";
                        } else
                            // jika nilai 3
                            if (outlet_status.equals("3")){
                                // dikonversi menjadi "Selesai"
                                outlet_status = "Selesai";
                            } else
                                // jika nilai 4
                                if (outlet_status.equals("4")) {
                                    // dikonversi menjadi "Diambil"
                                    outlet_status = "Diambil";
                                }

                    // menyiapkan TableRow untuk ditampilkan ke TableView
                    TableRow row = new TableRow(getContext());
                    // menyiapkan parameter view
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    // mengubah warna untuk baris berbeda
                    // untuk baris ganjil
                    if (count %2 == 1) {
                        row.setBackgroundColor(Color.parseColor("#9effd3"));
                    } else {
                        // untuk baris genap
                        row.setBackgroundColor(Color.parseColor("#69d1a2"));
                    }
                    // menyiapkan data pada tiap kolom
                    String[] colText={outlet_id+"",outlet_name,outlet_jenis,outlet_status};
                    // memulai iterasi untuk menambahkan kolom
                    for(String text:colText) {
                        TextView tv = new TextView(getContext());
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(16);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(text);
                        row.addView(tv);
                        // menambahkan listener pada baris
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getContext(), String.valueOf(outlet_id), Toast.LENGTH_SHORT).show();
                                // beralih halaman detail pesanan
                                startActivity(new Intent(getContext(), DetailPesanan.class).putExtra("idOrder", String.valueOf(outlet_id)));
                            }
                        });
                    }
                    // menambahkan baris yang telah disiapkan
                    tableLayout.addView(row);
                    count++;
                }

            }
            // mengubah status traksaksi dengan database
            db.setTransactionSuccessful();

        }
        // jika gagal transaksi dengan database
        catch (SQLiteException e)
        {
            // tampilkan toast dengan pesan error
            Toast.makeText(getContext(), "Gagal terhubung ke database. Error: "+e, Toast.LENGTH_LONG).show();
        }
        finally
        {
            // Mengakhiri transaksi dengan database
            db.endTransaction();
            // dan menutup koneksi dengan database untuk menghemat resource yang digunakan
            db.close();
        }
    }
}