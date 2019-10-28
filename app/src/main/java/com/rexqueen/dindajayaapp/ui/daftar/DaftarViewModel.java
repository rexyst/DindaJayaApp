package com.rexqueen.dindajayaapp.ui.daftar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DaftarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DaftarViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}