package com.iconasystems.christoandrew.brandtracker;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iconasystems.christoandrew.brandtracker.utils.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class Checkin extends AppCompatActivity {

    private LinearLayout mCheckInForm;
    private LinearLayout mCheckInSuccess;
    private LinearLayout mCheckInError;
    private LinearLayout mCheckInOffline;
    private Button mCheckIn;
    private ProgressBar mProgress;
    private LinearLayout mCheckInFormFields;
    private TextView mCheckOutTime;
    private TextView mCheckInSuccessMessage;
    private EditText mEntryStockView;
    private boolean isCheckedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra("bar"));

        mCheckOutTime = findViewById(R.id.check_out_time);
        mCheckInForm = findViewById(R.id.check_in_form);
        mCheckInSuccess = findViewById(R.id.check_in_success_layout);
        mCheckInError = findViewById(R.id.check_in_failed_layout);
        mCheckInOffline = findViewById(R.id.check_in_offline_layout);
        mProgress = findViewById(R.id.check_in_progress);
        mCheckInFormFields = findViewById(R.id.check_in_form_fields);
        mEntryStockView = findViewById(R.id.entry_stock);
        mCheckInSuccessMessage = findViewById(R.id.check_in_success_message);

        Typeface semiBold = Typeface.createFromAsset(getAssets(),
                "fonts/Raleway/Raleway-SemiBold.ttf");

        Typeface regular = Typeface.createFromAsset(getAssets(),
                "fonts/Raleway/Raleway-Regular.ttf");

        mCheckOutTime.setTypeface(regular);
        mCheckInSuccessMessage.setTypeface(semiBold);

        mCheckIn = findViewById(R.id.check_in);

        mCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (attemptCheckin()) {
                    isCheckedIn = true;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mCheckInFormFields.setVisibility(View.INVISIBLE);
                            mProgress.setVisibility(View.VISIBLE);
                        }
                    }, 0000);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mCheckInForm.setVisibility(View.INVISIBLE);
                            mCheckInSuccess.setVisibility(View.VISIBLE);
                            Calendar cal = Calendar.getInstance(); // creates calendar
                            cal.setTime(new Date()); // sets calendar time/date
                            cal.add(Calendar.HOUR_OF_DAY, 8); // adds one hour
                            Date date = cal.getTime(); // returns new date object, one hour in the future
                            mCheckOutTime.setText("Checkout time is ".concat(DateUtils.toString(date,
                                    getApplicationContext())));

                        }
                    }, 3000);
                }
            }
        });

    }

    boolean attemptCheckin() {
        boolean isValidCheckin = true;
        mEntryStockView.setError(null);
        String entryStock = mEntryStockView.getText().toString();
        if (TextUtils.isEmpty(entryStock)) {
            mEntryStockView.setError("Invalid stock number");
            mEntryStockView.requestFocus();
            isValidCheckin = false;
        }
        return isValidCheckin;
    }

    @Override
    public void onBackPressed(){
        Intent i=new Intent();
        i.putExtra("is_checked_in", isCheckedIn);
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
