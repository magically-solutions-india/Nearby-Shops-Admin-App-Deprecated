package org.nearbyshops.serviceprovider;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import org.nearbyshops.serviceprovider.ModelRoles.User;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.UserService;
import org.nearbyshops.serviceprovider.Utility.PrefGeneral;
import org.nearbyshops.serviceprovider.Utility.PrefLogin;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.distributorIDEdittext) EditText username;
    @BindView(R.id.loginButton) Button loginButton;
    @BindView(R.id.password) EditText password;


    @Inject Gson gson;


    public LoginScreen() {

        DaggerComponentBuilder.getInstance()
                .getNetComponent()
                .Inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);


        username.setText(PrefLogin.getUsername(this));
        password.setText(PrefLogin.getPassword(this));

    }



    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.loginButton:

                startActivity(new Intent(this,Home.class));
                break;
        }

    }




    @OnClick(R.id.loginButton)
    public void login()
    {

        PrefLogin.saveCredentials(this,username.getText().toString(),password.getText().toString());

        networkCallLoginAdminSimple();
    }




    void networkCallLoginAdminSimple()
    {

        String username = this.username.getText().toString();
        String password = this.password.getText().toString();


        setProgressBar(true);
        loginButton.setVisibility(View.INVISIBLE);





        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(PrefGeneral.getServiceURL(MyApplication.getAppContext()))
                .build();

        UserService userService = retrofit.create(UserService.class);

        Call<User> call = userService.getProfileWithLogin(PrefLogin.baseEncoding(username,password));


        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.code()==200)
                {

                    PrefLogin.saveUserProfile(response.body(),LoginScreen.this);
                    startActivity(new Intent(LoginScreen.this,Home.class));
                }
                else
                {
                    showSnackBar("Failed Code : " + String.valueOf(response.code()));
                }

                setProgressBar(false);
                loginButton.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {


                showSnackBar("Failed Network !");

                setProgressBar(false);
                loginButton.setVisibility(View.VISIBLE);
            }
        });




    }


//    void networkCallLoginAdmin()
//    {
//
//        String username = this.username.getText().toString();
//        String password = this.password.getText().toString();
//
//
//        setProgressBar(true);
//
//
//        Observable<Admin> call = adminService.getAdmin(UtilityLogin.baseEncoding(username,password));
//
//        /*call.enqueue(new Callback<Admin>() {
//            @Override
//            public void onResponse(Call<Admin> call, Response<Admin> response) {
//
//
//            }
//
//            @Override
//            public void onFailure(Call<Admin> call, Throwable t) {
//
//            }
//        });*/
//
//        call.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Admin>() {
//                    @Override
//                    public void onCompleted() {
//
//                        setProgressBar(false);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                        if(e instanceof HttpException)
//                        {
//                            HttpException response = (HttpException) e;
//                            Log.d("login",String.valueOf(response.code()));
//
//                            if(response.code()==403 || response.code() ==401)
//                            {
//                                showSnackBar("Unable to login. Username or password is incorrect !");
//                            }
//                            else
//                            {
//                                showSnackBar("LoginScreen failed. Please try again !");
//                            }
//
//                        }
//
//                        setProgressBar(false);
//
//                    }
//
//                    @Override
//                    public void onNext(Admin admin) {
//
//                        if(admin !=null)
//                        {
//                            Gson gson = new Gson();
//                            Log.d("login", gson.toJson(admin));
//
//                            UtilityLogin.saveAdmin(admin,LoginScreen.this);
//
//                            startActivity(new Intent(LoginScreen.this,Home.class));
//
//                        }
//
//                        setProgressBar(false);
//
//                    }
//                });
//
//    }






    void showSnackBar(String message)
    {
//        Snackbar.make(loginButton,message, Snackbar.LENGTH_SHORT).show();
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }









    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    void setProgressBar(boolean visible)
    {
        progressBar.setIndeterminate(true);

        if(visible)
        {
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }
        else
        {
            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }

    }


}
