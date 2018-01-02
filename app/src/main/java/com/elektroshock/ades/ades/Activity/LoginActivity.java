package com.elektroshock.ades.ades.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.elektroshock.ades.ades.R;

public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
    }
}
