package edu.example.androproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
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

        userName = (TextView) findViewById(R.id.username);
        password = (TextView) findViewById(R.id.password);

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

        if (StringUtils.isNotBlank(newName) && StringUtils.length(newPassword) > 5) {
            Intent intent = new Intent(CreateNewUserActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        } else {


        }

    }
}
