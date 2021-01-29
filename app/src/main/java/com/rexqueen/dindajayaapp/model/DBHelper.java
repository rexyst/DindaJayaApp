package com.rexqueen.dindajayaapp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    // menyiapkan nama database yang akan dipakai
    private static final String DATABASE_NAME = "dinda_jaya.db";
    // menyiapkan versi database
    private static final int DATABASE_VERSION = 14;

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
                "jenis integer null, " + //data[2]
                "ukuran integer null, " + //data[3]
                "jumlah integer null, " + //data[4]
                "noHp text null, " + //data[5]
                "tglPesan text null, " + //data[6]
                "tglSelesai text null, " + //data[7]
                "tglAmbil text null, " + //data[8]
                "keterangan text null, " + //data[9]
                "harga text null, " + //data[10]
                "total text null, " + //data[11]
                "urlFoto text null, " + //data[12]
                "status integer null);"; //data[13]
        Log.d("Data", "onCreate: " + sql);
        // eksekusi query
        db.execSQL(sql);

        // membuat table dengan nama "admins" untuk menyimpan data pin
        String sqla = "create table secur (pins text not null default \"123456\");"; //data[13]
        Log.d("Data", "onCreate: " + sqla);
        // eksekusi query
        db.execSQL(sqla);

        // membuat table dengan nama "admins" untuk menyimpan data pin
        String sqlb = "insert into secur (pins) values (\"123456\");"; //data[13]
        Log.d("Data", "onCreate: " + sqlb);
        // eksekusi query
        db.execSQL(sqlb);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }

}
