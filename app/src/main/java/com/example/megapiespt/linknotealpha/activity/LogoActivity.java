package com.example.megapiespt.linknotealpha.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.megapiespt.linknotealpha.R;
import com.example.megapiespt.linknotealpha.manager.DatabaseManager;
import com.facebook.AccessToken;

public class LogoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // remove title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_logo);
    }

    @Override
    protected void onStart() {
        super.onStart();

        AccessToken token = AccessToken.getCurrentAccessToken();
        if(token == null)
            gotoLogin();
        else
            gotoMain();
    }

    private void gotoMain() {
        DatabaseManager.getInstance().init();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void gotoLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
