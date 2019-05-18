package org.nearbyshops.serviceprovider.DetachedTabs;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import org.nearbyshops.serviceprovider.DetachedTabs.Interfaces.NotifyAssignParent;
import org.nearbyshops.serviceprovider.DetachedTabs.Interfaces.NotifyTitleChanged;
import org.nearbyshops.serviceprovider.DetachedTabs.Interfaces.NotifyScroll;
import org.nearbyshops.serviceprovider.R;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetachedTabs extends AppCompatActivity implements NotifyTitleChanged, NotifyScroll {


    @BindView(R.id.options) RelativeLayout options;


    private PagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.tablayoutPager)
    TabLayout tabLayoutPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_categories_tabs_detached);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new PagerAdapter(getSupportFragmentManager(),this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayoutPager.setupWithViewPager(mViewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_item_categories_tabs, menu);
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


    @Override
    public void NotifyTitleChanged(String title, int tabPosition) {

        mSectionsPagerAdapter.setTitle(title,tabPosition);
    }






    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }






    @OnClick(R.id.changeParentBulk)
    void assignParentButtonClick()
    {

        Fragment fragment = getSupportFragmentManager().findFragmentByTag(makeFragmentName(mViewPager.getId(),mViewPager.getCurrentItem()));

        if(fragment instanceof NotifyAssignParent)
        {
            ((NotifyAssignParent) fragment).assignParentClick();
        }



//        if(mViewPager.getCurrentItem()==0)
//        {

//            if(assignParentItemCategory!=null)
//            {
//                assignParentItemCategory.assignParentClick();
//            }

//        }

    }



    private static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }




    @Override
    public void scrolled(int dx, int dy) {


        if((options.getTranslationY()+dy) >= 0 && (options.getTranslationY() + dy) < options.getHeight())
        {
            options.setTranslationY(options.getTranslationY() + dy);

        }
        else if((options.getTranslationY()+dy)<0)
        {
            options.setTranslationY(0);
        }
        else if((options.getTranslationY()+dy)>options.getHeight())
        {
            options.setTranslationY(options.getHeight());
        }
    }


}
