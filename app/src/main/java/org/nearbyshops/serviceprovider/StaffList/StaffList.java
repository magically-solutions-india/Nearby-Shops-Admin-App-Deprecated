package org.nearbyshops.serviceprovider.StaffList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.SignUp.PrefSignUp.PrefrenceSignUp;
import org.nearbyshops.serviceprovider.SignUp.SignUp;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class StaffList extends AppCompatActivity {


    public static final String TAG_STEP_ONE = "tag_step_one";
    public static final String TAG_STEP_TWO = "tag_step_two";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
        setContentView(R.layout.activity_staff_list);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
//        toolbar.setTitle("Forgot Password");
        setSupportActionBar(toolbar);


        if(savedInstanceState==null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,new StaffListFragment(),TAG_STEP_ONE)
                    .commitNow();
        }


    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
    }


    void showToastMessage(String message)
    {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }






    @OnClick(R.id.fab)
    void fabClick()
    {
//        showToastMessage("Fab click !");

        PrefrenceSignUp.saveUser(null,this);
        Intent intent = new Intent(this, SignUp.class);
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
