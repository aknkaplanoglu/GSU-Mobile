package edu.example.androproject.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.example.login.R;

//This class is not used but if you wanna use it to manage the user field of login you can!!
public class EditTextValidatedEmail extends EditTextValidated {


    public EditTextValidatedEmail(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean validate(boolean b) {

        if(this.getText().toString().length() == 0 && !b){

            this.setError(context.getString(R.string.account_email_emptyerror));
            return false;
        }

        if(!isValidEmail(this.getText().toString())){
            this.setError(context.getString(R.string.account_email_formaterror));
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String target) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}
