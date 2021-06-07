package com.rexqueen.dindajayaapp.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    // menyiapkan nama database yang akan dipakai
    private static final String DATABASE_NAME = "dinda_jaya.db";
    // menyiapkan versi database
    private static final int DATABASE_VERSION = 17;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    // membuat database lokal ketika aplikasi pertama kali dipasang
    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        // membuat table dengan nama "orders" untuk menyimpan data pesanan
        String createTableOrders = "create table orders (" +
                "idPesan integer primary key AUTOINCREMENT, " + //data[0]
                "nama text null, " + //data[1]
                "noHp text null, " + //data[2]
                "tglPesan text null, " + //data[3]
                "blnPesan text null,"+ //data[4]
                "tglSelesai text null, " + //data[5]
                "tglAmbil text null, " + //data[6]
                "keterangan text null, " + //data[7]
                "biaya text null, " + //data[8]
                "urlFoto text null, " + //data[9]
                "status integer null, "+ //data[10]

                "laki integer null, " + //data[11]
                "perempuan integer null, " + //data[12]
                "seragam integer null, " + //data[13]
                "atasan integer null, " + //data[14]
                "bawahan integer null, " + //data[15]
                "sbadan integer null, " + //data[16]
                "terusan integer null, " + //data[17]
                "lpanjang integer null, " + //data[18]
                "lpendek integer null, " + //data[19]
                "cpanjang integer null, " + //data[20]
                "cpendek integer null, " + //data[21]
                "rpanjang integer null, " + //data[22]
                "rpendek integer null, " + //data[23]
                "anak integer null, " + //data[24]
                "dewasa integer null, " + //data[25]
                "xtra integer null, " + //data[26]
                "jumlah integer null);"; //data[27]

        String createTableCriteria = "create table criterias (" +
                "idCriteria integer primary key AUTOINCREMENT, " +
                "criteriaName text null, " +
                "criteriaValue integer null);";

        Log.d("Data", "onCreate: " + createTableOrders);
        Log.d("Data", "onCreate: " + createTableCriteria);
        // eksekusi query
        db.execSQL(createTableOrders);
        db.execSQL(createTableCriteria);

        // membuat table dengan nama "admins" untuk menyimpan data pin
        String sqla = "create table secur (pins text not null default \"123456\");"; //data[13]
        Log.d("Data", "onCreate: " + sqla);
        // eksekusi query
        db.execSQL(sqla);

        // mengisi pin default
        String sqlb = "insert into secur (pins) values (\"123456\");"; //data[13]
        Log.d("Data", "onCreate: " + sqlb);
        // eksekusi query
        db.execSQL(sqlb);

        // mengisi criteria default
        String sqlc = "insert into criterias (criteriaName, criteriaValue) values " +
                "(\"waktuPesan\", 1)," +
                "(\"jenisKelamin\", 1)," +
                "(\"jenisPakaian\", 1)," +
                "(\"model\", 1)," +
                "(\"ukuran\", 1)," +
                "(\"jumlah\", 1)," +
                "(\"status\", 9);";

        Log.d("Data", "onCreate: "+sqlc);
        db.execSQL(sqlc);

    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub
    }

}
