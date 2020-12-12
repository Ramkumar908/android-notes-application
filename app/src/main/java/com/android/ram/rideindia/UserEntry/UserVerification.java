package com.android.ram.rideindia.UserEntry;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.ram.rideindia.R;

public class UserVerification extends AppCompatActivity {
    private static final String LOG_TAG=UserVerification.class.getName();

  EditText otp;
  String registerOtp;
  String otp_user;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usr_verify);
        otp=(EditText)findViewById(R.id.mail_code);
        final Intent intent=getIntent();
        otp_user= intent.getStringExtra("Verification_code");
Button button=(Button)findViewById(R.id.send_otp);
button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String userCode=otp.getText().toString();

        if(otp_user==null)
        {
            Toast.makeText(UserVerification.this, "hey Please enter the code again", Toast.LENGTH_SHORT).show();
        }
        if(otp_user.equals(userCode))
        {
            Toast.makeText(UserVerification.this, "hey You are successfully verified your code is"+userCode, Toast.LENGTH_SHORT).show();
            Intent intent1=new Intent(getApplicationContext(),UserLoginActivity.class);
            startActivity(intent1);
        }
        else
        {
            Toast.makeText(UserVerification.this, "hey Please enter the correct code again  ", Toast.LENGTH_SHORT).show();

        }
    }
});



    }
}
