package org.nearbyshops.serviceprovider.zAddItems.ItemCategories;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.nearbyshops.serviceprovider.DaggerComponentBuilder;
import org.nearbyshops.serviceprovider.Model.ItemCategory;
import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.RetrofitRESTContract.ItemCategoryService;
import org.nearbyshops.serviceprovider.SelectParent.ItemCategoriesParent;
import org.nearbyshops.serviceprovider.Preferences.PrefGeneral;
import org.nearbyshops.serviceprovider.Preferences.PrefLogin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sumeet on 19/12/15.
 */


public class ItemCategoriesAdapter extends RecyclerView.Adapter<ItemCategoriesAdapter.ViewHolder>{


    Map<Integer,ItemCategory> selectedItems = new HashMap<>();


    @Inject
    ItemCategoryService itemCategoryService;

    List<ItemCategory> dataset;

    Context context;
    ItemCategoriesFragment activity;


    ItemCategory requestedChangeParent = null;


    NotificationReceiver notificationReceiver;


    final String IMAGE_ENDPOINT_URL = "/api/Images";

    public ItemCategoriesAdapter(List<ItemCategory> dataset, Context context, ItemCategoriesFragment activity, ItemCategoriesAdapter.NotificationReceiver notificationReceiver) {


        DaggerComponentBuilder.getInstance()
                .getNetComponent().Inject(this);


        this.notificationReceiver = notificationReceiver;
        this.dataset = dataset;
        this.context = context;
        this.activity = activity;

        if(this.dataset == null)
        {
            this.dataset = new ArrayList<ItemCategory>();
        }

    }

    @Override
    public ItemCategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_item_category,parent,false);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ItemCategoriesAdapter.ViewHolder holder, final int position) {

        holder.categoryName.setText(dataset.get(position).getCategoryName());
        holder.categoryDescription.setText(dataset.get(position).getDescriptionShort());

        if(selectedItems.containsKey(dataset.get(position).getItemCategoryID()))
        {
            holder.itemCategoryListItem.setBackgroundColor(context.getResources().getColor(R.color.gplus_color_2));
        }
        else
        {
            holder.itemCategoryListItem.setBackgroundColor(context.getResources().getColor(R.color.white));
        }



        String imagePath = PrefGeneral.getImageEndpointURL(context)
                + dataset.get(position).getImagePath();

        if(!dataset.get(position).getImagePath().equals(""))
        {
            Picasso.get()
                    .load(imagePath)
                    .into(holder.categoryImage);
        }


        Log.d("applog",imagePath);

    }


    @Override
    public int getItemCount() {

        return dataset.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {


        private TextView categoryName,categoryDescription;

        @BindView(R.id.itemCategoryListItem) LinearLayout itemCategoryListItem;

        @BindView(R.id.categoryImage) ImageView categoryImage;


        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);

            categoryImage = (ImageView) itemView.findViewById(R.id.categoryImage);
            categoryName = (TextView) itemView.findViewById(R.id.categoryName);
            categoryDescription = (TextView) itemView.findViewById(R.id.categoryDescription);

            itemCategoryListItem = (LinearLayout) itemView.findViewById(R.id.itemCategoryListItem);
        }



        @OnLongClick(R.id.itemCategoryListItem)
        public boolean listItemLongClick()
        {

//            showToastMessage("Long Click !");


            if(selectedItems.containsKey(
                    dataset.get(getLayoutPosition())
                            .getItemCategoryID()
            )
                    )

            {
                selectedItems.remove(dataset.get(getLayoutPosition()).getItemCategoryID());

            }else
            {

                selectedItems.put(dataset.get(getLayoutPosition()).getItemCategoryID(),dataset.get(getLayoutPosition()));

                notificationReceiver.notifyItemCategorySelected();
            }


            notifyItemChanged(getLayoutPosition());

//            showToastMessage(String.valueOf(selectedItems.size()));


//            itemCategoryListItem.setBackgroundColor(context.getResources().getColor(R.color.cyan900));

            return true;
        }



        @OnClick(R.id.itemCategoryListItem)
        public void itemCategoryListItemClick()
        {

            if (dataset == null) {

                return;
            }

            if(dataset.size()==0)
            {
                return;
            }

            if (dataset.get(getLayoutPosition()).getIsLeafNode()) {

//                Intent intent = new Intent(context, Items.class);
//
//                intent.putExtra(Items.ITEM_CATEGORY_INTENT_KEY,dataset.get(getLayoutPosition()));
//
//                context.startActivity(intent);

            }
            else
            {
                notificationReceiver.notifyRequestSubCategory(dataset.get(getLayoutPosition()));

                selectedItems.clear();
            }
        }



        public void deleteItemCategory()
        {


            Call<ResponseBody> call = itemCategoryService.deleteItemCategory(PrefLogin.getAuthorizationHeaders(context),
                    dataset.get(getLayoutPosition()).getItemCategoryID());

            call.enqueue(new Callback<ResponseBody>() {

                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                    if(response.code()==200)
                    {
                        notifyDelete();

                        showToastMessage("Removed !");

                    }else if(response.code()==304)
                    {
                        showToastMessage("Delete failed !");

                    }else
                    {
                        showToastMessage("Server Error !");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                    showToastMessage("Network request failed ! Please check your connection!");
                }
            });
        }


        @OnClick(R.id.more_options)
        void optionsOverflowClick(View v)
        {
            PopupMenu popup = new PopupMenu(context, v);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.item_category_item_overflow, popup.getMenu());
            popup.setOnMenuItemClickListener(this);
            popup.show();
        }


        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId())
            {
                case R.id.action_remove:

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setTitle("Confirm Delete Item Category !")
                            .setMessage("Do you want to delete this Item Category ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    deleteItemCategory();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    showToastMessage("Cancelled !");
                                }
                            })
                            .show();


                    break;

                case R.id.action_edit:

                    Intent intent = new Intent(context,EditItemCategory.class);
                    intent.putExtra(EditItemCategory.ITEM_CATEGORY_INTENT_KEY,dataset.get(getLayoutPosition()));
                    context.startActivity(intent);

                    break;



//                case R.id.action_detach:
//
//                    showToastMessage("Detach");
//
//                    break;

                case R.id.action_change_parent:


//                    showToastMessage("Change parent !");

                    Intent intentParent = new Intent(context, ItemCategoriesParent.class);

                    requestedChangeParent = dataset.get(getLayoutPosition());

                    // add the selected item category in the exclude list so that it does not get showed up as an option.
                    // This is required to prevent an item category to assign itself or its children as its parent.
                    // This should not happen because it would be erratic.

                    ItemCategoriesParent.clearExcludeList(); // it is a safe to clear the list before adding any items in it.
                    ItemCategoriesParent.excludeList
                            .put(requestedChangeParent.getItemCategoryID(),requestedChangeParent);


                    activity.startActivityForResult(intentParent,1,null);


                    break;




                default:

                    break;

            }

            return false;
        }



    }// ViewHolder Class declaration ends




    void showToastMessage(String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }


    public void notifyDelete()
    {
        activity.notifyDelete();

    }


    public interface NotificationReceiver
    {
        // method for notifying the list object to request sub category
        public void notifyRequestSubCategory(ItemCategory itemCategory);

        public void notifyItemCategorySelected();

    }


    public void setRequestedChangeParent(ItemCategory requestedChangeParent) {
        this.requestedChangeParent = requestedChangeParent;
    }

    public ItemCategory getRequestedChangeParent() {
        return requestedChangeParent;
    }





}