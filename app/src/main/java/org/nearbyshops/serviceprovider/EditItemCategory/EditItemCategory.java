package org.nearbyshops.serviceprovider.EditItemCategory;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import org.nearbyshops.serviceprovider.R;



public class EditItemCategory extends AppCompatActivity {

    public static final String TAG_FRAGMENT_EDIT = "fragment_edit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item_category_new);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_EDIT)==null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container,new EditItemCategoryFragment(),TAG_FRAGMENT_EDIT)
                    .commit();
        }
    }


}
