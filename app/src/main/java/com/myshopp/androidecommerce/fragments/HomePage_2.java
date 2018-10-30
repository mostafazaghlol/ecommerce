package com.myshopp.androidecommerce.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

import com.myshopp.androidecommerce.adapters.ViewPagerSimpleAdapter;
import com.myshopp.androidecommerce.app.App;
import com.myshopp.androidecommerce.constant.ConstantValues;
import com.myshopp.androidecommerce.models.banner_model.BannerDetails;
import com.myshopp.androidecommerce.models.category_model.CategoryDetails;


public class HomePage_2 extends Fragment implements BaseSliderView.OnSliderClickListener {

    ViewPager viewPager;
    TabLayout tabLayout;

    SliderLayout sliderLayout;
    PagerIndicator pagerIndicator;

    List<BannerDetails> bannerImages = new ArrayList<>();
    List<CategoryDetails> allCategoriesList = new ArrayList<>();
    List<CategoryDetails> allSubCategoriesList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homepage_2, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(ConstantValues.APP_HEADER);

        // Get BannersList from ApplicationContext
        bannerImages = ((App) getContext().getApplicationContext()).getBannersList();

        // Get CategoriesList from AppContext
        allCategoriesList = ((App) getContext().getApplicationContext()).getCategoriesList();


        // Binding Layout Views
        viewPager = (ViewPager) rootView.findViewById(R.id.myViewPager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);
        sliderLayout = (SliderLayout) rootView.findViewById(R.id.banner_slider);
        pagerIndicator = (PagerIndicator) rootView.findViewById(R.id.banner_slider_indicator);


        allSubCategoriesList = new ArrayList<>();

        // Get SubCategoriesList from AllCategoriesList
        for (int i=0;  i<allCategoriesList.size();  i++) {
            if (!allCategoriesList.get(i).getParentId().equalsIgnoreCase("0")) {
                allSubCategoriesList.add(allCategoriesList.get(i));
            }
        }


        // Setup BannerSlider
        setupBannerSlider(bannerImages);


        // Setup ViewPagers
        setupViewPager(viewPager);

        // Add corresponding ViewPagers to TabLayouts
        tabLayout.setupWithViewPager(viewPager);


        // Setup CustomTabs for all the Categories
        setupCustomTabs();


        return rootView;

    }



    //*********** Setup the given ViewPager ********//

    private void setupCustomTabs() {

        // Initialize New View for custom Tab
        View tabOne = (View) LayoutInflater.from(getContext()).inflate(R.layout.layout_tabs_custom, null);
        
        // Set Text of custom Tab
        TextView tabText1 = (TextView) tabOne.findViewById(R.id.myTabs_text);
        tabText1.setText(getString(R.string.all));
        
        // Set Icon of custom Tab
        ImageView tabIcon1 = (ImageView) tabOne.findViewById(R.id.myTabs_icon);
        tabIcon1.setImageResource(R.drawable.ic_list);

        // Add tabOne to TabLayout at index 0
        tabLayout.getTabAt(0).setCustomView(tabOne);


        for (int i=0;  i < allSubCategoriesList.size();  i++) {

            // Initialize New View for custom Tab
            View tabNew = (View) LayoutInflater.from(getContext()).inflate(R.layout.layout_tabs_custom, null);
            
            // Set Text of custom Tab
            TextView tabText2 = (TextView) tabNew.findViewById(R.id.myTabs_text);
            tabText2.setText(allSubCategoriesList.get(i).getName());
            
            // Set Icon of custom Tab
            ImageView tabIcon2 = (ImageView) tabNew.findViewById(R.id.myTabs_icon);
            Glide.with(this).load(ConstantValues.ECOMMERCE_URL+allSubCategoriesList.get(i).getIcon()).asBitmap().into(tabIcon2);

            
            // Add tabTwo to TabLayout at specified index
            tabLayout.getTabAt(i+1).setCustomView(tabNew);
        }
    }



    //*********** Setup the given ViewPager ********//

    private void setupViewPager(ViewPager viewPager) {

        // Initialize ViewPagerAdapter with ChildFragmentManager for ViewPager
        ViewPagerSimpleAdapter viewPagerAdapter = new ViewPagerSimpleAdapter(getChildFragmentManager());

        // Add the Fragments to the ViewPagerAdapter with TabHeader
        viewPagerAdapter.addFragment(new All_Products(), getString(R.string.all));


        for (int i=0;  i < allSubCategoriesList.size();  i++){

            // Add CategoryID to new Bundle for Fragment arguments
            Bundle categoryInfo = new Bundle();
            categoryInfo.putInt("CategoryID", Integer.parseInt(allSubCategoriesList.get(i).getId()));

            // Initialize Category_Products Fragment with specified arguments
            Fragment fragment = new Category_Products();
            fragment.setArguments(categoryInfo);

            // Add the Fragments to the ViewPagerAdapter with TabHeader
            viewPagerAdapter.addFragment(fragment, allSubCategoriesList.get(i).getName());
        }

        // Set the number of pages that should be retained to either side of the current page
        viewPager.setOffscreenPageLimit(0);
        // Attach the ViewPagerAdapter to given ViewPager
        viewPager.setAdapter(viewPagerAdapter);
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
                getFragmentManager().beginTransaction()
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
                getFragmentManager().beginTransaction()
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
            getFragmentManager().beginTransaction()
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
            getFragmentManager().beginTransaction()
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
            getFragmentManager().beginTransaction()
                    .replace(R.id.main_fragment, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(getString(R.string.actionHome)).commit();
            
        }
        
    }

}

