package org.nearbyshops.serviceprovider.DaggerComponents;


import org.nearbyshops.serviceprovider.AddCredit.FragmentAddCredit;
import org.nearbyshops.serviceprovider.AddFromGlobalSelection.FragmentAddFromGlobal;
import org.nearbyshops.serviceprovider.AdminDashboard.AdminDashboardFragment;
import org.nearbyshops.serviceprovider.AdminDashboard.AdminHome;
import org.nearbyshops.serviceprovider.DetachedTabs.ItemCategories.DetachedItemCatAdapter;
import org.nearbyshops.serviceprovider.DetachedTabs.ItemCategories.DetachedItemCatFragment;
import org.nearbyshops.serviceprovider.DetachedTabs.Items.DetachedItemAdapter;
import org.nearbyshops.serviceprovider.DetachedTabs.Items.DetachedItemFragment;
import org.nearbyshops.serviceprovider.EditProfileStaff.ChangeEmail.FragmentChangeEmail;
import org.nearbyshops.serviceprovider.EditProfileStaff.ChangeEmail.FragmentVerifyEmailChange;
import org.nearbyshops.serviceprovider.EditProfileStaff.ChangePassword.FragmentChangePassword;
import org.nearbyshops.serviceprovider.EditProfileStaff.ChangePhone.FragmentChangePhone;
import org.nearbyshops.serviceprovider.EditProfileStaff.ChangePhone.FragmentVerifyPhone;
import org.nearbyshops.serviceprovider.EditProfileStaff.FragmentEditProfileStaff;
import org.nearbyshops.serviceprovider.EditProfileAdmin.EditAdminFragment;
import org.nearbyshops.serviceprovider.FilterItemsBySpecifications.FilterItemsFragment;
import org.nearbyshops.serviceprovider.ItemSpecName.EditItemSpecName.EditItemSpecNameFragment;
import org.nearbyshops.serviceprovider.ItemSpecName.ItemSpecNameFragment;
import org.nearbyshops.serviceprovider.ItemSpecValue.EditItemSpecValue.EditItemSpecValueFragment;
import org.nearbyshops.serviceprovider.ItemSpecValue.ItemSpecValueFragment;
import org.nearbyshops.serviceprovider.ItemSubmissionsList.ImageUpdates.ImageUpdatesFragment;
import org.nearbyshops.serviceprovider.ItemSubmissionsList.ItemReviewFragment;
import org.nearbyshops.serviceprovider.ItemSubmissionsList.ItemUpdates.ItemUpdatesFragment;
import org.nearbyshops.serviceprovider.ItemSubmissionsList.SubmissionDetails.SubmissionDetailsFragment;
import org.nearbyshops.serviceprovider.EditItem.EditItemFragmentNew;
import org.nearbyshops.serviceprovider.EditItemCategory.EditItemCategoryFragment;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.ItemCategories.Deprecated.EditItemCategoryOld;
import org.nearbyshops.serviceprovider.EditItemImage.EditItemImageFragment;
import org.nearbyshops.serviceprovider.EditItemOld.EditItemFragmentOld;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Items.Deprecated.EditItemOld;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Items.ItemFragmentTwo;
import org.nearbyshops.serviceprovider.ItemsByCategorySimple.ItemCategoriesFragmentSimple;
import org.nearbyshops.serviceprovider.ItemsByCategorySimple.ItemImageList.ImageListFragment;
import org.nearbyshops.serviceprovider.Login.LoginFragment;
import org.nearbyshops.serviceprovider.Login.LoginGlobalFragment;
import org.nearbyshops.serviceprovider.Login.ServiceIndicatorFragment;
import org.nearbyshops.serviceprovider.MarketDetail.MarketDetailFragment;
import org.nearbyshops.serviceprovider.MarketDetail.RateReviewDialogMarket;
import org.nearbyshops.serviceprovider.Markets.MarketsFragmentNew;
import org.nearbyshops.serviceprovider.Markets.ViewHolders.AdapterMarkets;
import org.nearbyshops.serviceprovider.Markets.ViewHolders.ViewHolderConnectWithURL;
import org.nearbyshops.serviceprovider.Markets.ViewHolders.ViewHolderMarket;
import org.nearbyshops.serviceprovider.Markets.ViewHolders.ViewHolderSavedMarket;
import org.nearbyshops.serviceprovider.Markets.ViewModels.MarketViewModel;
import org.nearbyshops.serviceprovider.OrderDetail.FragmentOrderDetail;
import org.nearbyshops.serviceprovider.OrderHistoryNew.OrdersFragmentNew;
import org.nearbyshops.serviceprovider.Services.ServiceFragment.ServicesFragment;
import org.nearbyshops.serviceprovider.Services.SubmitURLDialog.SubmitURLDialog;
import org.nearbyshops.serviceprovider.ShopsList.EditShop.EditShopFragment;
import org.nearbyshops.serviceprovider.ShopsList.Fragment.FragmentShopList;
import org.nearbyshops.serviceprovider.SignUp.ForgotPassword.FragmentCheckResetCode;
import org.nearbyshops.serviceprovider.SignUp.ForgotPassword.FragmentEnterCredentials;
import org.nearbyshops.serviceprovider.SignUp.ForgotPassword.FragmentResetPassword;
import org.nearbyshops.serviceprovider.SignUp.FragmentEmailOrPhone;
import org.nearbyshops.serviceprovider.SignUp.FragmentEnterPassword;
import org.nearbyshops.serviceprovider.SignUp.FragmentVerifyEmailSignUp;
import org.nearbyshops.serviceprovider.StaffHome.EditStaffSelf.EditStaffSelfFragment;
import org.nearbyshops.serviceprovider.StaffHome.StaffHome;
import org.nearbyshops.serviceprovider.StaffHome.StaffHomeFragment;
import org.nearbyshops.serviceprovider.StaffList.StaffListFragment;
import org.nearbyshops.serviceprovider.zDistributorAccounts.DistributorAccountFragment;
import org.nearbyshops.serviceprovider.zDistributorAccounts.DistributorDetail.DistributorDetail;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.ItemCategories.ItemCategoriesFragment;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Items.Deprecated.AddItem;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Items.ItemAdapterTwo;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Items.Deprecated.ItemAdapterOld;
import org.nearbyshops.serviceprovider.LoginScreen;
import org.nearbyshops.serviceprovider.SelectParent.ItemCategoriesParent;
import org.nearbyshops.serviceprovider.SelectParent.ItemCategoriesParentAdapter;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.ItemCategories.ItemCategoriesAdapter;
import org.nearbyshops.serviceprovider.DaggerModules.AppModule;
import org.nearbyshops.serviceprovider.DaggerModules.NetModule;
import org.nearbyshops.serviceprovider.Settings.SettingsActivity;
import org.nearbyshops.serviceprovider.ShopAdminList.EditShopAdmin.EditShopAdminFragment;
import org.nearbyshops.serviceprovider.ShopAdminList.Fragment.FragmentShopAdmins;
import org.nearbyshops.serviceprovider.StaffAccounts.EditStaff.EditStaffFragment;
import org.nearbyshops.serviceprovider.StaffAccounts.FragmentStaffAccounts;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by sumeet on 14/5/16.
 */

@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {



    void Inject(EditItemCategoryOld editItemCategoryOld);

    void Inject(ItemCategoriesFragment itemCategoriesFragment);

    void Inject(ItemCategoriesAdapter itemCategoriesAdapter);

    void Inject(ItemCategoriesParentAdapter itemCategoriesParentAdapter);

    void Inject(ItemCategoriesParent itemCategoriesParent);

    void Inject(org.nearbyshops.serviceprovider.zAddItems.ItemCategories.ItemCategoriesFragment itemCategoriesFragment);

    void Inject(org.nearbyshops.serviceprovider.zAddItems.ItemCategories.ItemCategoriesAdapter itemCategoriesAdapter);

    void Inject(org.nearbyshops.serviceprovider.zAddItems.ItemCategories.EditItemCategory editItemCategory);

    void Inject(ItemFragmentTwo itemFragmentTwo);

    void Inject(ItemAdapterOld itemAdapterOld);

    void Inject(AddItem addItem);

    void Inject(EditItemOld editItemOld);

    void Inject(DetachedItemCatFragment detachedItemCatFragment);

    void Inject(DetachedItemCatAdapter detachedItemCatAdapter);

    void Inject(DetachedItemAdapter detachedItemAdapter);

    void Inject(DetachedItemFragment detachedItemFragment);

    void Inject(ItemAdapterTwo itemAdapterTwo);

    void Inject(SettingsActivity settingsActivity);

    void Inject(DistributorAccountFragment distributorAccountFragment);

    void Inject(DistributorDetail distributorDetail);

    void Inject(EditShopAdminFragment editShopAdminFragment);

    void Inject(FragmentShopAdmins fragmentShopAdmins);

    void Inject(FragmentShopList fragmentShopApprovals);

    void Inject(EditStaffFragment editStaffFragment);

    void Inject(FragmentStaffAccounts fragmentStaffAccounts);

    void Inject(EditItemFragmentOld editItemFragmentOld);

    void Inject(EditItemCategoryFragment editItemCategoryFragment);

    void Inject(ItemCategoriesFragmentSimple itemCategoriesFragmentSimple);

    void Inject(EditStaffSelfFragment editStaffSelfFragment);

    void Inject(StaffHome staffHome);

    void Inject(AdminHome home);

    void Inject(LoginScreen loginScreen);

    void Inject(FragmentAddFromGlobal fragmentAddFromGlobal);

    void Inject(EditAdminFragment editAdminFragment);

    void Inject(EditShopFragment editShopFragment);

    void Inject(ServicesFragment servicesFragment);

    void Inject(SubmitURLDialog submitURLDialog);

    void Inject(EditItemFragmentNew editItemFragmentNew);

    void Inject(EditItemImageFragment editItemImageFragment);

    void Inject(FilterItemsFragment filterItemsFragment);

    void Inject(EditItemSpecNameFragment editItemSpecNameFragment);

    void Inject(ItemSpecNameFragment itemSpecNameFragment);

    void Inject(EditItemSpecValueFragment editItemSpecValueFragment);

    void Inject(ItemSpecValueFragment itemSpecValueFragment);


    void Inject(ItemReviewFragment itemReviewFragment);

    void Inject(SubmissionDetailsFragment submissionDetailsFragment);

    void Inject(ItemUpdatesFragment itemUpdatesFragment);

    void Inject(ImageUpdatesFragment imageUpdatesFragment);

    void Inject(FragmentChangeEmail fragmentChangeEmail);

    void Inject(FragmentVerifyEmailChange fragmentVerifyEmailChange);

    void Inject(FragmentChangePassword fragmentChangePassword);

    void Inject(FragmentChangePhone fragmentChangePhone);

    void Inject(FragmentVerifyPhone fragmentVerifyPhone);

    void Inject(FragmentEditProfileStaff fragmentEditProfileStaff);

    void Inject(org.nearbyshops.serviceprovider.StaffList.EditProfileStaff.FragmentEditProfileStaff fragmentEditProfileStaff);

    void Inject(FragmentEmailOrPhone fragmentEmailOrPhone);

    void Inject(FragmentEnterPassword fragmentEnterPassword);

    void Inject(FragmentVerifyEmailSignUp fragmentVerifyEmailSignUp);

    void Inject(StaffListFragment staffListFragment);

    void Inject(ImageListFragment imageListFragment);

    void Inject(AdminDashboardFragment adminDashboardFragment);

    void Inject(LoginFragment loginFragment);

    void Inject(FragmentCheckResetCode fragmentCheckResetCode);

    void Inject(FragmentResetPassword fragmentResetPassword);

    void Inject(FragmentEnterCredentials fragmentEnterCredentials);

    void Inject(StaffHomeFragment staffHomeFragment);

    void Inject(FragmentAddCredit fragmentAddCredit);

    void Inject(OrdersFragmentNew ordersFragmentNew);

    void Inject(FragmentOrderDetail fragmentOrderDetail);

    void Inject(org.nearbyshops.serviceprovider.EditServiceConfig.EditConfigurationFragment editConfigurationFragment);

    void Inject(ViewHolderSavedMarket viewHolderSavedMarket);

    void Inject(AdapterMarkets adapterMarkets);

    void Inject(ViewHolderConnectWithURL viewHolderConnectWithURL);

    void Inject(ViewHolderMarket viewHolderMarket);

    void Inject(org.nearbyshops.serviceprovider.Markets.SubmitURLDialog submitURLDialog);

    void Inject(MarketViewModel marketViewModel);

    void Inject(LoginGlobalFragment loginGlobalFragment);

    void Inject(MarketsFragmentNew marketsFragmentNew);

    void Inject(MarketDetailFragment marketDetailFragment);

    void Inject(RateReviewDialogMarket rateReviewDialogMarket);

    void Inject(ServiceIndicatorFragment serviceIndicatorFragment);


//    void Inject(LoginDialog loginDialog);
}
