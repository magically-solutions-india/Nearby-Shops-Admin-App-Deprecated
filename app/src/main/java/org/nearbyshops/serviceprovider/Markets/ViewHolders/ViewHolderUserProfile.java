package org.nearbyshops.serviceprovider.Markets.ViewHolders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import org.nearbyshops.serviceprovider.ModelRoles.User;
import org.nearbyshops.serviceprovider.Preferences.PrefServiceConfig;
import org.nearbyshops.serviceprovider.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViewHolderUserProfile extends RecyclerView.ViewHolder {


    @BindView(R.id.profile_image) ImageView profileImage;
    @BindView(R.id.user_id) TextView userID;
    @BindView(R.id.user_name) TextView userName;
//    @BindView(R.id.phone) TextView phone;



    private Context context;




    public static ViewHolderUserProfile create(ViewGroup parent, Context context)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_user_profile,parent,false);

        return new ViewHolderUserProfile(view,context);
    }




    public ViewHolderUserProfile(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this,itemView);

    }



    void setItem(User user)
    {

        userID.setText("User ID : " + String.valueOf(user.getUserID()));


        Drawable placeholder = ContextCompat.getDrawable(context,R.drawable.ic_nature_people_white_48px);
        String imagePath = PrefServiceConfig.getServiceURL_SDS(context) + "/api/v1/User/Image/" + "five_hundred_"+ user.getProfileImagePath() + ".jpg";


        Picasso.get()
                .load(imagePath)
                .placeholder(placeholder)
                .into(profileImage);


//        phone.setText(user.getPhone());
        userName.setText(user.getName());
    }







//    @OnClick(R.id.list_item)
//    void onlistItemClick()
//    {
//        Intent  intent = new Intent(context, EditProfileStaff.class);
//        intent.putExtra(EditProfileStaff.TAG_IS_GLOBAL_PROFILE,true);
//        intent.putExtra(FragmentEditProfileGlobal.EDIT_MODE_INTENT_KEY,FragmentEditProfileGlobal.MODE_UPDATE);
//        context.startActivity(intent);
//    }


}
