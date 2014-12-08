package edu.example.androproject.widget;

import android.content.Context;
import android.util.AttributeSet;

public class EditTextValidatedName extends EditTextValidated {


    public EditTextValidatedName(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean validate(boolean b) {

        if(this.getText().toString().length() == 0 && !b){
            this.setError(context.getString(com.example.login.R.string.login_name_emptyerror));
            return false;
        }

        return true;

    }
}
