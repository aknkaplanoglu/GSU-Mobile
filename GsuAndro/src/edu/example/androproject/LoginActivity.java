package edu.example.androproject;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import com.example.login.R;
import edu.example.androproject.widget.EditTextValidated;
import edu.example.androproject.widget.EditTextValidatedName;
import edu.example.androproject.widget.EditTextValidatedPassword;
import edu.example.androproject.widget.TextViewError;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends Activity {

    private ProgressDialog progressDialog;
    static LoginActivity loginActivity;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginActivity = this;

        setContentView(R.layout.login);

        findViewById(R.id.login_button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (getPasswordEditText().validate(false) & getNameEditText().validate(false)) {
                    login();
                    Log.d("loginden sonra", "loginden sonra");
                }
            }
        });

        findViewById(R.id.login_createaccount_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(LoginActivity.this, CreateNewUserActivity.class));
                finish();
            }
        });
    }

    public static LoginActivity getInstance() {
        return loginActivity;
    }

    public String getTelImei() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        return deviceId;
    }

    public String getUserName() {
        Editable text = getNameEditText().getText();
        String s = text.toString();
        return s;
    }

    private void login() {

        showProgressDialog();

        new LoginTask(getNameEditText().getText() + "", getPasswordEditText().getText() + "") {

            @Override
            public void onFailure() {
                Log.d("DEBUG", "onFailure");
                hideProgressDialog();
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);

                hideProgressDialog();

                //this logic depends on what my server return, know if the server return null
                if (o == null || o.equals("f")) {
                    getErrorTextView().manageTextViewError(getString(R.string.login_error)); //|| !(Boolean) o || !((Boolean) o).booleanValue()

                } else {
                    //Result JSON passed can contain something I need about the user logged.
                    //setUserAsLogged(o);
                    Intent intent = new Intent(LoginActivity.this, LocationActivity.class);
                    startActivity(intent);
                    //here I must insert the code to execute if the login is success
                    finish();
                }
            }
        }.execute();
    }

    private EditTextValidated getPasswordEditText() {
        return (EditTextValidatedPassword) findViewById(R.id.login_editText_password);
    }

    private EditTextValidated getNameEditText() {
        return (EditTextValidatedName) findViewById(R.id.login_editText_name);
    }

    private TextViewError getErrorTextView() {
        return (TextViewError) findViewById(R.id.login_textview_error);
    }

    private void setUserAsLogged(JSONObject resp) {

        SharedPreferences.Editor editor = getSharedPreferences("Preferences", MODE_PRIVATE).edit();
        editor.putString("LOGGED", "YES");

        try {

            editor.putString("MAIL", resp.getString("mail") + "");
            editor.putString("DESCRIPTION", resp.getString("description") + "");
            editor.putString("STATE", resp.getString("state" + ""));
            editor.putString("NAME", resp.getString("name") + "");
        } catch (JSONException e) {
            Log.e("Error", "JSONExc in setUserAsLogged");
        }

        editor.commit();
    }

    //This two methods can be in a superclass and used by everyone wants to show or hide progressDialogs.
    private void hideProgressDialog() {
        runOnUiThread(new Runnable() {
            public void run() {
                progressDialog.dismiss();
            }
        });
    }

    private void showProgressDialog() {
        runOnUiThread(new Runnable() {
            public void run() {
                progressDialog = ProgressDialog.show(LoginActivity.this, "", getString(R.string.progress_dialog_text));
            }
        });
    }
}
