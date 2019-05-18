package org.nearbyshops.serviceprovider.Login;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import org.nearbyshops.serviceprovider.Login.Interfaces.NotifyAboutLogin;
import org.nearbyshops.serviceprovider.Login.Interfaces.ShowFragmentSelectService;
import org.nearbyshops.serviceprovider.R;


public class Login extends AppCompatActivity implements ShowFragmentSelectService, NotifyAboutLogin {


    public static final String TAG_STEP_ONE = "tag_step_one";
    public static final String TAG_STEP_TWO = "tag_step_two";
    public static final String TAG_STEP_THREE = "tag_step_three";
    public static final String TAG_STEP_FOUR = "tag_step_four";

    public static final String TAG_SELECT_SERVICE = "select_service";


    public static final String TAG_LOGIN_GLOBAL  = "tag_login_global";

    public static final String TAG_LOGIN_FRAGMENT = "tag_login_fragment";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);

        setContentView(R.layout.activity_login_new);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
        toolbar.setTitle("Login");
        setSupportActionBar(toolbar);

        if(savedInstanceState==null)
        {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.fragment_container,new LoginFragment(),TAG_STEP_ONE)
//                    .commitNow();

            if(getIntent().getBooleanExtra(TAG_LOGIN_GLOBAL,false))
            {

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,new LoginGlobalFragment(),TAG_LOGIN_FRAGMENT)
                        .commitNow();

            }
            else
            {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,new LoginFragment(),TAG_LOGIN_FRAGMENT)
                        .commitNow();

            }

        }

    }




    @Override
    public void showSelectServiceFragment() {


//        if(getSupportFragmentManager().findFragmentByTag(TAG_SELECT_SERVICE)==null)
//        {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
//                    .replace(R.id.fragment_container,new SelectServiceFragment(),TAG_SELECT_SERVICE)
//                    .addToBackStack("select_service")
//                    .commit();
//        }

    }







    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
    }

    public static final int RESULT_CODE_LOGIN_SUCCESS  = 1;






    @Override
    public void loginSuccess() {

        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void loggedOut() {

    }
}
