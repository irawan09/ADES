package com.elektroshock.ades.ades.Activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.elektroshock.ades.ades.Activity.DatabaseHandler.DatabaseHandler;
import com.elektroshock.ades.ades.Activity.Util.Konsumen;
import com.elektroshock.ades.ades.Activity.Util.Penerima;
import com.elektroshock.ades.ades.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class SignatureActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    String message, phoneNo;

    Button btn_get_sign, mClear, mGetSign, mCancel, kirim;

    DatabaseHandler dbcenter;

    File file;
    Dialog dialog;
    LinearLayout mContent;
    View view;
    signature mSignature;
    Bitmap bitmap;
    String comment, nilai;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    private RatingBar ratingBar;
    protected EditText komentar;

    Penerima penerima;
    Konsumen konsumen;

    protected String id_penerima, id_driver, id_pembeli, ttd, penerima_nama, penerima_ktp, penerima_kontak, penerima_email,
            penerima_status, penerima_hobi, penerima_instagram, penerima_twitter, penerima_youtube,
            penerima_facebook, penerima_rating, penerima_komentar, selfieString, ttdString;

    // Creating Separate Directory for saving Generated Images
    String DIRECTORY = Environment.getExternalStorageDirectory().getPath() + "/TandaTangan/";
    String pic_name;
    String StoredPath = DIRECTORY + pic_name + ".JPEG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Tanda Tangan Pelanggan");
        toolbar.setTitleTextColor(Color.WHITE);

        penerima = new Penerima();
        konsumen = new Konsumen();
        dbcenter = new DatabaseHandler(this);

        // Button to open signature panel
        btn_get_sign = (Button) findViewById(R.id.signature);

        komentar = (EditText) findViewById(R.id.komentar);
        kirim = (Button) findViewById(R.id.kirim_data) ;

        // Method to create Directory, if the Directory doesn't exists
        file = new File(DIRECTORY);
        if (!file.exists()) {
            file.mkdir();
        }
        // Dialog Function
        dialog = new Dialog(SignatureActivity.this);
        // Removing the features of Normal Dialogs
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_signature);
        dialog.setCancelable(true);

        btn_get_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Function call for Digital Signature
                dialog_action();
            }
        });

        addListenerOnRatingBar();

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = komentar.getText().toString();
                String rating = nilai;

                SharedPreferences acceptor = getSharedPreferences("Penerima",MODE_PRIVATE);
                SharedPreferences.Editor editor = acceptor.edit();
                editor.putString("rating",rating);
                editor.putString("komentar",comment);
                editor.commit();

                SharedPreferences driver = getSharedPreferences("driver",MODE_PRIVATE);
                id_driver = driver.getString("id_driver","");


                id_penerima = String.valueOf(acceptor.getInt("id_penerima", 0));
                penerima_nama = acceptor.getString("penerima_nama","");
                penerima_ktp = acceptor.getString("penerima_ktp","");
                penerima_kontak = acceptor.getString("penerima_kontak","");
                penerima_email = acceptor.getString("penerima_email","");
                penerima_status = acceptor.getString("penerima_status","");
                penerima_hobi = acceptor.getString("penerima_hobi","");
                penerima_instagram = acceptor.getString("penerima_instagram","");
                penerima_twitter = acceptor.getString("penerima_twitter","");
                penerima_youtube = acceptor.getString("penerima_youtube","");
                penerima_facebook = acceptor.getString("penerima_facebook","");
                penerima_rating = acceptor.getString("rating","");
                penerima_komentar = acceptor.getString("komentar","");
                selfieString = acceptor.getString("selfie","");

                SharedPreferences pembeli = getSharedPreferences("Pembeli",MODE_PRIVATE);
                id_pembeli = pembeli.getString("id_pembeli","");

                SharedPreferences TandaTangan = getSharedPreferences("Signature",MODE_PRIVATE);
                ttdString = TandaTangan.getString("ttd","");

                SharedPreferences konsumen = getSharedPreferences("konsumen",MODE_PRIVATE);
                id_pembeli = konsumen.getString("id_pembeli", "");

                penerima.setID_PENERIMA(id_penerima);
                penerima.setID_PEMBELI(id_pembeli);
                penerima.setID_DRIVER(id_driver);
                penerima.setNAMA_PENERIMA(penerima_nama);
                penerima.setKTP_PENERIMA(penerima_ktp);
                penerima.setTLP_PENERIMA(penerima_kontak);
                penerima.setEMAIL_PENERIMA(penerima_email);
                penerima.setSTATUS_KEKELUARGAAN(penerima_status);
                penerima.setHOBI(penerima_hobi);
                penerima.setINSTAGRAM(penerima_instagram);
                penerima.setTWITTER(penerima_twitter);
                penerima.setYOUTUBE(penerima_youtube);
                penerima.setFACEBOOK(penerima_facebook);
                penerima.setRATING(penerima_rating);
                penerima.setCOMMENT(penerima_komentar);
                penerima.setSELFIE(selfieString);
                penerima.setTTD(ttdString);

                Log.e("PEMBELI ID", penerima.getID_PEMBELI()+"");
                Log.e("PENERIMA ID", penerima.getID_PENERIMA()+"");
                Log.e("DRIVER ID", penerima.getID_DRIVER()+"");
                Log.e("NAMA PENERIMA", penerima.getNAMA_PENERIMA()+"");
                Log.e("HOBI", penerima.getHOBI()+"");
                Log.e("COMMENT", penerima.getCOMMENT()+"");
                Log.e("RATING", penerima.getRATING()+"");
                Log.e("PENERIMA SELFIE", penerima.getSELFIE()+"");
                Log.e("PENERIMA TTD", penerima.getTTD()+"");

                sendSMSMessage();

                saveToDb(penerima);

                Toast.makeText(getBaseContext(),"Data tersimpan", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SignatureActivity.this, DriverActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }

    private void addListenerOnRatingBar() {
        // TODO Auto-generated method stub
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);


        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                nilai = String.valueOf(rating);
                Log.e("tag", "Rating mentah " + nilai);
            }
        });
    }

    // Function for Digital Signature
    public void dialog_action() {

        mContent = (LinearLayout) dialog.findViewById(R.id.linearLayout);
        mSignature = new signature(getApplicationContext(), null);
        mSignature.setBackgroundColor(Color.WHITE);
        // Dynamically generating Layout through java code
        mContent.addView(mSignature, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mClear = (Button) dialog.findViewById(R.id.clear);
        mGetSign = (Button) dialog.findViewById(R.id.getsign);
        mGetSign.setEnabled(false);
        mCancel = (Button) dialog.findViewById(R.id.cancel);
        view = mContent;

        mClear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("tag", "Panel Cleared");
                mSignature.clear();
                mGetSign.setEnabled(false);
            }
        });
        mGetSign.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String dates = sdf.format(timestamp);

                pic_name = "TTD " + dates;
                StoredPath = DIRECTORY + pic_name + ".JPEG";
                Log.e("tag", "DATES :"+dates);

                Log.e("tag", "Directory" + StoredPath);
                view.setDrawingCacheEnabled(true);
                mSignature.save(view, StoredPath);

                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Berhasil Disimpan", Toast.LENGTH_SHORT).show();
                // Calling the same class
                recreate();

            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.v("tag", "Panel Dibatalkan");
                dialog.dismiss();
                // Calling the same class
                recreate();
            }
        });
        dialog.show();
    }

    public class signature extends View {
        private static final float STROKE_WIDTH = 5f;
        private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        private Paint paint = new Paint();
        private Path path = new Path();

        private float lastTouchX;
        private float lastTouchY;
        private final RectF dirtyRect = new RectF();

        public signature(Context context, AttributeSet attrs) {
            super(context, attrs);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeWidth(STROKE_WIDTH);
        }

        public void save(View v, String StoredPath) {
            Log.v("tag", "Width: " + v.getWidth());
            Log.v("tag", "Height: " + v.getHeight());
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(mContent.getWidth(), mContent.getHeight(), Bitmap.Config.RGB_565);
            }

            Canvas canvas = new Canvas(bitmap);
            try {
                     // Output the file
                     FileOutputStream mFileOutStream = new FileOutputStream(StoredPath);
                     v.draw(canvas);
                     // Convert the output file to Image such as .jpeg
                     bitmap.compress(Bitmap.CompressFormat.JPEG, 90, mFileOutStream);
                     mFileOutStream.flush();
                     mFileOutStream.close();
                } catch (Exception e) {
                    Log.v("log_tag", e.toString());
                }

            // Convert the output file to Image such as .jpeg
            ByteArrayOutputStream stream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            byte[] image=stream.toByteArray();
            penerima.setTTD(StoredPath);
          //  String ttd = Base64.encodeToString(penerima.getTTD(), 0);

            SharedPreferences TandaTangan = getSharedPreferences("Signature",MODE_PRIVATE);
            SharedPreferences.Editor editor = TandaTangan.edit();
            editor.putString("ttd",penerima.getTTD());
            editor.commit();

            Log.e("tag", "TTD " + penerima.getTTD()+"");
        }

        public void clear() {
            path.reset();
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float eventX = event.getX();
            float eventY = event.getY();
            mGetSign.setEnabled(true);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(eventX, eventY);
                    lastTouchX = eventX;
                    lastTouchY = eventY;
                    return true;

                case MotionEvent.ACTION_MOVE:

                case MotionEvent.ACTION_UP:
                    resetDirtyRect(eventX, eventY);
                    int historySize = event.getHistorySize();
                    for (int i = 0; i < historySize; i++) {
                        float historicalX = event.getHistoricalX(i);
                        float historicalY = event.getHistoricalY(i);
                        expandDirtyRect(historicalX, historicalY);
                        path.lineTo(historicalX, historicalY);
                    }
                    path.lineTo(eventX, eventY);
                    break;
                default:
                    debug("Ignored touch event: " + event.toString());
                    return false;
            }

            invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                    (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                    (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

            lastTouchX = eventX;
            lastTouchY = eventY;

            return true;
        }

        private void debug(String string) {
            Log.v("log_tag", string);
        }

        private void expandDirtyRect(float historicalX, float historicalY) {
            if (historicalX < dirtyRect.left) {
                dirtyRect.left = historicalX;
            } else if (historicalX > dirtyRect.right) {
                dirtyRect.right = historicalX;
            }

            if (historicalY < dirtyRect.top) {
                dirtyRect.top = historicalY;
            } else if (historicalY > dirtyRect.bottom) {
                dirtyRect.bottom = historicalY;
            }
        }

        private void resetDirtyRect(float eventX, float eventY) {
            dirtyRect.left = Math.min(lastTouchX, eventX);
            dirtyRect.right = Math.max(lastTouchX, eventX);
            dirtyRect.top = Math.min(lastTouchY, eventY);
            dirtyRect.bottom = Math.max(lastTouchY, eventY);
        }
    }

    protected void sendSMSMessage() {
        SharedPreferences konsumen = getSharedPreferences("konsumen", Context.MODE_PRIVATE);
        phoneNo = konsumen.getString("kontak_pelanggan", "" );

        Log.e("No. HP :", phoneNo);

        message = "Salam satu hari, kami infokan bapak/ibu baru saja menerima pengiriman unit honda. \n" +
                "Jika ada pertanyaan atau keluhan silahkan menghubungi 081 917 90 8000 \n" +
                "Terima Kasih";

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"SMS gagal, ulangi !", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }

    public void saveToDb(Penerima penerima) {

       dbcenter.insertPenerima(penerima);
    }
}
