package com.rexqueen.dindajayaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rexqueen.dindajayaapp.model.DBHelper;

public class ChPin extends AppCompatActivity {

    Button ubah, kembali;
    EditText oldPin, newPin;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ch_pin);



//        inisiasi button
        ubah = findViewById(R.id.bt_ubah);
        kembali = findViewById(R.id.bt_kembali);

//        inisiasi edittext
        oldPin = findViewById(R.id.oldPin);
        newPin = findViewById(R.id.newPin);

//        listener ubah
        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = String.valueOf(oldPin.getText());
                String b = String.valueOf(newPin.getText());
                if (a.length() == 0){
                    Toast.makeText(ChPin.this, "PIN Kosong", Toast.LENGTH_SHORT).show();
                    oldPin.requestFocus();
                } else if (a.length() <6) {
                    Toast.makeText(ChPin.this, "PIN berupa 6 digit angka", Toast.LENGTH_SHORT).show();
                    oldPin.requestFocus();
                } else if (b.length() == 0) {
                    Toast.makeText(ChPin.this, "PIN Kosong", Toast.LENGTH_SHORT).show();
                    newPin.requestFocus();
                } else if (b.length() < 6) {
                    Toast.makeText(ChPin.this, "PIN berupa 6 digit angka", Toast.LENGTH_SHORT).show();
                    newPin.requestFocus();
                } else {
                    // inisiasi db
                    dbHelper = new DBHelper(ChPin.this);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    try {
                        db.execSQL("update secur set `pins`='"+b+"';");
                        Toast.makeText(ChPin.this, "Berhasil ubah PIN", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChPin.this, About.class);
                        startActivity(intent);
                        finish();
                    } catch (SQLException e) {
                        Toast.makeText(ChPin.this, "Gagal ubah PIN", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChPin.this, About.class);
                startActivity(intent);
                finish();
            }
        });
    }
}