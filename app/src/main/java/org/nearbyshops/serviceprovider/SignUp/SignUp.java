package org.nearbyshops.serviceprovider.SignUp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.SignUp.Interfaces.ShowFragmentSignUp;


public class SignUp extends AppCompatActivity implements ShowFragmentSignUp {


    public static final String TAG_STEP_ONE = "tag_step_one";
    public static final String TAG_STEP_TWO = "tag_step_two";
    public static final String TAG_STEP_THREE = "tag_step_three";
    public static final String TAG_STEP_FOUR = "tag_step_four";

//    User signUpProfile = new User();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overridePendingTransition(R.anim.enter_from_right,R.anim.exit_to_left);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white));
//        toolbar.setTitle("Add Credit");
        setSupportActionBar(toolbar);

//        if(getSupportFragmentManager().findFragmentByTag(TAG_STEP_ONE)==null)
//        {


        if(savedInstanceState==null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,new FragmentEnterName(),TAG_STEP_ONE)
                    .commitNow();
        }


//        }
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_from_left,R.anim.exit_to_right);
    }



    @Override
    public void showEmailPhone() {

            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                    .replace(R.id.fragment_container,new FragmentEmailOrPhone(),TAG_STEP_TWO)
                    .addToBackStack("step_two")
                    .commit();

//
    }



    @Override
    public void showVerifyEmail() {

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                .replace(R.id.fragment_container,new FragmentVerifyEmailSignUp(),TAG_STEP_THREE)
                .addToBackStack("step_three")
                .commit();


    }

    @Override
    public void showEnterPassword() {

        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                .replace(R.id.fragment_container,new FragmentEnterPassword(),TAG_STEP_FOUR)
                .addToBackStack("step_four")
                .commit();

//                        .addToBackStack("step_four")
    }

    @Override
    public void showResultSuccess() {

        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);


        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right,R.anim.exit_to_left,R.anim.enter_from_left,R.anim.exit_to_right)
                .replace(R.id.fragment_container,new FragmentResult())
                .commit();

    }




//    @Override
//    public User getSignUpProfile() {
//        return signUpProfile;
//    }
//
//    @Override
//    public void setSignUpProfile(User signUpProfile) {
//        this.signUpProfile = signUpProfile;
//    }
//





}
