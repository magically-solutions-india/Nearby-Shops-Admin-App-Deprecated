package org.nearbyshops.serviceprovider.SignUp;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.textfield.TextInputEditText;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.nearbyshops.serviceprovider.ModelRoles.User;
import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.SignUp.Interfaces.ShowFragmentSignUp;
import org.nearbyshops.serviceprovider.SignUp.PrefSignUp.PrefrenceSignUp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sumeet on 27/6/17.
 */

public class FragmentEnterName extends Fragment {


    @BindView(R.id.name)
    TextInputEditText name;
    @BindView(R.id.referrar_user_id)
    TextInputEditText referrerUserID;



    User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);


        setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.fragment_enter_name, container, false);
        ButterKnife.bind(this,rootView);


//        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(ContextCompat.getColor(getActivity(),R.color.white));
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);



        user = PrefrenceSignUp.getUser(getActivity());

        if(user == null)
        {
            user = new User();
        }

        bindViews();


        return rootView;
    }


//    void bindViews()
//    {
//
//        if(getActivity() instanceof ReadWriteUser)
//        {
//            User user = ((ReadWriteUser) getActivity()).getSignUpProfile();
//
//            name.setText(user.getName());
//            name.requestFocus();
//        }
//
//    }


//    void saveDataFromViews()
//    {
//        if(getActivity() instanceof ReadWriteUser)
//        {
//            User user = ((ReadWriteUser) getActivity()).getSignUpProfile();
//
//            user.setName(name.getText().toString());
//
//            ((ReadWriteUser) getActivity()).setSignUpProfile(user);
//
//        }
//    }



    void bindViews()
    {
//        User user = PrefrenceSignUp.getUser(getActivity());
//
//        if(user == null)
//        {
//            return;
//        }

        name.setText(user.getName());
        name.requestFocus();

        referrerUserID.setText(String.valueOf(user.getReferredBy()));
    }




    void saveDataFromViews()
    {
        user.setName(name.getText().toString());


        if(!referrerUserID.getText().toString().equals(""))
        {
            int referrerID = Integer.parseInt(referrerUserID.getText().toString());
            user.setReferredBy(referrerID);
        }
    }





    @OnClick(R.id.next)
    void nextClick()
    {
        if(name.getText().toString().equals(""))
        {
            name.setError("Please enter your name !");
            return;
        }


        saveDataFromViews();


        PrefrenceSignUp.saveUser(user,getActivity());

        if(getActivity() instanceof ShowFragmentSignUp)
        {
            ((ShowFragmentSignUp) getActivity()).showEmailPhone();
        }
    }








}
