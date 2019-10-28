package com.rexqueen.dindajayaapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Home extends AppCompatActivity {

    // inisisasi variabel
    int getExtra;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_daftar, R.id.navigation_menu)
                .build();
        // inisiasi navigasi bawah
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        // mendapatkan nilai variabel extra dari intent
        Bundle extras = getIntent().getExtras();
        // mengecek apakah extra tidak kosong
        if (extras != null) {
            // mendapatkan nilai extra dari intent dengan kata kunci "page"
            getExtra = extras.getInt("page");
            // jika nilai extra adalah 2
            if (getExtra == 2) {
                // menampilkan fragment daftar
                navController.navigate(R.id.navigation_daftar);
            }
        }

    }

    // menimpa method tombol kembali
    // sehingga ketika berada pada class Home dan tombol kembali diklik
    // aplikasi akan dimilalkan
    @Override
    public void onBackPressed()
    {
        intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
