package com.elektroshock.ades.ades.Activity.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    // static variable
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "pelanggan.db";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql1 =
           "CREATE TABLE PENERIMA(ID integer primary key autoincrement, NAMA text null, " +
           "KTP text null, KONTAK text null, EMAIL text null, STATUS text null, HOBI text null, " +
           "INSTAGRAM text null, TWITTER text null, YOUTUBE text null, FACEBOOK text null, " +
           "RATING text null, KOMENTAR text null, SELFIE text null, TTD text null);";
        Log.d("Data Penerima", "onCreate: " + sql1);
        db.execSQL(sql1);

        sql1 = "INSERT INTO PENERIMA (NAMA, KTP, KONTAK, EMAIL, STATUS, HOBI, " +
                "INSTAGRAM, TWITTER, YOUTUBE, FACEBOOK, RATING, KOMENTAR, SELFIE, TTD) " +
                "VALUES ('Hapus Data Ini', 'HTREGDJ', '085858252999','arykurniadiirawan@gmail.com', 'Ayah', " +
                "'Olahraga','arykurniadiirawan', 'irawan09', 'irawan09', 'irawan09', '4', 'coba', 'a', 'b');";
        db.execSQL(sql1);

   /*     String sql2 = "CREATE TABLE KONSUMEN(ID integer primary key autoincrement, NAMA text null, TTL text null, " +
                "KONTAK text null, ALAMAT text null, TYPE text null, WARNA text null, " +
                "NO_MESIN text null);";
        Log.d("Data Konsumen", "onCreate: " + sql2);
        db.execSQL(sql2); */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
