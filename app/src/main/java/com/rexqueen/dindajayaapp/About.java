package com.rexqueen.dindajayaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class About extends AppCompatActivity {

    TextView contact;
    Intent intent;
    String dev_cont;
    Button kembali_bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        contact = findViewById(R.id.dev_contact);
        kembali_bt = findViewById(R.id.kembali_bt);
        dev_cont = "https://bit.ly/Rex1054";

        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(dev_cont));
                startActivity(intent);
            }
        });

        kembali_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(About.this, Home.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
