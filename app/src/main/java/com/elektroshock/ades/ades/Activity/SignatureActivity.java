package com.elektroshock.ades.ades.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.elektroshock.ades.ades.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class SignatureActivity extends AppCompatActivity {

    Button btn_get_sign, mClear, mGetSign, mCancel, kirim;

    File file;
    Dialog dialog;
    LinearLayout mContent;
    View view;
    signature mSignature;
    Bitmap bitmap;
    String comment, nilai;
    static int number; //static will get memory only once and retain its value
    // (makes the counter not set again to default value)

    private RatingBar ratingBar;
    protected EditText komentar;

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
        setSupportActionBar(toolbar);

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
        Log.e("tag", "ANGKA oncreate:"+number);

        addListenerOnRatingBar();

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment = komentar.getText().toString();
                String rating = nilai;

                Log.e("tag", "Rating " + nilai);
                Log.e("tag", "Komentar " + comment);

                SharedPreferences preferences = getSharedPreferences("Penerima",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("rating",rating);
                editor.putString("komentar",comment);
                editor.commit();

                Toast.makeText(getBaseContext(),"Data tersimpan", Toast.LENGTH_SHORT).show();




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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_penerima, menu);
        //getMenuInflater().inflate(R.menu.menu_pelanggan, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.kelola){
            Intent intent=new Intent(SignatureActivity.this, DetailDataPenerimaActivity.class);
            startActivity(intent);

        }  else if (item.getItemId() == R.id.ambil) {
            Intent intent=new Intent(SignatureActivity.this, ListPenerimaActivity.class);
            startActivity(intent);
        }

        return true;
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
                number++;
                pic_name = "TTD" + number;
                StoredPath = DIRECTORY + pic_name + ".JPEG";
                Log.e("tag", "ANGKA onclick:"+number);

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
            String img_str = Base64.encodeToString(image, 0);

            Log.e("tag", "String Gambar mentah " + img_str);

            SharedPreferences pref = getSharedPreferences("Signature",MODE_PRIVATE);
            SharedPreferences.Editor ubah = pref.edit();
            ubah.putString("ttd",img_str);
            ubah.commit();


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
}
