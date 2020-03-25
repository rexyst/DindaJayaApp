package com.rexqueen.dindajayaapp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class DBHelper extends SQLiteOpenHelper {
    // menyiapkan nama database yang akan dipakai
    private static final String DATABASE_NAME = "dinda_jaya.db";
    // menyiapkan versi database
    private static final int DATABASE_VERSION = 9;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    // membuat database lokal ketika aplikasi pertama kali dipasang
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        // membuat table dengan nama "orders" untuk menyimpan data pesanan
        String sql = "create table orders (" +
                "idPesan integer primary key AUTOINCREMENT, " + //data[0]
                "nama text null, " + //data[1]
                "jenis text null, " + //data[2]
                "jumlah text null, " + //data[3]
                "noHp text null, " + //data[4]
                "tglPesan text null, " + //data[5]
                "tglSelesai text null, " + //data[6]
                "tglAmbil text null, " + //data[7]
                "keterangan text null, " + //data[8]
                "harga text null, " + //data[9]
                "total text null, " + //data[10]
                "urlFoto text null, " + //data[11]
                "status text null, " + //data[12]
                "skor double null);"; //data[13]
        Log.d("Data", "onCreate: " + sql);
        // eksekusi query
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }

}
