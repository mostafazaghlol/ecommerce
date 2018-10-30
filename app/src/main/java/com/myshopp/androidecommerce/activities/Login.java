package com.myshopp.androidecommerce.activities;


import android.support.annotation.Nullable;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.myshopp.androidecommerce.R;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.myshopp.androidecommerce.network.APIClient;
import com.myshopp.androidecommerce.customs.DialogLoader;
import com.myshopp.androidecommerce.network.StartAppRequests;
import com.myshopp.androidecommerce.utils.LocaleHelper;
import com.myshopp.androidecommerce.utils.ValidateInputs;
import com.myshopp.androidecommerce.app.MyAppPrefsManager;
import com.myshopp.androidecommerce.databases.User_Info_DB;
import com.myshopp.androidecommerce.constant.ConstantValues;
import com.myshopp.androidecommerce.models.user_model.UserData;
import com.myshopp.androidecommerce.models.user_model.UserDetails;


/**
 * Login activity handles User's Email, Facebook and Google Login
 **/


public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    View parentView;
    Toolbar toolbar;
    ActionBar actionBar;

    EditText user_email, user_password;
    TextView forgotPasswordText, signupText;
    Button loginBtn, facebookLoginBtn, googleLoginBtn;
    
    
    User_Info_DB userInfoDB;
    DialogLoader dialogLoader;
    
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    private CallbackManager callbackManager;

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions mGoogleSignInOptions;

    private static final int RC_SIGN_IN = 100;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize Facebook SDk for Facebook Login
        FacebookSdk.sdkInitialize(getApplicationContext());

        // Initializing Google SDK for Google Login
        mGoogleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        
        setContentView(R.layout.login);
        


        // setting Toolbar
        toolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.login));
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        
        
        // Binding Layout Views
        user_email = (EditText) findViewById(R.id.user_email);
        user_password = (EditText) findViewById(R.id.user_password);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        facebookLoginBtn = (Button) findViewById(R.id.facebookLoginBtn);
        googleLoginBtn = (Button) findViewById(R.id.googleLoginBtn);
        signupText = (TextView) findViewById(R.id.login_signupText);
        forgotPasswordText = (TextView) findViewById(R.id.forgot_password_text);
    
        parentView = signupText;
    
        
        if (ConstantValues.IS_GOOGLE_LOGIN_ENABLED) {
            googleLoginBtn.setVisibility(View.VISIBLE);
        } else {
            googleLoginBtn.setVisibility(View.GONE);
        }
        
        if (ConstantValues.IS_FACEBOOK_LOGIN_ENABLED) {
            facebookLoginBtn.setVisibility(View.VISIBLE);
        } else {
            facebookLoginBtn.setVisibility(View.GONE);
        }
        

        dialogLoader = new DialogLoader(Login.this);
        
        userInfoDB = new User_Info_DB();
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
    
        
        user_email.setText(sharedPreferences.getString("userEmail", null));


        // Register Callback for Facebook LoginManager
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                // Get Access Token and proceed Facebook Registration
                String accessToken = loginResult.getAccessToken().getToken();
                processFacebookRegistration(accessToken);
                
            }
            @Override
            public void onCancel() {
                // If User Canceled
            }
            @Override
            public void onError(FacebookException e) {
                // If Login Fails
                Toast.makeText(Login.this, "FacebookException : "+e, Toast.LENGTH_LONG).show();
            }
        });
        
        
        
        // Initializing Google API Client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(Login.this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, mGoogleSignInOptions)
                .build();
        
        
        
        // Handle on Forgot Password Click
        forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(Login.this);
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_input, null);
                dialog.setView(dialogView);
                dialog.setCancelable(true);
    
                final Button dialog_button = (Button) dialogView.findViewById(R.id.dialog_button);
                final EditText dialog_input = (EditText) dialogView.findViewById(R.id.dialog_input);
                final TextView dialog_title = (TextView) dialogView.findViewById(R.id.dialog_title);
    
                dialog_button.setText(getString(R.string.send));
                dialog_title.setText(getString(R.string.forgot_your_password));
                
    
                final AlertDialog alertDialog = dialog.create();
                alertDialog.show();
                
                dialog_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        
                        if (ValidateInputs.isValidEmail(dialog_input.getText().toString().trim())) {
                            // Request for Password Reset
                            processForgotPassword(dialog_input.getText().toString());
                            
                        }
                        else {
                            Snackbar.make(parentView, getString(R.string.invalid_email), Snackbar.LENGTH_LONG).show();
                        }
    
                        alertDialog.dismiss();
                    }
                });
            }
        });
    
    
    
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SignUp Activity
                startActivity(new Intent(Login.this, Signup.class));
                overridePendingTransition(R.anim.enter_from_left, R.anim.exit_out_left);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate Login Form Inputs
                boolean isValidData = validateLogin();

                if (isValidData) {

                    // Proceed User Login
                    processLogin();
                }
            }
        });


        // Handle Facebook Login Button click
        facebookLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout the User if already Logged-in
                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logOut();
                }

                // Login and Access User Date
                LoginManager.getInstance().logInWithReadPermissions(Login.this, Arrays.asList("public_profile", "user_friends"));
            }
        });
    
        
        // Handle Google Login Button click
        googleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Logout the User if already Logged-in
                if (mGoogleApiClient.isConnected()){
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                }

                // Get the Google SignIn Intent
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                
                // Start Activity with Google SignIn Intent
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
        
    }
    
    
    
    //*********** Called if Connection fails for Google Login ********//
    
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // If Connection fails for GoogleApiClient
    }
    
    
    
    //*********** Receives the result from a previous call of startActivityForResult(Intent, int) ********//
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            // Handle Activity Result
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        }
        
        callbackManager.onActivityResult(requestCode, resultCode, data);
        
    }



    //*********** Get Google Account Details from GoogleSignInResult ********//

    private void handleGoogleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            // Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();

            // Proceed Google Registration
            processGoogleRegistration(acct);

        } else {
            // If Login Fails
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }



    //*********** Proceed Login with User Email and Password ********//

    private void processLogin() {

        dialogLoader.showProgressDialog();

        Call<UserData> call = APIClient.getInstance()
                .processLogin
                        (
                                user_email.getText().toString().trim(),
                                user_password.getText().toString().trim()
                        );

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {

                dialogLoader.hideProgressDialog();

                if (response.isSuccessful()) {

                    if (response.body().getSuccess().equalsIgnoreCase("1") || response.body().getSuccess().equalsIgnoreCase("2"))
                    {
                        // Get the User Details from Response
                        UserDetails userDetails = response.body().getData().get(0);

                        // Save User Data to Local Databases
                        if (userInfoDB.getUserData(userDetails.getCustomersId()) != null) {
                            // User already exists
                            userInfoDB.updateUserData(userDetails);
                        } else {
                            // Insert Details of New User
                            userInfoDB.insertUserData(userDetails);
                        }

                        // Save necessary details in SharedPrefs
                        editor = sharedPreferences.edit();
                        editor.putString("userID", userDetails.getCustomersId());
                        editor.putString("userEmail", userDetails.getCustomersEmailAddress());
                        editor.putString("userName", userDetails.getCustomersFirstname()+" "+userDetails.getCustomersLastname());
                        editor.putString("userDefaultAddressID", userDetails.getCustomersDefaultAddressId());
                        editor.putBoolean("isLogged_in", true);
                        editor.apply();
    
                        // Set UserLoggedIn in MyAppPrefsManager
                        MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                        myAppPrefsManager.setUserLoggedIn(true);
    
                        // Set isLogged_in of ConstantValues
                        ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();
    
                        
                        StartAppRequests.RegisterDeviceForFCM(Login.this);
                        
                        
                        // Navigate back to MainActivity
                        Intent i = new Intent(Login.this, MainActivity.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0"))
                    {
                        // Get the Error Message from Response
                        String message = response.body().getMessage();
                        Snackbar.make(parentView, message, Snackbar.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(Login.this, getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    // Show the Error Message
                    Toast.makeText(Login.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(Login.this, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Proceed Forgot Password Request ********//

    private void processForgotPassword(String email) {
    
        dialogLoader.showProgressDialog();

        Call<UserData> call = APIClient.getInstance()
                .processForgotPassword
                        (
                                email
                        );

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
    
                dialogLoader.hideProgressDialog();
                
                if (response.isSuccessful()) {
                    // Show the Response Message
                    String message = response.body().getMessage();
                    Snackbar.make(parentView, message, Snackbar.LENGTH_LONG).show();

                } else {
                    // Show the Error Message
                    Toast.makeText(Login.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(Login.this, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Proceed Facebook Registration Request ********//

    private void processFacebookRegistration(String access_token) {

        dialogLoader.showProgressDialog();
    
        Log.i("test", access_token);
    

        Call<UserData> call = APIClient.getInstance()
                .facebookRegistration
                        (
                                access_token
                        );

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {

                dialogLoader.hideProgressDialog();

                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1") || response.body().getSuccess().equalsIgnoreCase("2"))
                    {
                        // Get the User Details from Response
                        UserDetails userDetails = response.body().getData().get(0);

                        // Save User Data to Local Databases
                        if (userInfoDB.getUserData(userDetails.getCustomersId()) != null) {
                            // User already exists
                            userInfoDB.updateUserData(userDetails);
                        } else {
                            // Insert Details of New User
                            userInfoDB.insertUserData(userDetails);
                        }

                        // Save necessary details in SharedPrefs
                        editor = sharedPreferences.edit();
                        editor.putString("userID", userDetails.getCustomersId());
                        editor.putString("userEmail", userDetails.getCustomersEmailAddress());
                        editor.putString("userName", userDetails.getCustomersFirstname()+" "+userDetails.getCustomersLastname());
                        editor.putString("userDefaultAddressID", userDetails.getCustomersDefaultAddressId());
                        editor.putBoolean("isLogged_in", true);
                        editor.apply();
    
                        
                        // Set UserLoggedIn in MyAppPrefsManager
                        MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                        myAppPrefsManager.setUserLoggedIn(true);
    
                        // Set isLogged_in of ConstantValues
                        ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();
    
                        StartAppRequests.RegisterDeviceForFCM(Login.this);
                        
                        
                        // Navigate back to MainActivity
                        Intent i = new Intent(Login.this, MainActivity.class);
                        startActivity(i);
                        finish();
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);

                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        // Get the Error Message from Response
                        String message = response.body().getMessage();
                        Snackbar.make(parentView, message, Snackbar.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(Login.this, getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Show the Error Message
                    Toast.makeText(Login.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(Login.this, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Proceed Google Registration Request ********//

    private void processGoogleRegistration(GoogleSignInAccount account) {

        dialogLoader.showProgressDialog();

        String photoURL = account.getPhotoUrl() != null ? account.getPhotoUrl().toString() : "";

        Call<UserData> call = APIClient.getInstance()
                .googleRegistration
                        (
                                account.getIdToken(),
                                account.getId(),
                                account.getGivenName(),
                                account.getFamilyName(),
                                account.getEmail(),
                                photoURL
                        );

        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {

                dialogLoader.hideProgressDialog();

                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1") || response.body().getSuccess().equalsIgnoreCase("2")) {
                        
                        UserDetails userDetails = response.body().getData().get(0);

                        // Save User Data to Local Databases
                        if (userInfoDB.getUserData(userDetails.getCustomersId()) != null) {
                            userInfoDB.updateUserData(userDetails);
                        }
                        else {
                            userInfoDB.insertUserData(userDetails);
                        }

                        // Save necessary details in SharedPrefs
                        editor = sharedPreferences.edit();
                        editor.putString("userID", userDetails.getCustomersId());
                        editor.putString("userEmail", userDetails.getCustomersEmailAddress());
                        editor.putString("userName", userDetails.getCustomersFirstname()+" "+userDetails.getCustomersLastname());
                        editor.putString("userDefaultAddressID", userDetails.getCustomersDefaultAddressId());
                        editor.apply();
    
                        
                        // Set UserLoggedIn in MyAppPrefsManager
                        MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(Login.this);
                        myAppPrefsManager.setUserLoggedIn(true);
    
                        ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();
    
                        StartAppRequests.RegisterDeviceForFCM(Login.this);
                        

                        // Navigate back to MainActivity
                        startActivity(new Intent(Login.this, MainActivity.class));
                        finish();
                        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);

                        
                    }
                    else if (response.body().getSuccess().equalsIgnoreCase("0")) {
                        Snackbar.make(parentView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                        
                    }
                    else {
                        Toast.makeText(Login.this, getString(R.string.unexpected_response), Toast.LENGTH_SHORT).show();
                    }
                    
                }
                else {
                    // Show the Error Message
                    Toast.makeText(Login.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(Login.this, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }



    //*********** Validate Login Form Inputs ********//

    private boolean validateLogin() {
        if (!ValidateInputs.isValidEmail(user_email.getText().toString().trim())) {
            user_email.setError(getString(R.string.invalid_email));
            return false;
        }
        else if (!ValidateInputs.isValidPassword(user_password.getText().toString().trim())) {
            user_password.setError(getString(R.string.invalid_password));
            return false;
        }
        else {
            return true;
        }
    }
    
    
    
    //*********** Set the Base Context for the ContextWrapper ********//
    
    @Override
    protected void attachBaseContext(Context newBase) {
    
        String languageCode = ConstantValues.LANGUAGE_CODE;
        if ("".equalsIgnoreCase(languageCode))
            languageCode = ConstantValues.LANGUAGE_CODE = "en";
    
        super.attachBaseContext(LocaleHelper.wrapLocale(newBase, languageCode));
    }
    
    
    
    //*********** Called when the Activity has detected the User pressed the Back key ********//
    
    @Override
    public void onBackPressed() {
        
        // Navigate back to MainActivity
        startActivity(new Intent(Login.this, MainActivity.class));
        finish();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
    }
    
}

