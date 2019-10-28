package com.rexqueen.dindajayaapp.ui.daftar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rexqueen.dindajayaapp.AddOrders;
import com.rexqueen.dindajayaapp.Home;
import com.rexqueen.dindajayaapp.R;
import com.rexqueen.dindajayaapp.model.DBHelper;

public class DaftarFragment extends Fragment {

    // inisiasi variabel
    DBHelper dbHelper;
    TableRow tr;
    TextView[] col;
    Intent intent;
    Home home;
    TableLayout tableLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // membuat root untuk mengakses view
        final View root = inflater.inflate(R.layout.fragment_daftar, container, false);

        // inisiasi class DBHelper
        dbHelper = new DBHelper(root.getContext());
        // inisiasi variabel col untuk menyimpan textview sebanyak 6
        col = new TextView[6];
        // inisiasi tableLayout pada view
        tableLayout = root.findViewById(R.id.tb_data);

        // insiasi class Home
        home = new Home();

        // insiasi tombol melayang (+)
        FloatingActionButton fab = root.findViewById(R.id.fab);
        // menambahkan listener pada tombol melayang
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // membuat intent untuk beralih ke halaman AddOrders
                startActivity(new Intent(getActivity(), AddOrders.class));
                // menutup activity Home
                home.finish();
            }
        });

        // mebuat header pada TableLayout
        // mendapatkan nilai TableRow dari TableLayout
        TableRow rowHeader = new TableRow(root.getContext());
        // mengubah warna pada header
        rowHeader.setBackgroundColor(Color.parseColor("#f6c89f"));
        // mengubah parameter layout pada view untuk Header
        rowHeader.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));
        // menyiapkan judul pada Header
        String[] headerText={"ID","Nama","Jenis","Status"};
        // memulai iterasi untuk membuat Header per-kolom
        for(String c:headerText) {
            TextView tv = new TextView(root.getContext());
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
            // menyiapkan query
            String selectQuery = "SELECT * FROM orders";
            // menyiapkan cursor untuk membaca nilai
            Cursor cursor = db.rawQuery(selectQuery,null);
            // jika jumlah baris lebih dari 0
            if(cursor.getCount() >0)
            {
                // memulai iterasi untuk mengambil data perbaris
                while (cursor.moveToNext()) {
                    // mengambil nilai perkolom
                    // mengambil nilai dari kolom "no" untuk ID
                    int outlet_id= cursor.getInt(cursor.getColumnIndex("no"));
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
                    }

                    // menyiapkan TableRow untuk ditampilkan ke TableView
                    TableRow row = new TableRow(root.getContext());
                    // menyiapkan parameter view
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    // mengubah warna untuk baris berbeda
                    // untuk baris ganjil
                    if (outlet_id %2 == 1) {
                        row.setBackgroundColor(Color.parseColor("#9effd3"));
                    } else {
                        // untuk baris genap
                        row.setBackgroundColor(Color.parseColor("#69d1a2"));
                    }
                    // menyiapkan data pada tiap kolom
                    String[] colText={outlet_id+"",outlet_name,outlet_jenis,outlet_status};
                    // memulai iterasi untuk menambahkan kolom
                    for(String text:colText) {
                        TextView tv = new TextView(root.getContext());
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
                                Toast.makeText(root.getContext(), outlet_name, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    // menambahkan baris yang telah disiapkan
                    tableLayout.addView(row);

                }

            }
            // mengubah status traksaksi dengan database
            db.setTransactionSuccessful();

        }
        // jika gagal transaksi dengan database
        catch (SQLiteException e)
        {
            // tampilkan toast dengan pesan error
            Toast.makeText(root.getContext(), "Gagal terhubung ke database. Error: "+e, Toast.LENGTH_LONG).show();
        }
        finally
        {
            // Mengakhiri transaksi dengan database
            db.endTransaction();
            // dan menutup koneksi dengan database untuk menghemat resource yang digunakan
            db.close();
        }
        return root;
    }
}