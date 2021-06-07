package com.rexqueen.dindajayaapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.rexqueen.dindajayaapp.model.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static androidx.appcompat.app.AlertDialog.*;

public class AddOrders extends AppCompatActivity {

    // inisiasi class DBHelper
    DBHelper dbHelper;

    //inisiasi variabel dari View
    EditText nama, keterangan, noHp, biaya;
    CheckBox laki, perempuan, seragam, atasan, bawahan, anak, dewasa, xtra, ml0, ml1, ml2, ml3, ml4, ml5, ml6, ml7;
    Button tanggal_bt, pesanan_bt, kembali;
    TextView tgl, jumlah, jlaki, jperem, jseragam, jatasan, jbawahan, jsbadan, jterusan, jlpanjang, jlpendek, jcpanjang, jcpendek, jrpanjang, jrpendek, janak, jdewasa, jxtra, total;
    String tanggal, tbulan, tag="DATA VALIDATOR";
    LinearLayout jka, jkb, jnsa, jnsb, jnsc, m0, m1, m2, m3, m4, m5, m6, m7, j0, j1, j2, j3, j4, j5, j6, j7, u1, u2, u3;
    int valid=0, jns, ukuran, jum, jumlaki, jumperem, jumseragam, jumatasan, jumbawahan, jumsbadan, jumterusan, jumlpanjang, jumlpendek, jumcpanjang, jumcpendek, jumrpanjang, jumrpendek, jumanak, jumdewasa, jumxtra;



    // inisiasi variabel pendukung
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat dateFormatterMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_orders);

        // membuat format tanggal
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        dateFormatterMonth = new SimpleDateFormat("MM-yyyy", Locale.US);
        // inisiasi nilai awal variabel
        jns = 0;
        tanggal = "x";

        // mendapatkan nilai dari view
        // edittext
        nama = findViewById(R.id.nama);
        keterangan = findViewById(R.id.keterangan);
        noHp = findViewById(R.id.hp);
        biaya = findViewById(R.id.biaya);

        // button
        tanggal_bt = findViewById(R.id.tanggal_bt);
        pesanan_bt = findViewById(R.id.addPesanan);
        kembali = findViewById(R.id.kembali);

        // textview
        tgl = findViewById(R.id.tanggal);
        jumlah = findViewById(R.id.jumlah);
        jlaki = findViewById(R.id.jumlahl);
        jperem = findViewById(R.id.jumlahp);
        jseragam = findViewById(R.id.jumlahja);
        jatasan = findViewById(R.id.jumlahjb);
        jbawahan = findViewById(R.id.jumlahjc);
        jsbadan = findViewById(R.id.jumlaha);
        jterusan = findViewById(R.id.jumlahb);
        jlpanjang = findViewById(R.id.jumlahc);
        jlpendek = findViewById(R.id.jumlahd);
        jcpanjang = findViewById(R.id.jumlahe);
        jcpendek = findViewById(R.id.jumlahf);
        jrpanjang = findViewById(R.id.jumlahg);
        jrpendek = findViewById(R.id.jumlahh);
        janak = findViewById(R.id.jumlahua);
        jdewasa = findViewById(R.id.jumlahub);
        jxtra = findViewById(R.id.jumlahuc);
        total = findViewById(R.id.biaya);

        // checkbox button
        laki = findViewById(R.id.laki);
        perempuan = findViewById(R.id.perempuan);
        seragam = findViewById(R.id.seragam);
        atasan = findViewById(R.id.atasan);
        bawahan = findViewById(R.id.bawahan);
        anak = findViewById(R.id.anak);
        dewasa = findViewById(R.id.dewasa);
        xtra = findViewById(R.id.extra);
        ml0 = findViewById(R.id.sbadan);
        ml1 = findViewById(R.id.terusan);
        ml2 = findViewById(R.id.lpanjang);
        ml3 = findViewById(R.id.lpendek);
        ml4 = findViewById(R.id.cpanjang);
        ml5 = findViewById(R.id.cpendek);
        ml6 = findViewById(R.id.rpanjang);
        ml7 = findViewById(R.id.rpendek);

        // linear layout
        // linear checkbox
        m0 = findViewById(R.id.ml0);
        m1 = findViewById(R.id.ml1);
        m2 = findViewById(R.id.ml2);
        m3 = findViewById(R.id.ml3);
        m4 = findViewById(R.id.ml4);
        m5 = findViewById(R.id.ml5);
        m6 = findViewById(R.id.ml6);
        m7 = findViewById(R.id.ml7);

        // linear jumlah
        jka = findViewById(R.id.jka);
        jkb = findViewById(R.id.jkb);
        j0 = findViewById(R.id.m0);
        j1 = findViewById(R.id.m1);
        j2 = findViewById(R.id.m2);
        j3 = findViewById(R.id.m3);
        j4 = findViewById(R.id.m4);
        j5 = findViewById(R.id.m5);
        j6 = findViewById(R.id.m6);
        j7 = findViewById(R.id.m7);
        jnsa = findViewById(R.id.jnsa);
        jnsb = findViewById(R.id.jnsb);
        jnsc = findViewById(R.id.jnsc);
        u1 = findViewById(R.id.ua);
        u2 = findViewById(R.id.ub);
        u3 = findViewById(R.id.uc);

        laki.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getJumlah();
            }
        });
        perempuan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getJumlah();
            }
        });


        // menambahkan listener pada tombol tanggal
        tanggal_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mendapatkan kalender
                Calendar newCalendar = Calendar.getInstance();

                // inisiasi datePickerDialog untuk menampilkan pemilihan tanggal
                datePickerDialog = new DatePickerDialog(AddOrders.this, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        // method ini dijalankan ketika tanggal sudah dipilih
                        // mengambil nilai tanggal yang sudah dipilih
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);

                        // menampilkan nilai tanggal pada textview
                        tgl.setText(dateFormatter.format(newDate.getTime()));
                        // menyimpan nilai tanggal pada variabel tanggal
                        tanggal = dateFormatter.format(newDate.getTime());
                        tbulan = dateFormatterMonth.format(newDate.getTime());
                    }

                },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

                // menampilkan dialog pemilihan tanggal
                datePickerDialog.show();
            }
        });

        // menambahkan listener pada tombol tambah pesanan
        pesanan_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // melakukan pengecekan
                checkNama();
                checkJenisK();
                checkJenisP();
                checkModel();
                checkUkuran();
                checkBiaya();
                checkTanggal();

                if (valid == 0) {
                    Toast.makeText(AddOrders.this, "Silahkan periksa kembali!", Toast.LENGTH_LONG).show();
                } else {
                    // membuat dialog konfirmasi
                    Builder builder = new Builder(AddOrders.this);
                    builder.setTitle("Tambah Pesanan");
                    builder.setMessage("Apakah Anda yakin untuk menambah data?");
                    builder.setCancelable(false);
                    // membuat tombol "Ya" dengan listenernya
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            // mendapatkan database dari class DBHelper
                            dbHelper = new DBHelper(AddOrders.this);
                            SQLiteDatabase db = dbHelper.getWritableDatabase();
                            // lakukan percobaan
                            try {

                                String nm = String.valueOf(nama.getText());
                                String nh = String.valueOf(noHp.getText());
                                String ket = String.valueOf(keterangan.getText());
                                String byy = String.valueOf(biaya.getText());
                                int dda,ddb,ddc,ddd,dde,ddf,ddg,ddh,ddi,ddj,ddk,ddl,ddm, ddn, ddo, ddp;
                                int ddq = Integer.parseInt(jumlah.getText().toString());

                                if (laki.isChecked()) {
                                    dda = Integer.parseInt(jlaki.getText().toString());
                                } else {dda = 0;}
                                if (perempuan.isChecked()) {
                                    ddb = Integer.parseInt(jperem.getText().toString());
                                } else {ddb = 0;}
                                if (seragam.isChecked()) {
                                    ddc = Integer.parseInt(jseragam.getText().toString());
                                } else {ddc = 0;}
                                if (atasan.isChecked()) {
                                    ddd = Integer.parseInt(jatasan.getText().toString());
                                } else {ddd = 0;}
                                if (bawahan.isChecked()) {
                                    dde = Integer.parseInt(jbawahan.getText().toString());
                                } else {dde = 0;}
                                if (ml0.isChecked()) {
                                    ddf = Integer.parseInt(jsbadan.getText().toString());
                                } else {ddf = 0;}
                                if (ml1.isChecked()) {
                                    ddg = Integer.parseInt(jterusan.getText().toString());
                                } else {ddg = 0;}
                                if (ml2.isChecked()) {
                                    ddh = Integer.parseInt(jlpanjang.getText().toString());
                                } else {ddh = 0;}
                                if (ml3.isChecked()) {
                                    ddi = Integer.parseInt(jlpendek.getText().toString());
                                } else {ddi = 0;}
                                if (ml4.isChecked()) {
                                    ddj = Integer.parseInt(jcpanjang.getText().toString());
                                } else {ddj = 0;}
                                if (ml5.isChecked()) {
                                    ddk = Integer.parseInt(jcpendek.getText().toString());
                                } else {ddk = 0;}
                                if (ml6.isChecked()) {
                                    ddl = Integer.parseInt(jrpanjang.getText().toString());
                                } else {ddl = 0;}
                                if (ml7.isChecked()) {
                                    ddm = Integer.parseInt(jrpendek.getText().toString());
                                } else {ddm = 0;}
                                if (anak.isChecked()) {
                                    ddn = Integer.parseInt(janak.getText().toString());
                                } else {ddn = 0;}
                                if (dewasa.isChecked()) {
                                    ddo = Integer.parseInt(jdewasa.getText().toString());
                                } else {ddo = 0;}
                                if (xtra.isChecked()) {
                                    ddp = Integer.parseInt(jxtra.getText().toString());
                                } else {ddp = 0;}

                                if (nh.length() < 1) {
                                    nh = "-";
                                }
                                if (ket.length() < 1) {
                                    ket = "-";
                                }

                                // mengeksekusi query menambahkan data ke database pada tabel "orders"
                                db.execSQL("insert into orders(nama, noHp, tglPesan, blnPesan, tglSelesai, tglAmbil, keterangan, biaya, urlFoto, status, laki, perempuan, seragam, atasan, bawahan, sbadan, terusan, lpanjang, lpendek, cpanjang, cpendek, rpanjang, rpendek, anak, dewasa, xtra, jumlah) " +
                                        "values(" +
                                        "'"+nm+"', " +
                                        "'"+nh+"', " +
                                        "'"+tanggal+"', " +
                                        "'"+tbulan+"', " +
                                        "'-', " +
                                        "'-', " +
                                        "'"+ket+"', " +
                                        "'"+byy+"', " +
                                        "'-', '1'" +
                                        ", '" + dda +
                                        "', '" + ddb +
                                        "', '" + ddc +
                                        "', '" + ddd +
                                        "', '" + dde +
                                        "', '" + ddf +
                                        "', '" + ddg +
                                        "', '" + ddh +
                                        "', '" + ddi +
                                        "', '" + ddj +
                                        "', '" + ddk +
                                        "', '" + ddl +
                                        "', '" + ddm +
                                        "', '" + ddn +
                                        "', '" + ddo +
                                        "', '" + ddp +
                                        "', '" + ddq +
                                        "');");

                                // jika berhasil tampilkan toast
                                Toast.makeText(AddOrders.this, "Berhasil menambahkan pesanan", Toast.LENGTH_SHORT).show();
                                // dan berpindah ke tampilan Home dengan imbuhan variabel extra dengan nilai 1 untuk fragment "home"
                                startActivity(new Intent(AddOrders.this, Home.class).putExtra("page", 1));
                                // tutup activity AddOrders
                                finish();
                                db.close();
                            } catch (Exception e) {
                                // jika gagal tampilkan toast dengan pesan error dari consol
                                System.out.println(e);
                                Toast.makeText(AddOrders.this, "Gagal menambah pesanan, error: "+e, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    // membuat tombol "Tidak" dengan listener
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // jika tombol "Tidak" dipilih, tampilkan toast
                            Toast.makeText(AddOrders.this, "Silahkan periksa data sebelum menyimpan", Toast.LENGTH_SHORT).show();
                        }
                    });

                    // menampilkan dialog konfirmasi
                    builder.show();
                }

            }
        });

        // menambahkan listener pada tombol kembali
        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // beralih ke halaman Home
                startActivity(new Intent(AddOrders.this, Home.class));
                // dan mengakhiri activity AddOrders
                finish();
            }
        });

    }

    void checkNama() {
        try {
            if (nama.getText().length() <= 0){
                nama.setError("Nama tidak boleh kosong!");
            } else {
                Log.d(tag, "checkNama: VALID");
            }
        } catch (Exception e) {}
    }

    void checkJenisK() {
        try {
            int xxx = 9;
            if (laki.isChecked()) {
                xxx = 0;
            } else if (perempuan.isChecked()) {
                xxx = 0;
            } else {
                xxx = 1;
                laki.setError("Jenis kelamin belum pilih!");
            }

            if (xxx == 0) {
                Log.d(tag, "checkJenisK: VALID");
            }
        } catch (Exception e) {}
    }

    void checkJenisP() {
        try {
            int xy = Integer.parseInt(jlaki.getText().toString());
            int xz = Integer.parseInt(jperem.getText().toString());

            int xa = Integer.parseInt(jseragam.getText().toString());
            int xb = Integer.parseInt(jatasan.getText().toString());
            int xc = Integer.parseInt(jbawahan.getText().toString());

            int nx = xy+xz;
            int ny = xa+xb+xc;

            if (seragam.isChecked()) {
                if (nx != ny) {
                    seragam.setError("Jumlah total tidak sesuai!");
                }
            } else if (atasan.isChecked()) {
                if (nx != ny) {
                    atasan.setError("Jumlah total tidak sesuai!");
                }
            } else if (bawahan.isChecked()) {
                if (nx != ny) {
                    bawahan.setError("Jumlah total tidak sesuai!");
                }
            } else {
                seragam.setError("Jenis pakaian belum dipilih!");
            }

            if (nx == ny) {
                Log.d("DATA VALIDATOR", "checkJenisP: VALID");
            }

        } catch (Exception e) {}
    }

    void checkModel() {
        try {
            int xy = Integer.parseInt(jlaki.getText().toString());
            int xz = Integer.parseInt(jperem.getText().toString());

            int xa = Integer.parseInt(jsbadan.getText().toString());
            int xb = Integer.parseInt(jterusan.getText().toString());
            int xc = Integer.parseInt(jlpanjang.getText().toString());
            int xd = Integer.parseInt(jlpendek.getText().toString());
            int xe = Integer.parseInt(jcpanjang.getText().toString());
            int xf = Integer.parseInt(jcpendek.getText().toString());
            int xg = Integer.parseInt(jrpanjang.getText().toString());
            int xh = Integer.parseInt(jrpendek.getText().toString());

            int nx = xy+xz;
            int ny = xa+xb+xc+xd+xe+xf+xg+xh;

            if (ml0.isChecked()) {
                if (nx != ny) {
                    jsbadan.setError("Jumlah tidak sesuai, silahkan periksa!");
                    valid = 0;
                }
            } else if (ml1.isChecked()) {
                if (nx != ny) {
                    jterusan.setError("jumlah tidak sesuai, silahkan periksa!");
                    valid = 0;
                }
            } else if (ml2.isChecked()) {
                if (nx != ny) {
                    jlpanjang.setError("jumlah tidak sesuai, silahkan periksa!");
                    valid = 0;
                }
            } else if (ml3.isChecked()) {
                if (nx != ny) {
                    jlpendek.setError("jumlah tidak sesuai, silahkan periksa!");
                    valid = 0;
                }
            } else if (ml4.isChecked()) {
                if (nx != ny) {
                    jcpanjang.setError("jumlah tidak sesuai, silahkan periksa!");
                    valid = 0;
                }
            } else if (ml5.isChecked()) {
                if (nx != ny) {
                    jcpendek.setError("jumlah tidak sesuai, silahkan periksa!");
                    valid = 0;
                }
            } else if (ml6.isChecked()) {
                if (nx != ny) {
                    jrpanjang.setError("jumlah tidak sesuai, silahkan periksa!");
                    valid = 0;
                }
            } else if (ml7.isChecked()) {
                if (nx != ny) {
                    jrpendek.setError("jumlah tidak sesuai, silahkan periksa!");
                    valid = 0;
                }
            } else {
                ml0.setError("Model belum dipilih");
                valid = 0;
            }

            if (nx == ny) {
                Log.d(tag, "checkModel: VALID");
                valid = 1;
            }
        } catch (Exception e) {}
    }

    void checkUkuran() {
        try {
            int xy = Integer.parseInt(jlaki.getText().toString());
            int xz = Integer.parseInt(jperem.getText().toString());

            int xa = Integer.parseInt(janak.getText().toString());
            int xb = Integer.parseInt(jdewasa.getText().toString());
            int xc = Integer.parseInt(jxtra.getText().toString());

            int nx = xy+xz;
            int ny = xa+xb+xc;

            if (anak.isChecked()) {
                if (nx != ny) {
                    janak.setError("Jumlah tidak sesuai, silahkan periksa!");
                    valid = 0;
                }
            } else if (dewasa.isChecked()) {
                if (nx != ny) {
                    jdewasa.setError("Jumlah tidak sesuai, silahkan periksa!");
                    valid = 0;
                }
            } else if (xtra.isChecked()) {
                if (nx != ny) {
                    jxtra.setError("Jumlah tidak sesuai, silahkan periksa!");
                    valid = 0;
                }
            } else {
                anak.setError("Ukuran belum dipilih!");
                valid = 0;
            }

            if (nx == ny) {
                Log.d(tag, "checkUkuran: VALID");
                valid = 1;
            }
        } catch (Exception e) {}
    }

    void checkBiaya() {
        try {
            String by = String.valueOf(biaya.getText());
            if (by.length()<1) {
                biaya.setError("Biaya kosong!");
                valid = 0;
            } else {
                Log.d(tag, "checkBiaya: VALID");
                valid = 1;
            }
        } catch (Exception e) {}
    }

    void checkTanggal() {
        try {
            String tg = String.valueOf(tgl.getText());
            if (tg.length()<1) {
                tgl.setError("Tanggal belum dipilih!");
                valid = 0;
            } else {
                Log.d(tag, "checkTanggal: VALID");
                valid = 1;
            }
        } catch (Exception e) {}
    }

    public void getJumlah(){
        try {
            if (laki.isChecked()) {
            jumlaki = Integer.parseInt(jlaki.getText().toString());
            } else {
                jumlaki = 0;
            }
            if (perempuan.isChecked()) {
            jumperem = Integer.parseInt(jperem.getText().toString());
            } else {
                jumperem = 0;
            }

            jum = jumlaki+jumperem;
            jumlah.setText(String.valueOf(jum));
        } catch (Exception e){
            Toast.makeText(this.getApplicationContext(), "Error: "+e, Toast.LENGTH_SHORT).show();
        }
    }

//    public void jenisClick(View view) {
//        if (seragam.isChecked()){
//            ml0.setVisibility(View.VISIBLE);
//            ml1.setVisibility(View.VISIBLE);
//            ml2.setVisibility(View.VISIBLE);
//            ml3.setVisibility(View.VISIBLE);
//        } else {
//            ml0.setVisibility(View.INVISIBLE);
//            ml1.setVisibility(View.INVISIBLE);
//            ml2.setVisibility(View.INVISIBLE);
//            ml3.setVisibility(View.INVISIBLE);
//        }
//
//        if (atasan.isChecked()){
//            ml0.setVisibility(View.VISIBLE);
//            ml1.setVisibility(View.VISIBLE);
//            ml2.setVisibility(View.VISIBLE);
//            ml3.setVisibility(View.VISIBLE);
//        } else {
//            ml0.setVisibility(View.INVISIBLE);
//            ml1.setVisibility(View.INVISIBLE);
//            ml2.setVisibility(View.INVISIBLE);
//            ml3.setVisibility(View.INVISIBLE);
//        }
//
//        if (bawahan.isChecked()){
//            ml4.setVisibility(View.VISIBLE);
//            ml5.setVisibility(View.VISIBLE);
//            ml6.setVisibility(View.VISIBLE);
//            ml7.setVisibility(View.VISIBLE);
//        } else {
//            ml4.setVisibility(View.INVISIBLE);
//            ml5.setVisibility(View.INVISIBLE);
//            ml6.setVisibility(View.INVISIBLE);
//            ml7.setVisibility(View.INVISIBLE);
//        }
//    }


    public void decrementl(View view) {
        jumlaki = Integer.parseInt(jlaki.getText().toString());
        if (jumlaki > 1) {
            jumlaki --;
        } else {
            Toast.makeText(getApplicationContext(), "Jumlah minimal 1!", Toast.LENGTH_SHORT).show();
        }
        jlaki.setText(String.valueOf(jumlaki));
        getJumlah();
    }

    public void incrementl(View view) {
        jumlaki = Integer.parseInt(jlaki.getText().toString());
        jumlaki ++;
        jlaki.setText(String.valueOf(jumlaki));
        getJumlah();
    }

    public void decrementp(View view) {
        jumperem = Integer.parseInt(jperem.getText().toString());
        if (jumperem > 1) {
            jumperem --;
        } else {
            Toast.makeText(getApplicationContext(), "Jumlah minimal 1!", Toast.LENGTH_SHORT).show();
        }
        jperem.setText(String.valueOf(jumperem));
        getJumlah();
    }

    public void incrementp(View view) {
        jumperem = Integer.parseInt(jperem.getText().toString());
        jumperem ++;
        jperem.setText(String.valueOf(jumperem));
        getJumlah();
    }

    public void decrementja(View view) {
        jumseragam = Integer.parseInt(jseragam.getText().toString());
        if (jumseragam > 1) {
            jumseragam --;
        } else {
            Toast.makeText(getApplicationContext(), "Jumlah minimal 1!", Toast.LENGTH_SHORT).show();
        }
        jseragam.setText(String.valueOf(jumseragam));
    }

    public void incrementja(View view) {
        jumseragam = Integer.parseInt(jseragam.getText().toString());
        jumseragam ++;
        jseragam.setText(String.valueOf(jumseragam));
    }

    public void decrementjb(View view) {
        jumatasan = Integer.parseInt(jatasan.getText().toString());
        if (jumatasan > 1) {
            jumatasan --;
        } else {
            Toast.makeText(getApplicationContext(), "Jumlah minimal 1!", Toast.LENGTH_SHORT).show();
        }
        jatasan.setText(String.valueOf(jumatasan));
    }

    public void incrementjb(View view) {
        jumatasan = Integer.parseInt(jatasan.getText().toString());
        jumatasan ++;
        jatasan.setText(String.valueOf(jumatasan));
    }

    public void decrementjc(View view) {
        jumbawahan = Integer.parseInt(jbawahan.getText().toString());
        if (jumbawahan > 1) {
            jumbawahan --;
        } else {
            Toast.makeText(getApplicationContext(), "Jumlah minimal 1!", Toast.LENGTH_SHORT).show();
        }
        jbawahan.setText(String.valueOf(jumbawahan));
    }

    public void incrementjc(View view) {
        jumbawahan = Integer.parseInt(jbawahan.getText().toString());
        jumbawahan ++;
        jbawahan.setText(String.valueOf(jumbawahan));
    }

    public void decrementa(View view) {
        jumsbadan = Integer.parseInt(jsbadan.getText().toString());
        if (jumsbadan > 1) {
            jumsbadan --;
        } else {
            Toast.makeText(getApplicationContext(), "Jumlah minimal 1!", Toast.LENGTH_SHORT).show();
        }
        jsbadan.setText(String.valueOf(jumsbadan));
    }

    public void incrementa(View view) {
        jumsbadan = Integer.parseInt(jsbadan.getText().toString());
        jumsbadan ++;
        jsbadan.setText(String.valueOf(jumsbadan));
    }

    public void decrementb(View view) {
        jumterusan = Integer.parseInt(jterusan.getText().toString());
        if (jumterusan > 1) {
            jumterusan --;
        } else {
            Toast.makeText(getApplicationContext(), "Jumlah minimal 1!", Toast.LENGTH_SHORT).show();
        }
        jterusan.setText(String.valueOf(jumterusan));
    }

    public void incrementb(View view) {
        jumterusan = Integer.parseInt(jterusan.getText().toString());
        jumterusan ++;
        jterusan.setText(String.valueOf(jumterusan));
    }

    public void decrementc(View view) {
        jumlpanjang = Integer.parseInt(jlpanjang.getText().toString());
        if (jumlpanjang > 1) {
            jumlpanjang --;
        } else {
            Toast.makeText(getApplicationContext(), "Jumlah minimal 1!", Toast.LENGTH_SHORT).show();
        }
        jlpanjang.setText(String.valueOf(jumlpanjang));
    }

    public void incrementc(View view) {
        jumlpanjang = Integer.parseInt(jlpanjang.getText().toString());
        jumlpanjang ++;
        jlpanjang.setText(String.valueOf(jumlpanjang));
    }

    public void decrementd(View view) {
        jumlpendek = Integer.parseInt(jlpendek.getText().toString());
        if (jumlpendek > 1) {
            jumlpendek --;
        } else {
            Toast.makeText(getApplicationContext(), "Jumlah minimal 1!", Toast.LENGTH_SHORT).show();
        }
        jlpendek.setText(String.valueOf(jumlpendek));
    }

    public void incrementd(View view) {
        jumlpendek = Integer.parseInt(jlpendek.getText().toString());
        jumlpendek ++;
        jlpendek.setText(String.valueOf(jumlpendek));
    }

    public void decremente(View view) {
        jumcpanjang = Integer.parseInt(jcpanjang.getText().toString());
        if (jumcpanjang > 1) {
            jumcpanjang --;
        } else {
            Toast.makeText(getApplicationContext(), "Jumlah minimal 1!", Toast.LENGTH_SHORT).show();
        }
        jcpanjang.setText(String.valueOf(jumcpanjang));
    }

    public void incremente(View view) {
        jumcpanjang = Integer.parseInt(jcpanjang.getText().toString());
        jumcpanjang ++;
        jcpanjang.setText(String.valueOf(jumcpanjang));
    }

    public void decrementf(View view) {
        jumcpendek = Integer.parseInt(jcpendek.getText().toString());
        if (jumcpendek > 1) {
            jumcpendek --;
        } else {
            Toast.makeText(getApplicationContext(), "Jumlah minimal 1!", Toast.LENGTH_SHORT).show();
        }
        jcpendek.setText(String.valueOf(jumcpendek));
    }

    public void incrementf(View view) {
        jumcpendek = Integer.parseInt(jcpendek.getText().toString());
        jumcpendek ++;
        jcpendek.setText(String.valueOf(jumcpendek));
    }

    public void decrementg(View view) {
        jumrpanjang = Integer.parseInt(jrpanjang.getText().toString());
        if (jumrpanjang > 1) {
            jumrpanjang --;
        } else {
            Toast.makeText(getApplicationContext(), "Jumlah minimal 1!", Toast.LENGTH_SHORT).show();
        }
        jrpanjang.setText(String.valueOf(jumrpanjang));
    }

    public void incrementg(View view) {
        jumrpanjang = Integer.parseInt(jrpanjang.getText().toString());
        jumrpanjang ++;
        jrpanjang.setText(String.valueOf(jumrpanjang));
    }

    public void decrementh(View view) {
        jumrpendek = Integer.parseInt(jrpendek.getText().toString());
        if (jumrpendek > 1) {
            jumrpendek --;
        } else {
            Toast.makeText(getApplicationContext(), "Jumlah minimal 1!", Toast.LENGTH_SHORT).show();
        }
        jrpendek.setText(String.valueOf(jumrpendek));
    }

    public void incrementh(View view) {
        jumrpendek = Integer.parseInt(jrpendek.getText().toString());
        jumrpendek ++;
        jrpendek.setText(String.valueOf(jumrpendek));
    }

    public void decrementua(View view) {
        jumanak = Integer.parseInt(janak.getText().toString());
        if (jumanak > 1) {
            jumanak --;
        } else {
            Toast.makeText(getApplicationContext(), "Jumlah minimal 1!", Toast.LENGTH_SHORT).show();
        }
        janak.setText(String.valueOf(jumanak));
    }

    public void incrementua(View view) {
        jumanak = Integer.parseInt(janak.getText().toString());
        jumanak ++;
        janak.setText(String.valueOf(jumanak));
    }

    public void decrementub(View view) {
        jumdewasa = Integer.parseInt(jdewasa.getText().toString());
        if (jumdewasa > 1) {
            jumdewasa --;
        } else {
            Toast.makeText(getApplicationContext(), "Jumlah minimal 1!", Toast.LENGTH_SHORT).show();
        }
        jdewasa.setText(String.valueOf(jumdewasa));
    }

    public void incrementub(View view) {
        jumdewasa = Integer.parseInt(jdewasa.getText().toString());
        jumdewasa ++;
        jdewasa.setText(String.valueOf(jumdewasa));
    }

    public void decrementuc(View view) {
        jumxtra = Integer.parseInt(jxtra.getText().toString());
        if (jumxtra > 1) {
            jumxtra --;
        } else {
            Toast.makeText(getApplicationContext(), "Jumlah minimal 1!", Toast.LENGTH_SHORT).show();
        }
        jxtra.setText(String.valueOf(jumxtra));
    }

    public void incrementuc(View view) {
        jumxtra = Integer.parseInt(jxtra.getText().toString());
        jumxtra ++;
        jxtra.setText(String.valueOf(jumxtra));
    }

}
