package com.rexqueen.dindajayaapp.ui.menu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.rexqueen.dindajayaapp.About;
import com.rexqueen.dindajayaapp.Home;
import com.rexqueen.dindajayaapp.Login;
import com.rexqueen.dindajayaapp.R;
import com.rexqueen.dindajayaapp.model.DBHelper;
import com.rexqueen.dindajayaapp.settingKriteria;

public class MenuFragment extends Fragment {

    // inisiasi variabel
    Button tentang, setCrit, logout;
//    Button backup;
    Intent intent;
    Login login;
    Home home;
    About about;
    settingKriteria settingKriteria;
    DBHelper dbHelper;

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
        settingKriteria = new settingKriteria();
        dbHelper = new DBHelper(this.getContext());

        // mendapatkan nilai dari tombol
        tentang = root.findViewById(R.id.about);
        logout = root.findViewById(R.id.logout);
        setCrit = root.findViewById(R.id.setCrit);
//        backup = root.findViewById(R.id.backup);

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

        setCrit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(root.getContext(), settingKriteria.getClass());
                startActivity(intent);

                home.finish();
            }
        });

        // menambahkan listener pada tombol logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // membuat dialog konfirmasi
                AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
                builder.setTitle("Keluar");
                builder.setMessage("Apakah Anda yakin untuk keluar?");
                builder.setCancelable(false);
                // membuat tombol "Ya" dengan listenernya
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // membuat intent untuk beralih ke halaman Login
                        intent = new Intent(root.getContext(), login.getClass());
                        startActivity(intent);
                        // menampilkan toast
                        Toast.makeText(root.getContext(), "Berhasil Keluar", Toast.LENGTH_SHORT).show();
                        // menutup activity Home
                        home.finish();
                    }
                });

                // membuat tombol "Tidak" dengan listener
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // jika tombol "Tidak" dipilih, tidak apa-apa
                    }
                });

                // menampilkan dialog konfirmasi
                builder.show();


            }
        });

        return root;
    }
}