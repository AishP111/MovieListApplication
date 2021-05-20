package com.example.android.movielistapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView mSignUpTextView;
    Button mLoginButton;
    EditText mLoginUserName, mLoginUserPwd;
    private SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "LoginCred";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSignUpTextView = (TextView) findViewById(R.id.createnewac);

        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

        mLoginUserName = (EditText) findViewById(R.id.login_user_name);
        mLoginUserPwd = (EditText) findViewById(R.id.login_user_pwd);
        mLoginButton = (Button) findViewById(R.id.login_button);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processLogin();
            }
        });
    }

    void processLogin() {
        String username = mLoginUserName.getText().toString();
        String password = mLoginUserPwd.getText().toString();

        if(username.trim().length() > 0 && password.trim().length() > 0){
            String uName = null;
            String uPassword =null;
            if (sharedPreferences.contains("userName"))
            {
                uName = sharedPreferences.getString("userName", "");
            }
            if (sharedPreferences.contains("userPassword"))
            {
                uPassword = sharedPreferences.getString("userPassword", "");

            }

            if(username.equals(uName) && password.equals(uPassword)){
                // Starting new page
                Intent i = new  Intent(getApplicationContext(),MovieListActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                //Toast.makeText(getApplicationContext(), "Logged In!!", Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(getApplicationContext(),
                        "Invalid Credentials",
                        Toast.LENGTH_LONG).show();

            }
        }else{
            Toast.makeText(getApplicationContext(),
                    "Please enter username and password",
                    Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}