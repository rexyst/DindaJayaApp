package com.rexqueen.dindajayaapp.ui.daftar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rexqueen.dindajayaapp.AddOrders;
import com.rexqueen.dindajayaapp.Home;
import com.rexqueen.dindajayaapp.R;
import com.rexqueen.dindajayaapp.model.DBHelper;

public class DaftarFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // membuat root untuk mengakses view
        final View root = inflater.inflate(R.layout.fragment_daftar, container, false);


        return root;
    }
}