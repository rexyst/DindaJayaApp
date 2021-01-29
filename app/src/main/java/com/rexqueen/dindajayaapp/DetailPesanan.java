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
    TextView id, nama, jenis, ukuran, jumlah, hp, tglPesan, tglSelesai, tglAmbil, keterangan, harga, total, status;
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
        data = new String[14];

        // mengambil nilai dari textview
        id = findViewById(R.id.idPesan);
        nama = findViewById(R.id.nama);
        jenis = findViewById(R.id.jenis);
        ukuran = findViewById(R.id.ukuran);
        jumlah = findViewById(R.id.jumlah);
        hp = findViewById(R.id.hp);
        tglPesan = findViewById(R.id.tglPesan);
        tglSelesai = findViewById(R.id.tglSelesai);
        tglAmbil = findViewById(R.id.tglDiambil);
        keterangan = findViewById(R.id.keterangan);
        harga = findViewById(R.id.harga);
        total = findViewById(R.id.total);
        status = findViewById(R.id.status);

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
                    for (int i = 1; i < 14; i++) {
                        data[i] = cursor.getString(i);
                    }

//                    konversi nilai
                    // konversi nilai status
                    if (data[13].equals("1")) {
                        data[13] = "Menunggu";
                    } else
                    if (data[13].equals("2")) {
                        data[13] = "Diproses";
                    } else
                    if (data[13].equals("3")) {
                        data[13] = "Selesai";
                    } else
                    if (data[13].equals("4")) {
                        data[13] = "Diambil";
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

                    // menampilkan data dari array ke textview
                    id.setText(data[0]);
                    nama.setText(data[1]);
                    jenis.setText(data[2]);
                    ukuran.setText(data[3]);
                    jumlah.setText(data[4]);
                    hp.setText(data[5]);
                    tglPesan.setText(data[6]);
                    tglSelesai.setText(data[7]);
                    tglAmbil.setText(data[8]);
                    keterangan.setText(data[9]);
                    harga.setText(data[10]);
                    total.setText(data[11]);
                    status.setText(data[13]);

                    // cek status untuk menampilkan tombol yang sesuai
                    // status menunggu
                    if (data[13].equals("Menunggu")) {
                        diproses.setVisibility(View.VISIBLE);
                        selesai.setVisibility(View.GONE);
                        ambilFoto.setVisibility(View.GONE);
                        ambil.setVisibility(View.GONE);
                    } else
                        // status diproses
                        if (data[13].equals("Diproses")) {
                            diproses.setVisibility(View.GONE);
                            selesai.setVisibility(View.VISIBLE);
                            ambilFoto.setVisibility(View.GONE);
                            ambil.setVisibility(View.GONE);
                        } else
                            // status selesai
                            if (data[13].equals("Selesai")) {
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
                    if (data[12].length() > 1) {
                        currentPhotoPath = data[12];
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
