package com.iconasystems.christoandrew.brandtracker;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class SelectLoginType extends AppCompatActivity {
    Button mOnTrade;
    Button mOffTrade;
    TextView mBrand;
    TextView mLoginTypeLabel;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_select_login_type);

        mBrand = (TextView) findViewById(R.id.brand);
        mLoginTypeLabel = (TextView) findViewById(R.id.login_type_label);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Raleway/Raleway-Bold.ttf");
        mBrand.setTypeface(font);
        mLoginTypeLabel.setTypeface(font);

        mOnTrade = (Button) findViewById(R.id.select_on_trade);
        mOnTrade.setTypeface(font);

        mOffTrade = (Button) findViewById(R.id.select_off_trade);
        mOffTrade.setTypeface(font);

        mOnTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        mOffTrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
    }
}
