package com.android.ram.rideindia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.ram.rideindia.UserEntry.UserLoginActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button admin_btn=(Button)findViewById(R.id.admin_login);
        Button student_btn=(Button)findViewById(R.id.student_login);

        admin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
                startActivity(intent);
            }
        });

        student_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
