package org.nearbyshops.serviceprovider.ItemsByCategorySimple.ItemImageList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;


import org.nearbyshops.serviceprovider.DaggerComponentBuilder;
import org.nearbyshops.serviceprovider.ItemSubmissionsList.HeaderTitle;
import org.nearbyshops.serviceprovider.ItemsByCategorySimple.EditItemImage.EditItemImage;
import org.nearbyshops.serviceprovider.ItemsByCategorySimple.EditItemImage.EditItemImageFragment;
import org.nearbyshops.serviceprovider.ItemsByCategorySimple.EditItemImage.UtilityItemImage;
import org.nearbyshops.serviceprovider.Model.ItemImage;
import org.nearbyshops.serviceprovider.ModelItemSpecification.EndPoints.ItemImageEndPoint;
import org.nearbyshops.serviceprovider.ModelRoles.User;
import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.UserService;
import org.nearbyshops.serviceprovider.RetrofitRESTContractItem.ItemImageService;
import org.nearbyshops.serviceprovider.Utility.PrefLogin;
import org.nearbyshops.serviceprovider.Utility.UtilityFunctions;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sumeet on 14/6/17.
 */

public class ImageListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Adapter.NotificationsFromAdapter{

    boolean isDestroyed = false;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;


    @Inject
    ItemImageService service;

    GridLayoutManager layoutManager;
    Adapter listAdapter;

    ArrayList<Object> dataset = new ArrayList<>();


    // flags
    boolean clearDataset = false;

    boolean getRowCountVehicle = false;
    boolean resetOffsetVehicle = false;


    private int limit_vehicle = 10;
    int offset_vehicle = 0;
    public int item_count_vehicle = 0;


//    @BindView(R.id.drivers_count) TextView driversCount;
//    int i = 1;

    public ImageListFragment() {

        DaggerComponentBuilder.getInstance()
                .getNetComponent().Inject(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        setRetainInstance(true);
        View rootView = inflater.inflate(R.layout.fragment_trip_history, container, false);
        ButterKnife.bind(this, rootView);


//        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
//        toolbar.setTitleTextColor(ContextCompat.getColor(getActivity(),R.color.white));
//        toolbar.setTitle("Trip History");
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);


        setupSwipeContainer();
        setupRecyclerView();

        if (savedInstanceState == null) {
            makeRefreshNetworkCall();
        }


//        driversCount.setText("Drivers COunt : " + String.valueOf(++i));

        return rootView;
    }


    void setupSwipeContainer() {

        if (swipeContainer != null) {

            swipeContainer.setOnRefreshListener(this);
            swipeContainer.setColorSchemeResources(
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
        }

    }


    void setupRecyclerView() {

        listAdapter = new Adapter(dataset, getActivity(), this, this);
        recyclerView.setAdapter(listAdapter);

        layoutManager = new GridLayoutManager(getActivity(), 1, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

//        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));


        final DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                if (layoutManager.findLastVisibleItemPosition() == dataset.size()) {

                    if (offset_vehicle + limit_vehicle > layoutManager.findLastVisibleItemPosition()) {
                        return;
                    }


                    // trigger fetch next page

                    if ((offset_vehicle + limit_vehicle) <= item_count_vehicle) {
                        offset_vehicle = offset_vehicle + limit_vehicle;

                        getTripHistory();
                    }


                }
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        isDestroyed = false;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
    }


    void makeRefreshNetworkCall() {
        swipeContainer.post(new Runnable() {
            @Override
            public void run() {

                swipeContainer.setRefreshing(true);
                onRefresh();
            }
        });
    }


    @Override
    public void onRefresh() {

        clearDataset = true;
        getRowCountVehicle = true;
        resetOffsetVehicle = true;

        getTripHistory();
    }






    /* Token renewal variables : BEGIN */

    // constants - request codes for token renewal
    public static final int REQUEST_CODE_GET_REQUESTS = 1;
    public static final int REQUEST_CODE_UPDATE_ENABLE_STATUS = 2;
    private static final int REQUEST_CODE_GET_CURRENT_TRIP = 3;
    private static final int REQUEST_CODE_DELETE_IMAGE = 4;

    // housekeeping for token renewal
    int token_renewal_attempts = 0;  // variable to keep record of renewal attempts
    int token_renewal_request_code = -1; // variable to store the request code;

    /* Token renewal variables : END */


    void getTripHistory() {

        if (resetOffsetVehicle) {
            offset_vehicle = 0;
            resetOffsetVehicle = false;
        }


        User user = PrefLogin.getUser(getActivity());

        if (user == null) {
            swipeContainer.setRefreshing(false);
            return;
        }


        int itemID = getActivity().getIntent().getIntExtra("item_id", 0);


        Call<ItemImageEndPoint> call = service.getItemImages(
                 itemID, ItemImage.IMAGE_ORDER,
                null, null,false
        );


        call.enqueue(new Callback<ItemImageEndPoint>() {
            @Override
            public void onResponse(Call<ItemImageEndPoint> call, Response<ItemImageEndPoint> response) {

                if (isDestroyed) {
                    return;
                }

                if (response.code() == 200 && response.body() != null) {

                    if (clearDataset) {
                        dataset.clear();
                        clearDataset = false;

//                        dataset.add(new FilterSubmissions());
                    }


                    if (getRowCountVehicle) {

                        item_count_vehicle = response.body().getItemCount();
                        getRowCountVehicle = false;

//                            dataset.add(new HeaderTitle("Type of Data"));

                        dataset.add(new HeaderTitle("Images"));
                    }


                    if (response.body().getResults() != null) {
                        dataset.addAll(response.body().getResults());
                    }

                    listAdapter.notifyDataSetChanged();
                }


                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<ItemImageEndPoint> call, Throwable t) {


                if (isDestroyed) {
                    return;
                }

                showToastMessage("Network Connection Failed !");

                swipeContainer.setRefreshing(false);
            }
        });



    }


    void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }





    ActionMode mActionMode;


    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

        // Called when the action mode is created; startActionMode() was called
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate a menu resource providing context menu items
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.action_menu_images_single, menu);
            return true;
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false; // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_edit:


//                    showToastMessage("Edit !");
                    listItemClick(listAdapter.selectedItemSingle, -1);

                    mode.finish(); // Action picked, so close the CAB

                    return true;

                case R.id.action_delete:

                    showToastMessage("Delete !");
                    mode.finish();

                    return true;


                default:
                    return false;
            }
        }

        // Called when the user exits the action mode
        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
        }
    };


    ItemImage taxiImageDelete;
    int positionDelete;


    void deleteClickMain(final ItemImage taxiImage, final int position)
    {

        taxiImageDelete = taxiImage;
        positionDelete = position;


        String filename = " ";
        if(taxiImageDelete.getImageFilename()!=null)
        {
            filename = taxiImageDelete.getImageFilename();
        }



        Call<ResponseBody> call = service.deleteItemImage(
                PrefLogin.getAuthorizationHeaders(getActivity()),
                filename
        );




        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(isDestroyed)
                {
                    return;
                }


                if (response.code()==200)
                {
                    showToastMessage("Deleted !");
                    listAdapter.notifyItemRemoved(position);
                    dataset.remove(taxiImage);

                }
                else
                {
                    showToastMessage("Failed code : " + String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {


                if(isDestroyed)
                {
                    return;
                }


                showToastMessage("Delete Failed !");

            }
        });


    }


    @Override
    public void deleteClick(final ItemImage taxiImage, final int position) {


        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setTitle("Confirm Delete Image !")
                .setMessage("Are you sure you want to delete this Image !")
                .setPositiveButton("Yes",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        deleteClickMain(taxiImage,position);

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


    @Override
    public void notifyListItemSelected() {


        if(mActionMode!=null) {
            int size = listAdapter.selectedItems.size();
            mActionMode.setTitle(String.valueOf(size));


            if (size == 0) {
                mActionMode.finish();
            }
            else if(size==1)
            {
                mActionMode.getMenu().findItem(R.id.action_edit).setVisible(true);

//                mActionMode.getMenuInflater()
//                        .inflate(R.menu.action_menu_images_single,mActionMode.getMenu());
            }
            else if(size>1)
            {
                mActionMode.getMenu().findItem(R.id.action_edit).setVisible(false);


//                mActionMode.getMenuInflater()
//                        .inflate(R.menu.action_menu_images,mActionMode.getMenu());
            }

        }
        else
        {
            mActionMode = ((AppCompatActivity)getActivity()).startSupportActionMode(mActionModeCallback);
            mActionMode.setTitle(String.valueOf(listAdapter.selectedItems.size()));
        }
    }

    @Override
    public void listItemClick(ItemImage itemImage, int position) {

        int itemID = getActivity().getIntent().getIntExtra("item_id",0);

        Intent intent = new Intent(getActivity(), EditItemImage.class);
        intent.putExtra(EditItemImageFragment.EDIT_MODE_INTENT_KEY,EditItemImageFragment.MODE_UPDATE);
        intent.putExtra(EditItemImageFragment.ITEM_ID_INTENT_KEY,itemID);


        UtilityItemImage.saveItemImage(itemImage,getActivity());
        startActivity(intent);
    }



    @Override
    public boolean listItemLongClick(View view, ItemImage taxiImage, int position) {
        return false;
    }


}
