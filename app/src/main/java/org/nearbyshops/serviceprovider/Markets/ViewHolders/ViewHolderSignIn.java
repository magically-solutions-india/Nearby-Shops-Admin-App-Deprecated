package org.nearbyshops.serviceprovider.Markets.ViewHolders;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import org.nearbyshops.serviceprovider.Login.Interfaces.NotifyAboutLogin;
import org.nearbyshops.serviceprovider.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class ViewHolderSignIn extends RecyclerView.ViewHolder{



    private Context context;
    private NotifyAboutLogin fragment;




    public static ViewHolderSignIn create(ViewGroup parent, Context context)
    {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_sign_in, parent, false);

        return new ViewHolderSignIn(view,parent,context);
    }





    public ViewHolderSignIn(View itemView, ViewGroup parent, Context context)
    {
        super(itemView);
        ButterKnife.bind(this,itemView);
        this.context = context;
//        this.fragment = fragment;
    }








    @OnClick(R.id.sign_in_button)
    void selectMarket()
    {
        if(context instanceof VHSignIn)
        {
            ((VHSignIn) context).signInClick();
        }
    }


    public interface VHSignIn
    {
        void signInClick();
    }

}



