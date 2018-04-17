package com.elektroshock.ades.ades.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.elektroshock.ades.ades.Activity.Util.Penerima;
import com.elektroshock.ades.ades.R;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PenerimaActivity extends AppCompatActivity {

    Button next;
    private String selectedImagePath;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    private ImageView mImageView;
    private TextView textGambar;
    static int id_penerima;
    String status, hobi, nama_penerima, no_ktp, kontak, email, instagram, twitter, youtube, facebook;
    String url_gambar;
    protected EditText penerima_nama,penerima_ktp, penerima_kontak, penerima_email,
            penerima_instagram, penerima_twitter, penerima_youtube, penerima_facebook;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    File file;
    // Creating Separate Directory for saving Generated Images
    String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/SelfieASTRA/";
    String pic_name;
    String StoredPath;

    private Uri mImageCaptureUri;
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;

    Penerima penerima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penerima);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Data Penerima");
        toolbar.setTitleTextColor(Color.WHITE);

        // Method to create Directory, if the Directory doesn't exists
        file = new File(DIRECTORY);
        if (!file.exists()) {
            file.mkdir();
        }

        penerima = new Penerima();
        preferences = getSharedPreferences("Penerima",MODE_PRIVATE);
        editor = preferences.edit();

        next = (Button) findViewById(R.id.next);

        textGambar = (TextView) findViewById(R.id.text_gambar);

        penerima_nama = (EditText) findViewById(R.id.nama_penerima);
        penerima_ktp = (EditText) findViewById(R.id.no_ktp);
        penerima_kontak = (EditText) findViewById(R.id.no_hp);
        penerima_email = (EditText) findViewById(R.id.email);
        penerima_instagram = (EditText) findViewById(R.id.instagram);
        penerima_twitter = (EditText) findViewById(R.id.twitter);
        penerima_youtube = (EditText) findViewById(R.id.youtube);
        penerima_facebook = (EditText) findViewById(R.id.facebook);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id_penerima = preferences.getInt("id_penerima", 0);

                id_penerima++;
                nama_penerima = penerima_nama.getText().toString();
                no_ktp = penerima_ktp.getText().toString();
                kontak = penerima_kontak.getText().toString();
                email = penerima_email.getText().toString();
                instagram = penerima_instagram.getText().toString();
                twitter = penerima_twitter.getText().toString();
                youtube = penerima_youtube.getText().toString();
                facebook = penerima_facebook.getText().toString();

                editor.putInt("id_penerima",id_penerima);
                editor.putString("penerima_nama",nama_penerima);
                editor.putString("penerima_ktp",no_ktp);
                editor.putString("penerima_kontak",kontak);
                editor.putString("penerima_email",email);
                editor.putString("penerima_status",status);
                editor.putString("penerima_hobi",hobi);
                editor.putString("penerima_instagram",instagram);
                editor.putString("penerima_twitter",twitter);
                editor.putString("penerima_youtube",youtube);
                editor.putString("penerima_facebook",facebook);
                editor.commit();

                if (nama_penerima.trim().length() > 0){
                    if (no_ktp.trim().length() > 0){
                        if (kontak.trim().length() > 0){
                            if(status != "0"){
                                if ((instagram.trim().length() > 0) || (twitter.trim().length() >0) || (youtube.trim().length() >0) || (facebook.trim().length() >0)) {
                                    if (url_gambar.length() > 0) {

                                        Intent intent = new Intent(PenerimaActivity.this, VideoActivity.class);
                                        startActivity(intent);
                                        finish();

                                    }else {
                                        Toast.makeText(getApplicationContext() ,"Pilih gambar dahulu", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else{
                                    Toast.makeText(getApplicationContext(), "Mohon isi salah satu data akun sosial media !",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Pilih status kekeluargaan !",Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Isi data nomor handphone !",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Isi data no. KTP !", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Isi data nama !", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Spinner spinner1 = (Spinner) findViewById(R.id.status);

        final String[] status = new String[]{
                "Status Kekeluargaan",
                "Yang bersangkutan",
                "Ayah",
                "Ibu",
                "Anak",
                "Anggota keluarga lain"
        };

        final List<String> statusList = new ArrayList<>(Arrays.asList(status));

        final ArrayAdapter<String> spinner1ArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.status_spinner,statusList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinner1ArrayAdapter.setDropDownViewResource(R.layout.status_spinner);
        spinner1.setAdapter(spinner1ArrayAdapter);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item
                    PenerimaActivity.this.status = selectedItemText;
                }
                else {
                    PenerimaActivity.this.status = "0";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Spinner spinner2 = (Spinner) findViewById(R.id.hobi);

        final String[] hobi = new String[]{
                "Hobi",
                "Game",
                "Travelling",
                "Hiburan",
                "Kolektor Barang",
                "Olahraga"
        };

        final List<String> hobiList = new ArrayList<>(Arrays.asList(hobi));

        final ArrayAdapter<String> spinner2ArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.hobi_spinner,hobiList){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        spinner2ArrayAdapter.setDropDownViewResource(R.layout.hobi_spinner);
        spinner2.setAdapter(spinner2ArrayAdapter);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item
                    PenerimaActivity.this.hobi = selectedItemText;
                }
                else {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //proses ambil gambar melalui galeri ataupun camera
        url_gambar = null;

        final String [] items           = new String [] {"From Camera", "From SD Card"};
        ArrayAdapter<String> adapter    = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item,items);
        AlertDialog.Builder builder     = new AlertDialog.Builder(this);
        builder.setTitle("Select Image");
        builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
            public void onClick( DialogInterface dialog, int item ) {
                if (item == 0) {
                    Intent intent    = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    try {
                        startActivityForResult(intent, PICK_FROM_CAMERA);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    dialog.cancel();
                } else {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
                }
            }
        } );

        final AlertDialog dialog = builder.create();

        mImageView = (ImageView) findViewById(R.id.iv_pic);

        ((ImageView) findViewById(R.id.iv_pic)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;

        Bitmap bitmap   = null;
        String path     = "";

        if (requestCode == PICK_FROM_FILE) {
            mImageCaptureUri = data.getData();
            path = getRealPathFromURI(mImageCaptureUri); //from Gallery

            if (path == null)
                path = mImageCaptureUri.getPath(); //from File Manager

            if (path != null)
                bitmap  = BitmapFactory.decodeFile(path);
        } else {

            Bundle extras = data.getExtras();
            bitmap = (Bitmap) extras.get("data");
        }

        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String dates = sdf.format(timestamp);
            pic_name = "Selfie "+dates;
            StoredPath = DIRECTORY + pic_name + ".JPEG";

            FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, mFileOutStream);
            mFileOutStream.flush();
            mFileOutStream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File imgFile = new  File(StoredPath);

        if(imgFile.exists()){

            bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            mImageView.setImageBitmap(bitmap);
            textGambar.setVisibility(View.GONE);
        }
        penerima.setSELFIE(StoredPath);
        url_gambar = penerima.getSELFIE();

        editor.putString("selfie",penerima.getSELFIE());
        Log.e("GAMBAR SELFIE", penerima.getSELFIE()+"");
    }

    public String getRealPathFromURI(Uri contentUri) {
        String [] proj      = {MediaStore.Images.Media.DATA};
        Cursor cursor       = this.getContentResolver().query( contentUri, proj, null, null,null);

        if (cursor == null) return null;

        int column_index    = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);
    }

    protected byte[] ImgtoString(Bitmap gambar){

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        gambar.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();

        return imageBytes;
    }
}
