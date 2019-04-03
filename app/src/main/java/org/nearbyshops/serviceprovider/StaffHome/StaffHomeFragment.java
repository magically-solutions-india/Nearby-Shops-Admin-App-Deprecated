package org.nearbyshops.serviceprovider.StaffHome;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.nearbyshops.serviceprovider.DaggerComponentBuilder;
import org.nearbyshops.serviceprovider.EditProfile.EditProfile;
import org.nearbyshops.serviceprovider.EditProfile.FragmentEditProfile;
import org.nearbyshops.serviceprovider.Login.Interfaces.NotifyAboutLogin;
import org.nearbyshops.serviceprovider.ModelRoles.OldFiles.Staff;
import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.StaffService;
import org.nearbyshops.serviceprovider.Preferences.PrefLogin;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class StaffHomeFragment extends Fragment {

    @BindView(R.id.notice) TextView notice;
    @Inject StaffService staffService;

    public StaffHomeFragment() {
        DaggerComponentBuilder.getInstance().getNetComponent().Inject(this);
    }





    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.activity_staff_home, container, false);
        ButterKnife.bind(this,rootView);

        checkAccountActivation();

        return rootView;
    }




    void checkAccountActivation()
    {
        // if account is deactivated notify User

        Staff staff = PrefLogin.getStaff(getActivity());

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



        Intent intent = new Intent(getActivity(), EditProfile.class);
        intent.putExtra(FragmentEditProfile.EDIT_MODE_INTENT_KEY, FragmentEditProfile.MODE_UPDATE);
        startActivity(intent);
    }



    @OnClick(R.id.dashboard)
    void dashboardClick()
    {
        Call<Staff> call = staffService.getLogin(PrefLogin.getAuthorizationHeaders(getActivity()));

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
        startActivity(new Intent(getActivity(),StaffDashboard.class));
    }


    void showToastMessage(String message)
    {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }







    @OnClick(R.id.logout)
    void logoutClick()
    {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setTitle("Confirm Logout !")
                .setMessage("Do you want to log out !")
                .setPositiveButton("Yes",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        logout();

                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        showToastMessage("Cancelled !");
                    }
                })
                .show();
    }






    void logout()
    {
        // log out
        PrefLogin.saveUserProfile(null,getActivity());
        PrefLogin.saveCredentials(getActivity(),null,null);

        // stop location update service

        if(getActivity() instanceof NotifyAboutLogin)
        {
            ((NotifyAboutLogin) getActivity()).loginSuccess();
        }
    }



}
