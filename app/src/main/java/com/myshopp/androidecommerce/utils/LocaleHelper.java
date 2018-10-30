package com.myshopp.androidecommerce.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import com.myshopp.androidecommerce.R;
import com.myshopp.androidecommerce.app.App;
import com.myshopp.androidecommerce.constant.ConstantValues;
import com.myshopp.androidecommerce.models.device_model.AppSettingsDetails;

import java.util.Locale;


/**
 * LocaleHelper is used to change App's Locale and Persist this change for the next time
 */

public class LocaleHelper extends ContextWrapper {
    
    
    public LocaleHelper(Context base) {
        super(base);
    }
    
    
    @SuppressWarnings("deprecation")
    public static ContextWrapper wrapLocale(Context context, String language) {
        
        // Get config
        Configuration config = context.getResources().getConfiguration();
        
        if (!"".equalsIgnoreCase(language)) {
            
            // Set new Locale as Default
            Locale newLocale = new Locale(language);
            Locale.setDefault(newLocale);
    
            Resources res = context.getResources();
            Configuration configuration = res.getConfiguration();
    
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                setSystemLocale(config, newLocale);
                
                configuration.setLocale(newLocale);
        
                LocaleList localeList = new LocaleList(newLocale);
                LocaleList.setDefault(localeList);
                configuration.setLocales(localeList);
        
                context = context.createConfigurationContext(configuration);
        
            } else {
                setSystemLocaleLegacy(config, newLocale);
                
                configuration.setLocale(newLocale);
                context = context.createConfigurationContext(configuration);
        
            }
            
        }
    
        
        AppSettingsDetails appSettingsDetails = ((App) context.getApplicationContext()).getAppSettingsDetails();
    
        if (appSettingsDetails != null) {
            ConstantValues.DEFAULT_HOME_STYLE = context.getString(R.string.actionHome) +" "+ appSettingsDetails.getHomeStyle();
            ConstantValues.DEFAULT_CATEGORY_STYLE = context.getString(R.string.actionCategory) +" "+ appSettingsDetails.getCategoryStyle();
        }
        else {
            ConstantValues.DEFAULT_HOME_STYLE = context.getString(R.string.actionHome) +" "+ 1;
            ConstantValues.DEFAULT_CATEGORY_STYLE = context.getString(R.string.actionCategory) +" "+ 1;
        }
        
        
        return new LocaleHelper(context);
    }
    
    
    @SuppressWarnings("deprecation")
    public static Locale getSystemLocaleLegacy(Configuration config){
        return config.locale;
    }
    
    
    @TargetApi(Build.VERSION_CODES.N)
    public static Locale getSystemLocale(Configuration config){
        return config.getLocales().get(0);
    }
    
    
    @SuppressWarnings("deprecation")
    public static void setSystemLocaleLegacy(Configuration config, Locale locale){
        config.locale = locale;
    }
    
    
    @TargetApi(Build.VERSION_CODES.N)
    public static void setSystemLocale(Configuration config, Locale locale){
        config.setLocale(locale);
    }
    
}

