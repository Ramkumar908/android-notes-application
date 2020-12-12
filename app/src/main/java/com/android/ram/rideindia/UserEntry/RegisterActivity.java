package com.android.ram.rideindia.UserEntry;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.ram.rideindia.Mailer.GMailSender;
import com.android.ram.rideindia.R;
import com.android.ram.rideindia.db.UserRegisterDbContract;
import com.android.ram.rideindia.db.UserRegisterDbHelper;

import java.util.Random;

public class RegisterActivity extends AppCompatActivity {
    private static final String LOG_TAG=RegisterActivity.class.getName();
    EditText userName,userMail,userAddress,userContact,userPass,userRePass;
   private String verificationCode;
   Button registerBtn;
    GMailSender gMailSender;
    String mailUser="housekii.info@gmail.com";
    String mailPassword="housekiiservice";
    String mailSubject="Testing Subject";
    String mailBody="";
    String mailRecipient="";
    UserRegisterDbHelper dbHelper;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView login_screen=(TextView)findViewById(R.id.login_screen);
        dbHelper=new UserRegisterDbHelper(this);

        //for showing toast that user just enter in the register page
        Toast.makeText(this,"Enter in Register page ",Toast.LENGTH_SHORT).show();
        // fetching all the field value that the user input in the register field aue register page
        userName=(EditText)findViewById(R.id.usernameRegister);
        userMail=(EditText)findViewById(R.id.userMailRegister);
        userPass=(EditText)findViewById(R.id.userPassRegister) ;
        userRePass=(EditText)findViewById(R.id.rePassRegister) ;
       // userAddress=(EditText)findViewById(R.id.rePass);
       // userContact=(EditText)findViewById(R.id.userContact);
        registerBtn=(Button)findViewById(R.id.register);



        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // here we generate the random code for sending mail conformation and send to te user after register button clicked
                Random r=new Random();
                int mailCode=r.nextInt(999999);
                verificationCode=String.format("%06d",mailCode);
                gMailSender=new GMailSender(mailUser,mailPassword);
                mailSubject="Verification Code";
                mailBody=verificationCode;
                mailRecipient=userMail.getText().toString();
                Log.e(LOG_TAG,"The random code is "+verificationCode);
                Log.e(LOG_TAG,"The usermail is "+userMail.getText().toString());

                new MyAsyncClass().execute();
                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues contentValues=new ContentValues();
                contentValues.put(UserRegisterDbContract.userRegister.USERNAME,userName.getText().toString());
                contentValues.put(UserRegisterDbContract.userRegister.USEREMAIL,userMail.getText().toString());
                contentValues.put(UserRegisterDbContract.userRegister.USERPASSWORD,userPass.getText().toString());
                contentValues.put(UserRegisterDbContract.userRegister.MAILCODE,verificationCode);
               long res= db.insert(UserRegisterDbContract.userRegister.TABLE_NAME,null,contentValues);
                if(res==-1)
                {
                    Log.e(LOG_TAG,"Some problem to send mail please try again");
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Data inserted successfully",Toast.LENGTH_LONG).show();
//                    Intent intent=new Intent(getApplicationContext(),MailVerification.class);
//                    intent.putExtra("Verification_code",verificationCode);
//                    startActivity(intent);
                }

            }
        });
        // To send the user in the login page if the user has already register in the app
        login_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),UserLoginActivity.class);
                startActivity(intent);
            }
        });
    }

    class MyAsyncClass extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            pDialog = new ProgressDialog(RegisterActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();

        }

        @Override

        protected Void doInBackground(Void... mApi) {
            try {

                // Add subject, Body, your mail Id, and receiver mail Id.
                gMailSender.sendMail(mailSubject, mailBody, mailUser, mailRecipient);
                Log.d("send", "done");

            }
            catch (Exception ex) {
                Log.d("exceptionsending", ex.toString());
            }
            return null;
        }

        @Override

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);
            pDialog.cancel();

            Toast.makeText(RegisterActivity.this, "mail send", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),UserVerification.class);
            intent.putExtra("Verification_code",verificationCode);
            startActivity(intent);

        }
    }
}
