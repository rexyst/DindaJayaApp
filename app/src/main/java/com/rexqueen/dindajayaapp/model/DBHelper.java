package com.rexqueen.dindajayaapp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    // menyiapkan nama database yang akan dipakai
    private static final String DATABASE_NAME = "data_master.db";
    // menyiapkan versi database
    private static final int DATABASE_VERSION = 7;

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
                "idPesan integer primary key AUTOINCREMENT, " +
                "nama text null, " +
                "jenis text null, " +
                "jumlah text null, " +
                "noHp text null, " +
                "tglPesan text null, " +
                "tglSelesai text null, " +
                "tglAmbil text null, " +
                "keterangan text null, " +
                "harga text null, " +
                "total text null, " +
                "status text null);";
        Log.d("Data", "onCreate: " + sql);
        // eksekusi query
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }

}
