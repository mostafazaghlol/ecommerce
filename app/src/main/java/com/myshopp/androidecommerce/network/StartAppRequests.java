package com.myshopp.androidecommerce.network;

import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import android.content.SharedPreferences;

import com.myshopp.androidecommerce.R;
import com.onesignal.OneSignal;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.IOException;

import com.myshopp.androidecommerce.app.App;
import com.myshopp.androidecommerce.utils.Utilities;
import com.myshopp.androidecommerce.constant.ConstantValues;
import com.myshopp.androidecommerce.models.user_model.UserData;
import com.myshopp.androidecommerce.models.banner_model.BannerData;
import com.myshopp.androidecommerce.models.category_model.CategoryData;
import com.myshopp.androidecommerce.models.device_model.AppSettingsData;
import com.myshopp.androidecommerce.models.device_model.DeviceInfo;
import com.myshopp.androidecommerce.models.pages_model.PagesDetails;
import com.myshopp.androidecommerce.models.pages_model.PagesData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


/**
 * StartAppRequests contains some Methods and API Requests, that are Executed on Application Startup
 **/

public class StartAppRequests {

    private App app = new App();


    public StartAppRequests(Context context) {
        app = ((App) context.getApplicationContext());
    }



    //*********** Contains all methods to Execute on Startup ********//

    public void StartRequests(){

        RequestBanners();
        RequestAllCategories();
        RequestAppSetting();
        RequestStaticPagesData();
        
    }
    
    
    
    //*********** Register Device to Admin Panel with the Device's Info ********//
    
    public static void RegisterDeviceForFCM(final Context context) {
    
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
    
        String deviceID = "";
        DeviceInfo device = Utilities.getDeviceInfo(context);
        String customer_ID = sharedPreferences.getString("userID", "");
        
        
        if (ConstantValues.DEFAULT_NOTIFICATION.equalsIgnoreCase("onesignal")) {
            deviceID = OneSignal.getPermissionSubscriptionState ().getSubscriptionStatus().getUserId();
        }
        else if (ConstantValues.DEFAULT_NOTIFICATION.equalsIgnoreCase("fcm")) {
            deviceID = FirebaseInstanceId.getInstance().getToken();
        }
        
        
        
        Call<UserData> call = APIClient.getInstance()
                .registerDeviceToFCM
                        (
                                deviceID,
                                device.getDeviceType(),
                                device.getDeviceRAM(),
                                device.getDeviceProcessors(),
                                device.getDeviceAndroidOS(),
                                device.getDeviceLocation(),
                                device.getDeviceModel(),
                                device.getDeviceManufacturer(),
                                customer_ID
                        );
        
        call.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, retrofit2.Response<UserData> response) {
                
                if (response.isSuccessful()) {
                    if (response.body().getSuccess().equalsIgnoreCase("1")) {
                        
                        Log.i("notification", response.body().getMessage());
//                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    
                    }
                    else {
                        
                        Log.i("notification", response.body().getMessage());
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Log.i("notification", response.errorBody().toString());
                }
            }
            
            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
//                Toast.makeText(context, "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
        
    }



    //*********** API Request Method to Fetch App Banners ********//

    private void RequestBanners() {
    
        Call<BannerData> call = APIClient.getInstance()
                .getBanners();
    
        try {
            Response<BannerData> response = call.execute();
    
            BannerData bannerData = new BannerData();
        
            if (response.isSuccessful()) {
    
                bannerData = response.body();
    
                if (!bannerData.getSuccess().isEmpty())
                    app.setBannersList(bannerData.getData());
            
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }



    //*********** API Request Method to Fetch All Categories ********//
    
    private void RequestAllCategories() {
    
        Call<CategoryData> call = APIClient.getInstance()
                .getAllCategories
                        (
                                ConstantValues.LANGUAGE_ID
                        );
    
        try {
            Response<CategoryData> response = call.execute();
        
            CategoryData categoryData = new CategoryData();
        
            if (response.isSuccessful()) {
            
                categoryData = response.body();
            
                if (!categoryData.getSuccess().isEmpty())
                    app.setCategoriesList(categoryData.getData());
            
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }



    //*********** Request App Settings from the Server ********//
    
    private void RequestAppSetting() {
    
        Call<AppSettingsData> call = APIClient.getInstance()
                .getAppSetting();
    
        try {
            Response<AppSettingsData> response = call.execute();
        
            if (response.isSuccessful()) {
    
                AppSettingsData appSettingsData = null;
    
                appSettingsData = response.body();
    
                if (!appSettingsData.getSuccess().isEmpty())
                    app.setAppSettingsDetails(appSettingsData.getProductData().get(0));
            
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    
    
    //*********** Request Static Pages Data from the Server ********//
    
    private void RequestStaticPagesData() {
    
        ConstantValues.ABOUT_US = app.getString(R.string.lorem_ipsum);
        ConstantValues.TERMS_SERVICES = app.getString(R.string.lorem_ipsum);
        ConstantValues.PRIVACY_POLICY = app.getString(R.string.lorem_ipsum);
        ConstantValues.REFUND_POLICY = app.getString(R.string.lorem_ipsum);
    
    
        Call<PagesData> call = APIClient.getInstance()
                .getStaticPages
                        (
                                ConstantValues.LANGUAGE_ID
                        );
    
        try {
            Response<PagesData> response = call.execute();
    
            PagesData pages = new PagesData();
            
            if (response.isSuccessful()) {
                
                pages = response.body();
    
                if (pages.getSuccess().equalsIgnoreCase("1")) {
        
                    app.setStaticPagesDetails(pages.getPagesData());
        
                    for (int i=0;  i<pages.getPagesData().size();  i++) {
                        PagesDetails page = pages.getPagesData().get(i);
            
                        if (page.getSlug().equalsIgnoreCase("about-us")) {
                            ConstantValues.ABOUT_US = page.getDescription();
                        }
                        else if (page.getSlug().equalsIgnoreCase("term-services")) {
                            ConstantValues.TERMS_SERVICES = page.getDescription();
                        }
                        else if (page.getSlug().equalsIgnoreCase("privacy-policy")) {
                            ConstantValues.PRIVACY_POLICY = page.getDescription();
                        }
                        else if (page.getSlug().equalsIgnoreCase("refund-policy")) {
                            ConstantValues.REFUND_POLICY = page.getDescription();
                        }
                    }
                }
            }
        
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
