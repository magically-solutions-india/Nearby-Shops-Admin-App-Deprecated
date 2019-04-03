package org.nearbyshops.serviceprovider.EditServiceConfig;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.nearbyshops.serviceprovider.R;


public class EditConfiguration extends AppCompatActivity {

    public static final String TAG_FRAGMENT_EDIT = "fragment_edit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
        setContentView(R.layout.activity_edit_service_configuration);





        if(getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT_EDIT)==null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container,new EditConfigurationFragment(),TAG_FRAGMENT_EDIT)
                    .commit();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
    }

}
