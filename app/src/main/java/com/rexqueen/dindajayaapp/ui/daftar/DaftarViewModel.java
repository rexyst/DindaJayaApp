package com.rexqueen.dindajayaapp.ui.daftar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DaftarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DaftarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}