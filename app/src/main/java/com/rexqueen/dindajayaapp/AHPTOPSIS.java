package com.rexqueen.dindajayaapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.rexqueen.dindajayaapp.model.DBHelper;

import java.util.Arrays;

public class AHPTOPSIS {
    double[][] waktuPesan;
    double[][] jenis;
    double[][] ukuran;
    double[][] jumlah;
    double[][] kriteria;
    double[][] data;
    String[][] sData;
    DBHelper dbHelper;
    int[][] pesanan;
    int nData;
    Context context;

    public void AHPTOPSIS(String tanggal, Context context, TableLayout tableLayout){
        AHPTOPSIS at = new AHPTOPSIS();

//        input data pesanan
//        pesanan [0] = waktu pesan
//        pesanan [1] = jenis
//        pesanan [2] = ukuran
//        pesanan [3] = jumlah pesanan

        // inisiasi class DBHelper
        dbHelper = new DBHelper(context);
        // Mengambil database untuk dibaca
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Memulai transaksi dengan database
        db.beginTransaction();

        try
        {
            // konversi tanggal
            String a = tanggal;
            // menyiapkan query
            String selectQuery = "SELECT * FROM orders where `status`!=4 and `tglPesan`='"+a+"'";
            // menyiapkan cursor untuk membaca nilai
            Cursor cursor = db.rawQuery(selectQuery,null);
            // jika jumlah baris lebih dari 0
            if(cursor.getCount() >0)
            {

                System.out.print("Masukkan jumlah pesanan : ");
                nData = cursor.getCount();
                pesanan = new int[nData][4];
                sData = new String[nData][4];

                // inisiasi kriteria
                at.waktuPesan = new double[nData + 1][nData];
                at.jenis = new double[nData + 1][nData];
                at.ukuran = new double[nData + 1][nData];
                at.jumlah = new double[nData + 1][nData];
                at.kriteria = new double[5][4];

                //        mengisi data dummy
                for (int i = 0; i < nData; i++) {
                    for (int j = 0; j < nData; j++) {
                        at.waktuPesan[i][j] = 0;
                        at.jenis[i][j] = 0;
                        at.ukuran[i][j] = 0;
                        at.jumlah[i][j] = 0;
                    }
                }
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 4; j++) {
                        at.kriteria[i][j] = 0;
                    }
                }

//        mengisi nilai 1 untuk alternatif dengan pembanding yang sama
                for (int i = 0; i < nData; i++) {
                    at.waktuPesan[i][i] = 1;
                    at.jenis[i][i] = 1;
                    at.ukuran[i][i] = 1;
                    at.jumlah[i][i] = 1;
                }
                for (int i = 0; i < 4; i++) {
                    at.kriteria[i][i] = 1;
                }

                data = new double[cursor.getCount()][4];
                int count = 0;
                // memulai iterasi untuk mengambil data perbaris
                while (cursor.moveToNext()) {
                    // mengambil nilai perkolom
                    //mengambil id untuk penentuan waktu pesan
                    data[count][0]= cursor.getInt(cursor.getColumnIndex("idPesan"));
                    // mengambil nilai dari kolom "jenis"
                    int jenis= cursor.getInt(cursor.getColumnIndex("jenis"));
                    data[count][1] = (double) jenis;
                    // mengambil nilai dari kolom "ukuran"
                    int ukuran= cursor.getInt(cursor.getColumnIndex("ukuran"));
                    data[count][2] = (double) ukuran;
//                    mengambil nilai jumlah
                    int jumlah= cursor.getInt(cursor.getColumnIndex("jumlah"));
                    data[count][3] = (double) jumlah;

//                    mengambil data untuk ditampilkan
                    sData[count][0]= String.valueOf(cursor.getInt(cursor.getColumnIndex("idPesan")));
                    // mengambil nilai dari kolom "nama"
                    sData[count][1]= cursor.getString(cursor.getColumnIndex("nama"));
                    // mengambil nilai dari kolom "jenis"
                    sData[count][2]= cursor.getString(cursor.getColumnIndex("jenis"));
                    // mengambil nilai dari kolom "status"
                    sData[count][3]= cursor.getString(cursor.getColumnIndex("status"));

                    // konversi nilai dari jenis
                    // jika nilai 1
                    if (sData[count][2].equals("1")) {
                        // dikonversi menjadi "Seragam"
                        sData[count][2] = "Seragam";
                    } else
                        // jika nilai 2
                        if (sData[count][2].equals("2")) {
                            // dikonversi menjadi "Atasan"
                            sData[count][2] = "Atasan";
                        } else
                            // jika nilai 3
                            if (sData[count][2].equals("3")) {
                                // dikonversi menjadi "Bawahan"
                                sData[count][2] = "Bawahan";
                            }

                    // konversi nilai dari status
                    // jika nilai 1
                    if (sData[count][3].equals("1")){
                        // dikonversi menjadi "Menunggu"
                        sData[count][3] = "Menunggu";
                    } else
                        // jika nilai 2
                        if (sData[count][3].equals("2")){
                            // dikonversi menjadi "Diproses"
                            sData[count][3] = "Diproses";
                        } else
                            // jika nilai 3
                            if (sData[count][3].equals("3")){
                                // dikonversi menjadi "Selesai"
                                sData[count][3] = "Selesai";
                            } else
                                // jika nilai 4
                                if (sData[count][3].equals("4")) {
                                    // dikonversi menjadi "Diambil"
                                    sData[count][3] = "Diambil";
                                }

                    count++;
                }

            }
            // mengubah status traksaksi dengan database
            db.setTransactionSuccessful();

        }
        // jika gagal transaksi dengan database
        catch (SQLiteException e)
        {
            // tampilkan toast dengan pesan error
            Toast.makeText(context, "Gagal terhubung ke database. Error: "+e, Toast.LENGTH_LONG).show();
        }
        finally
        {
            // Mengakhiri transaksi dengan database
            db.endTransaction();
        }

//        input nilai waktu pesan / urutan pesan
        for (int i = 0; i < nData; i++) {
//            konversi nilai jam ke 3,5,7,9
//            urutan pertama pemesanan bernilai 9,
//            urutan ke 2 bernilai 7,
//            urutan ke 3 bernilai 5,
//            urutan 4 dan seterusnya bernilai 3

            double x = data[i][0];
            if (i==0) {
                pesanan[i][0] = 9;
            } else if (i==1) {
                pesanan[i][0] = 7;
            } else if (i==2) {
                pesanan[i][0] = 5;
            } else if (i>2) {
                pesanan[i][0] = 3;
            }

//            input nilai jenis pesanan
//            konversi nilai jenis ke 3,5,7
            int y = (int)data[i][1];
            switch (y) {
                case 1:
                    pesanan[i][1] = 7;
                    break;
                case 2:
                    pesanan[i][1] = 5;
                    break;
                case 3:
                    pesanan[i][1] = 3;
                    break;
                default:
                    break;
            }


//            input nilai ukuran
//            konversi nilai ukuran ke 3,5,7
            int d = (int)data[i][2];
            switch (d) {
                case 1:
                    pesanan[i][2] = 3;
                    break;
                case 2:
                    pesanan[i][2] = 5;
                    break;
                case 3:
                    pesanan[i][2] = 3;
                    break;
                default:
                    break;
            }


//            input nilai jumlah
//            konversi nilai jumlah ke 3,5,7
            int e = (int)data[i][3];
            if (e == 1) {
                pesanan[i][3] = 5;
            } else if (e == 2) {
                pesanan[i][3] = 7;
            } else if (e > 2) {
                pesanan[i][3] = 3;
            }
        }

        System.out.println("cek data pesanan");
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                if (j == (nData - 1)) {
                    System.out.println(pesanan[i][j]);
                } else {
                    System.out.print(pesanan[i][j] + " | ");
                }
            }
        }
        System.out.println("");

        this.context = context;
        at.ahp(at.kriteria, 5, 7, 3, 3, nData, pesanan, at.waktuPesan, at.jenis, at.ukuran, at.jumlah, this.context, tableLayout);
    }

    void ahp(double[][] kriteria, int k1, int k2, int k3, int k4, int nData, int[][] pesanan, double[][] waktuPesan, double[][] jenis, double[][] ukuran, double[][] jumlah, Context context, TableLayout tableLayout) {
        int z = 4;
        double[] crit = {k1, k2, k3, k4};
        double[][] normKriteria = new double[z + 1][z];
        double[] eigenKriteria = new double[z];

//        membandingkan kriteria (i,j)
        System.out.println("membandingkan kriteria (i,j)");
        for (int i = 0; i < z; i++) {
            for (int j = 0; j < z - 1; j++) {
                if (kriteria[i][j] == 0) {
                    if (crit[j] > crit[j + 1]) {
                        kriteria[j][i] = crit[j];
                        kriteria[i][j] = (double) 1 / crit[j];
                    } else if (crit[j] == crit[j + 1]) {
                        kriteria[j][i] = 1;
                        kriteria[i][j] = 1;
                    } else {
                        kriteria[i][j] = crit[j];
                        kriteria[j][i] = (double) 1 / crit[j];
                    }
                }
            }
        }

//        menjumlah matriks secara vertikal
        for (int i = 0; i < z; i++) {
            for (int j = 0; j < z; j++) {
                kriteria[z][i] += kriteria[j][i];
            }
        }

//        menampilkan data
        System.out.println("kriteria");
        for (int i = 0; i < z; i++) {
            for (int j = 0; j < z; j++) {
                if (j == (z - 1)) {
                    System.out.println(kriteria[i][j]);
                } else {
                    System.out.print(kriteria[i][j] + " | ");
                }
            }
        }
        System.out.println("");
        System.out.println("jumlah dari data vertikal");
        for (int i = 0; i < z; i++) {
            if (i == (z - 1)) {
                System.out.println(kriteria[z][i]);
            } else {
                System.out.print(kriteria[z][i] + " | ");
            }
        }
        System.out.println("");

//        normalisasi kriteria
        System.out.println("normalisasi kriteria");
        System.out.println("kriteria");
        for (int i = 0; i < z; i++) {
            for (int j = 0; j < z; j++) {
                normKriteria[j][i] = (double) kriteria[j][i] / kriteria[z][i];
            }
        }
//        menghitung jumlah dari masing" nilai pada baris per kolom [vertikal]
        for (int i = 0; i < z; i++) {
            for (int j = 0; j < z; j++) {
                normKriteria[z][i] += normKriteria[j][i];
            }
        }

        for (int i = 0; i < z; i++) {
            for (int j = 0; j < z; j++) {
                if (j == (z - 1)) {
                    System.out.println(normKriteria[i][j]);
                } else {
                    System.out.print(normKriteria[i][j] + " | ");
                }
            }
        }
        System.out.println("");
        System.out.println("jumlah dari data vertikal");
        for (int i = 0; i < z; i++) {
            if (i == (z - 1)) {
                System.out.println(normKriteria[z][i]);
            } else {
                System.out.print(normKriteria[z][i] + " | ");
            }
        }

//        menghitung eigen kriteria
        for (int i = 0; i < z; i++) {
            for (int j = 0; j < z; j++) {
                eigenKriteria[i] += normKriteria[i][j];
            }
            eigenKriteria[i] = (double) eigenKriteria[i] / z;
        }

        System.out.println("eigen kriteria");
        for (int i = 0; i < z; i++) {
            System.out.println("eigen kriteria " + (i + 1) + " : " + eigenKriteria[i]);
        }
        System.out.println("");

        topsis(nData, pesanan, waktuPesan, jenis, ukuran, jumlah, eigenKriteria, context, tableLayout);
    }

    void topsis(int nData, int[][] pesanan, double[][] waktuPesan, double[][] jenis, double[][] ukuran, double[][] jumlah, double[] eigenCrit, Context context, TableLayout tableLayout) {

        double[][] normWaktuPesan = new double[nData + 1][nData];
        double[][] normJenis = new double[nData + 1][nData];
        double[][] normUkuran = new double[nData + 1][nData];
        double[][] normJumlah = new double[nData + 1][nData];
        double[][] eigen = new double[4][nData];
        double[][] weightAlt = new double[nData][4];
        double[] ahp = new double[nData];
        double[] pis = new double[4], nis = new double[4];
        double[] x1 = new double[nData], x2 = new double[nData], x3 = new double[nData], x4 = new double[nData];
        double[] d1 = new double[nData], d2 = new double[nData], rc = new double[nData];

//        nilai eigen dari masing-masing kriteria
//        waktu pesan = 0.49
//        jenis = 0.29
//        ukuran = 0.15
//        jumlah pesan = 0.07
        System.out.println("");
        System.out.println("");

        System.out.println("menghitung perbandingan i,j pada kriteria waktu pesanan");
//        menghitung waktu pesanan
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData - 1; j++) {
                if (waktuPesan[i][j] == 0) {
                    if (pesanan[j][0] > pesanan[j + 1][0]) {
                        waktuPesan[j][i] = pesanan[j][0];
                        waktuPesan[i][j] = (double) 1 / pesanan[j][0];
                    } else if (pesanan[j][0] == pesanan[j + 1][0]) {
                        waktuPesan[j][i] = 1;
                        waktuPesan[i][j] = 1;
                    } else {
                        waktuPesan[i][j] = pesanan[j][0];
                        waktuPesan[j][i] = (double) 1 / pesanan[j][0];
                    }
                }
            }
        }
//        menghitung jumlah dari masing" nilai pada baris per kolom [vertikal]
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                waktuPesan[nData][i] += waktuPesan[j][i];
            }
        }

        System.out.println("waktu pesan");
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                if (j == (nData - 1)) {
                    System.out.println(waktuPesan[i][j]);
                } else {
                    System.out.print(waktuPesan[i][j] + " | ");
                }
            }
        }
        System.out.println("");
        System.out.println("jumlah dari data vertikal");
        for (int i = 0; i < nData; i++) {
            if (i == (nData - 1)) {
                System.out.println(waktuPesan[nData][i]);
            } else {
                System.out.print(waktuPesan[nData][i] + " | ");
            }
        }
        System.out.println("");

//        menghitung jenis
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData - 1; j++) {
                if (jenis[i][j] == 0.0) {
                    if (pesanan[j][1] > pesanan[j + 1][1]) {
                        jenis[j][i] = pesanan[j][1];
                        jenis[i][j] = (double) 1 / pesanan[j][1];
                    } else if (pesanan[j][1] == pesanan[j + 1][1]) {
                        jenis[i][j] = 1;
                        jenis[j][i] = 1;
                    } else {
                        jenis[i][j] = pesanan[j][1];
                        jenis[j][i] = (double) 1 / pesanan[j][1];
                    }
                }
            }
        }
//        menghitung jumlah dari masing" nilai pada baris per kolom [vertikal]
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                jenis[nData][i] += jenis[j][i];
            }
        }

        System.out.println("jenis");
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                if (j == (nData - 1)) {
                    System.out.println(jenis[i][j]);
                } else {
                    System.out.print(jenis[i][j] + " | ");
                }
            }
        }
        System.out.println("");
        System.out.println("jumlah dari data vertikal");
        for (int i = 0; i < nData; i++) {
            if (i == (nData - 1)) {
                System.out.println(jenis[nData][i]);
            } else {
                System.out.print(jenis[nData][i] + " | ");
            }
        }
        System.out.println("");

//        menghitung ukuran
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData - 1; j++) {
                if (ukuran[i][j] == 0.0) {
                    if (pesanan[j][2] > pesanan[j + 1][2]) {
                        ukuran[j][i] = pesanan[j][2];
                        ukuran[i][j] = (double) 1 / pesanan[j][2];
                    } else if (pesanan[j][2] == pesanan[j + 1][2]) {
                        ukuran[i][j] = 1;
                        ukuran[j][i] = 1;
                    } else {
                        ukuran[i][j] = pesanan[j][2];
                        ukuran[j][i] = (double) 1 / pesanan[j][2];
                    }
                }
            }
        }
//        menghitung jumlah dari masing" nilai pada baris per kolom [vertikal]
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                ukuran[nData][i] += ukuran[j][i];
            }
        }

        System.out.println("ukuran");
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                if (j == (nData - 1)) {
                    System.out.println(ukuran[i][j]);
                } else {
                    System.out.print(ukuran[i][j] + " | ");
                }
            }
        }
        System.out.println("");
        System.out.println("jumlah dari data vertikal");
        for (int i = 0; i < nData; i++) {
            if (i == (nData - 1)) {
                System.out.println(ukuran[nData][i]);
            } else {
                System.out.print(ukuran[nData][i] + " | ");
            }
        }

//        menghitung jumlah pesanan
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData - 1; j++) {
                if (jumlah[i][j] == 0.0) {
                    if (pesanan[j][3] > pesanan[j + 1][3]) {
                        jumlah[j][i] = pesanan[j][3];
                        jumlah[i][j] = (double) 1 / pesanan[j][3];
                    } else if (pesanan[j][3] == pesanan[j + 1][3]) {
                        jumlah[i][j] = 1;
                        jumlah[j][i] = 1;
                    } else {
                        jumlah[i][j] = pesanan[j][3];
                        jumlah[j][i] = (double) 1 / pesanan[j][3];
                    }
                }
            }
        }
//        menghitung jumlah dari masing" nilai pada baris per kolom [vertikal]
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                jumlah[nData][i] += jumlah[j][i];
            }
        }

        System.out.println("jumlah pesan");
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                if (j == (nData - 1)) {
                    System.out.println(jumlah[i][j]);
                } else {
                    System.out.print(jumlah[i][j] + " | ");
                }
            }
        }
        System.out.println("");
        System.out.println("jumlah dari data vertikal");
        for (int i = 0; i < nData; i++) {
            if (i == (nData - 1)) {
                System.out.println(jumlah[nData][i]);
            } else {
                System.out.print(jumlah[nData][i] + " | ");
            }
        }

        System.out.println("menormalisasi matriks");

//        menormalisasi matriks
//        waktu pemesanan
        System.out.println("waktu pesan");
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                normWaktuPesan[j][i] = (double) waktuPesan[j][i] / waktuPesan[nData][i];
            }
        }
//        menghitung jumlah dari masing" nilai pada baris per kolom [vertikal]
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                normWaktuPesan[nData][i] += normWaktuPesan[j][i];
            }
        }

        System.out.println("waktu pesan");
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                if (j == (nData - 1)) {
                    System.out.println(normWaktuPesan[i][j]);
                } else {
                    System.out.print(normWaktuPesan[i][j] + " | ");
                }
            }
        }
        System.out.println("");
        System.out.println("jumlah dari data vertikal");
        for (int i = 0; i < nData; i++) {
            if (i == (nData - 1)) {
                System.out.println(normWaktuPesan[nData][i]);
            } else {
                System.out.print(normWaktuPesan[nData][i] + " | ");
            }
        }

//        jenis
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                normJenis[j][i] = (double) jenis[j][i] / jenis[nData][i];
            }
        }
//        menghitung jumlah dari masing" nilai pada baris per kolom [vertikal]
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                normJenis[nData][i] += normJenis[j][i];
            }
        }

        System.out.println("jenis");
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                if (j == (nData - 1)) {
                    System.out.println(normJenis[i][j]);
                } else {
                    System.out.print(normJenis[i][j] + " | ");
                }
            }
        }
        System.out.println("");
        System.out.println("jumlah dari data vertikal");
        for (int i = 0; i < nData; i++) {
            if (i == (nData - 1)) {
                System.out.println(normJenis[nData][i]);
            } else {
                System.out.print(normJenis[nData][i] + " | ");
            }
        }

//        ukuran
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                normUkuran[j][i] = (double) ukuran[j][i] / ukuran[nData][i];
            }
        }
//        menghitung jumlah dari masing" nilai pada baris per kolom [vertikal]
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                normUkuran[nData][i] += normUkuran[j][i];
            }
        }

        System.out.println("ukuran");
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                if (j == (nData - 1)) {
                    System.out.println(normUkuran[i][j]);
                } else {
                    System.out.print(normUkuran[i][j] + " | ");
                }
            }
        }
        System.out.println("");
        System.out.println("jumlah dari data vertikal");
        for (int i = 0; i < nData; i++) {
            if (i == (nData - 1)) {
                System.out.println(normUkuran[nData][i]);
            } else {
                System.out.print(normUkuran[nData][i] + " | ");
            }
        }

//        jumlah pesanan
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                normJumlah[j][i] = (double) jumlah[j][i] / jumlah[nData][i];
            }
        }
//        menghitung jumlah dari masing" nilai pada baris per kolom [vertikal]
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                normJumlah[nData][i] += normJumlah[j][i];
            }
        }

        System.out.println("jumlah pesan");
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                if (j == (nData - 1)) {
                    System.out.println(normJumlah[i][j]);
                } else {
                    System.out.print(normJumlah[i][j] + " | ");
                }
            }
        }
        System.out.println("");
        System.out.println("jumlah dari data vertikal");
        for (int i = 0; i < nData; i++) {
            if (i == (nData - 1)) {
                System.out.println(normJumlah[nData][i]);
            } else {
                System.out.print(normJumlah[nData][i] + " | ");
            }
        }

        System.out.println("");
        System.out.println("");

        System.out.println("menghitung eigen tiap alternatif");
//        menghitung eigen dari tiap alternatif
//        waktu pesan
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                eigen[0][i] += normWaktuPesan[i][j];
            }
            eigen[0][i] = (double) eigen[0][i] / nData;
        }

        System.out.println("waktu pesan");
        for (int i = 0; i < nData; i++) {
            if (i == (nData - 1)) {
                System.out.println(eigen[0][i]);
            } else {
                System.out.print(eigen[0][i] + " | ");
            }
        }
        System.out.println("");

//        jenis
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                eigen[1][i] += normJenis[i][j];
            }
            eigen[1][i] = (double) eigen[1][i] / nData;
        }

        System.out.println("jenis");
        for (int i = 0; i < nData; i++) {
            if (i == (nData - 1)) {
                System.out.println(eigen[1][i]);
            } else {
                System.out.print(eigen[1][i] + " | ");
            }
        }
        System.out.println("");

//        ukuran
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                eigen[2][i] += normUkuran[i][j];
            }
            eigen[2][i] = (double) eigen[2][i] / nData;
        }

        System.out.println("ukuran");
        for (int i = 0; i < nData; i++) {
            if (i == (nData - 1)) {
                System.out.println(eigen[2][i]);
            } else {
                System.out.print(eigen[2][i] + " | ");
            }
        }
        System.out.println("");

//       jumlah pesan
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < nData; j++) {
                eigen[3][i] += normJumlah[i][j];
            }
            eigen[3][i] = (double) eigen[3][i] / nData;
        }

        System.out.println("jumlah pesan");
        for (int i = 0; i < nData; i++) {
            if (i == (nData - 1)) {
                System.out.println(eigen[3][i]);
            } else {
                System.out.print(eigen[3][i] + " | ");
            }
        }
        System.out.println("");
        for (int i = 0; i < nData; i++) {
            System.out.print("eigen data " + (i + 1) + " : ");
            for (int j = 0; j < 4; j++) {
                if (j == 3) {
                    System.out.println(eigen[j][i]);
                } else {
                    System.out.print(eigen[j][i] + " | ");
                }
            }
        }

        System.out.println("");

        System.out.println("mengalikan eigen tiap alternatif dengan bobot/eigen dari tiap kriteria");
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < 4; j++) {
                weightAlt[i][j] = (double) eigen[j][i] * eigenCrit[j];
            }
        }

        System.out.println("weighted alternatif");
        for (int i = 0; i < nData; i++) {
            for (int j = 0; j < 4; j++) {
                if (j == 3) {
                    System.out.println(weightAlt[i][j]);
                } else {
                    System.out.print(weightAlt[i][j] + " | ");
                }
            }
        }
        System.out.println("");

        System.out.println("menghitung pis+");
//        konversi nilai dari weighted alternatif
        for (int i = 0; i < nData; i++) {
            x1[i] = weightAlt[i][0];
            x2[i] = weightAlt[i][1];
            x3[i] = weightAlt[i][2];
            x4[i] = weightAlt[i][3];
        }

//        mencari pis
        Arrays.sort(x1);
        Arrays.sort(x2);
        Arrays.sort(x3);
        Arrays.sort(x4);
        pis[0] = x1[nData - 1];
        pis[1] = x2[nData - 1];
        pis[2] = x3[nData - 1];
        pis[3] = x4[nData - 1];
        System.out.println("PIS");
        for (int i = 0; i < 4; i++) {
            if (i == 3) {
                System.out.println(pis[i]);
            } else {
                System.out.print(pis[i] + " | ");
            }
        }

//        mencari nis
        nis[0] = x1[0];
        nis[1] = x2[0];
        nis[2] = x3[0];
        nis[3] = x4[0];
        System.out.println("NIS");
        for (int i = 0; i < 4; i++) {
            if (i == 3) {
                System.out.println(nis[i]);
            } else {
                System.out.print(nis[i] + " | ");
            }
        }
        System.out.println("");

//        menghitung separation d+ dan d-
        System.out.println("menghitung separation");
//        d+
        System.out.println("d+");
        for (int i = 0; i < nData; i++) {
            d1[i] = Math.pow((Math.pow((double) (weightAlt[i][0] - pis[0]), 2) + Math.pow((double) (weightAlt[i][1] - pis[1]), 2) + Math.pow((double) (weightAlt[i][2] - pis[2]), 2) + Math.pow((double) (weightAlt[i][3] - pis[3]), 2)), 0.5);
        }

        for (int i = 0; i < nData; i++) {
            System.out.println(d1[i]);
        }
        System.out.println("");

//        d-
        System.out.println("d-");
        for (int i = 0; i < nData; i++) {
            d2[i] = Math.pow((Math.pow((double) (weightAlt[i][0] - nis[0]), 2) + Math.pow((double) (weightAlt[i][1] - nis[1]), 2) + Math.pow((double) (weightAlt[i][2] - nis[2]), 2) + Math.pow((double) (weightAlt[i][3] - nis[3]), 2)), 0.5);
        }

        for (int i = 0; i < nData; i++) {
            System.out.println(d2[i]);
        }
        System.out.println("");

//        menghitung relative closeness
        System.out.println("relative closeness");
        for (int i = 0; i < nData; i++) {
            rc[i] = d2[i] / (d1[i] + d2[i]);
        }

        for (int i = 0; i < nData; i++) {
            if (i == (nData - 1)) {
                System.out.println(rc[i]);
            } else {
                System.out.print(rc[i] + " | ");
            }
        }
        System.out.println("");

        sortasi(sData, rc, context, tableLayout);

    }

    void sortasi(String[][] sData, double[] rc, Context context, TableLayout tableLayout){

//        sort rc
        boolean sorted = false;
        double temp1;
        String temp2, temp3, temp4, temp5;
        while(!sorted) {
            sorted = true;
            for (int i = 0; i < rc.length - 1; i++) {
                if (rc[i] > rc[i+1]) {
//                    rc
                    temp1 = rc[i];
                    rc[i] = rc[i+1];
                    rc[i+1] = temp1;

//                    data id
                    temp2 = sData[i][0];
                    sData[i][0] = sData[i+1][0];
                    sData[i+1][0] = temp2;

//                    data nama
                    temp3 = sData[i][1];
                    sData[i][1] = sData[i+1][1];
                    sData[i+1][1] = temp3;

//                    data jenis
                    temp4 = sData[i][2];
                    sData[i][2] = sData[i+1][2];
                    sData[i+1][2] = temp4;

//                    data status
                    temp5 = sData[i][3];
                    sData[i][3] = sData[i+1][3];
                    sData[i+1][3] = temp5;

                    sorted = false;
                }
            }
        }

        getList(rc.length, sData, context, tableLayout);

    }

    void getList(int nData, final String[][] sData, final Context context, TableLayout tableLayout) {
        for (int i = 0; i < nData; i++) {
            // menyiapkan TableRow untuk ditampilkan ke TableView
            TableRow row = new TableRow(context);
            // menyiapkan parameter view
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            // mengubah warna untuk baris berbeda
            // untuk baris ganjil
            if (i % 2 == 1) {
                row.setBackgroundColor(Color.parseColor("#9effd3"));
            } else {
                // untuk baris genap
                row.setBackgroundColor(Color.parseColor("#69d1a2"));
            }
            // menyiapkan data pada tiap kolom
            String[] colText = {sData[i][0]+"", sData[i][1], sData[i][2], sData[i][3]};
            // memulai iterasi untuk menambahkan kolom
            for (String text : colText) {
                TextView tv = new TextView(context);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(16);
                tv.setPadding(5, 5, 5, 5);
                tv.setText(text);
                row.addView(tv);
                // menambahkan listener pada baris
                final int ii = i;
                tv.setOnClickListener(new View.OnClickListener() {
                    private Context context;

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(this.context, String.valueOf(sData[ii][0]), Toast.LENGTH_SHORT).show();
                        // beralih halaman detail pesanan
                        this.context.startActivity(new Intent(context, DetailPesanan.class).putExtra("idOrder", sData[ii][0]));
                    }
                });
            }
            // menambahkan baris yang telah disiapkan
            tableLayout.addView(row);

        }
    }

}

