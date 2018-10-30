package com.myshopp.androidecommerce.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Transformers.BaseTransformer;

import com.myshopp.androidecommerce.activities.MainActivity;
import com.myshopp.androidecommerce.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.myshopp.androidecommerce.adapters.ViewPagerCustomAdapter;
import com.myshopp.androidecommerce.app.App;
import com.myshopp.androidecommerce.constant.ConstantValues;
import com.myshopp.androidecommerce.models.banner_model.BannerDetails;
import com.myshopp.androidecommerce.models.category_model.CategoryDetails;


public class HomePage_1 extends Fragment implements BaseSliderView.OnSliderClickListener {

    View rootView;

    ViewPager viewPager;
    TabLayout tabLayout;

    SliderLayout sliderLayout;
    PagerIndicator pagerIndicator;

    List<BannerDetails> bannerImages = new ArrayList<>();
    List<CategoryDetails> allCategoriesList = new ArrayList<>();

    FragmentManager fragmentManager;
    Fragment recentlyViewed, productsFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.homepage_1, container, false);
    
        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(ConstantValues.APP_HEADER);
    
        // Get BannersList from ApplicationContext
        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();
        

        // Binding Layout Views
        viewPager = (ViewPager) rootView.findViewById(R.id.myViewPager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        sliderLayout = (SliderLayout) rootView.findViewById(R.id.banner_slider);
        pagerIndicator = (PagerIndicator) rootView.findViewById(R.id.banner_slider_indicator);
        
    
        // Setup BannerSlider
        setupBannerSlider(bannerImages);
    
        // Setup ViewPagers
        setupViewPagerOne(viewPager);
    
        // Add corresponding ViewPagers to TabLayouts
        tabLayout.setupWithViewPager(viewPager);

        
        fragmentManager = getFragmentManager();

        // Add RecentlyViewed Fragment to specified FrameLayout
        productsFragment = new Products();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isSubFragment", true);
        productsFragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.products_fragment, productsFragment).commit();
    
        
        // Add RecentlyViewed Fragment to specified FrameLayout
        recentlyViewed = new RecentlyViewed();
        fragmentManager.beginTransaction().replace(R.id.recently_viewed_fragment, recentlyViewed).commit();
        
        
        
        return rootView;

    }
    


    //*********** Setup the given ViewPager ********//

    private void setupViewPagerOne(ViewPager viewPager) {

        // Initialize new Bundle for Fragment arguments
        Bundle bundle = new Bundle();
        bundle.putBoolean("isHeaderVisible", false);

        // Initialize Fragments
        Fragment topSeller = new Top_Seller();
        Fragment specialDeals = new Special_Deals();
        Fragment mostLiked = new Most_Liked();
    
        topSeller.setArguments(bundle);
        specialDeals.setArguments(bundle);
        mostLiked.setArguments(bundle);

        
        // Initialize ViewPagerAdapter with ChildFragmentManager for ViewPager
        ViewPagerCustomAdapter viewPagerCustomAdapter = new ViewPagerCustomAdapter(getChildFragmentManager());
        
        // Add the Fragments to the ViewPagerAdapter with TabHeader
        viewPagerCustomAdapter.addFragment(topSeller, getString(R.string.top_seller));
        viewPagerCustomAdapter.addFragment(specialDeals, getString(R.string.super_deals));
        viewPagerCustomAdapter.addFragment(mostLiked, getString(R.string.most_liked));

        
        viewPager.setOffscreenPageLimit(2);
        
        // Attach the ViewPagerAdapter to given ViewPager
        viewPager.setAdapter(viewPagerCustomAdapter);
    
        
        /*Configuration config = getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            //use the RTL trick here
            viewPager.setRotationY(180);
            viewPager.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }*/
        
    }



    //*********** Setup the BannerSlider with the given List of BannerImages ********//

    private void setupBannerSlider(final List<BannerDetails> bannerImages) {

        // Initialize new LinkedHashMap<ImageName, ImagePath>
        final LinkedHashMap<String, String> slider_covers = new LinkedHashMap<>();


        for (int i=0;  i< bannerImages.size();  i++) {
            // Get bannerDetails at given Position from bannerImages List
            BannerDetails bannerDetails =  bannerImages.get(i);

            // Put Image's Name and URL to the HashMap slider_covers
            slider_covers.put
                    (
                            "Image"+bannerDetails.getTitle(),
                            ConstantValues.ECOMMERCE_URL+bannerDetails.getImage()
                    );
        }


        for(String name : slider_covers.keySet()) {
            // Initialize DefaultSliderView
            final DefaultSliderView defaultSliderView = new DefaultSliderView(getContext());

            // Set Attributes(Name, Image, Type etc) to DefaultSliderView
            defaultSliderView
                    .description(name)
                    .image(slider_covers.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            

            // Add DefaultSliderView to the SliderLayout
            sliderLayout.addSlider(defaultSliderView);
        }

        // Set PresetTransformer type of the SliderLayout
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);


        // Check if the size of Images in the Slider is less than 2
        if (slider_covers.size() < 2) {
            // Disable PagerTransformer
            sliderLayout.setPagerTransformer(false, new BaseTransformer() {
                @Override
                protected void onTransform(View view, float v) {
                }
            });

            // Hide Slider PagerIndicator
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);

        } else {
            // Set custom PagerIndicator to the SliderLayout
            sliderLayout.setCustomIndicator(pagerIndicator);
            // Make PagerIndicator Visible
            sliderLayout.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
        }
        
    }
    
    
    
    //*********** Handle the Click Listener on BannerImages of Slider ********//
    
    @Override
    public void onSliderClick(BaseSliderView slider) {
        
        int position = sliderLayout.getCurrentPosition();
        String url = bannerImages.get(position).getUrl();
        String type = bannerImages.get(position).getType();
    
        if (type.equalsIgnoreCase("product")) {
            if (!url.isEmpty()) {
                // Get Product Info
                Bundle bundle = new Bundle();
                bundle.putInt("itemID", Integer.parseInt(url));
            
                // Navigate to Product_Description of selected Product
                Fragment fragment = new Product_Description();
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            }
            
        }
        else if (type.equalsIgnoreCase("category")) {
            if (!url.isEmpty()) {
                int categoryID = 0;
                String categoryName = "";
            
                for (int i=0;  i<allCategoriesList.size();  i++) {
                    if (url.equalsIgnoreCase(allCategoriesList.get(i).getId())) {
                        categoryName = allCategoriesList.get(i).getName();
                        categoryID = Integer.parseInt(allCategoriesList.get(i).getId());
                    }
                }
            
                // Get Category Info
                Bundle bundle = new Bundle();
                bundle.putInt("CategoryID", categoryID);
                bundle.putString("CategoryName", categoryName);
            
                // Navigate to Products Fragment
                Fragment fragment = new Products();
                fragment.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.main_fragment, fragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .addToBackStack(getString(R.string.actionHome)).commit();
            }
            
        }
        else if (type.equalsIgnoreCase("deals")) {
            Bundle bundle = new Bundle();
            bundle.putString("sortBy", "special");
        
            // Navigate to Products Fragment
            Fragment fragment = new Products();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();
            
        }
        else if (type.equalsIgnoreCase("top seller")) {
            Bundle bundle = new Bundle();
            bundle.putString("sortBy", "top seller");
        
            // Navigate to Products Fragment
            Fragment fragment = new Products();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();
            
        }
        else if (type.equalsIgnoreCase("most liked")) {
            Bundle bundle = new Bundle();
            bundle.putString("sortBy", "most liked");
        
            // Navigate to Products Fragment
            Fragment fragment = new Products();
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();
            
        }
        
    }
    
}

