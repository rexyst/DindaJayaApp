package com.rexqueen.dindajayaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class About extends AppCompatActivity {

    // inisiasi variabel
    TextView contact;
    Intent intent;
    String dev_cont;
    Button kembali_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        // mengambil nilai dari textview
        contact = findViewById(R.id.dev_contact);
        // mengambil nilai dari tombol kembali
        kembali_bt = findViewById(R.id.kembali_bt);
        // menyiapkan URL kontak
        dev_cont = "https://bit.ly/Rex1054";

        // menambahkan listener pada textview kontak
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // membuat intent untuk membuka browser dengan alamat URL yang telah disiapkan (dev_cont)
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(dev_cont));
                startActivity(intent);
            }
        });

        // menambahkna listener pada tombol kembali
        kembali_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // membuat intent untuk beralih ke halaman Home
                intent = new Intent(About.this, Home.class);
                startActivity(intent);
                // menutup activity About
                finish();
            }
        });

    }
}
