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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.ram.rideindia.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UploadNotice extends AppCompatActivity {

    Spinner selectNoticeType;
    Spinner selectBranch;
    EditText noticeDate;
    final Calendar calendar=Calendar.getInstance();
    Button chooseNotice;
    EditText filePath;
    private static  final int IMAGE_PICK_CODE=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_notice);
        selectNoticeType=(Spinner)findViewById(R.id.spinner1);
        noticeDate=(EditText)findViewById(R.id.noticeDate);
        //chooseNotice=(Button)findViewById(R.id.chooseNotice) ;
        chooseNotice=(Button)findViewById(R.id.mynotice);
        filePath=(EditText)findViewById(R.id.show_path);
         String [] items=new String[]{"Notice Type","General_Notice","Special Notice","University Notice"};
         final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
         selectNoticeType.setAdapter(adapter);
         final DatePickerDialog.OnDateSetListener date=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
         selectNoticeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("item", " Hey you selected te Notice type is "+(String) parent.getItemAtPosition(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
               Log.e("UploadNotice","Hey nothing is selected from the list");

            }
        });
        selectBranch=(Spinner)findViewById(R.id.spinner2);
        String[] branchList= new String[]{"Select Branch","CSE","MECH","CIVIL","EE","ECE"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,branchList);
          selectBranch.setAdapter(arrayAdapter);
          selectBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                  Log.e("item", " Hey you selected te Branch  is "+(String) parent.getItemAtPosition(position));

              }

              @Override
              public void onNothingSelected(AdapterView<?> parent) {
                  Log.e("UploadNotice","Hey nothing is selected from the list");
                }
          });
          noticeDate.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View v, MotionEvent event) {
                  if(event.getAction() == MotionEvent.ACTION_DOWN)
                  {
                      new DatePickerDialog(UploadNotice.this,date,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
                  }
                  return false;
              }
          });

          chooseNotice.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                  intent.setType("*/*");
                  intent = Intent.createChooser(intent, "Choose a file");
                startActivityForResult(intent,IMAGE_PICK_CODE);
              }
          });
        Toast.makeText(this, "coming in Upload Notice page ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
                 try {
                     if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {

                         Uri uri = data.getData();
                         String src = uri.getPath();
                         filePath.setText(src);

//                         String path = data.getData().getPath();
                         Log.e("UploadNotice","The file path is "+src);
                        File file=new File(src);
                        OutputStream oos=new FileOutputStream("test.pdf");
                        byte[] buf=new byte[8192];
                         InputStream inputStream=new FileInputStream(file);
                         int c=0;
                         while((inputStream.read(buf,0,buf.length))>0)
                         {
                             oos.write(buf,0,c);
                             oos.flush();
                         }
                          oos.close();
                         System.out.println("pdf writing is finished ");
                         inputStream.close();

                     }
                 }catch (Exception e)
                 {
                     e.printStackTrace();
                 }
    }
    public String getPath(Uri uri) {

        String path = null;
        String[] projection = { MediaStore.Files.FileColumns.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if(cursor == null){
            path = uri.getPath();
        }
        else{
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndexOrThrow(projection[0]);
            path = cursor.getString(column_index);
            cursor.close();
        }

        return ((path == null || path.isEmpty()) ? (uri.getPath()) : path);
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        noticeDate.setText(sdf.format(calendar.getTime()));
    }


}


