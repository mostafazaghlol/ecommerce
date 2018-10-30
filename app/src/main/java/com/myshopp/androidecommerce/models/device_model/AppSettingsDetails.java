package com.myshopp.androidecommerce.models.device_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class AppSettingsDetails {
    
    
    @SerializedName("setting_id")
    @Expose
    private int settingId;
    @SerializedName("facebook_app_id")
    @Expose
    private String facebookAppId;
    @SerializedName("facebook_secret_id")
    @Expose
    private String facebookSecretId;
    @SerializedName("facebook_login")
    @Expose
    private int facebookLogin;
    @SerializedName("google_login")
    @Expose
    private int googleLogin;
    @SerializedName("contact_us_email")
    @Expose
    private String contactUsEmail;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("zip")
    @Expose
    private int zip;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("fcm_android")
    @Expose
    private String fcmAndroid;
    @SerializedName("fcm_ios")
    @Expose
    private String fcmIos;
    @SerializedName("fcm_desktop")
    @Expose
    private String fcmDesktop;
    @SerializedName("app_logo")
    @Expose
    private String appLogo;
    @SerializedName("fcm_android_sender_id")
    @Expose
    private String fcmAndroidSenderId;
    @SerializedName("fcm_ios_sender_id")
    @Expose
    private String fcmIosSenderId;
    @SerializedName("app_name")
    @Expose
    private String appName;
    @SerializedName("currency_symbol")
    @Expose
    private String currencySymbol;
    @SerializedName("new_product_duration")
    @Expose
    private int newProductDuration;
    @SerializedName("notification_title")
    @Expose
    private String notificationTitle;
    @SerializedName("notification_text")
    @Expose
    private String notificationText;
    @SerializedName("lazzy_loading_effect")
    @Expose
    private String lazzyLoadingEffect;
    @SerializedName("footer_button")
    @Expose
    private int footerButton;
    @SerializedName("cart_button")
    @Expose
    private int cartButton;
    @SerializedName("featured_category")
    @Expose
    private int featuredCategory;
    @SerializedName("notification_duration")
    @Expose
    private String notificationDuration;
    @SerializedName("home_style")
    @Expose
    private int homeStyle;
    @SerializedName("wish_list_page")
    @Expose
    private int wishListPage;
    @SerializedName("edit_profile_page")
    @Expose
    private int editProfilePage;
    @SerializedName("shipping_address_page")
    @Expose
    private int shippingAddressPage;
    @SerializedName("my_orders_page")
    @Expose
    private int myOrdersPage;
    @SerializedName("contact_us_page")
    @Expose
    private int contactUsPage;
    @SerializedName("about_us_page")
    @Expose
    private int aboutUsPage;
    @SerializedName("news_page")
    @Expose
    private int newsPage;
    @SerializedName("intro_page")
    @Expose
    private int introPage;
    @SerializedName("setting_page")
    @Expose
    private int settingPage;
    @SerializedName("share_app")
    @Expose
    private int shareApp;
    @SerializedName("rate_app")
    @Expose
    private int rateApp;
    @SerializedName("site_url")
    @Expose
    private String siteUrl;
    @SerializedName("admob")
    @Expose
    private int admob;
    @SerializedName("admob_id")
    @Expose
    private String admobId;
    @SerializedName("ad_unit_id_banner")
    @Expose
    private String adUnitIdBanner;
    @SerializedName("ad_unit_id_interstitial")
    @Expose
    private String adUnitIdInterstitial;
    @SerializedName("category_style")
    @Expose
    private int categoryStyle;
    @SerializedName("package_name")
    @Expose
    private String packageName;
    @SerializedName("google_analytic_id")
    @Expose
    private String googleAnalyticId;
    @SerializedName("default_notification")
    @Expose
    private String defaultNotification;
    @SerializedName("onesignal_app_id")
    @Expose
    private String onesignalAppId;
    
    
    public int getSettingId() {
        return settingId;
    }
    
    public void setSettingId(int settingId) {
        this.settingId = settingId;
    }
    
    public String getFacebookAppId() {
        return facebookAppId;
    }
    
    public void setFacebookAppId(String facebookAppId) {
        this.facebookAppId = facebookAppId;
    }
    
    public String getFacebookSecretId() {
        return facebookSecretId;
    }
    
    public void setFacebookSecretId(String facebookSecretId) {
        this.facebookSecretId = facebookSecretId;
    }
    
    public int getFacebookLogin() {
        return facebookLogin;
    }
    
    public void setFacebookLogin(int facebookLogin) {
        this.facebookLogin = facebookLogin;
    }
    
    public int getGoogleLogin() {
        return googleLogin;
    }
    
    public void setGoogleLogin(int googleLogin) {
        this.googleLogin = googleLogin;
    }
    
    public String getContactUsEmail() {
        return contactUsEmail;
    }
    
    public void setContactUsEmail(String contactUsEmail) {
        this.contactUsEmail = contactUsEmail;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public int getZip() {
        return zip;
    }
    
    public void setZip(int zip) {
        this.zip = zip;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getLatitude() {
        return latitude;
    }
    
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    
    public String getLongitude() {
        return longitude;
    }
    
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    
    public String getPhoneNo() {
        return phoneNo;
    }
    
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    
    public String getFcmAndroid() {
        return fcmAndroid;
    }
    
    public void setFcmAndroid(String fcmAndroid) {
        this.fcmAndroid = fcmAndroid;
    }
    
    public String getFcmIos() {
        return fcmIos;
    }
    
    public void setFcmIos(String fcmIos) {
        this.fcmIos = fcmIos;
    }
    
    public String getFcmDesktop() {
        return fcmDesktop;
    }
    
    public void setFcmDesktop(String fcmDesktop) {
        this.fcmDesktop = fcmDesktop;
    }
    
    public String getAppLogo() {
        return appLogo;
    }
    
    public void setAppLogo(String appLogo) {
        this.appLogo = appLogo;
    }
    
    public String getFcmAndroidSenderId() {
        return fcmAndroidSenderId;
    }
    
    public void setFcmAndroidSenderId(String fcmAndroidSenderId) {
        this.fcmAndroidSenderId = fcmAndroidSenderId;
    }
    
    public String getFcmIosSenderId() {
        return fcmIosSenderId;
    }
    
    public void setFcmIosSenderId(String fcmIosSenderId) {
        this.fcmIosSenderId = fcmIosSenderId;
    }
    
    public String getAppName() {
        return appName;
    }
    
    public void setAppName(String appName) {
        this.appName = appName;
    }
    
    public String getCurrencySymbol() {
        return currencySymbol;
    }
    
    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }
    
    public int getNewProductDuration() {
        return newProductDuration;
    }
    
    public void setNewProductDuration(int newProductDuration) {
        this.newProductDuration = newProductDuration;
    }
    
    public String getNotificationTitle() {
        return notificationTitle;
    }
    
    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }
    
    public String getNotificationText() {
        return notificationText;
    }
    
    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }
    
    public String getLazzyLoadingEffect() {
        return lazzyLoadingEffect;
    }
    
    public void setLazzyLoadingEffect(String lazzyLoadingEffect) {
        this.lazzyLoadingEffect = lazzyLoadingEffect;
    }
    
    public int getFooterButton() {
        return footerButton;
    }
    
    public void setFooterButton(int footerButton) {
        this.footerButton = footerButton;
    }
    
    public int getCartButton() {
        return cartButton;
    }
    
    public void setCartButton(int cartButton) {
        this.cartButton = cartButton;
    }
    
    public int getFeaturedCategory() {
        return featuredCategory;
    }
    
    public void setFeaturedCategory(int featuredCategory) {
        this.featuredCategory = featuredCategory;
    }
    
    public String getNotificationDuration() {
        return notificationDuration;
    }
    
    public void setNotificationDuration(String notificationDuration) {
        this.notificationDuration = notificationDuration;
    }
    
    public int getHomeStyle() {
        return homeStyle;
    }
    
    public void setHomeStyle(int homeStyle) {
        this.homeStyle = homeStyle;
    }
    
    public int getWishListPage() {
        return wishListPage;
    }
    
    public void setWishListPage(int wishListPage) {
        this.wishListPage = wishListPage;
    }
    
    public int getEditProfilePage() {
        return editProfilePage;
    }
    
    public void setEditProfilePage(int editProfilePage) {
        this.editProfilePage = editProfilePage;
    }
    
    public int getShippingAddressPage() {
        return shippingAddressPage;
    }
    
    public void setShippingAddressPage(int shippingAddressPage) {
        this.shippingAddressPage = shippingAddressPage;
    }
    
    public int getMyOrdersPage() {
        return myOrdersPage;
    }
    
    public void setMyOrdersPage(int myOrdersPage) {
        this.myOrdersPage = myOrdersPage;
    }
    
    public int getContactUsPage() {
        return contactUsPage;
    }
    
    public void setContactUsPage(int contactUsPage) {
        this.contactUsPage = contactUsPage;
    }
    
    public int getAboutUsPage() {
        return aboutUsPage;
    }
    
    public void setAboutUsPage(int aboutUsPage) {
        this.aboutUsPage = aboutUsPage;
    }
    
    public int getNewsPage() {
        return newsPage;
    }
    
    public void setNewsPage(int newsPage) {
        this.newsPage = newsPage;
    }
    
    public int getIntroPage() {
        return introPage;
    }
    
    public void setIntroPage(int introPage) {
        this.introPage = introPage;
    }
    
    public int getSettingPage() {
        return settingPage;
    }
    
    public void setSettingPage(int settingPage) {
        this.settingPage = settingPage;
    }
    
    public int getShareApp() {
        return shareApp;
    }
    
    public void setShareApp(int shareApp) {
        this.shareApp = shareApp;
    }
    
    public int getRateApp() {
        return rateApp;
    }
    
    public void setRateApp(int rateApp) {
        this.rateApp = rateApp;
    }
    
    public String getSiteUrl() {
        return siteUrl;
    }
    
    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }
    
    public int getAdmob() {
        return admob;
    }
    
    public void setAdmob(int admob) {
        this.admob = admob;
    }
    
    public String getAdmobId() {
        return admobId;
    }
    
    public void setAdmobId(String admobId) {
        this.admobId = admobId;
    }
    
    public String getAdUnitIdBanner() {
        return adUnitIdBanner;
    }
    
    public void setAdUnitIdBanner(String adUnitIdBanner) {
        this.adUnitIdBanner = adUnitIdBanner;
    }
    
    public String getAdUnitIdInterstitial() {
        return adUnitIdInterstitial;
    }
    
    public void setAdUnitIdInterstitial(String adUnitIdInterstitial) {
        this.adUnitIdInterstitial = adUnitIdInterstitial;
    }
    
    public int getCategoryStyle() {
        return categoryStyle;
    }
    
    public void setCategoryStyle(int categoryStyle) {
        this.categoryStyle = categoryStyle;
    }
    
    public String getPackageName() {
        return packageName;
    }
    
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    
    public String getGoogleAnalyticId() {
        return googleAnalyticId;
    }
    
    public void setGoogleAnalyticId(String googleAnalyticId) {
        this.googleAnalyticId = googleAnalyticId;
    }
    
    public String getDefaultNotification() {
        return defaultNotification;
    }
    
    public void setDefaultNotification(String defaultNotification) {
        this.defaultNotification = defaultNotification;
    }
    
    public String getOnesignalAppId() {
        return onesignalAppId;
    }
    
    public void setOnesignalAppId(String onesignalAppId) {
        this.onesignalAppId = onesignalAppId;
    }
    
}
