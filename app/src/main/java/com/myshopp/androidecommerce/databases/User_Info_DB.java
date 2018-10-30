package com.myshopp.androidecommerce.databases;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.myshopp.androidecommerce.models.user_model.UserDetails;


/**
 * User_Info_DB creates the table User_Record and handles all CRUD operations relevant to User_Record
 **/

public class User_Info_DB {

    SQLiteDatabase db;

    // Table Name
    public static final String TABLE_USER_INFO = "User_Record";
    // Table Columns
    public static final String CUSTOMERS_ID                      = "customers_id";
    public static final String CUSTOMERS_FIRSTNAME               = "customers_firstname";
    public static final String CUSTOMERS_LASTNAME                = "customers_lastname";
    public static final String CUSTOMERS_EMAIL_ADDRESS           = "customers_email_address";
    public static final String CUSTOMERS_PASSWORD                = "customers_password";
    public static final String CUSTOMERS_DOB                     = "customers_dob";
    public static final String CUSTOMERS_TELEPHONE               = "customers_telephone";
    public static final String CUSTOMERS_FAX                     = "customers_fax";
    public static final String CUSTOMERS_GENDER                  = "customers_gender";
    public static final String CUSTOMERS_NEWSLETTER              = "customers_newsletter";
    public static final String CUSTOMERS_PICTURE                 = "customers_picture";




    //*********** Returns the Query to Create TABLE_USER_INFO ********//

    public static String createTable() {

        return "CREATE TABLE "+ TABLE_USER_INFO +
                "(" +
                    CUSTOMERS_ID               +" TEXT," +
                    CUSTOMERS_FIRSTNAME        +" TEXT," +
                    CUSTOMERS_LASTNAME         +" TEXT," +
                    CUSTOMERS_EMAIL_ADDRESS    +" TEXT," +
                    CUSTOMERS_PASSWORD         +" TEXT," +
                    CUSTOMERS_DOB              +" TEXT," +
                    CUSTOMERS_TELEPHONE        +" TEXT," +
                    CUSTOMERS_FAX              +" TEXT," +
                    CUSTOMERS_GENDER           +" TEXT," +
                    CUSTOMERS_NEWSLETTER       +" TEXT," +
                    CUSTOMERS_PICTURE          +" TEXT" +
                ")";
    }



    //*********** Insert New User Data ********//

    public void insertUserData(UserDetails user){
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        ContentValues values = new ContentValues();

        values.put(CUSTOMERS_ID,              user.getCustomersId());
        values.put(CUSTOMERS_FIRSTNAME,       user.getCustomersFirstname());
        values.put(CUSTOMERS_LASTNAME,        user.getCustomersLastname());
        values.put(CUSTOMERS_EMAIL_ADDRESS,   user.getCustomersEmailAddress());
        values.put(CUSTOMERS_PASSWORD,        user.getCustomersPassword());
        values.put(CUSTOMERS_DOB,             user.getCustomersDob());
        values.put(CUSTOMERS_TELEPHONE,       user.getCustomersTelephone());
        values.put(CUSTOMERS_FAX,             user.getCustomersFax());
        values.put(CUSTOMERS_GENDER,          user.getCustomersGender());
        values.put(CUSTOMERS_NEWSLETTER,      user.getCustomersNewsletter());
        values.put(CUSTOMERS_PICTURE,         user.getCustomersPicture());

        db.insert(TABLE_USER_INFO, null, values);

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }



    //*********** Get the Details of single User ********//

    public UserDetails getUserData(String userID){
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        Cursor cursor =  db.rawQuery( "SELECT * FROM "+ TABLE_USER_INFO +" WHERE "+ CUSTOMERS_ID +" =?", new String[] {userID});

        UserDetails userInfo = new UserDetails();


        if (cursor.moveToFirst()) {
            do {
                userInfo.setCustomersId(cursor.getString(0));
                userInfo.setCustomersFirstname(cursor.getString(1));
                userInfo.setCustomersLastname(cursor.getString(2));
                userInfo.setCustomersEmailAddress(cursor.getString(3));
                userInfo.setCustomersPassword(cursor.getString(4));
                userInfo.setCustomersDob(cursor.getString(5));
                userInfo.setCustomersTelephone(cursor.getString(6));
                userInfo.setCustomersFax(cursor.getString(7));
                userInfo.setCustomersGender(cursor.getString(8));
                userInfo.setCustomersNewsletter(cursor.getString(9));
                userInfo.setCustomersPicture(cursor.getString(10));

            } while (cursor.moveToNext());


            // close the Database
            DB_Manager.getInstance().closeDatabase();

            return userInfo;
        }

        return null;
    }



    //*********** Update the Details of existing User ********//

    public void updateUserData(UserDetails user){
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        ContentValues values = new ContentValues();

        values.put(CUSTOMERS_ID,              user.getCustomersId());
        values.put(CUSTOMERS_FIRSTNAME,       user.getCustomersFirstname());
        values.put(CUSTOMERS_LASTNAME,        user.getCustomersLastname());
        values.put(CUSTOMERS_DOB,             user.getCustomersDob());
        values.put(CUSTOMERS_TELEPHONE,       user.getCustomersTelephone());
        values.put(CUSTOMERS_FAX,             user.getCustomersFax());
        values.put(CUSTOMERS_GENDER,          user.getCustomersGender());
        values.put(CUSTOMERS_NEWSLETTER,      user.getCustomersNewsletter());
        values.put(CUSTOMERS_PICTURE,         user.getCustomersPicture());

        db.update(TABLE_USER_INFO, values, CUSTOMERS_EMAIL_ADDRESS +" = ?", new String[]{user.getCustomersEmailAddress()});

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }



    //*********** Update the Password of existing User ********//

    public void updateUserPassword(UserDetails user){
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        ContentValues values = new ContentValues();

        values.put(CUSTOMERS_PASSWORD,        user.getCustomersPassword());

        db.update(TABLE_USER_INFO, values, CUSTOMERS_ID +" = ?", new String[]{user.getCustomersId()});

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }



    //*********** Delete the Data of the User ********//

    public void deleteUserData(String userID){
        // get and open SQLiteDatabase Instance from static method of DB_Manager class
        db = DB_Manager.getInstance().openDatabase();

        db.delete(TABLE_USER_INFO, CUSTOMERS_ID +" = ?", new String[] {userID});

        // close the Database
        DB_Manager.getInstance().closeDatabase();
    }
}
