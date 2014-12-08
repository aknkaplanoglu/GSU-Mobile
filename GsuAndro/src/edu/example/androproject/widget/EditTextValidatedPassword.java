package edu.example.androproject.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.example.login.R;


public class EditTextValidatedPassword extends EditTextValidated {


    public EditTextValidatedPassword(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //this method check if the pas has minum 6 chars
    @Override
    public boolean validate(boolean b) {

        if(this.getText().toString().length() == 0 && !b){
            this.setError(context.getString(R.string.login_name_emptyerror));
            return false;
        } else if(this.getText().toString().length() < 5 && !b){
            this.setError(context.getString(R.string.login_name_shorterror));
            return false;
        }

        return true;
    }
}
