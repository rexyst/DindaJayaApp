package com.rexqueen.dindajayaapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.rexqueen.dindajayaapp.model.DBHelper;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    // insiasi variabel
    Button keyOk;
    Intent intent;
    EditText pin;
    String key, pins="";
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // inisiasi class DBHelper
        dbHelper = new DBHelper(this);

//        if (pins.isEmpty()){
//            count = 0;
//        } else {
//            count = pins.size();
//        }

        // mendapatkan nilai variabel dari edittext
        pin = findViewById(R.id.pin);

        // mendapatkan nilai dari tombol
//        key1 = findViewById(R.id.key1);
//        key2 = findViewById(R.id.key2);
//        key3 = findViewById(R.id.key3);
//        key4 = findViewById(R.id.key4);
//        key5 = findViewById(R.id.key5);
//        key6 = findViewById(R.id.key6);
//        key7 = findViewById(R.id.key7);
//        key8 = findViewById(R.id.key8);
//        key9 = findViewById(R.id.key9);
//        key0 = findViewById(R.id.key0);
//        keyErase = findViewById(R.id.keyErase);
        keyOk = findViewById(R.id.keyOk);

//        menambahkan listener pada tiap tombol
//        key1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (count == 6){}
//                else {
//                    pins.add("1");
//                    key = pins.toString();
//                    key = key.replaceAll("]","");
//                    key = key.replaceAll("\\[","");
//                    key = key.replaceAll(" ","");
//                    key = key.replaceAll(",","");
//                    pin.setText(key);
//                    if (count <6) {
//                        count++;
//                    }
//                }
//            }
//        });
//
//        key2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (count == 6){}
//                else {
//                    pins.add("2");
//                    key = pins.toString();
//                    key = key.replaceAll("]","");
//                    key = key.replaceAll("\\[","");
//                    key = key.replaceAll(" ","");
//                    key = key.replaceAll(",","");
//                    pin.setText(key);
//                    if (count <6) {
//                        count++;
//                    }
//                }
//            }
//        });
//
//        key3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (count == 6){}
//                else {
//                    pins.add("3");
//                    key = pins.toString();
//                    key = key.replaceAll("]","");
//                    key = key.replaceAll("\\[","");
//                    key = key.replaceAll(" ","");
//                    key = key.replaceAll(",","");
//                    pin.setText(key);
//                    if (count <6) {
//                        count++;
//                    }
//                }
//            }
//        });
//
//        key4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (count == 6){}
//                else {
//                    pins.add("4");
//                    key = pins.toString();
//                    key = key.replaceAll("]","");
//                    key = key.replaceAll("\\[","");
//                    key = key.replaceAll(" ","");
//                    key = key.replaceAll(",","");
//                    pin.setText(key);
//                    if (count <6) {
//                        count++;
//                    }
//                }
//            }
//        });
//
//        key5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (count == 6){}
//                else {
//                    pins.add("5");
//                    key = pins.toString();
//                    key = key.replaceAll("]","");
//                    key = key.replaceAll("\\[","");
//                    key = key.replaceAll(" ","");
//                    key = key.replaceAll(",","");
//                    pin.setText(key);
//                    if (count <6) {
//                        count++;
//                    }
//                }
//            }
//        });
//
//        key6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (count == 6){}
//                else {
//                    pins.add("6");
//                    key = pins.toString();
//                    key = key.replaceAll("]","");
//                    key = key.replaceAll("\\[","");
//                    key = key.replaceAll(" ","");
//                    key = key.replaceAll(",","");
//                    pin.setText(key);
//                    if (count <6) {
//                        count++;
//                    }
//                }
//            }
//        });
//
//        key7.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (count == 6){}
//                else {
//                    pins.add("7");
//                    key = pins.toString();
//                    key = key.replaceAll("]","");
//                    key = key.replaceAll("\\[","");
//                    key = key.replaceAll(" ","");
//                    key = key.replaceAll(",","");
//                    pin.setText(key);
//                    if (count <6) {
//                        count++;
//                    }
//                }
//            }
//        });
//
//        key8.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (count == 6){}
//                else {
//                    pins.add("8");
//                    key = pins.toString();
//                    key = key.replaceAll("]","");
//                    key = key.replaceAll("\\[","");
//                    key = key.replaceAll(" ","");
//                    key = key.replaceAll(",","");
//                    pin.setText(key);
//                    if (count <6) {
//                        count++;
//                    }
//                }
//            }
//        });
//
//        key9.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (count == 6){}
//                else {
//                    pins.add("9");
//                    key = pins.toString();
//                    key = key.replaceAll("]","");
//                    key = key.replaceAll("\\[","");
//                    key = key.replaceAll(" ","");
//                    key = key.replaceAll(",","");
//                    pin.setText(key);
//                    if (count <6) {
//                        count++;
//                    }
//                }
//            }
//        });
//
//        key0.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (count == 6){}
//                else {
//                    pins.add("0");
//                    key = pins.toString();
//                    key = key.replaceAll("]","");
//                    key = key.replaceAll("\\[","");
//                    key = key.replaceAll(" ","");
//                    key = key.replaceAll(",","");
//                    pin.setText(key);
//                    if (count <6) {
//                        count++;
//                    }
//                }
//            }
//        });
//
//        keyErase.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (count == 6){}
//                else if(count > 0) {
//                    pins.remove(count-1);
//                    if (pins.size()>0) {
//                        key = pins.toString();
//                        key = key.replaceAll("]","");
//                        key = key.replaceAll("\\[","");
//                        key = key.replaceAll(" ","");
//                        key = key.replaceAll(",","");
//                        pin.setText(key);
//                    } else {
//                        pin.setText("");
//                    }
//                    if (count >0) {
//                        count--;
//                    }
//                }
//            }
//        });

        keyOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pins = String.valueOf(pin.getText());
                // melakukan pengecekan apakah pin kosong
                if (pins.length() == 0){
                    // menampilkan toast
                    Toast.makeText(Login.this, "Isi PIN terlebih dahulu!", Toast.LENGTH_SHORT).show();
                    // menampilkan pesan error pada edittext
                    pin.setError("PIN tidak boleh kosong");
                }
                // melakukan pengecekan apakah pin terisi semua (6 digit)
                else if (pins.length() <6){
                    // menampilkan toast
                    Toast.makeText(Login.this, "PIN 6 digit!", Toast.LENGTH_SHORT).show();
                    //menampilkan pesan error pada edittext
                    pin.setError("PIN berupa 6 digit angka");
                }
                // jika sudah terisi semua
                else {
                    // Mengambil database untuk dibaca
                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                    // Memulai transaksi dengan database
                    db.beginTransaction();

                    // mencoba menjalankan query
                    try
                    {
                        // menyiapkan query
                        String selectQuery = "SELECT * FROM secur";
                        // menyiapkan cursor untuk membaca nilai
                        Cursor cursor = db.rawQuery(selectQuery,null);
                        // jika jumlah baris lebih dari 0
                        if(cursor.getCount() >0)
                        {
                            cursor.moveToFirst();
                            String pind = cursor.getString(cursor.getColumnIndex("pins"));
                            Log.d("Testssss", "PIND : "+pind);
                            key = pins;
//                            key = pins.toString();
//                            key = key.replaceAll("]","");
//                            key = key.replaceAll("\\[","");
//                            key = key.replaceAll(" ","");
//                            key = key.replaceAll(",","");
//                            pin.setText(key);
                            Toast.makeText(Login.this, pind, Toast.LENGTH_SHORT).show();
                            if (key.equals(pind)){
                                // beralih ke halaman Home
                                intent = new Intent(Login.this, Home.class);
                                startActivity(intent);
                                // menampilkan toast selamat datang
//                    Toast.makeText(Login.this, "Selamat Datang \"" + pins.toString() + "\"", Toast.LENGTH_SHORT).show();
                                Toast.makeText(Login.this, "Selamat Datang", Toast.LENGTH_SHORT).show();
                                // menutup activity login
                                finish();
                            } else {
                                Toast.makeText(Login.this, "PIN salah", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(Login.this, "GAGAL!!!!", Toast.LENGTH_SHORT).show();
                        }
                        // mengubah status traksaksi dengan database
                        db.setTransactionSuccessful();

                    }
                    // jika gagal transaksi dengan database
                    catch (SQLiteException e)
                    {
                        // tampilkan toast dengan pesan error
                        Toast.makeText(getApplicationContext(), "Gagal terhubung ke database. Error: "+e, Toast.LENGTH_LONG).show();
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
        });


//        // menambahkan onclick listener pada tombol masuk
//        masuk_bt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // melakukan pengecekan apakah nama pengguna kosong
//                if (editUser.getText().length() <= 0){
//                    // menampilkan toast
//                    Toast.makeText(Login.this, "Nama Pengguna kosong!", Toast.LENGTH_SHORT).show();
//                    // menampilkan pesan error pada edittext
//                    editUser.setError("Nama Pengguna tidak boleh kosong");
//                }
//                // melakukan pengecekan apakah kata sandi kosong
//                else if (passWd.getText().length() <= 0){
//                    // menampilkan toast
//                    Toast.makeText(Login.this, "Kata Sandi kosong!", Toast.LENGTH_SHORT).show();
//                    //menampilkan pesan error pada edittext
//                    passWd.setError("Kata Sandi tidak boleh kosong");
//                }
//                // jika sudah terisi semua
//                else {
//                    // beralih ke halaman Home
//                    intent = new Intent(Login.this, Home.class);
//                    startActivity(intent);
//                    // menampilkan toast selamat datang
//                    Toast.makeText(Login.this, "Selamat Datang \"" + editUser.getText() + "\"", Toast.LENGTH_SHORT).show();
//                    // menutup activity login
//                    finish();
//                }
//            }
//        });

    }

}
