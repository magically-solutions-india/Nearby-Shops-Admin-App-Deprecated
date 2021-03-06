package org.nearbyshops.serviceprovider.ItemCategoriesTabs;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.wunderlist.slidinglayer.SlidingLayer;

import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Interfaces.NotifyBackPressed;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Interfaces.NotifyCategoryChanged;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Interfaces.NotifyInsertTab;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Interfaces.NotifyFabClick_Item;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Interfaces.NotifyFabClick_ItemCategories;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Interfaces.NotifySort;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Interfaces.NotifySwipeToRight;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Interfaces.NotifyTitleChanged;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Interfaces.ToggleFab;
import org.nearbyshops.serviceprovider.Model.ItemCategory;
import org.nearbyshops.serviceprovider.R;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItemCategoriesTabs extends AppCompatActivity implements NotifyInsertTab,
        NotifyTitleChanged, ToggleFab, ViewPager.OnPageChangeListener,
        NotifyCategoryChanged, NotifySort, NotifySwipeToRight{

    // Fab Variables
    @BindView(R.id.fab_menu)
    FloatingActionMenu fab_menu;

    @BindView(R.id.fab_detach)
    FloatingActionButton fab_detach;

    @BindView(R.id.fab_add)
    FloatingActionButton fab_add;

    @BindView(R.id.fab_change_parent)
    FloatingActionButton fab_change_parent;

    @BindView(R.id.fab_add_from_global)
    FloatingActionButton fab_add_from_global;

    @BindView(R.id.slidinglayerfragment)
    FrameLayout slidingFragmentContainer;


//    public NotifyFabClick_ItemCategories notifyFabClickItemCategories;
//    public NotifyFabClick_Item notifyFabClick_item;
    // Fab Variables Ends


    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

//    public NotifyBackPressed notifyBackPressed;
//    public NotifyCategoryChanged notifyCategoryChanged;


    @BindView(R.id.appbar)
    AppBarLayout appBar;

    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.tablayoutPager)
    TabLayout tabLayoutPager;


    @BindView(R.id.slidingLayer)
    SlidingLayer slidingLayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_categories_tabs);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(),this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        tabLayoutPager.setupWithViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(this);

        setFabBackground();
        setupSlidingLayer();

        insertTab("Root");
    }



    void setupSlidingLayer()
    {

        ////slidingLayer.setShadowDrawable(R.drawable.sidebar_shadow);
        //slidingLayer.setShadowSizeRes(R.dimen.shadow_size);

        if(slidingLayer!=null)
        {
            slidingLayer.setChangeStateOnTap(true);
            slidingLayer.setSlidingEnabled(true);
            slidingLayer.setPreviewOffsetDistance(15);
            slidingLayer.setOffsetDistance(10);
            slidingLayer.setStickTo(SlidingLayer.STICK_TO_RIGHT);

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(250, ViewGroup.LayoutParams.MATCH_PARENT);

            //slidingContents.setLayoutParams(layoutParams);

            //slidingContents.setMinimumWidth(metrics.widthPixels-50);


            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.slidinglayerfragment,new SlidingLayerItemSort())
                    .commit();

        }

    }


    private void setFabBackground() {
        // assign background to the FAB's
        fab_add.setImageResource(R.drawable.fab_add);
        Drawable drawable = VectorDrawableCompat.create(getResources(), R.drawable.ic_low_priority_black_24px, getTheme());
        fab_change_parent.setImageDrawable(drawable);

        Drawable drawable_add = VectorDrawableCompat.create(getResources(), R.drawable.ic_playlist_add_from_global, getTheme());
        fab_add_from_global.setImageDrawable(drawable_add);

        Drawable drawable_detach = VectorDrawableCompat.create(getResources(), R.drawable.ic_detach, getTheme());
        fab_detach.setImageDrawable(drawable_detach);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_item_categories_tabs, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void NotifyTitleChanged(String title, int tabPosition) {

        mPagerAdapter.setTitle(title,tabPosition);
    }






    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(makeFragmentName(mViewPager.getId(),0));

        //notifyBackPressed !=null

        if(fragment instanceof NotifyBackPressed)
        {
            if(((NotifyBackPressed) fragment).backPressed())
            {
                super.onBackPressed();


            }else
            {
//                mViewPager.setCurrentItem(0,true);
                mViewPager.setCurrentItem(0);
            }
        }
        else
        {
            super.onBackPressed();
        }
    }



    @Override
    public void itemCategoryChanged(ItemCategory currentCategory, Boolean isBackPressed) {

        Log.d("applog","Item Category Changed : " + currentCategory.getCategoryName() + " : " + String.valueOf(currentCategory.getItemCategoryID()));

        showFab();


        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(makeFragmentName(mViewPager.getId(),1));

        //notifyCategoryChanged !=null
        //            notifyCategoryChanged.itemCategoryChanged(currentCategory,isBackPressed);

        if(fragment instanceof NotifyCategoryChanged)
        {
            ((NotifyCategoryChanged) fragment).itemCategoryChanged(currentCategory,isBackPressed);
        }
    }

    @Override
    public void insertTab(String categoryName) {

        if(tabLayout.getVisibility()==View.GONE)
        {
            tabLayout.setVisibility(View.VISIBLE);
        }

        tabLayout.addTab(tabLayout.newTab().setText("" + categoryName + " : : "));
        tabLayout.setScrollPosition(tabLayout.getTabCount()-1,0,true);
    }

    @Override
    public void removeLastTab() {

        if(tabLayout.getTabCount()<=1)
        {
            return;
        }

        tabLayout.removeTabAt(tabLayout.getTabCount()-1);
        tabLayout.setScrollPosition(tabLayout.getTabCount()-1,0,true);

        if(tabLayout.getTabCount()==0)
        {
//            tabLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void notifySwipeToright() {

//        mViewPager.setCurrentItem(1,true);
        mViewPager.setCurrentItem(1);
    }







    @Override
    public void showFab() {
        fab_menu.animate().translationY(0);
    }

    @Override
    public void hideFab() {
        fab_menu.animate().translationY(fab_menu.getHeight());
    }




    // Click Listeners for Fab Buttons


    @OnClick(R.id.fab_detach)
    void fabDetachClick()
    {
        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(makeFragmentName(mViewPager.getId(),mViewPager.getCurrentItem()));


        if(fragment instanceof NotifyFabClick_Item)
        {
            ((NotifyFabClick_Item) fragment).detachSelectedClick();
        }
        else if(fragment instanceof NotifyFabClick_ItemCategories)
        {
            ((NotifyFabClick_ItemCategories) fragment).detachSelectedClick();
        }


//        if(mViewPager.getCurrentItem()==0)
//        {
//            if(notifyFabClickItemCategories!=null)
//            {
//                notifyFabClickItemCategories.detachSelectedClick();
//            }
//        }
//        else if(mViewPager.getCurrentItem()==1)
//        {
//            if(notifyFabClick_item!=null)
//            {
//                notifyFabClick_item.detachSelectedClick();
//            }
//        }


    }

    @OnClick(R.id.fab_change_parent)
    void fabChangeParentClick()
    {

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(makeFragmentName(mViewPager.getId(),mViewPager.getCurrentItem()));


        if(fragment instanceof NotifyFabClick_Item)
        {
            ((NotifyFabClick_Item) fragment).changeParentForSelected();
        }
        else if(fragment instanceof NotifyFabClick_ItemCategories)
        {
            ((NotifyFabClick_ItemCategories) fragment).changeParentForSelected();
        }



//        if(mViewPager.getCurrentItem()==0)
//        {
//            if(notifyFabClickItemCategories!=null)
//            {
//                notifyFabClickItemCategories.changeParentForSelected();
//            }
//        }
//        else if(mViewPager.getCurrentItem()==1)
//        {
//            if(notifyFabClick_item!=null)
//            {
//                notifyFabClick_item.changeParentForSelected();
//            }
//        }
    }

    @OnClick(R.id.fab_add)
    void fabAddClick()
    {

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(makeFragmentName(mViewPager.getId(),mViewPager.getCurrentItem()));


        if(fragment instanceof NotifyFabClick_Item)
        {
            ((NotifyFabClick_Item) fragment).addItem();
        }
        else if(fragment instanceof NotifyFabClick_ItemCategories)
        {
            ((NotifyFabClick_ItemCategories) fragment).addItemCategory();
        }

//        if(mViewPager.getCurrentItem()==0)
//        {
//            if(notifyFabClickItemCategories!=null)
//            {
//                notifyFabClickItemCategories.addItemCategory();
//            }
//        }
//        else if (mViewPager.getCurrentItem()==1)
//        {
//            if(notifyFabClick_item!=null)
//            {
//                notifyFabClick_item.addItem();
//            }
//        }


    }


    @OnClick(R.id.fab_add_from_global)
    void fabAddFromGlobal()
    {

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(makeFragmentName(mViewPager.getId(),mViewPager.getCurrentItem()));


        if(fragment instanceof NotifyFabClick_Item)
        {
            ((NotifyFabClick_Item) fragment).addfromGlobal();
        }
        else if(fragment instanceof NotifyFabClick_ItemCategories)
        {
            ((NotifyFabClick_ItemCategories) fragment).addfromGlobal();
        }


//        if(mViewPager.getCurrentItem()==0)
//        {
//            if(notifyFabClickItemCategories!=null)
//            {
//                notifyFabClickItemCategories.addfromGlobal();
//            }
//        }
//        else if(mViewPager.getCurrentItem()==1)
//        {
//            if(notifyFabClick_item!=null)
//            {
//                notifyFabClick_item.addfromGlobal();
//            }
//        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    @Override
    public void onPageSelected(int position) {

        if(position==0)
        {
            showFab();
            fab_detach.setLabelText(getString(R.string.fab_label_detach_item_category));
            fab_add.setLabelText(getString(R.string.fab_label_add_item_category));
            fab_change_parent.setLabelText(getString(R.string.fab_label_change_parent_categories));
            fab_add_from_global.setLabelText(getString(R.string.fab_label_add_from_global_item_cat));
            fab_menu.setMenuButtonColorNormal(getResources().getColor(R.color.phonographyBlue));

        }else if(position == 1)
        {
            showFab();
            fab_detach.setLabelText(getString(R.string.fab_label_detach_item));
            fab_add.setLabelText(getString(R.string.fab_label_add_Item));
            fab_change_parent.setLabelText(getString(R.string.fab_label_change_parent_item));
            fab_add_from_global.setLabelText(getString(R.string.fab_label_add_from_global_item));
            fab_menu.setMenuButtonColorNormal(getResources().getColor(R.color.orangeDark));
        }
    }


    @OnClick({R.id.icon_sort,R.id.text_sort})
    void sortClick()
    {
        slidingLayer.openLayer(true);
    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }



    @Override
    public void notifySortChanged() {

        if(mViewPager.getCurrentItem()==1)
        {
            Fragment fragment = (Fragment)mPagerAdapter.instantiateItem(mViewPager,mViewPager.getCurrentItem());

            if(fragment instanceof NotifySort)
            {
                ((NotifySort)fragment).notifySortChanged();
            }
        }
    }

}
