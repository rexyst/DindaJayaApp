package com.rexqueen.dindajayaapp.ui.daftar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.rexqueen.dindajayaapp.R;

public class DaftarFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // membuat root untuk mengakses view
        final View root = inflater.inflate(R.layout.fragment_daftar, container, false);
        

        return root;
    }
}