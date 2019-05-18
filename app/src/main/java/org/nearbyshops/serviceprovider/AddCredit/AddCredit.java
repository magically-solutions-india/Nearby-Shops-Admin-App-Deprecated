package org.nearbyshops.serviceprovider.AddCredit;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.nearbyshops.serviceprovider.R;


public class AddCredit extends AppCompatActivity {


    public static final String TAG_USER_ID = "tag_user_id";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
        toolbar.setTitle("Add Credit");
        setSupportActionBar(toolbar);





        if(savedInstanceState==null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,new FragmentAddCredit(),"tag_fragment_one")
                    .commitNow();
        }


    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
    }






}
