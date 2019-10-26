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

import com.rexqueen.dindajayaapp.Home;
import com.rexqueen.dindajayaapp.Login;
import com.rexqueen.dindajayaapp.R;

public class MenuFragment extends Fragment {

    Button tutor, tentang, logout, minim;
    Intent intent;
    Login login;
    Home home;

    private MenuViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(MenuViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_menu, container, false);

        login = new Login();
        home = new Home();

        tutor = root.findViewById(R.id.tutorial);
        tentang = root.findViewById(R.id.about);
        logout = root.findViewById(R.id.logout);
        minim = root.findViewById(R.id.minimize);

        tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(root.getContext(), "Fitur belum tersedia", Toast.LENGTH_SHORT).show();
            }
        });

        tentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(root.getContext(), "Fitur belum tersedia", Toast.LENGTH_SHORT).show();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(root.getContext(), login.getClass());
                startActivity(intent);
                Toast.makeText(root.getContext(), "Berhasil Keluar", Toast.LENGTH_SHORT).show();
                home.finish();
            }
        });

        minim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(root.getContext(), "Aplikasi dimimalkan", Toast.LENGTH_SHORT).show();
                System.exit(0);
            }
        });

        return root;
    }
}