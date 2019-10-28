package com.rexqueen.dindajayaapp.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.rexqueen.dindajayaapp.About;
import com.rexqueen.dindajayaapp.Home;
import com.rexqueen.dindajayaapp.Login;
import com.rexqueen.dindajayaapp.R;

public class MenuFragment extends Fragment {

    // inisiasi variabel
    Button tutor, tentang, logout, minim;
    Intent intent;
    Login login;
    Home home;
    About about;

    // inisiasi Class MenuViewModel
    private MenuViewModel menuViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        // set root untuk mengakses view
        final View root = inflater.inflate(R.layout.fragment_menu, container, false);

        // inisiasi class pendukung
        login = new Login();
        home = new Home();
        about = new About();

        // mendapatkan nilai dari tombol
        tutor = root.findViewById(R.id.tutorial);
        tentang = root.findViewById(R.id.about);
        logout = root.findViewById(R.id.logout);
        minim = root.findViewById(R.id.minimize);

        // menambahkan listener pada tombol tutor
        tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // menampilkan toast
                Toast.makeText(root.getContext(), "Fitur belum tersedia", Toast.LENGTH_SHORT).show();
            }
        });

        // menambahkan listener pada tombol tentang
        tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // membuat intent untuk beralih ke halaman About
                intent = new Intent(root.getContext(), about.getClass());
                startActivity(intent);
                // menutup activity Home
                home.finish();
            }
        });

        // menambahkan listener pada tombol logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // membuat intent untuk beralih ke halaman Login
                intent = new Intent(root.getContext(), login.getClass());
                startActivity(intent);
                // menampilkan toast
                Toast.makeText(root.getContext(), "Berhasil Keluar", Toast.LENGTH_SHORT).show();
                // menutup activity Home
                home.finish();
            }
        });

        // menambahkan listener pada tombol minimalkan
        minim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // menampilkan toast
                Toast.makeText(root.getContext(), "Aplikasi dimimalkan", Toast.LENGTH_SHORT).show();
                // membuat intent untuk meminimalkan aplikasi
                intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return root;
    }
}