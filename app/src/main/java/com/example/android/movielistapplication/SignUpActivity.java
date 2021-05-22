package com.example.android.movielistapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = SignUpActivity.class.getSimpleName();
    EditText mSignUpUserName, mSignUpUserPwd, mSignUpUserEmail, mSignUpUserPhoneNo;
    Button mSignUpButton;
    TextView mCompInfo;
    TableLayout mSignUpTableLayout;
    Spinner mProfessionDropdown;
    SharedPreferences sharedPreferences;
    Editor editor;
    private static final String SHARED_PREF_NAME = "LoginCred";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_activity);

        mSignUpUserName = (EditText) findViewById(R.id.sign_up_user_name);
        mSignUpUserPwd = (EditText) findViewById(R.id.sign_up_user_pwd);
        mSignUpUserEmail = (EditText) findViewById(R.id.sign_up_user_email);
        mSignUpUserPhoneNo = (EditText) findViewById(R.id.sign_up_user_ph_num);
        mSignUpButton = (Button) findViewById(R.id.sign_up_button);
        mCompInfo = (TextView) findViewById(R.id.sign_up_company_info);
        mCompInfo.setVisibility(View.INVISIBLE);
        mSignUpTableLayout = (TableLayout) findViewById(R.id.sign_up_table);
        mSignUpTableLayout.setVisibility(View.VISIBLE);
        mProfessionDropdown = (Spinner) findViewById(R.id.sign_up_profession);
        String[] professionList = new String[]{"Doctor", "Software Engineer", "Entrepreneur", "Teacher", "Builder", "Nurse", "Fireman"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, professionList);
        mProfessionDropdown.setAdapter(adapter);

        sharedPreferences = getApplicationContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Processing new user registration request");
                processSignUp();
            }
        });
    }

    void processSignUp() {
        Log.i(TAG, "processSignUp");
        String name = mSignUpUserName.getText().toString();
        String password = mSignUpUserPwd.getText().toString();
        String email = mSignUpUserEmail.getText().toString();
        String phoneNo = mSignUpUserPhoneNo.getText().toString();
        String profession = mProfessionDropdown.getSelectedItem().toString();

        if(mSignUpUserName.getText().length()<=0){
            Toast.makeText(SignUpActivity.this, "Enter name", Toast.LENGTH_SHORT).show();
        }
        else if( mSignUpUserPwd.getText().length()<=0){
            Toast.makeText(SignUpActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
        }
        else if( mSignUpUserEmail.getText().length()<=0){
            Toast.makeText(SignUpActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
        }
        else if( mSignUpUserPhoneNo.getText().length()<=0){
            Toast.makeText(SignUpActivity.this, "Enter phone number", Toast.LENGTH_SHORT).show();
        }
        else if (mProfessionDropdown.getSelectedItem().toString().length() <= 0) {
            Toast.makeText(SignUpActivity.this, "Select any Profession", Toast.LENGTH_SHORT).show();
        }
        else{
            editor.putString("userName", name);
            editor.putString("userPassword",password);
            editor.putString("userEmail",email);
            editor.putString("userPhoneNo",phoneNo);
            editor.putString("userProfession", profession);
            editor.commit();
            Toast.makeText(SignUpActivity.this, "Registered Successfully!", Toast.LENGTH_SHORT).show();
        }
        Log.i(TAG, "Launching Login Page");
        Intent loginPageIntent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(loginPageIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItemId = item.getItemId();
        if (selectedItemId == R.id.action_company_info) {
            Log.d(TAG, "Displaying company info");
            mSignUpTableLayout.setVisibility(View.INVISIBLE);
            mCompInfo.setVisibility(View.VISIBLE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
