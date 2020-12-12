package com.android.ram.rideindia.UserEntry;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.ram.rideindia.R;
import com.android.ram.rideindia.admin.admin_home_activity;
import com.android.ram.rideindia.db.UserRegisterDbHelper;

public class UserLoginActivity  extends AppCompatActivity {


    EditText username,userpassword;
    private  String username1="";
    private  String userPassword1;
    Button login_btn;
    UserRegisterDbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



         login_btn=(Button)findViewById(R.id.login_btn);

        db=new UserRegisterDbHelper(this);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=(EditText)findViewById(R.id.username);
                userpassword=(EditText)findViewById(R.id.user_password);
                username1=username.getText().toString();
                userPassword1=userpassword.getText().toString();
                boolean userExist=db.checkusernamepassword(username1,userPassword1);
                Log.e("UserLoginActivity","Hello the return Boolean value is"+userExist);
                if(username1==null||userPassword1==null)
                {
                    Toast.makeText(getApplicationContext(),"Filed can not be empty",Toast.LENGTH_SHORT).show();
                }
               if(userExist==true)
               {

                     Intent intent=new Intent(getApplicationContext(), admin_home_activity.class);
                     startActivity(intent);
               }
                else
               {
                    Toast.makeText(getApplicationContext(),"user not exists please register",Toast.LENGTH_SHORT).show();

                }
            }
        });

    }



    public  void register(View view)
    {
        Intent intent=new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(intent);

    }

    }
