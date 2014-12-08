package edu.example.androproject.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class TextViewError extends TextView{


    public TextViewError(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void manageTextViewError(final String msg) {
        setErrorTextViewVisibility(true);
        setErrorTextViewText(msg);
    }

    private void setErrorTextViewText(String msg) {
        this.setText(msg);
    }

    private void setErrorTextViewVisibility(boolean isVisible) {

        this.setVisibility(isVisible? View.VISIBLE:View.GONE);
    }
}
