package com.elektroshock.ades.ades.Activity.DatabaseHandler;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;

import com.elektroshock.ades.ades.Activity.Util.Konsumen;
import com.elektroshock.ades.ades.Activity.Util.Penerima;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    String TAG = "DbHelper";
    SQLiteDatabase db;
    Context context;

    // static variable
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "pelanggan_db";

    private static final String TB_KONSUMEN = "tb_konsumen";
    private static final String TB_PENERIMA = "tb_penerima";

    String konsumen =
            "CREATE TABLE IF NOT EXISTS "+TB_KONSUMEN+" (ID integer primary key autoincrement, ID_KONSUMEN integer, NAMA text, TTL text, " +
                "KONTAK text, ALAMAT text, TYPE text, WARNA text, " +
                "NO_MESIN text);";

    String penerima =
            "CREATE TABLE IF NOT EXISTS "+TB_PENERIMA+" (ID integer primary key autoincrement, ID_PENERIMA text, ID_PEMBELI text, ID_DRIVER text, " +
                    "NO_KTP_PENERIMA text, NAMA_PENERIMA text, TLP_PENERIMA text, EMAIL_PENERIMA text, STATUS_KEKELUARGAAN text, HOBI text," +
                    "INSTAGRAM text, TWITTER text, YOUTUBE text, FACEBOOK text, RATING integer, COMMENT text, SELFIE text, TTD text)";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e(TAG, "start db helper");
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        db = sqLiteDatabase;
        db.execSQL(konsumen);
        db.execSQL(penerima);
        Log.e(TAG, "Database di buat" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertKonsumen(Konsumen konsumen) {

        db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("ID_KONSUMEN", konsumen.getPembeli_id());
        values.put("NAMA", konsumen.getPembeli_nama());
        values.put("TTL", konsumen.getPembeli_ttl());
        values.put("KONTAK", konsumen.getPembeli_kontak());
        values.put("ALAMAT", konsumen.getPembeli_alamat());
        values.put("TYPE", konsumen.getPembeli_type());
        values.put("WARNA", konsumen.getPembeli_warna());
        values.put("NO_MESIN", konsumen.getPembeli_mesin());

        db.insert(TB_KONSUMEN, null,values);
        Log.e(TAG, "INSERT KONSUMEN" );
    }

    public void insertPenerima(Penerima penerima){
        db = this.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put("ID_PENERIMA",penerima.getID_PENERIMA());
        values.put("ID_PEMBELI",penerima.getID_PEMBELI());
        values.put("ID_DRIVER",penerima.getID_DRIVER());
        values.put("NO_KTP_PENERIMA",penerima.getKTP_PENERIMA());
        values.put("NAMA_PENERIMA",penerima.getNAMA_PENERIMA());
        values.put("TLP_PENERIMA",penerima.getTLP_PENERIMA());
        values.put("EMAIL_PENERIMA",penerima.getEMAIL_PENERIMA());
        values.put("STATUS_KEKELUARGAAN",penerima.getSTATUS_KEKELUARGAAN());
        values.put("HOBI",penerima.getHOBI());
        values.put("INSTAGRAM",penerima.getINSTAGRAM());
        values.put("TWITTER",penerima.getTWITTER());
        values.put("YOUTUBE",penerima.getYOUTUBE());
        values.put("FACEBOOK",penerima.getFACEBOOK());
        values.put("RATING",penerima.getRATING());
        values.put("COMMENT",penerima.getCOMMENT());
        values.put("SELFIE",penerima.getSELFIE());
        values.put("TTD",penerima.getTTD());

        db.insert(TB_PENERIMA, null,values);
        Log.e(TAG, "insertPenerima" );
    }

    public ArrayList<Konsumen> getKonsumen(){
        ArrayList<Konsumen> konsumen = new ArrayList<Konsumen>();

        db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TB_KONSUMEN;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Konsumen cust = new Konsumen();
                cust.setPembeli_id(cursor.getString(1));
                cust.setPembeli_nama(cursor.getString(2));
                cust.setPembeli_ttl(cursor.getString(3));
                cust.setPembeli_kontak(cursor.getString(4));
                cust.setPembeli_alamat(cursor.getString(5));
                cust.setPembeli_type(cursor.getString(6));
                cust.setPembeli_warna(cursor.getString(7));
                cust.setPembeli_mesin(cursor.getString(8));

                konsumen.add(cust);
            }
            while (cursor.moveToNext());
        }

        return konsumen;
    }

    public ArrayList<Penerima> getPenerima(){
        ArrayList<Penerima> penerima = new ArrayList<Penerima>();

        db = getReadableDatabase();
        String query = "SELECT * FROM " + TB_PENERIMA;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            do {
                Penerima accept = new Penerima();
                accept.setID(cursor.getString(0));
                accept.setID_PENERIMA(cursor.getString(1));
                accept.setID_PEMBELI(cursor.getString(2));
                accept.setID_DRIVER(cursor.getString(3));
                accept.setKTP_PENERIMA(cursor.getString(4));
                accept.setNAMA_PENERIMA(cursor.getString(5));
                accept.setTLP_PENERIMA(cursor.getString(6));
                accept.setEMAIL_PENERIMA(cursor.getString(7));
                accept.setSTATUS_KEKELUARGAAN(cursor.getString(8));
                accept.setHOBI(cursor.getString(9));
                accept.setINSTAGRAM(cursor.getString(10));
                accept.setTWITTER(cursor.getString(11));
                accept.setYOUTUBE(cursor.getString(12));
                accept.setFACEBOOK(cursor.getString(13));
                accept.setRATING(cursor.getString(14));
                accept.setCOMMENT(cursor.getString(15));
                accept.setSELFIE(cursor.getString(16));
                accept.setTTD(cursor.getString(17));

                penerima.add(accept);

            } while (cursor.moveToNext());
        }
        return penerima;
    }

    public void deleteAllKonsumen(){
        db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TB_KONSUMEN);
    }

    public void deleteAllPenerima(){
        db = this.getWritableDatabase();

        db.execSQL("DELETE FROM "+TB_PENERIMA);
    }

    public void deletePenerima(String id){
        db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TB_PENERIMA+" WHERE ID_PEMBELI = "+id);
    }

    public void updatePenerima(Penerima penerima, String id){
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ID_PENERIMA",penerima.getID_PENERIMA());
        values.put("ID_PEMBELI",penerima.getID_PEMBELI());
        values.put("ID_DRIVER",penerima.getID_DRIVER());
        values.put("NO_KTP_PENERIMA",penerima.getKTP_PENERIMA());
        values.put("NAMA_PENERIMA",penerima.getNAMA_PENERIMA());
        values.put("TLP_PENERIMA",penerima.getTLP_PENERIMA());
        values.put("EMAIL_PENERIMA",penerima.getEMAIL_PENERIMA());
        values.put("STATUS_KEKELUARGAAN",penerima.getSTATUS_KEKELUARGAAN());
        values.put("HOBI",penerima.getHOBI());
        values.put("INSTAGRAM",penerima.getINSTAGRAM());
        values.put("TWITTER",penerima.getTWITTER());
        values.put("YOUTUBE",penerima.getYOUTUBE());
        values.put("FACEBOOK",penerima.getFACEBOOK());
        values.put("RATING",penerima.getRATING());
        values.put("COMMENT",penerima.getCOMMENT());
        values.put("SELFIE",penerima.getSELFIE());
        values.put("TTD",penerima.getTTD());

        db.update(TB_PENERIMA, values, "ID_PENERIMA"+id, null);
        Log.e(TAG, "updatePenerima" );
    }

}
