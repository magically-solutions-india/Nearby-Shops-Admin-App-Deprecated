package org.nearbyshops.serviceprovider.ItemsByCategorySimple.ItemImageList;

import android.content.Intent;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.Toast;


import org.nearbyshops.serviceprovider.EditItemImage.EditItemImage;
import org.nearbyshops.serviceprovider.EditItemImage.EditItemImageFragment;
import org.nearbyshops.serviceprovider.R;

import butterknife.ButterKnife;
import butterknife.OnClick;



public class ImageList extends AppCompatActivity {


    public static final String TAG_STEP_ONE = "tag_step_one";
    public static final String TAG_STEP_TWO = "tag_step_two";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
        setContentView(R.layout.activity_image_list);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
//        toolbar.setTitle("Forgot Password");
        setSupportActionBar(toolbar);


        if(savedInstanceState==null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,new ImageListFragment(),TAG_STEP_ONE)
                    .commitNow();
        }


    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
    }


    void showToastMessage(String message)
    {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }







    @OnClick(R.id.fab)
    void fabClick()
    {
        int itemID = getIntent().getIntExtra("item_id",0);

        Intent intent = new Intent(this, EditItemImage.class);
        intent.putExtra(EditItemImageFragment.EDIT_MODE_INTENT_KEY,EditItemImageFragment.MODE_ADD);
        intent.putExtra(EditItemImageFragment.ITEM_ID_INTENT_KEY,itemID);
        startActivity(intent);
    }






//    @OnClick({R.id.filters})
    void showFilters()
    {
        showToastMessage("Filters Click !");
//        FilterTaxi filterTaxi = new FilterTaxi();
//        filterTaxi.show(getActivity().getSupportFragmentManager(),"filter_taxi");
    }

}
