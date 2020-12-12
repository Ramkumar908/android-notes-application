package com.android.ram.rideindia.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.ram.rideindia.R;

public class admin_home_activity extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actcivity_admin_home);
        Toast.makeText(this,"welcome to admin Dashboard",Toast.LENGTH_SHORT).show();
    }


    public  void  addStudent(View view)

    {
        Intent intent=new Intent(getApplicationContext(),addNewStudent.class);
        startActivity(intent);
    }


    public  void uploadNotice1(View view)
    {
        Intent intent=new Intent(getApplicationContext(),UploadNotice.class);
        startActivity(intent);

    }
}
