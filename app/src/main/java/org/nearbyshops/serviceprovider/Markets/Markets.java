package org.nearbyshops.serviceprovider.Markets;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import org.nearbyshops.serviceprovider.Interfaces.RefreshFragment;
import org.nearbyshops.serviceprovider.Login.Interfaces.NotifyAboutLogin;
import org.nearbyshops.serviceprovider.Login.Login;
import org.nearbyshops.serviceprovider.Markets.Interfaces.MarketSelected;
import org.nearbyshops.serviceprovider.Markets.ViewHolders.ViewHolderEmptyScreen;
import org.nearbyshops.serviceprovider.Markets.ViewHolders.ViewHolderSignIn;
import org.nearbyshops.serviceprovider.R;

import butterknife.ButterKnife;


public class Markets extends AppCompatActivity implements MarketSelected, NotifyAboutLogin, ViewHolderSignIn.VHSignIn , ViewHolderEmptyScreen.VHCreateMarket {


//    public static final String SHOP_DETAIL_INTENT_KEY = "shop_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_detail);
        ButterKnife.bind(this);



        if (getSupportFragmentManager().findFragmentByTag("market_fragment") == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new MarketsFragmentNew(), "market_fragment")
                    .commit();
        }



//        String shopJson = getIntent().getStringExtra(TAG_JSON_STRING);
//        Shop market = UtilityFunctions.provideGson().fromJson(shopJson, Shop.class);


//        getSupportActionBar().setTitle(market.getShopName());




    }






//    @Override
    public void NotifyLogin() {

    }






    @Override
    public void marketSelected() {
        finish();
    }

    @Override
    public void loginSuccess() {

        refreshFragment();
    }



    @Override
    public void loggedOut() {

        refreshFragment();
    }


    void refreshFragment()
    {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if(fragment instanceof RefreshFragment)
        {
            ((RefreshFragment) fragment).refreshFragment();
        }
    }







    @Override
    public void signInClick() {

        Intent intent = new Intent(this, Login.class);
        intent.putExtra(Login.TAG_LOGIN_GLOBAL,true);
        startActivityForResult(intent,123);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==123)
        {
            refreshFragment();
        }
    }






    @Override
    public void createMarketClick() {
//        showToastMessage("Create Market Clicked !");


        String url = "https://nearbyshops.org/entrepreneur.html";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }




    void showToastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
}
