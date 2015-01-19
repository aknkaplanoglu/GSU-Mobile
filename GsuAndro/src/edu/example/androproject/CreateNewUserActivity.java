package edu.example.androproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.login.R;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Akin on 04-Jan-15.
 */
public class CreateNewUserActivity extends Activity {


    private TextView userName;
    private TextView password;
    final Context context = this;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.createnewaccount);

        userName = (TextView) findViewById(R.id.uname);
        password = (TextView) findViewById(R.id.passwrd);

        findViewById(R.id.createNewAccountBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAccount();
            }
        });


    }

    private void createNewAccount() {

        String newName = userName.getText().toString();
        String newPassword = password.getText().toString();

        if (StringUtils.isNotBlank(newName) && StringUtils.isNotBlank(newPassword)) {
            new CreateNewUserTask(newName, newPassword) {

                @Override
                protected void onPostExecute(Object o) {
                    super.onPostExecute(o);


                    //this logic depends on what my server return, know if the server return null
                    if (o == null || o.equals("f")) {
                        Toast.makeText(getApplicationContext(), "Please,try different username", Toast.LENGTH_LONG).show();

                    } else {
                        //Result JSON passed can contain something I need about the user logged.
                        //setUserAsLogged(o);
                        Intent intent = new Intent(CreateNewUserActivity.this, LoginActivity.class);
                        startActivity(intent);
                        //here I must insert the code to execute if the login is success
                        finish();
                    }
                }

            }.execute();

        } else {


        }

    }
}
