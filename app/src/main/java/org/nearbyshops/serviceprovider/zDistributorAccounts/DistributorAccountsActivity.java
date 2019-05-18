package org.nearbyshops.serviceprovider.zDistributorAccounts;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.wunderlist.slidinglayer.SlidingLayer;

import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Interfaces.NotifySort;
import org.nearbyshops.serviceprovider.zDistributorAccounts.Interfaces.NotifyTitleChanged;
import org.nearbyshops.serviceprovider.zDistributorAccounts.Interfaces.ToggleFab;
import org.nearbyshops.serviceprovider.R;


import butterknife.BindView;
import butterknife.ButterKnife;

public class DistributorAccountsActivity extends AppCompatActivity
        implements ToggleFab,NotifyTitleChanged, NotifySort {

    private PagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @BindView(R.id.main_content)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.fab)
    FloatingActionButton fab;


    @BindView(R.id.slidingLayer)
    SlidingLayer slidingLayer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_accounts);
        ButterKnife.bind(this);


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        setupSlidingLayer();
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
                    .replace(R.id.slidinglayerfragment,new SlidingLayerDistributor())
                    .commit();

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_distributor_accounts, menu);
        return true;
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


    void showSnackbarMessage(String message)
    {
        Snackbar.make(coordinatorLayout, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }



    @Override
    public void showFab() {
        fab.animate().translationY(0);
    }

    @Override
    public void hideFab() {
        fab.animate().translationY(120);
    }


    @Override
    public void NotifyTitleChanged(String title, int tabPosition) {
        mSectionsPagerAdapter.setTitle(title,tabPosition);
    }


    @Override
    public void notifySortChanged() {

        Fragment fragment = (Fragment) mSectionsPagerAdapter.instantiateItem(mViewPager,mViewPager.getCurrentItem());

        if(fragment instanceof DistributorAccountFragment)
        {
            ((DistributorAccountFragment)fragment).notifySort();
        }
    }


    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }
}
