package com.myshopp.androidecommerce.fragments;

import android.Manifest;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myshopp.androidecommerce.R;
import com.myshopp.androidecommerce.utils.CheckPermissions;


public class Intro_Slide_1 extends Fragment {
    
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.intro_slide_1, container, false);
        
        if (!CheckPermissions.is_LOCATION_PermissionGranted()  ||  !CheckPermissions.is_PHONE_STATE_PermissionGranted())
            ActivityCompat.requestPermissions
                (
                    getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE},
                    CheckPermissions.PERMISSIONS_REQUEST_LOCATION
                );
            
        
        
        return rootView;
    }

}


