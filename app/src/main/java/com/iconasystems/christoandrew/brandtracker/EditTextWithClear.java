package com.iconasystems.christoandrew.brandtracker;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class EditTextWithClear extends AppCompatEditText {
    public EditTextWithClear(Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClear(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init(){

    }
}
