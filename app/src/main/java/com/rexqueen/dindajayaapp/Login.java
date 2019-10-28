package com.rexqueen.dindajayaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    // insiasi variabel
    Button masuk_bt;
    Intent intent;
    EditText editUser, passWd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // mendapatkan nilai variabel dari edittext
        editUser = findViewById(R.id.username);
        passWd = findViewById(R.id.pass);

        // mendapatkan nilai dari tombol masuk
        masuk_bt = findViewById(R.id.masuk_bt);

        // menambahkan onclick listener pada tombol masuk
        masuk_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // melakukan pengecekan apakah nama pengguna kosong
                if (editUser.getText().length() <= 0){
                    // menampilkan toast
                    Toast.makeText(Login.this, "Nama Pengguna kosong!", Toast.LENGTH_SHORT).show();
                    // menampilkan pesan error pada edittext
                    editUser.setError("Nama Pengguna tidak boleh kosong");
                }
                // melakukan pengecekan apakah kata sandi kosong
                else if (passWd.getText().length() <= 0){
                    // menampilkan toast
                    Toast.makeText(Login.this, "Kata Sandi kosong!", Toast.LENGTH_SHORT).show();
                    //menampilkan pesan error pada edittext
                    passWd.setError("Kata Sandi tidak boleh kosong");
                }
                // jika sudah terisi semua
                else {
                    // beralih ke halaman Home
                    intent = new Intent(Login.this, Home.class);
                    startActivity(intent);
                    // menampilkan toast selamat datang
                    Toast.makeText(Login.this, "Selamat Datang \"" + editUser.getText() + "\"", Toast.LENGTH_SHORT).show();
                    // menutup activity login
                    finish();
                }
            }
        });

    }
}
