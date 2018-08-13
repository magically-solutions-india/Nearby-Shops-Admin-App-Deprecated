package org.nearbyshops.serviceprovider.RetrofitRESTContract;



import org.nearbyshops.serviceprovider.Model.Image;
import org.nearbyshops.serviceprovider.ModelRoles.User;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sumeet on 3/4/17.
 */

public interface UserService {

    /* Staff Login : Begin */




    /* Staff Login : End */



    @GET("/api/v1/User/SignUp/CheckUsernameExists/{username}")
    Call<ResponseBody> checkUsernameExists(@Path("username") String username);



    @POST("/api/v1/User/SignUp/StaffRegistration")
    Call<User> staffRegistration(@Header("Authorization") String headers,
                                 @Body User user);



    /* Sign-Up using e-mail */

    @PUT("api/v1/User/SignUp/SendEmailVerificationCode/{email}")
    Call<ResponseBody> sendVerificationEmail(@Path("email") String email);



    @GET("/api/v1/User/SignUp/CheckEmailVerificationCode/{email}")
    Call<ResponseBody> checkEmailVerificationCode(@Path("email") String email,
                                                  @Query("VerificationCode") String verificationCode);



    /* Sign-Up using phone */

    @GET("/api/v1/User/SignUp/CheckPhoneVerificationCode/{phone}")
    Call<ResponseBody> checkPhoneVerificationCode(@Path("phone") String phone,
                                                  @Query("VerificationCode") String verificationCode);


    @PUT("api/v1/User/SignUp/SendPhoneVerificationCode/{phone}")
    Call<ResponseBody> sendVerificationPhone(@Path("phone") String phone);


    /* Staff Login : Begin */



    /* Staff Login : End */

    @PUT("/api/v1/User/UpdateFirebaseID/{FirebaseID}")
    Call<ResponseBody> updateFirebaseID(@Header("Authorization") String headers,
                                        @Path("FirebaseID") String firebaseID);



    @GET("/api/v1/User/GetToken")
    Call<User> getLogin(@Header("Authorization") String headers);



    @GET("/api/v1/User/GetProfileWithLogin")
    Call<User> getProfileWithLogin(@Header("Authorization") String headers);



    @GET("/api/v1/User/GetProfile")
    Call<User> getProfile(@Header("Authorization") String headers);






    /* Change Email */

    @PUT("/api/v1/User/UpdateEmail")
    Call<ResponseBody> updateEmail(@Header("Authorization") String headers,
                                   @Body User user);


    @PUT("/api/v1/User/UpdatePhone")
    Call<ResponseBody> updatePhone(@Header("Authorization") String headers,
                                   @Body User user);



    @PUT("/api/v1/User/ChangePassword/{OldPassword}")
    Call<ResponseBody> changePassword(@Header("Authorization") String headers,
                                      @Body User user, @Path("OldPassword") String oldPassword);




    // Image Calls

    @POST("/api/v1/User/Image")
    Call<Image> uploadImage(@Header("Authorization") String headers,
                            @Body RequestBody image);


    @DELETE("/api/v1/User/Image/{name}")
    Call<ResponseBody> deleteImage(@Header("Authorization") String headers,
                                   @Path("name") String fileName);

}
