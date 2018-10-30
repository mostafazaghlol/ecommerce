package com.myshopp.androidecommerce.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.myshopp.androidecommerce.customs.CircularImageView;

import com.myshopp.androidecommerce.activities.MainActivity;
import com.myshopp.androidecommerce.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.myshopp.androidecommerce.constant.ConstantValues;
import com.myshopp.androidecommerce.customs.DialogLoader;
import com.myshopp.androidecommerce.databases.User_Info_DB;
import com.myshopp.androidecommerce.models.user_model.UserData;
import com.myshopp.androidecommerce.models.user_model.UserDetails;
import com.myshopp.androidecommerce.network.APIClient;
import com.myshopp.androidecommerce.utils.CheckPermissions;
import com.myshopp.androidecommerce.utils.Utilities;
import com.myshopp.androidecommerce.utils.ImagePicker;
import com.myshopp.androidecommerce.utils.ValidateInputs;
import retrofit2.Call;
import retrofit2.Callback;


public class Update_Account extends Fragment {

    View rootView;
    String customers_id;
    String profileImageCurrent = "";
    String profileImageChanged = "";
    private static final int PICK_IMAGE_ID = 360;           // the number doesn't matter
    
    Button updateInfoBtn;
    CircularImageView user_photo;
    FloatingActionButton user_photo_edit_fab;
    EditText input_first_name, input_last_name, input_dob, input_contact_no, input_current_password, input_new_password;

    DialogLoader dialogLoader;

    UserDetails userInfo;
    User_Info_DB userInfoDB = new User_Info_DB();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.update_account, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.actionAccount));

        // Get the CustomerID from SharedPreferences
        customers_id = this.getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).getString("userID", "");


        // Binding Layout Views
        user_photo = (CircularImageView) rootView.findViewById(R.id.user_photo);
        input_first_name = (EditText) rootView.findViewById(R.id.firstname);
        input_last_name = (EditText) rootView.findViewById(R.id.lastname);
        input_dob = (EditText) rootView.findViewById(R.id.dob);
        input_contact_no = (EditText) rootView.findViewById(R.id.contact);
        input_current_password = (EditText) rootView.findViewById(R.id.current_password);
        input_new_password = (EditText) rootView.findViewById(R.id.new_password);
        updateInfoBtn = (Button) rootView.findViewById(R.id.updateInfoBtn);
        user_photo_edit_fab = (FloatingActionButton) rootView.findViewById(R.id.user_photo_edit_fab);


        // Set KeyListener of some View to null
        input_dob.setKeyListener(null);


        dialogLoader = new DialogLoader(getContext());

        // Get the User's Info from the Local Databases User_Info_DB
        userInfo = userInfoDB.getUserData(customers_id);
        

        // Set User's Info to Form Inputs
        input_first_name.setText(userInfo.getCustomersFirstname());
        input_last_name.setText(userInfo.getCustomersLastname());
        input_contact_no.setText(userInfo.getCustomersTelephone());


        // Set User's Date of Birth
        if (userInfo.getCustomersDob().equalsIgnoreCase("0000-00-00 00:00:00")) {
            input_dob.setText("");
        }
        else {
            // Get the String of Date from userInfo
            String dateString = userInfo.getCustomersDob();
            // Set Date Format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            // Convert String of Date to Date Format
            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            input_dob.setText(dateFormat.format(convertedDate));
        }


        // Set User's Photo
        if (userInfo.getCustomersPicture() != null && !userInfo.getCustomersPicture().isEmpty()){
            profileImageCurrent = userInfo.getCustomersPicture();
            Glide.with(this)
                    .load(ConstantValues.ECOMMERCE_URL+profileImageCurrent).asBitmap()
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .into(user_photo);
            
        }
        else {
            profileImageCurrent = "";
            Glide.with(this)
                    .load(R.drawable.profile).asBitmap()
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .into(user_photo);
        }



        // Handle Touch event of input_dob EditText
        input_dob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    // Get Calendar instance
                    final Calendar calendar = Calendar.getInstance();

                    // Initialize DateSetListener of DatePickerDialog
                    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                            // Set the selected Date Info to Calendar instance
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, monthOfYear);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            // Set Date Format
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

                            // Set Date in input_dob EditText
                            input_dob.setText(dateFormat.format(calendar.getTime()));
                        }
                    };


                    // Initialize DatePickerDialog
                    DatePickerDialog datePicker = new DatePickerDialog
                            (
                                    getContext(),
                                    date,
                                    calendar.get(Calendar.YEAR),
                                    calendar.get(Calendar.MONTH),
                                    calendar.get(Calendar.DAY_OF_MONTH)
                            );

                    // Show datePicker Dialog
                    datePicker.show();
                }

                return false;
            }
        });



        // Handle Click event of user_photo_edit_fab FAB
        user_photo_edit_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if (CheckPermissions.is_CAMERA_PermissionGranted()  &&  CheckPermissions.is_STORAGE_PermissionGranted()) {
                    pickImage();
                }
                else {
                    requestPermissions
                        (
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            CheckPermissions.PERMISSIONS_REQUEST_CAMERA
                        );
                }
                
            }
        });


        // Handle Click event of updateInfoBtn Button
        updateInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate User's Info Form Inputs
                boolean isValidData = validateInfoForm();

                if (isValidData) {
                    if ("".equalsIgnoreCase(input_current_password.getText().toString()) &&  "".equalsIgnoreCase(input_new_password.getText().toString())) {
                        // Proceed User Registration
                        updateCustomerInfo();
                    }
                    else {
                        if (validatePasswordForm())
                            updateCustomerInfo();
                    }
                    
                }
            }
        });


        return rootView;

    }



    //*********** Picks User Profile Image from Gallery or Camera ********//

    private void pickImage() {
        // Intent with Image Picker Apps from the static method of ImagePicker class
        Intent chooseImageIntent = ImagePicker.getImagePickerIntent(getContext());
        
        // Start Activity with Image Picker Intent
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }
    
    
    
    //*********** Receives the result from a previous call of startActivityForResult(Intent, int) ********//
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_ID) {

                // Get the User Selected Image as Bitmap from the static method of ImagePicker class
                Bitmap bitmap = ImagePicker.getImageFromResult(this.getActivity(), resultCode, data);

                // Upload the Bitmap to ImageView
                user_photo.setImageBitmap(bitmap);

                // Get the converted Bitmap as Base64ImageString from the static method of Helper class
                profileImageChanged = Utilities.getBase64ImageStringFromBitmap(bitmap);
            }
        }
    }
    
    
    
    //*********** This method is invoked for every call on requestPermissions(Activity, String[], int) ********//
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == CheckPermissions.PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // The Camera and Storage Permission is granted
                pickImage();
            }
            else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                    // Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(getString(R.string.permission_camera_storage));
                    builder.setMessage(getString(R.string.permission_camera_storage_needed));
                    builder.setPositiveButton(getString(R.string.grant), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            requestPermissions
                                (
                                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    CheckPermissions.PERMISSIONS_REQUEST_CAMERA
                                );
                        }
                    });
                    builder.setNegativeButton(getString(R.string.not_now), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
                else {
                    Toast.makeText(getContext(),getString(R.string.permission_rejected), Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    
    
    //*********** Updates User's Personal Information ********//

    private void updateCustomerInfo() {

        dialogLoader.showProgressDialog();
        

        Call<UserData> call = APIClient.getInstance()
                .updateCustomerInfo
                        (
                                customers_id,
                                input_first_name.getText().toString().trim(),
                                input_last_name.getText().toString().trim(),
                                input_contact_no.getText().toString().trim(),
                                input_dob.getText().toString().trim(),
                                profileImageChanged,
                                profileImageCurrent,
                                input_new_password.getText().toString().trim()
                        );

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, retrofit2.Response<UserData> response) {

                dialogLoader.hideProgressDialog();

                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")  &&  response.body().getData() != null) {
                        // User's Info has been Updated.
                        
                        UserDetails userDetails = response.body().getData().get(0);

                        // Update in Local Databases as well
                        userInfoDB.updateUserData(userDetails);
                        userInfoDB.updateUserPassword(userDetails);
    
                        // Get the User's Info from the Local Databases User_Info_DB
                        userInfo = userInfoDB.getUserData(customers_id);
                        
                        // Set the userName in SharedPreferences
                        SharedPreferences.Editor editor = getContext().getSharedPreferences("UserInfo", getContext().MODE_PRIVATE).edit();
                        editor.putString("userName", userDetails.getCustomersFirstname()+" "+userDetails.getCustomersLastname());
                        editor.apply();

                        // Set the User Info in the NavigationDrawer Header with the public method of MainActivity
                        ((MainActivity)getActivity()).setupExpandableDrawerHeader();

                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Unable to Update User's Info.
                        Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();

                    }
                    else {
                        // Unable to get Success status
                        Toast.makeText(getContext(), getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }
                    
                }
                else {
                    Toast.makeText(getContext(), ""+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    
    
    //*********** Validate User Info Form Inputs ********//
    
    private boolean validateInfoForm() {
        if (!ValidateInputs.isValidName(input_first_name.getText().toString().trim())) {
            input_first_name.setError(getString(R.string.invalid_first_name));
            return false;
        } else if (!ValidateInputs.isValidName(input_last_name.getText().toString().trim())) {
            input_last_name.setError(getString(R.string.invalid_last_name));
            return false;
        } else {
            return true;
        }
    }



    //*********** Validate Password Info Form Inputs ********//

    private boolean validatePasswordForm() {
        if (!input_current_password.getText().toString().trim().equals(userInfo.getCustomersPassword())) {
            input_current_password.setError(getString(R.string.invalid_password));
            return false;
        } else if (!ValidateInputs.isValidPassword(input_new_password.getText().toString().trim())) {
            input_new_password.setError(getString(R.string.invalid_password));
            return false;
        } else {
            return true;
        }
    }

}
