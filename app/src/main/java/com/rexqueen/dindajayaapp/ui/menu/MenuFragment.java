package com.rexqueen.dindajayaapp.ui.menu;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.rexqueen.dindajayaapp.About;
import com.rexqueen.dindajayaapp.Home;
import com.rexqueen.dindajayaapp.Login;
import com.rexqueen.dindajayaapp.R;
import com.rexqueen.dindajayaapp.model.DBHelper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuFragment extends Fragment {

    // inisiasi variabel
    Button tentang, logout;
//    Button backup;
    Intent intent;
    Login login;
    Home home;
    About about;
    DBHelper dbHelper;
    String currentFilePath, query;
//    String [][] datas;

    // inisiasi Class MenuViewModel
    private MenuViewModel menuViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        // set root untuk mengakses view
        final View root = inflater.inflate(R.layout.fragment_menu, container, false);

        // inisiasi class pendukung
        login = new Login();
        home = new Home();
        about = new About();
        dbHelper = new DBHelper(this.getContext());

        // mendapatkan nilai dari tombol
        tentang = root.findViewById(R.id.about);
        logout = root.findViewById(R.id.logout);
//        backup = root.findViewById(R.id.backup);

        // menambahkan listener pada tombol tentang
        tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // membuat intent untuk beralih ke halaman About
                intent = new Intent(root.getContext(), about.getClass());
                startActivity(intent);
                // menutup activity Home
                home.finish();
            }
        });

        // menambahkan listener pada tombol logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // membuat intent untuk beralih ke halaman Login
                intent = new Intent(root.getContext(), login.getClass());
                startActivity(intent);
                // menampilkan toast
                Toast.makeText(root.getContext(), "Berhasil Keluar", Toast.LENGTH_SHORT).show();
                // menutup activity Home
                home.finish();
            }
        });

//        backup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//                query = "SELECT * FROM `orders`";
//
////                "idPesan integer primary key AUTOINCREMENT, " + //data[0]
////                        "nama text null, " + //data[1]
////                        "jenis text null, " + //data[2]
////                        "jumlah text null, " + //data[3]
////                        "noHp text null, " + //data[4]
////                        "tglPesan text null, " + //data[5]
////                        "tglSelesai text null, " + //data[6]
////                        "tglAmbil text null, " + //data[7]
////                        "keterangan text null, " + //data[8]
////                        "harga text null, " + //data[9]
////                        "total text null, " + //data[10]
////                        "urlFoto text null, " + //data[11]
////                        "status text null, " + //data[12]
////                        "skor double null);"; //data[13]
//
//                JSONObject json = new JSONObject();
//                JSONArray idA = new JSONArray();
//                JSONArray namaA = new JSONArray();
//                JSONArray jenisA = new JSONArray();
//                JSONArray jumlahA = new JSONArray();
//                JSONArray noHpA = new JSONArray();
//                JSONArray tglPesanA = new JSONArray();
//                JSONArray tglSelesaiA = new JSONArray();
//                JSONArray tglAmbilA = new JSONArray();
//                JSONArray keteranganA = new JSONArray();
//                JSONArray hargaA = new JSONArray();
//                JSONArray totalA = new JSONArray();
//                JSONArray urlFotoA = new JSONArray();
//                JSONArray statusA = new JSONArray();
//                JSONArray skorA = new JSONArray();
//
//                try {
//                    Cursor cursor = db.rawQuery(query, null);
//                    int a = cursor.getCount();
//                    if (a == 0) {
//                        Toast.makeText(root.getContext(), "Tidak Ada Data", Toast.LENGTH_SHORT).show();
//                    } else {
//                        if (cursor.moveToFirst()) {
//                            for (int i = 0; i < a; i++) {
//                                idA.add(cursor.getString(0));
//                                namaA.add(cursor.getString(1));
//                                jenisA.add(cursor.getString(2));
//                                jumlahA.add(cursor.getString(3));
//                                noHpA.add(cursor.getString(4));
//                                tglPesanA.add(cursor.getString(5));
//                                tglSelesaiA.add(cursor.getString(6));
//                                tglAmbilA.add(cursor.getString(7));
//                                keteranganA.add(cursor.getString(8));
//                                hargaA.add(cursor.getString(9));
//                                totalA.add(cursor.getString(10));
//                                urlFotoA.add(cursor.getString(11));
//                                statusA.add(cursor.getString(12));
//                                skorA.add(cursor.getString(13));
//                            }
//                            json.put("id", idA);
//                            json.put("nama", namaA);
//                            json.put("jenis", jenisA);
//                            json.put("jumlah", jumlahA);
//                            json.put("noHp", noHpA);
//                            json.put("tglPesan", tglPesanA);
//                            json.put("tglSelesai", tglSelesaiA);
//                            json.put("tglAmbil", tglAmbilA);
//                            json.put("keterangan", keteranganA);
//                            json.put("harga", hargaA);
//                            json.put("total", totalA);
//                            json.put("urlFoto", urlFotoA);
//                            json.put("status", statusA);
//                            json.put("skor", skorA);
//
//                            FileWriter writer;
//                            writer = new FileWriter("Android/data/com.rexqueen.dindajayaapp/backup/dinda-jaya.txt");
//
//                            try {
//                                writer.write(json.toJSONString());
//                                Toast.makeText(root.getContext(), "Berhasil Backup Data", Toast.LENGTH_SHORT).show();
//                            } catch (IOException e) {
//                                Toast.makeText(root.getContext(), "Gagal Backup Data", Toast.LENGTH_SHORT).show();
//                            } finally {
//                                writer.flush();
//                                writer.close();
//                            }
//                        }
//                    }
//                    } catch(Exception e){
//                        Toast.makeText(root.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//        });



        return root;
    }
}