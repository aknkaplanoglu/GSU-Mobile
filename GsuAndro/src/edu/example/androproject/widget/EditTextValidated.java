package edu.example.androproject.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

public abstract class EditTextValidated extends EditText {

    protected Context context;

    public EditTextValidated(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;

        this.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View view, boolean b) {

                validate(b);
            }
        });

    }


    public abstract boolean validate(boolean b);

}
