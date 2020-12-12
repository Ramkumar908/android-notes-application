package com.android.ram.rideindia.admin;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.ram.rideindia.R;
import com.android.ram.rideindia.db.UserRegisterDbHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class addNewStudent extends AppCompatActivity {
    private static final int REQUEST_CODE_GALlARY=999;
    Button    choosebtn;
    EditText  SName;
    EditText  SFathersName;
    EditText  SAdmNo;
    EditText  SDob;
    EditText  SAddress;
    EditText  SBranch;
    EditText  SSem;
    UserRegisterDbHelper dbHelper;
    private  static  final  int  PICK_IMAGE=100;
    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student_layout);
        dbHelper=new UserRegisterDbHelper(this);
        choosebtn=(Button)findViewById(R.id.chooseImage);


         final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        SDob=(EditText)findViewById(R.id.sDateOfBirth);
        SDob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    new DatePickerDialog(addNewStudent.this, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
                return false;
            }
        });
        choosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
                Intent intent=new Intent(Intent.ACTION_PICK, Uri.parse(
                        "content://media/internal/images/media"
                ));
                startActivityForResult(intent,PICK_IMAGE);
            }
        });
        Toast.makeText(this, "Hello I am Ready to add Student page ", Toast.LENGTH_SHORT).show();
    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        SDob.setText(sdf.format(myCalendar.getTime()));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        Log.e("UserRegisterActivity","hello onActivityResult is  calling and try to run ");

        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE)
        {
            Uri uri=data.getData();
            String x=getPath(uri);
            Toast.makeText(this, x, Toast.LENGTH_SHORT).show();
            SName=(EditText)findViewById(R.id.sName);
            SFathersName=(EditText)findViewById(R.id.sFathersName);
            SAdmNo=(EditText)findViewById(R.id.sAdmNo);
            //SDob=(EditText)findViewById(R.id.sDateOfBirth);
            SAddress=(EditText)findViewById(R.id.sAddress);
            SBranch=(EditText)findViewById(R.id.sBranch);
            SSem=(EditText)findViewById(R.id.sSemester);
            String name=SName.getText().toString();
            String fatherName=SFathersName.getText().toString();
            String AdmNo=SAdmNo.getText().toString();
            String Dob=SDob.getText().toString();
            String Address=SAddress.getText().toString();
            String Branch=SBranch.getText().toString();
            String Sem=SSem.getText().toString();
             if(dbHelper.insertNewStudent(name,fatherName,AdmNo,Dob,Address,Branch,Sem,x))
            {
                Toast.makeText(this,"Hey your data is upload successfully",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),admin_home_activity.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this,"Something is wrong while uploading your data ",Toast.LENGTH_SHORT).show();

            }
        }
    }

    public String getPath(Uri uri) {

        Log.e("UserRegisterActivity","hello getPathUri is  calling and try to run ");

        if(uri==null )return  null;
        String [] projection={MediaStore.Images.Media.DATA};
        Cursor cursor=managedQuery(uri,projection,null,null,null);
        if(cursor!=null)
        {
            int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }




}
