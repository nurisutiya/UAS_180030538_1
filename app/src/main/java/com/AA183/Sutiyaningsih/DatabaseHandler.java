package com.AA183.Sutiyaningsih;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "db_mahasiswa77";
    private final static String TABLE_MAHASISWA77 = "t_mahasiswa";
    private final static String KEY_ID_MAHASISWA = "ID_Mahasiswa";
    private final static String KEY_NAMA_MAHASISWA = "Nama_Mahasiswa";
    private final static String KEY_TGL = "Tanggal";
    private final static String KEY_GAMBAR = "Gambar";
    private final static String KEY_NAMA_PANGGILAN = "Nama_Panggilan";
    private final static String KEY_NIM = "Nim";
    private final static String KEY_JURUSAN = "Jurusan";
    private SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private Context context;

    public DatabaseHandler (Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = ctx;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_MAHASISWA = "CREATE TABLE " + TABLE_MAHASISWA77
                + "(" + KEY_ID_MAHASISWA + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAMA_MAHASISWA + " TEXT, " + KEY_TGL + " DATE, "
                + KEY_GAMBAR + " TEXT, " + KEY_NAMA_PANGGILAN + " TEXT, "
                + KEY_NIM + " TEXT, " + KEY_JURUSAN + " TEXT);";

        db.execSQL(CREATE_TABLE_MAHASISWA);
        inialisasiAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_MAHASISWA77;
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }

    public void tambahMahasiswa (Mahasiswa dataMahasiswa){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_NAMA_MAHASISWA, dataMahasiswa.getNamamahasiswa());
        cv.put(KEY_TGL, sdFormat.format(dataMahasiswa.getTanggal()));
        cv.put(KEY_GAMBAR, dataMahasiswa.getGambar());
        cv.put(KEY_NAMA_PANGGILAN, dataMahasiswa.getNamapanggilan());
        cv.put(KEY_NIM, dataMahasiswa.getNim());
        cv.put(KEY_JURUSAN, dataMahasiswa.getJurusan());

        db.insert(TABLE_MAHASISWA77, null, cv);
        db.close();
    }

    public void tambahMahasiswa (Mahasiswa dataMahasiswa, SQLiteDatabase db){
        ContentValues cv = new ContentValues();

        cv.put(KEY_NAMA_MAHASISWA, dataMahasiswa.getNamamahasiswa());
        cv.put(KEY_TGL, sdFormat.format(dataMahasiswa.getTanggal()));
        cv.put(KEY_GAMBAR, dataMahasiswa.getGambar());
        cv.put(KEY_NAMA_PANGGILAN, dataMahasiswa.getNamapanggilan());
        cv.put(KEY_NIM, dataMahasiswa.getNim());
        cv.put(KEY_JURUSAN, dataMahasiswa.getJurusan());

        db.insert(TABLE_MAHASISWA77, null, cv);
    }

    public void editMahasiswa (Mahasiswa dataMahasiswa){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(KEY_TGL, sdFormat.format(dataMahasiswa.getTanggal()));
        cv.put(KEY_GAMBAR, dataMahasiswa.getGambar());
        cv.put(KEY_NAMA_PANGGILAN, dataMahasiswa.getNamapanggilan());
        cv.put(KEY_NIM, dataMahasiswa.getNim());
        cv.put(KEY_JURUSAN, dataMahasiswa.getJurusan());


        db.update(TABLE_MAHASISWA77, cv, KEY_ID_MAHASISWA + "=?", new String[]{String.valueOf(dataMahasiswa.getIdMahasiswa())});
        db.close();
    }

    public void hapusMahasiswa (int idMahasiswa){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_MAHASISWA77, KEY_ID_MAHASISWA + "=?", new String[]{String.valueOf(idMahasiswa)});
        db.close();
    }

    public ArrayList<Mahasiswa> getAllMahasiswa(){
        ArrayList<Mahasiswa> dataBerita = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_MAHASISWA77;
        SQLiteDatabase db = getReadableDatabase();
        Cursor csr =  db.rawQuery(query, null);
        if (csr.moveToFirst()){
            do {
                Date tempDate = new Date();
                try {
                    tempDate = sdFormat.parse(csr.getString(2));
                } catch (ParseException er){
                    er.printStackTrace();
                }

                Mahasiswa tempBerita = new Mahasiswa(
                        csr.getInt ( 0),
                        csr.getString( 1),
                        tempDate,
                        csr.getString( 3),
                        csr.getString( 4),
                        csr.getString( 5),
                        csr.getString( 6)
                );

                dataBerita.add(tempBerita);
            } while (csr.moveToNext());
        }
        return dataBerita;
    }

    private String storeImagesFiles(int id){
        String location;
        Bitmap image = BitmapFactory.decodeResource(context.getResources(), id);
        location = InputActivity.saveImageToInternalStorage(image, context);
        return location;
    }

    private void inialisasiAwal(SQLiteDatabase db){
        int idMahasiswa = 0;
        Date tempDate = new Date();

        try {
            tempDate = sdFormat.parse("13/03/1998");
        }catch (ParseException er){
            er.printStackTrace();
        }



        Mahasiswa list1 = new Mahasiswa(
                idMahasiswa,
                "Mutia Fauzia Aulia",
                tempDate,
                storeImagesFiles(R.drawable.gambar1),
                "Aulia",
                "180070987",
                "Sistem Informasi"
        );

        tambahMahasiswa(list1, db);
        idMahasiswa++;

        try {
            tempDate = sdFormat.parse("23/05/1997");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Mahasiswa list2 = new Mahasiswa(
                idMahasiswa,
                "Galang Satria Prasada",
                tempDate,
                storeImagesFiles(R.drawable.gambar2),
                "Galang",
                "1800050456",
                "Sistem Komputer"
        );

        tambahMahasiswa(list2, db);
        idMahasiswa++;

        try {
            tempDate = sdFormat.parse("25/11/1998");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Mahasiswa list3 = new Mahasiswa(
                idMahasiswa,
                "Aulia itzy",
                tempDate,
                storeImagesFiles(R.drawable.gambar3),
                "itzy",
                "180090678",
                "Sistem Komputer"
        );

        tambahMahasiswa(list3, db);
        idMahasiswa++;

        try {
            tempDate = sdFormat.parse("13/12/1985");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Mahasiswa list4 = new Mahasiswa(
                idMahasiswa,
                "Elang Irfan Ersana",
                tempDate,
                storeImagesFiles(R.drawable.gambar4),
                "Elang",
                "180030432",
                "Sistem Komputer"
        );

        tambahMahasiswa(list4, db);
        idMahasiswa++;

        try {
            tempDate = sdFormat.parse("30/11/1998");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Mahasiswa list5 = new Mahasiswa(
                idMahasiswa,
                "Intan Permata",
                tempDate,
                storeImagesFiles(R.drawable.gambar5),
                "Intan",
                "180090123",
                "Sistem Informasi"
        );

        tambahMahasiswa(list5, db);
        idMahasiswa++;

        try {
            tempDate = sdFormat.parse("09/09/1997");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Mahasiswa list6 = new Mahasiswa(
                idMahasiswa,
                "Sherin Nanda",
                tempDate,
                storeImagesFiles(R.drawable.gambar6),
                "Nanda",
                "180020345",
                "Sistem Informasi"
        );

        tambahMahasiswa(list6, db);
        idMahasiswa++;

        try {
            tempDate = sdFormat.parse("02/11/1998");
        }catch (ParseException er){
            er.printStackTrace();
        }

        Mahasiswa list7 = new Mahasiswa(
                idMahasiswa,
                "Agus Sutejo",
                tempDate,
                storeImagesFiles(R.drawable.gambar7),
                "Agus",
                "180010875",
                "Sistem Komputer"
        );

        tambahMahasiswa(list7, db);
    }

}
