package org.nearbyshops.serviceprovider.EditItemOld;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.nearbyshops.serviceprovider.DaggerComponentBuilder;
import org.nearbyshops.serviceprovider.Model.Image;
import org.nearbyshops.serviceprovider.Model.Item;
import org.nearbyshops.serviceprovider.Model.ItemCategory;
import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.RetrofitRESTContractItem.ItemService;
import org.nearbyshops.serviceprovider.Preferences.PrefGeneral;
import org.nearbyshops.serviceprovider.Preferences.PrefLogin;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class EditItemFragmentOld extends Fragment {

    public static int PICK_IMAGE_REQUEST = 21;
    // Upload the image after picked up
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 56;


    ItemCategory itemCategory;

    public static final String ITEM_CATEGORY_INTENT_KEY = "item_cat";

//    Validator validator;


//    @Inject
//    DeliveryGuySelfService deliveryService;

    @Inject
    ItemService itemService;


    // flag for knowing whether the image is changed or not
    boolean isImageChanged = false;
    boolean isImageRemoved = false;


    // bind views
    @BindView(R.id.uploadImage)
    ImageView resultView;


//    @BindView(R.id.shop_open) CheckBox shopOpen;
//    @BindView(R.id.shop_id) EditText shopID;

    @BindView(R.id.itemID) EditText itemID;
    @BindView(R.id.itemName) EditText itemName;
    @BindView(R.id.itemDescription) EditText itemDescription;
    @BindView(R.id.itemDescriptionLong) EditText itemDescriptionLong;
    @BindView(R.id.quantityUnit) EditText quantityUnit;


//    @BindView(R.id.enter_shop_id) EditText shopIDEnter;
//    @BindView(R.id.shopName) EditText shopName;

//    @BindView(R.id.shopAddress) EditText shopAddress;
//    @BindView(R.id.shopCity) EditText city;
//    @BindView(R.id.shopPincode) EditText pincode;
//    @BindView(R.id.shopLandmark) EditText landmark;

//    @BindView(R.id.customerHelplineNumber) EditText customerHelplineNumber;
//    @BindView(R.id.deliveryHelplineNumber) EditText deliveryHelplineNumber;

//    @BindView(R.id.shopShortDescription) EditText shopDescriptionShort;
//    @BindView(R.id.shopLongDescription) EditText shopDescriptionLong;

//    @BindView(R.id.latitude) EditText latitude;
//    @BindView(R.id.longitude) EditText longitude;
//    @BindView(R.id.pick_location_button) TextView pickLocationButton;
//    @BindView(R.id.rangeOfDelivery) EditText rangeOfDelivery;

//    @BindView(R.id.deliveryCharges) EditText deliveryCharge;
//    @BindView(R.id.billAmountForFreeDelivery) EditText billAmountForFreeDelivery;

//    @BindView(R.id.pick_from_shop_available) CheckBox pickFromShopAvailable;
//    @BindView(R.id.home_delivery_available) CheckBox homeDeliveryAvailable;



//    @BindView(R.id.item_id) EditText item_id;
//    @BindView(R.id.name) EditText name;
//    @BindView(R.id.username) EditText username;
//    @BindView(R.id.password) EditText password;
//    @BindView(R.id.about) EditText about;

//    @BindView(R.id.phone_number) EditText phone;
//    @BindView(R.id.designation) EditText designation;
//    @BindView(R.id.switch_enable) Switch aSwitch;

    @BindView(R.id.saveButton) Button buttonUpdateItem;


    public static final String SHOP_INTENT_KEY = "shop_intent_key";
    public static final String EDIT_MODE_INTENT_KEY = "edit_mode";

    public static final int MODE_UPDATE = 52;
    public static final int MODE_ADD = 51;

    int current_mode = MODE_ADD;

//    DeliveryGuySelf deliveryGuySelf = new DeliveryGuySelf();
//    ShopAdmin shopAdmin = new ShopAdmin();
        Item item = new Item();

    public EditItemFragmentOld() {

        DaggerComponentBuilder.getInstance()
                .getNetComponent().Inject(this);
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.content_edit_item_fragment, container, false);

        ButterKnife.bind(this,rootView);

        if(savedInstanceState==null)
        {
//            shopAdmin = getActivity().getIntent().getParcelableExtra(SHOP_ADMIN_INTENT_KEY);

            current_mode = getActivity().getIntent().getIntExtra(EDIT_MODE_INTENT_KEY,MODE_ADD);
            itemCategory = getActivity().getIntent().getParcelableExtra(ITEM_CATEGORY_INTENT_KEY);

            if(current_mode == MODE_UPDATE)
            {
                item = UtilityItemOld.getItem(getContext());
            }
            else if (current_mode == MODE_ADD)
            {
//                item.setItemCategoryID(itemCategory.getItemCategoryID());
//                System.out.println("Item Category ID : " + item.getItemCategoryID());
            }


            if(item !=null) {
                bindDataToViews();
            }


            showLogMessage("Inside OnCreateView - Saved Instance State !");
        }



//        if(validator==null)
//        {
//            validator = new Validator(this);
//            validator.setValidationListener(this);
//        }

        updateIDFieldVisibility();


        if(item !=null) {
            loadImage(item.getItemImageURL());
            showLogMessage("Inside OnCreateView : DeliveryGUySelf : Not Null !");
        }


        showLogMessage("Inside On Create View !");

        return rootView;
    }

    void updateIDFieldVisibility()
    {

        if(current_mode==MODE_ADD)
        {
            buttonUpdateItem.setText("Add Item");
            itemID.setVisibility(View.GONE);
        }
        else if(current_mode== MODE_UPDATE)
        {
            itemID.setVisibility(View.VISIBLE);
            buttonUpdateItem.setText("Save");
        }
    }


    public static final String TAG_LOG = "TAG_LOG";

    void showLogMessage(String message)
    {
        Log.i(TAG_LOG,message);
        System.out.println(message);
    }



    void loadImage(String imagePath) {

        String iamgepath = PrefGeneral.getServiceURL(getContext()) + "/api/v1/Item/Image/" + imagePath;

        System.out.println(iamgepath);

        Picasso.get()
                .load(iamgepath)
                .into(resultView);
    }




    @OnClick(R.id.saveButton)
    public void UpdateButtonClick()
    {

        if(!validateData())
        {
//            showToastMessage("Please correct form data before save !");
            return;
        }

        if(current_mode == MODE_ADD)
        {
            item = new Item();
            addAccount();
        }
        else if(current_mode == MODE_UPDATE)
        {
            update();
        }
    }


    boolean validateData()
    {
        boolean isValid = true;

        if(itemName.getText().toString().length()==0)
        {
            itemName.setError("Item Name cannot be empty !");
            itemName.requestFocus();
            isValid= false;
        }



        return isValid;
    }




    void addAccount()
    {
        if(isImageChanged)
        {
            if(!isImageRemoved)
            {
                // upload image with add
                uploadPickedImage(false);
            }


            // reset the flags
            isImageChanged = false;
            isImageRemoved = false;

        }
        else
        {
            // post request
            retrofitPOSTRequest();
        }

    }


    void update()
    {

        if(isImageChanged)
        {


            // delete previous Image from the Server
            deleteImage(item.getItemImageURL());

            /*ImageCalls.getInstance()
                    .deleteImage(
                            itemForEdit.getItemImageURL(),
                            new DeleteImageCallback()
                    );*/


            if(isImageRemoved)
            {

                item.setItemImageURL(null);
                retrofitPUTRequest();

            }else
            {

                uploadPickedImage(true);
            }


            // resetting the flag in order to ensure that future updates do not upload the same image again to the server
            isImageChanged = false;
            isImageRemoved = false;

        }else {

            retrofitPUTRequest();
        }
    }



    void bindDataToViews()
    {
        if(item !=null) {

            itemID.setText(String.valueOf(item.getItemID()));
            itemName.setText(item.getItemName());
            itemDescription.setText(item.getItemDescription());

            quantityUnit.setText(item.getQuantityUnit());
            itemDescriptionLong.setText(item.getItemDescriptionLong());
        }
    }





    void getDataFromViews()
    {
        if(item ==null)
        {
            if(current_mode == MODE_ADD)
            {
//                item = new Item();
            }
            else
            {
                return;
            }
        }

//        if(current_mode == MODE_ADD)
//        {
//            deliveryGuySelf.setShopID(UtilityShopHome.getShop(getContext()).getShopID());
//        }


        item.setItemName(itemName.getText().toString());
        item.setItemDescription(itemDescription.getText().toString());

        item.setItemDescriptionLong(itemDescriptionLong.getText().toString());
        item.setQuantityUnit(quantityUnit.getText().toString());

    }



    public void retrofitPUTRequest()
    {

        getDataFromViews();


        Call<ResponseBody> call = itemService.updateItem(
                PrefLogin.getAuthorizationHeaders(getContext()),
                item,item.getItemID()
        );


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.code()==200)
                {
                    showToastMessage("Update Successful !");
                    UtilityItemOld.saveItem(item,getContext());
                }
                else if(response.code()== 403 || response.code() ==401)
                {
                    showToastMessage("Failed ! Reason : Not Permitted !");
                }
                else
                {
                    showToastMessage("Update Failed Code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }


    void retrofitPOSTRequest()
    {
        getDataFromViews();
        item.setItemCategoryID(itemCategory.getItemCategoryID());

        System.out.println("Item Category ID (POST) : " + item.getItemCategoryID());

        Call<Item> call = itemService.insertItem(PrefLogin.getAuthorizationHeaders(getContext()), item);

        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(Call<Item> call, Response<Item> response) {

                if(response.code()==201)
                {
                    showToastMessage("Add successful !");

                    current_mode = MODE_UPDATE;
                    updateIDFieldVisibility();
                    item = response.body();
                    bindDataToViews();

                    UtilityItemOld.saveItem(item,getContext());

                }
                else
                {
                    showToastMessage("Add failed Code : " + response.code());
                }

            }

            @Override
            public void onFailure(Call<Item> call, Throwable t) {
                showToastMessage("Add failed !");
            }
        });

    }





    /*
        Utility Methods
     */




    void showToastMessage(String message)
    {
        Toast.makeText(getContext(),message,Toast.LENGTH_SHORT).show();
    }




    @BindView(R.id.textChangePicture)
    TextView changePicture;


    @OnClick(R.id.removePicture)
    void removeImage()
    {

        File file = new File(getContext().getCacheDir().getPath() + "/" + "SampleCropImage.jpeg");
        file.delete();

        resultView.setImageDrawable(null);

        isImageChanged = true;
        isImageRemoved = true;
    }



    public static void clearCache(Context context)
    {
        File file = new File(context.getCacheDir().getPath() + "/" + "SampleCropImage.jpeg");
        file.delete();
    }



    @OnClick(R.id.textChangePicture)
    void pickShopImage() {

//        ImageCropUtility.showFileChooser(()getActivity());



        // code for checking the Read External Storage Permission and granting it.
        if (PermissionChecker.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            /// / TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_READ_EXTERNAL_STORAGE);

            return;
        }



        clearCache(getContext());

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent result) {

        super.onActivityResult(requestCode, resultCode, result);



        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && result != null
                && result.getData() != null) {


            Uri filePath = result.getData();

            //imageUri = filePath;

            if (filePath != null) {

                startCropActivity(result.getData(),getContext());
            }

        }


        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {

            resultView.setImageURI(null);
            resultView.setImageURI(UCrop.getOutput(result));

            isImageChanged = true;
            isImageRemoved = false;


        } else if (resultCode == UCrop.RESULT_ERROR) {

            final Throwable cropError = UCrop.getError(result);

        }
    }



    // upload image after being picked up
    void startCropActivity(Uri sourceUri, Context context) {



        final String SAMPLE_CROPPED_IMAGE_NAME = "SampleCropImage.jpeg";

        Uri destinationUri = Uri.fromFile(new File(getContext().getCacheDir(), SAMPLE_CROPPED_IMAGE_NAME));

        UCrop.Options options = new UCrop.Options();
        options.setFreeStyleCropEnabled(true);

//        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
//        options.setCompressionQuality(100);

        options.setToolbarColor(ContextCompat.getColor(getContext(),R.color.blueGrey800));
        options.setStatusBarColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
        options.setAllowedGestures(UCropActivity.ALL, UCropActivity.ALL, UCropActivity.ALL);


        // this function takes the file from the source URI and saves in into the destination URI location.
        UCrop.of(sourceUri, destinationUri)
                .withOptions(options)
                .start(context,this);

        //.withMaxResultSize(400,300)
        //.withMaxResultSize(500, 400)
        //.withAspectRatio(16, 9)
    }





    /*

    // Code for Uploading Image

     */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    showToastMessage("Permission Granted !");
                    pickShopImage();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {


                    showToastMessage("Permission Denied for Read External Storage . ");
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }

    }





    public void uploadPickedImage(final boolean isModeEdit)
    {

        Log.d("applog", "onClickUploadImage");


        // code for checking the Read External Storage Permission and granting it.
        if (PermissionChecker.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {


            /// / TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_READ_EXTERNAL_STORAGE);

            return;
        }


        File file = new File(getContext().getCacheDir().getPath() + "/" + "SampleCropImage.jpeg");


        // Marker

        RequestBody requestBodyBinary = null;

        InputStream in = null;

        try {
            in = new FileInputStream(file);

            byte[] buf;
            buf = new byte[in.available()];
            while (in.read(buf) != -1) ;

            requestBodyBinary = RequestBody.create(MediaType.parse("application/octet-stream"), buf);

        } catch (Exception e) {
            e.printStackTrace();
        }



        Call<Image> imageCall = itemService.uploadImage(PrefLogin.getAuthorizationHeaders(getContext()),
                requestBodyBinary);


        imageCall.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {

                if(response.code()==201)
                {
//                    showToastMessage("Image UPload Success !");

                    Image image = response.body();
                    // check if needed or not . If not needed then remove this line
//                    loadImage(image.getPath());


                    item.setItemImageURL(image.getPath());

                }
                else if(response.code()==417)
                {
                    showToastMessage("Cant Upload Image. Image Size should not exceed 2 MB.");

                    item.setItemImageURL(null);

                }
                else
                {
                    showToastMessage("Image Upload failed !");
                    item.setItemImageURL(null);

                }

                if(isModeEdit)
                {
                    retrofitPUTRequest();
                }
                else
                {
                    retrofitPOSTRequest();
                }


            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {

                showToastMessage("Image Upload failed !");
                item.setItemImageURL(null);


                if(isModeEdit)
                {
                    retrofitPUTRequest();
                }
                else
                {
                    retrofitPOSTRequest();
                }
            }
        });

    }



    void deleteImage(String filename)
    {
        Call<ResponseBody> call = itemService.deleteImage(
                PrefLogin.getAuthorizationHeaders(getContext()),
                filename);



        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                    if(response.code()==200)
                    {
                        showToastMessage("Image Removed !");
                    }
                    else
                    {
//                        showToastMessage("Image Delete failed");
                    }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

//                showToastMessage("Image Delete failed");
            }
        });
    }


}
