package com.android.ram.rideindia.db;

import android.graphics.Bitmap;
import android.provider.BaseColumns;
import android.widget.Button;
import android.widget.EditText;

public class UserRegisterDbContract {



    public static final class  userRegister implements BaseColumns
    {
        public   static  final  String ADD_STUDENT_TABLE_NAME="Save_Student";
       public static final String TABLE_NAME="Users";
       public static final String USERNAME="user_name";
      public static  final String USEREMAIL="user_mail";
        public static final String USERPASSWORD="userr_password";

        //       public static final String USERADDRESS="user_address";
//       public static final String USERCONTACT="user_contact";
       public static final String MAILCODE="confirm_code";

       public static final String SNAME="student_name";
       public static final String SFATHERSNAME="student_fathers_name";
        public static final String SADMNO="student_adm_no";
        public static final String SDOB="student_date_of_birth";
        public static final String SADDRESS="student_address";
        public static final String SBRANCH="student_branch";
        public static final String SSEM="student_semester";
        public static final Bitmap SIMAGE =null;



    }
}
