package org.nearbyshops.serviceprovider.ItemSubmissionsList.ItemUpdates;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.nearbyshops.serviceprovider.ItemSubmissionsList.HeaderTitle;
import org.nearbyshops.serviceprovider.Model.Item;
import org.nearbyshops.serviceprovider.Model.ItemImage;
import org.nearbyshops.serviceprovider.ModelItemSubmission.ItemImageSubmission;
import org.nearbyshops.serviceprovider.ModelItemSubmission.ItemSubmission;
import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.Preferences.PrefGeneral;

import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by sumeet on 19/12/15.
 */


public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private List<Object> dataset;
    private Context context;
    private NotificationsFromAdapter notificationReceiver;
    private Fragment fragment;

    public static final int VIEW_TYPE_ITEM_CURRENT = 1;
    public static final int VIEW_TYPE_IMAGES_ADDED = 2;
    public static final int VIEW_TYPE_IMAGES_UPDATED = 3;
    public static final int VIEW_TYPE_ITEM_UPDATES = 4;
    public static final int VIEW_TYPE_HEADER = 5;
    private final static int VIEW_TYPE_PROGRESS_BAR = 6;



    public Adapter(List<Object> dataset, Context context, NotificationsFromAdapter notificationReceiver, Fragment fragment) {

//        DaggerComponentBuilder.getInstance()
//                .getNetComponent().Inject(this);

        this.notificationReceiver = notificationReceiver;
        this.dataset = dataset;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        if(viewType == VIEW_TYPE_ITEM_CURRENT)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_item_submission,parent,false);
            return new ViewHolderItemCurrent(view);
        }
        else if(viewType == VIEW_TYPE_HEADER)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_header_type_simple,parent,false);
            return new ViewHolderHeader(view);
        }
        else if(viewType == VIEW_TYPE_IMAGES_ADDED)
        {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image_submission,parent,false);
            return new ViewHolderImagesAdded(view);
        }
        else if(viewType == VIEW_TYPE_IMAGES_UPDATED)
        {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_image_submission,parent,false);
            return new ViewHolderImagesUpdated(view);
        }
        else if(viewType == VIEW_TYPE_ITEM_UPDATES)
        {

            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_item_update,parent,false);
            return new ViewHolderItemSubmissions(view);
        }
        else if (viewType == VIEW_TYPE_PROGRESS_BAR)
        {

            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item_progress_bar,parent,false);

            return new LoadingViewHolder(view);
        }


//        else
//        {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_item_guide,parent,false);
//            return new ViewHolderItemSimple(view);
//        }

        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof ViewHolderItemSubmissions)
        {
            bindItemSubissions((ViewHolderItemSubmissions) holder,position);
        }
        else if(holder instanceof ViewHolderItemCurrent)
        {
            bindItem((ViewHolderItemCurrent) holder,position);
        }
        else if(holder instanceof ViewHolderImagesAdded)
        {
            bindImagesAdded((ViewHolderImagesAdded)holder,position);
        }
        else if(holder instanceof ViewHolderImagesUpdated)
        {
            bindImagesUpdated((ViewHolderImagesUpdated) holder,position);
        }
        else if(holder instanceof ViewHolderHeader)
        {
            if(dataset.get(position) instanceof HeaderTitle)
            {
                HeaderTitle header = (HeaderTitle) dataset.get(position);

                ((ViewHolderHeader) holder).header.setText(header.getHeading());
            }

        }
        else if(holder instanceof LoadingViewHolder)
        {

            LoadingViewHolder viewHolder = (LoadingViewHolder) holder;

            int itemCount = 0;

            if(fragment instanceof ItemUpdatesFragment)
            {
                itemCount = (((ItemUpdatesFragment) fragment).item_count_item_updates  + ((ItemUpdatesFragment) fragment).item_count_images_added + ((ItemUpdatesFragment) fragment).item_count_images_updated+ 5);
            }


            if(position == 0 || position == itemCount)
            {
                viewHolder.progressBar.setVisibility(View.GONE);
            }
            else
            {
                viewHolder.progressBar.setVisibility(View.VISIBLE);
                viewHolder.progressBar.setIndeterminate(true);

            }

        }


    }


    @Override
    public int getItemViewType(int position) {

        super.getItemViewType(position);

        if(position == dataset.size())
        {
            return VIEW_TYPE_PROGRESS_BAR;
        }
        else if (dataset.get(position) instanceof Item)
        {
            return VIEW_TYPE_ITEM_CURRENT;
        }
        else if(dataset.get(position) instanceof HeaderTitle)
        {
            return VIEW_TYPE_HEADER;
        }
        else if(dataset.get(position) instanceof ItemImageSubmission)
        {
            return VIEW_TYPE_IMAGES_ADDED;
        }
        else if(dataset.get(position) instanceof ItemImage)
        {
            return VIEW_TYPE_IMAGES_UPDATED;
        }
        else if(dataset.get(position) instanceof ItemSubmission)
        {
            return VIEW_TYPE_ITEM_UPDATES;
        }


        return -1;
    }

    @Override
    public int getItemCount() {

        return (dataset.size()+1);
    }


    void bindImagesAdded(ViewHolderImagesAdded holder,int position)
    {
        if(dataset.get(position) instanceof ItemImageSubmission)
        {
            ItemImageSubmission itemImageSubmission = (ItemImageSubmission) dataset.get(position);


            holder.caption.setText(itemImageSubmission.getItemImage().getCaptionTitle() + "\n"
                    + itemImageSubmission.getItemImage().getCaption());


            Drawable drawable = ContextCompat.getDrawable(context,R.drawable.ic_nature_people_white_48px);
            String imagePath = PrefGeneral.getServiceURL(context) + "/api/v1/ItemImage/Image/" + "three_hundred_"+ itemImageSubmission.getItemImage().getImageFilename() + ".jpg";

            Picasso.with(context)
                    .load(imagePath)
                    .placeholder(drawable)
                    .into(holder.profileImage);


        }
    }




    class ViewHolderImagesAdded extends RecyclerView.ViewHolder{



        @BindView(R.id.profile_image) ImageView profileImage;
        @BindView(R.id.caption) TextView caption;



        public ViewHolderImagesAdded(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }// ViewHolder Class declaration ends




    void bindImagesUpdated(ViewHolderImagesUpdated holder,int position)
    {
        if(dataset.get(position) instanceof ItemImage)
        {
            ItemImage itemImage = (ItemImage) dataset.get(position);

            holder.caption.setText(itemImage.getCaptionTitle() + "\n" + itemImage.getCaption());

            Drawable drawable = ContextCompat.getDrawable(context,R.drawable.ic_nature_people_white_48px);
            String imagePath = PrefGeneral.getServiceURL(context) + "/api/v1/ItemImage/Image/" + "three_hundred_"+ itemImage.getImageFilename() + ".jpg";

            Picasso.with(context)
                    .load(imagePath)
                    .placeholder(drawable)
                    .into(holder.profileImage);


        }
    }




    class ViewHolderImagesUpdated extends RecyclerView.ViewHolder implements View.OnClickListener {




        @BindView(R.id.profile_image) ImageView profileImage;
        @BindView(R.id.caption) TextView caption;





        public ViewHolderImagesUpdated(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if(dataset.get(getLayoutPosition()) instanceof ItemImage)
            {
                notificationReceiver.imageUpdatedClick((ItemImage) dataset.get(getLayoutPosition()),getLayoutPosition());
            }
        }


    }// ViewHolder Class declaration ends






    class ViewHolderHeader extends RecyclerView.ViewHolder{


        @BindView(R.id.header)
        TextView header;

        public ViewHolderHeader(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

    }// ViewHolder Class declaration ends



    void bindItemSubissions(ViewHolderItemSubmissions holder,int position)
    {

            ItemSubmission itemSubmission = (ItemSubmission) dataset.get(position);
            Item item = ((ItemSubmission)dataset.get(position)).getItem();


            holder.itemName.setText(item.getItemName());
            holder.item_unit.setText(item.getQuantityUnit());
            holder.itemDescription.setText(item.getItemDescription());
            holder.latestUpdate.setText("Submitted :\n" + itemSubmission.getTimestampSubmitted().toLocaleString());



            Drawable drawable = ContextCompat.getDrawable(context,R.drawable.ic_nature_people_white_48px);
            String imagePath = PrefGeneral.getServiceURL(context) + "/api/v1/Item/Image/" + "three_hundred_"+ item.getItemImageURL() + ".jpg";

            Picasso.with(context)
                    .load(imagePath)
                    .placeholder(drawable)
                    .into(holder.itemImage);
    }



    class ViewHolderItemSubmissions extends RecyclerView.ViewHolder{


        @BindView(R.id.description_short) TextView itemDescription;
        @BindView(R.id.quantity_unit) TextView item_unit;
        @BindView(R.id.itemName) TextView itemName;
//        @BindView(R.id.items_list_item) CardView itemCategoryListItem;
        @BindView(R.id.itemImage) ImageView itemImage;
        @BindView(R.id.latest_update) TextView latestUpdate;


        public ViewHolderItemSubmissions(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }


        @OnClick(R.id.items_list_item)
        void listItemClick()
        {
            notificationReceiver.itemUpdateClick((ItemSubmission) dataset.get(getLayoutPosition()),getLayoutPosition());

        }

    }// ViewHolder Class declaration ends




    void bindItem(ViewHolderItemCurrent holder,int position)
    {

        Item item = (Item) dataset.get(position);

        holder.itemName.setText(item.getItemName());
//        holder.latestUpdate.setText("Latest Update :\n" + item.getItemStats().getLatestUpdate().toLocaleString());
//        holder.updatesCount.setText("Updates Count : " + String.valueOf(item.getItemStats().getSubmissionsCount()));


        String imagePath = PrefGeneral.getServiceURL(context)
                + "/api/v1/Item/Image/three_hundred_" + item.getItemImageURL() + ".jpg";


        Drawable drawable = VectorDrawableCompat
                .create(context.getResources(),
                        R.drawable.ic_nature_people_white_48px, context.getTheme());

        Picasso.with(context).load(imagePath)
                .placeholder(drawable)
                .into(holder.itemImage);

    }



    class ViewHolderItemCurrent extends RecyclerView.ViewHolder implements View.OnClickListener {


        @BindView(R.id.itemName) TextView itemName;
//        @BindView(R.id.updates_count) TextView updatesCount;
//        @BindView(R.id.latest_update) TextView latestUpdate;
        @BindView(R.id.itemImage) ImageView itemImage;



        public ViewHolderItemCurrent(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            notificationReceiver.currentItemClick((Item) dataset.get(getLayoutPosition()),getLayoutPosition());
        }
    }// ViewHolder Class declaration ends


    private void showToastMessage(String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }




    interface NotificationsFromAdapter
    {
        // method for notifying the list object to request sub category

        void currentItemClick(Item item, int position);
        void itemUpdateClick(ItemSubmission itemSubmission, int position);
        void imageUpdatedClick(ItemImage itemImage, int position);
    }



    public class LoadingViewHolder extends  RecyclerView.ViewHolder{

        @BindView(R.id.progress_bar)
        ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }






}