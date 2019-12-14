package org.nearbyshops.serviceprovider.StaffHome;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.nearbyshops.serviceprovider.DaggerComponentBuilder;
import org.nearbyshops.serviceprovider.EditProfileStaff.EditProfileStaff;
import org.nearbyshops.serviceprovider.EditProfileStaff.FragmentEditProfileStaff;
import org.nearbyshops.serviceprovider.ModelRoles.OldFiles.Staff;
import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.StaffService;
import org.nearbyshops.serviceprovider.Preferences.PrefLogin;

import javax.inject.Inject;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class StaffHome extends AppCompatActivity {

    @BindView(R.id.notice) TextView notice;
    @Inject StaffService staffService;

    public StaffHome() {
        DaggerComponentBuilder.getInstance().getNetComponent().Inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_home);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkAccountActivation();
    }







    void checkAccountActivation()
    {
        // if account is deactivated notify User

        Staff staff = PrefLogin.getStaff(this);

        if(staff!=null && !staff.getEnabled())
        {
            notice.setVisibility(View.VISIBLE);
        }
        else
        {
            notice.setVisibility(View.GONE);
        }

    }




    @OnClick(R.id.edit_profile)
    void editProfileClick()
    {
//        Intent intent = new Intent(this, EditStaffSelf.class);
//        intent.putExtra(EditStaffSelfFragment.EDIT_MODE_INTENT_KEY,EditStaffSelfFragment.MODE_UPDATE);
//        startActivity(intent);



        Intent intent = new Intent(this, EditProfileStaff.class);
        intent.putExtra(FragmentEditProfileStaff.EDIT_MODE_INTENT_KEY, FragmentEditProfileStaff.MODE_UPDATE);
        startActivity(intent);
    }



    @OnClick(R.id.dashboard)
    void dashboardClick()
    {
        Call<Staff> call = staffService.getLogin(PrefLogin.getAuthorizationHeaders(this));

//        call.enqueue(new Callback<Staff>() {
//            @Override
//            public void onResponse(Call<Staff> call, Response<Staff> response) {
//
//                if(response.code()==200)
//                {
//                    // permitted
//                    if(response.body().getEnabled())
//                    {
//                        PrefLogin.saveStaff(response.body(),StaffHome.this);
//                        startActivity(new Intent(StaffHome.this,StaffDashboard.class));
//                    }
//                    else
//                    {
//                        showToastMessage("Not Permitted. Your Account is Disabled !");
//                    }
//                }
//                else if(response.code() == 401 || response.code() ==403)
//                {
//                    showToastMessage("Not permitted !");
//                }
//                else
//                {
//                    showToastMessage("Server Error Code : " + String.valueOf(response.code()));
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<Staff> call, Throwable t) {
//
//                showToastMessage("Network Failed. Check your Internet Connection !");
//            }
//        });



//        PrefLogin.saveStaff(response.body(),StaffHome.this);
        startActivity(new Intent(StaffHome.this,StaffDashboard.class));
    }


    void showToastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }




}
