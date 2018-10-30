package com.myshopp.androidecommerce.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.myshopp.androidecommerce.R;

import com.myshopp.androidecommerce.activities.MainActivity;
import com.myshopp.androidecommerce.constant.ConstantValues;
import com.myshopp.androidecommerce.models.news_model.all_news.NewsDetails;


public class NewsDescription extends Fragment {

    View rootView;
    
    ImageView news_cover;
    TextView news_title, news_date;
    WebView news_description_webView;

    NewsDetails newsDetails;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.news_description, container, false);
        

        // Get NewsDetails from bundle arguments
        newsDetails = getArguments().getParcelable("NewsDetails");
    
    
        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.news_description));


        // Binding Layout Views
        news_cover = (ImageView) rootView.findViewById(R.id.news_cover);
        news_title = (TextView) rootView.findViewById(R.id.news_title);
        news_date = (TextView) rootView.findViewById(R.id.news_date);
        news_description_webView = (WebView) rootView.findViewById(R.id.news_description_webView);


        // Set News Details
        news_title.setText(newsDetails.getNewsName());
        news_date.setText(String.valueOf(newsDetails.getNewsDateAdded()));
    
        
        String description = newsDetails.getNewsDescription();
        String styleSheet = "<style> " +
                                "body{background:#eeeeee; margin:0; padding:0} " +
                                "p{color:#666666;} " +
                                "img{display:inline; height:auto; max-width:100%;}" +
                            "</style>";
    
        news_description_webView.setHorizontalScrollBarEnabled(false);
        news_description_webView.loadDataWithBaseURL(null, styleSheet+description, "text/html", "utf-8", null);
        

        Glide
            .with(getContext())
            .load(ConstantValues.ECOMMERCE_URL+newsDetails.getNewsImage()).asBitmap()
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(news_cover);
        


        return rootView;

    }

}

