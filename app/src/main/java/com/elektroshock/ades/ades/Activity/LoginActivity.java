package com.elektroshock.ades.ades.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.elektroshock.ades.ades.R;

public class LoginActivity extends Activity {

    Button masuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        masuk = (Button) findViewById(R.id.submit1);

        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LoginActivity.this, KonsumenActivity.class);
                startActivity(intent);
            }
        });


    }
}
