package com.rexqueen.dindajayaapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rexqueen.dindajayaapp.model.DBHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailPesanan extends AppCompatActivity {

    // inisiasi variabel
    TextView id, laki, perempuan, seragam, atasan, bawahan, nama, anak, dewasa, xtra, sbadan, terusan, lpanjang, lpendek,
            cpanjang, cpendek, rpanjang, rpendek, jumlah, hp, tglPesan, tglSelesai, tglAmbil, keterangan, harga, status;
    Button selesai, ambil, diproses, ambilFoto;
    ImageView foto;
    DBHelper dbHelper;
    String[] data;
    int getExtra;
    Intent intent;
    String query, tanggal;
    static final int REQUEST_TAKE_PHOTO = 1;
    String currentPhotoPath;
    int data_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pesanan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // inisiasi variabel
        // inisiasi class DBHelper
        dbHelper = new DBHelper(DetailPesanan.this);

        //inisiasi array untuk menyimpan data dari database
        data = new String[28];

        // mengambil nilai dari textview
        id = findViewById(R.id.idPesan);
        laki = findViewById(R.id.slaki);
        perempuan = findViewById(R.id.sperempuan);
        seragam = findViewById(R.id.sseragam);
        atasan = findViewById(R.id.satasan);
        bawahan = findViewById(R.id.sbawahan);
        nama = findViewById(R.id.snama);
        anak = findViewById(R.id.sanak);
        dewasa = findViewById(R.id.sdewasa);
        xtra = findViewById(R.id.sxtra);
        sbadan = findViewById(R.id.ssbadan);
        terusan = findViewById(R.id.sterusan);
        lpanjang = findViewById(R.id.slpanjang);
        lpendek = findViewById(R.id.slpendek);
        cpanjang = findViewById(R.id.scpanjang);
        cpendek = findViewById(R.id.scpendek);
        rpanjang = findViewById(R.id.srpanjang);
        rpendek = findViewById(R.id.srpendek);
        jumlah = findViewById(R.id.sjumlah);
        hp = findViewById(R.id.shp);
        tglPesan = findViewById(R.id.stglPesan);
        tglSelesai = findViewById(R.id.stglSelesai);
        tglAmbil = findViewById(R.id.stglDiambil);
        keterangan = findViewById(R.id.sketerangan);
        harga = findViewById(R.id.sbiaya);
        status = findViewById(R.id.sstatus);

        keterangan.setEnabled(false);

        // mengambil nilai dari button
        selesai = findViewById(R.id.selesai_bt);
        ambil = findViewById(R.id.diambil_bt);
        diproses = findViewById(R.id.proses_bt);
        ambilFoto = findViewById(R.id.foto_bt);

        // mengambil nilai dari imageview
        foto = findViewById(R.id.foto);

        // menambahkan listener pada floating button
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // ketika diklik maka akan beralih ke tammpilan Home
                startActivity(new Intent(DetailPesanan.this, Home.class));
            }
        });

        // mendapatkan nilai variabel extra dari intent
        Bundle extras = getIntent().getExtras();
        // mengecek apakah extra tidak kosong
        if (extras != null) {
            // mendapatkan nilai extra dari intent dengan kata kunci "page"
            getExtra = Integer.parseInt(extras.getString("idOrder"));
            query = "SELECT * FROM orders where idPesan = "+getExtra;
        }

        // Mengambil database untuk dibaca
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Memulai transaksi dengan database
        db.beginTransaction();

        // mencoba menjalankan query
        try {
            // menyiapkan query
            String selectQuery = query;
            // menyiapkan cursor untuk membaca nilai
            Cursor cursor = db.rawQuery(selectQuery, null);
            // jika jumlah baris lebih dari 0
            if (cursor.getCount() != 0) {
                // memulai iterasi untuk mengambil data perbaris
                if (cursor.moveToFirst()) {
                    // mengambil nilai perkolom
                    data_id = cursor.getInt(cursor.getColumnIndex("idPesan"));
                    data[0] = String.valueOf(data_id);
                    for (int i = 1; i < 27; i++) {
                        data[i] = cursor.getString(i);
                        System.out.println("data "+i+" : "+data[i]);
                    }

//                    konversi nilai
                    // konversi nilai status
                    if (data[10].equals("1")) {
                        data[10] = "Menunggu";
                    } else
                    if (data[10].equals("2")) {
                        data[10] = "Diproses";
                    } else
                    if (data[10].equals("3")) {
                        data[10] = "Selesai";
                    } else
                    if (data[10].equals("4")) {
                        data[10] = "Diambil";
                    }

                    // konversi nilai jenis
                    if (data[2].equals("1")) {
                        data[2] = "Seragam";
                    } else
                    if (data[2].equals("2")) {
                        data[2] = "Atasan";
                    } else
                    if (data[2].equals("3")) {
                        data[2] = "Bawahan";
                    }

                    // konversi nilai ukuran
                    if (data[3].equals("1")) {
                        data[3] = "Anak-anak";
                    } else
                    if (data[3].equals("2")) {
                        data[3] = "Dewasa";
                    } else
                    if (data[3].equals("3")) {
                        data[3] = "Extra";
                    }

//                    "idPesan integer primary key AUTOINCREMENT, " + //data[0]
//                            "nama text null, " + //data[1]
//                            "noHp text null, " + //data[2]
//                            "tglPesan text null, " + //data[3]
//                            "blnPesan text null,"+ //data[4]
//                            "tglSelesai text null, " + //data[5]
//                            "tglAmbil text null, " + //data[6]
//                            "keterangan text null, " + //data[7]
//                            "biaya text null, " + //data[8]
//                            "urlFoto text null, " + //data[9]
//                            "status integer null, "+ //data[10]
//
//                            "laki integer null, " + //data[11]
//                            "perempuan integer null, " + //data[12]
//                            "seragam integer null, " + //data[13]
//                            "atasan integer null, " + //data[14]
//                            "bawahan integer null, " + //data[15]
//                            "sbadan integer null, " + //data[16]
//                            "terusan integer null, " + //data[17]
//                            "lpanjang integer null, " + //data[18]
//                            "lpendek integer null, " + //data[19]
//                            "cpanjang integer null, " + //data[20]
//                            "cpendek integer null, " + //data[21]
//                            "rpanjang integer null, " + //data[22]
//                            "rpendek integer null, " + //data[23]
//                            "anak integer null, " + //data[24]
//                            "dewasa integer null, " + //data[25]
//                            "xtra integer null, " + //data[26]
//                            "jumlah integer null);"; //data[27]

                    // menampilkan data dari array ke textview
                    id.setText(data[0]);
                    laki.setText(data[11]);
                    perempuan.setText(data[12]);
                    seragam.setText(data[13]);
                    atasan.setText(data[14]);
                    bawahan.setText(data[15]);
                    nama.setText(data[1]);
                    anak.setText(data[24]);
                    dewasa.setText(data[25]);
                    xtra.setText(data[26]);
                    sbadan.setText(data[16]);
                    terusan.setText(data[17]);
                    lpanjang.setText(data[18]);
                    lpendek.setText(data[19]);
                    cpanjang.setText(data[20]);
                    cpendek.setText(data[21]);
                    rpanjang.setText(data[22]);
                    rpendek.setText(data[23]);
                    jumlah.setText(data[27]);
                    hp.setText(data[2]);
                    tglPesan.setText(data[3]);
                    tglSelesai.setText(data[5]);
                    tglAmbil.setText(data[6]);
                    keterangan.setText(data[7]);
                    harga.setText(data[8]);
                    status.setText(data[10]);

                    // cek status untuk menampilkan tombol yang sesuai
                    // status menunggu
                    if (data[10].equals("Menunggu")) {
                        diproses.setVisibility(View.VISIBLE);
                        selesai.setVisibility(View.GONE);
                        ambilFoto.setVisibility(View.GONE);
                        ambil.setVisibility(View.GONE);
                    } else
                        // status diproses
                        if (data[10].equals("Diproses")) {
                            diproses.setVisibility(View.GONE);
                            selesai.setVisibility(View.VISIBLE);
                            ambilFoto.setVisibility(View.GONE);
                            ambil.setVisibility(View.GONE);
                        } else
                            // status selesai
                            if (data[10].equals("Selesai")) {
                                diproses.setVisibility(View.GONE);
                                selesai.setVisibility(View.GONE);
                                ambilFoto.setVisibility(View.VISIBLE);
                                ambil.setVisibility(View.VISIBLE);
                            } else {
                                // status diambil
                                diproses.setVisibility(View.GONE);
                                selesai.setVisibility(View.GONE);
                                ambilFoto.setVisibility(View.GONE);
                                ambil.setVisibility(View.GONE);
                            }

                    // menambahkan listener untuk tombol diproses
                    diproses.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setStatus(Integer.parseInt(data[0]), "2");
                            startActivity(new Intent(DetailPesanan.this, Home.class));
                            finish();
                        }
                    });

                    // menambahkan listener untuk tombol selesai
                    selesai.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date());
                            setDate(Integer.parseInt(data[0]), "tglSelesai", date);
                            setStatus(Integer.parseInt(data[0]), "3");
                            startActivity(new Intent(DetailPesanan.this, Home.class));
                            finish();
                        }
                    });

                    // menambahkan listener untuk tombol ambilFoto
                    ambilFoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            // Ensure that there's a camera activity to handle the intent
                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                // Create the File where the photo should go
                                File photoFile = null;
                                try {
                                    photoFile = createImageFile();
                                } catch (IOException ex) {
                                    // Error occurred while creating the File
                                    Toast.makeText(DetailPesanan.this, "Gagal Membuat File", Toast.LENGTH_SHORT).show();
                                }
                                // Continue only if the File was successfully created
                                if (photoFile != null) {
                                    Uri photoURI = FileProvider.getUriForFile(DetailPesanan.this,
                                            "com.rexqueen.dindajayaapp.fileprovider",
                                            photoFile);
                                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                                }
                            }
                        }
                    });

                    // menambahkan listener untuk tombol ambil
                    ambil.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String date = new SimpleDateFormat("dd-MM-yyyy", Locale.US).format(new Date());
                            setDate(Integer.parseInt(data[0]), "tglAmbil", date);
                            setStatus(Integer.parseInt(data[0]), "4");
                            startActivity(new Intent(DetailPesanan.this, Home.class));
                            finish();
                        }
                    });

                    // cek foto
                    if (data[9].length() > 1) {
                        currentPhotoPath = data[9];
                        setPic();
                    } else {
                        foto.setImageResource(R.drawable.default_img);
                    }

                }

            }
        } catch (Exception e) {
            System.out.println(e);
            Toast.makeText(DetailPesanan.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
        }
        finally
        {
            // Mengakhiri transaksi dengan database
            db.endTransaction();
            // dan menutup koneksi dengan database untuk menghemat resource yang digunakan
            db.close();
        }
    }

    private void setStatus(int idPesan, String status) {
        ContentValues cv = new ContentValues();
        cv.put("status", status);
        // Mengambil database untuk dibaca
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.update("orders", cv, "idPesan="+idPesan, null);
            Toast.makeText(DetailPesanan.this, "Berhasil memperbarui status", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(DetailPesanan.this, "Berhasil memperbarui status", Toast.LENGTH_SHORT).show();
        }

    }

    private void setDate(int idPesan, String colName, String tanggal) {
        ContentValues cv = new ContentValues();
        cv.put(colName, tanggal);
        // Mengambil database untuk dibaca
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.update("orders", cv, "idPesan="+idPesan, null);
            Toast.makeText(DetailPesanan.this, "Berhasil memperbarui tanggal", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(DetailPesanan.this, "Berhasil memperbarui tanggal", Toast.LENGTH_SHORT).show();
        }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "DINDA_JAYA_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get(MediaStore.EXTRA_OUTPUT);
//            foto.setImageBitmap(imageBitmap);
            setPic();
            setUriFoto(getExtra, currentPhotoPath);
            galleryAddPic();
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
//        // Get the dimensions of the View
//        int targetW = foto.getWidth();
//        int targetH = foto.getHeight();
//
//        // Get the dimensions of the bitmap
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//
//        int photoW = bmOptions.outWidth;
//        int photoH = bmOptions.outHeight;
//
//        // Determine how much to scale down the image
////        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
//        int scaleFactor = photoH/targetH;
//
//        // Decode the image file into a Bitmap sized to fill the View
//        bmOptions.inJustDecodeBounds = false;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath);
        Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 300, 400, false);
        foto.setImageBitmap(scaled);
    }



    private void setUriFoto(int idPesan, String uri){
        ContentValues cv = new ContentValues();
        cv.put("urlFoto", uri);

        // Mengambil database untuk dibaca
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.update("orders", cv, "idPesan="+idPesan, null);
            status.setText(uri);
        } catch (Exception e) {
            Toast.makeText(DetailPesanan.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {;
//            try {
//                Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoPath);
//                currentPhotoPath = String.valueOf(photoPath);
//                newPath = currentPhotoPath.replace("com.rexqueen.dindajayaapp/my_images/Pictures/", "com.android.externalstorage.documents/document/primary#3AAndroid%2Fdata%2Fcom.rexqueen.dindajayaapp%2Ffiles%2FPictures%2F");
//                setPic(currentPhotoPath);
//                System.out.println("Photo Path : "+currentPhotoPath);
//                foto.setVisibility(View.VISIBLE);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            //tampilkan foto ke galeri
//            galleryAddPic();
//
//            // memasukkan url foto
//            setUriFoto(data_id, currentPhotoPath);
//        }
//    }
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "DJA_IMG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        currentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//
//    private void galleryAddPic() {
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(currentPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        this.sendBroadcast(mediaScanIntent);
//    }
//
//    private void setPic(String path) {
//        // Get the dimensions of the View
//        int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
//        int targetW = (int) (screenWidth - (screenWidth * 0.1));
//        int targetH = (int) (screenWidth - (screenWidth * 0.1));
//
//        // Get the dimensions of the bitmap
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//
//        int photoW = bmOptions.outWidth;
//        int photoH = bmOptions.outHeight;
//
//        // Determine how much to scale down the image
//        int scaleFactor = 4;
//
//        // Decode the image file into a Bitmap sized to fill the View
//        bmOptions.inJustDecodeBounds = false;
//        bmOptions.inSampleSize = scaleFactor;
//
//        File image = new File(path);
//
//        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
//        foto.setImageBitmap(bitmap);
//    }

}
