package org.nearbyshops.serviceprovider.AdminDashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.nearbyshops.serviceprovider.DaggerComponentBuilder;
import org.nearbyshops.serviceprovider.EditProfile.EditProfile;
import org.nearbyshops.serviceprovider.EditProfile.FragmentEditProfile;
import org.nearbyshops.serviceprovider.ItemSpecName.ItemSpecName;
import org.nearbyshops.serviceprovider.ItemsByCategorySimple.ItemCategoriesSimple;
import org.nearbyshops.serviceprovider.Login.Interfaces.NotifyAboutLogin;
import org.nearbyshops.serviceprovider.OrderHistoryNew.OrderHistoryNew;
import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.ServiceConfigurationService;
import org.nearbyshops.serviceprovider.ShopAdminList.ShopAdminApprovals;
import org.nearbyshops.serviceprovider.ShopsList.ShopsDatabase;
import org.nearbyshops.serviceprovider.StaffList.StaffList;
import org.nearbyshops.serviceprovider.Utility.PrefLogin;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class AdminDashboardFragment extends Fragment {

//    DrawerLayout drawer;
//    NavigationView navigationView;

//    @Bind(R.id.option_saved_configuration)
//    RelativeLayout optionSavedConfiguration;

    @Inject
    ServiceConfigurationService configurationService;



    public AdminDashboardFragment() {
        DaggerComponentBuilder.getInstance()
                .getNetComponent().Inject(this);
    }





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.app_bar_home, container, false);

        ButterKnife.bind(this,rootView);


        return rootView;
    }








//    void setupFab()
//    {
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }



//    @OnClick(R.id.option_service_stats)
//    void optionServiceStatsClick()
//    {
//        startActivity(new Intent(this,MapsActivity.class));
//    }


//    @OnClick(R.id.detached_items)
//    void optionDetachedClick()
//    {
//        startActivity(new Intent(this, DetachedTabs.class));
//    }


    @OnClick(R.id.items_database)
    void optionItemCatApprovals()
    {
        startActivity(new Intent(getActivity(), ItemCategoriesSimple.class));
    }





    @OnClick(R.id.item_specifications)
    void itemSpecNameClick()
    {
        Intent intent = new Intent(getActivity(), ItemSpecName.class);
        startActivity(intent);
    }







    void showToastMessage(String message)
    {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }






    @OnClick(R.id.shop_approvals)
    void optionAdminClick(View view)
    {
        Intent intent = new Intent(getActivity(), ShopsDatabase.class);
        startActivity(intent);
    }



    @OnClick(R.id.staff_accounts)
    void optionStaffClick(View view)
    {
//        Intent intent = new Intent(this, StaffAccounts.class);
//        startActivity(intent);

        startActivity(new Intent(getActivity(), StaffList.class));
    }





//    @OnClick(R.id.shop_admin_approvals)
    void distributorAccountClick(View view)
    {
//        Intent intent = new Intent(this, DistributorAccountsActivity.class);
//        startActivity(intent);

        Intent intent = new Intent(getActivity(), ShopAdminApprovals.class);
        startActivity(intent);
    }








    @OnClick(R.id.edit_profile)
    void editProfileClick()
    {
//        Intent intent = new Intent(this, EditProfileAdmin.class);
//        intent.putExtra(EditAdminFragment.EDIT_MODE_INTENT_KEY,EditAdminFragment.MODE_UPDATE);
//        startActivity(intent);

        Intent intent = new Intent(getActivity(), EditProfile.class);
        intent.putExtra(FragmentEditProfile.EDIT_MODE_INTENT_KEY, FragmentEditProfile.MODE_UPDATE);
        startActivity(intent);

    }


    @OnClick(R.id.orders_database)
    void ordersClick()
    {
//        showToastMessage("Orders DB Clicked !");


        Intent intent = new Intent(getActivity(), OrderHistoryNew.class);
        startActivity(intent);
    }



//    @OnClick(R.id.item_submissions)
//    void itemSubmissionClick()
//    {
//        Intent intent = new Intent(this, ItemSubmissionMenu.class);
//        startActivity(intent);
//    }




//    @Bind(R.id.row_three)
//    LinearLayout rowSettins;

//    @Bind(R.id.row_eight)
//    LinearLayout rowAccounts;


//    void setVisibilityByRole() {
//
//        if (UtilityLogin.getRoleID(this) == UtilityLogin.ROLE_STAFF)
//        {
//            rowAccounts.setVisibility(View.GONE);
//            rowSettins.setVisibility(View.GONE);
//
//        }
//        else if(UtilityLogin.getRoleID(this)== UtilityLogin.ROLE_ADMIN)
//        {
//            rowAccounts.setVisibility(View.VISIBLE);
//            rowSettins.setVisibility(View.VISIBLE);
//        }
//    }





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
