package com.rexqueen.dindajayaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    Button masuk_bt;
    Intent intent;
    EditText editUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editUser = findViewById(R.id.username);

        masuk_bt = findViewById(R.id.masuk_bt);
        masuk_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Login.this, Home.class);
                startActivity(intent);
                Toast.makeText(Login.this, "Selamat Datang \""+editUser.getText()+"\"", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
