package org.nearbyshops.serviceprovider.ItemSubmissionsList.ItemUpdates;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.nearbyshops.serviceprovider.R;

public class ItemUpdates extends AppCompatActivity {

    public static final String TAG_FRAGMENT = "tag_fragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_updates);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT)==null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new ItemUpdatesFragment(),TAG_FRAGMENT)
                    .commit();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
