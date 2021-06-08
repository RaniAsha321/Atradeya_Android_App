package com.atradeya.asharani;

import com.atradeya.asharani.Filter_POJO.ModelProductsFilter;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiLogin_Interface {

    String BASE_URL = "https://app.pickmyorder.co.uk/";

    @FormUrlEncoded
    @POST("user_verification")
    Call<ModelLogin> getClient(@Header("Content-Type") String header, @Field("email") String email, @Field("password") String password, @Field("deviceid") String deviceid, @Field("devicestatus") String devicestatus);

    @FormUrlEncoded
    @POST("GenratePasswordAndSendApi")
    Call<ModelRegister> Register_Email(@Field("Email") String email);

    @FormUrlEncoded
    @POST("CreateAtradeyaAppUsers")
    Call<ModelRegisterUserDetails> REGISTER_USER_DETAILS_CALL(@Field("contact_first_name") String contact_first_name,@Field("contact_last_name") String contact_last_name,@Field("contact_telephone") String contact_telephone,@Field("contact_email_address") String contact_email_address,@Field("account_password") String account_password,
                                                              @Field("billing_business_name") String billing_business_name,@Field("billing_first_name") String billing_first_name,@Field("billing_last_name") String billing_last_name,
                                                              @Field("billing_address_line1") String billing_address_line1,@Field("billing_address_line2") String billing_address_line2,@Field("billing_city") String billing_city,
                                                              @Field("billing_postcode") String billing_postcode,@Field("shipping_Business_name") String shipping_Business_name,@Field("shipping_first_name") String shipping_first_name,@Field("shipping_last_name") String shipping_last_name,
                                                              @Field("shipping_address_line1") String shipping_address_line1,@Field("shipping_address_line2") String shipping_address_line2,@Field("shipping_city") String shipping_city,@Field("shipping_postcode") String shipping_postcode);


    @FormUrlEncoded
    @POST("forgotpassword")
    Call<ModelForgot> Forgot(@Header("Content-Type") String header, @Field("email") String email);

    @FormUrlEncoded
    @POST("getproductcategory")
    Call<ModelProductsCategory> Products(@Field("userid") String userid) ;

    @FormUrlEncoded
    @POST("getproductsubcategory")
    Call <ModelProductsSubCategory> PRODUCTS_SUB_CATEGORY_CALL(@Field("catid") String catid);

    @FormUrlEncoded
    @POST("getproductsupersubcategory")
    Call<ModelProductsSubSubCategory> PRODUCTS_SUB_SUB_CATEGORY_CALL(@Field("subcat") String subcat);

    @FormUrlEncoded
    @POST("getproducts")
    Call<ModelDescription> PRODUCT_DESCRIPTION_CALL(@Field("supersubcat") String supersubcat, @Field("keystatus") String keystatus);

    @FormUrlEncoded
    @POST("changepass")
    Call<ModelChangePassword> CHANGE_PASSWORD_CALL(@Header("Content-Type") String header, @Field("userid") String userid, @Field("newpass") String newpass);

    @FormUrlEncoded
    @POST("AppLogout")
    Call<ModelLogout> LOGOUT_CALL(@Header("Content-Type") String header, @Field("userid") String userid, @Field("deviceid") String deviceid, @Field("devicestatus") String devicestatus);

    @FormUrlEncoded
    @POST("AtradeyNewGetOrder")
    Call<ModelBuyNow> CART_CALL(@Field("cart") String cart_string);

    @FormUrlEncoded
    @POST("newGetCateLouges")
    Call<ModelCatPromo> CATALOGUES_CALL(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("viewmyorder")
    Call<ModelOrderMenu> ORDER_MENU_CALL(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("NewSingleOrederApi")
    Call<ModelMyOrder> MY_ORDER_CALL(@Field("ref_id") String refid);

    @FormUrlEncoded
    @POST("GetProjects")
    Call<ModelProjects> PROJECTS_CALL(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("SearchKey")
    Call<ModelSearching> SEARCHING_CALL(@Field("SearchKey") String searchkey, @Field("userid") String userid);

    @FormUrlEncoded
    @POST("GetSupplier")
    Call<ModelSupplier>SUPPLIER_CALL(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("sendprojectfromapp")
    Call<ModelAddProject> ADD_PROJECT_CALL(@Field("Project") String Project);

    @FormUrlEncoded
    @POST("EditProjectFromApp")
    Call<ModelAddProject> UPDATE_PROJECT_CALL(@Field("Project") String Project);

    @FormUrlEncoded
    @POST("Getproductvariation")
    Call<ModelVariations> VARIATIONS_CALL(@Field("productid") String productsku);

    @FormUrlEncoded
    @POST("viewAwatingorder")
    Call<ModelAwaiting> AWAITING_CALL(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("ApproveOrderApi")
    Call<Approval> APPROVAL_CALL(@Field("poreff") String poreff, @Field("ponum") String ponum);

    @FormUrlEncoded
    @POST("Moredetails")
    Call<MoreDetails> MORE_DETAILS_CALL(@Field("refid") String refid);

    @FormUrlEncoded
    @POST("SendOprativeEnigineers")
    Call<AssignEngineer> ASSIGN_ENGINEER_CALL(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("GetBillingDetails")
    Call<ModelBillingDetails> BILLING_DETAILS_CALL(@Field("Enginnerid") String Enginnerid);

    @FormUrlEncoded
    @POST("GetDeleveryDetails")
    Call<ModelDefaultDeliveryAddress> DEFAULT_DELIVERY_ADDRESS_CALL(@Field("Enginnerid") String Enginnerid);

    @FormUrlEncoded
    @POST("DeleteProjectFromApp")
    Call<ModelRemoveProject> Remove_Project(@Field("id") String Project_id);

    @FormUrlEncoded
    @POST("SendSellersToApp")
    Call<ModelWholesellers> WHOLESELLERS_CALL(@Field("City") String PostCode);

    @FormUrlEncoded
    @POST("SendCityList")
    Call<ModelCityList> MODEL_CITY_LIST_CALL(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("MakeAPayment")
    Call<ModelPayment> PAYMENT_CALL(@Field("amount") int amount, @Field("token") String token, @Field("currency") String currency, @Field("description") String description, @Field("cart") String cart
            , @Field("card") String card, @Field("exp_month") int exp_month, @Field("exp_year") int exp_year, @Field("brand") String brand, @Field("cvc") String cvc, @Field("savestatus") String savestatus
            , @Field("WholsalerBusinessId") String WholsalerBusinessId);

    @FormUrlEncoded
    @POST("GetCards")
    Call<ModelAddedCards> MODEL_ADDED_CARDS_CALL(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("RemoveCard ")
    Call<ModelRemoveCard> REMOVE_CARD_CALL(@Field("id") String userid);

    @FormUrlEncoded
    @POST("AddCard ")
    Call<ModelAddCard> ADD_CARD_CALL(@Field("user_id") String userid, @Field("card") String card, @Field("brand") String brand, @Field("exp_month") String exp_month, @Field("exp_year") String exp_year);

    @FormUrlEncoded
    @POST("EditBillingAndShipping")
    Call<ModelUpdateAddress> UPDATE_ADDRESS_CALL(@Field("Enginnerid") String Enginnerid ,@Field("firstName") String firstName ,@Field("lastName") String lastName , @Field("type") String type , @Field("company_city") String company_city, @Field("address") String address, @Field("company_name") String company_name, @Field("company_adress") String company_adress
            , @Field("company_postcode") String company_postcode);

    @FormUrlEncoded
    @POST("Engineer_address_list")
    Call<ModelAddress> ADDRESS_CALL(@Field("Enginnerid") String Enginnerid,@Field("company_name") String company_name,@Field("First_name") String First_name,@Field("Last_name") String Last_name,@Field("Address_line_1") String Address_line_1,@Field("Address_line_2") String Address_line_2,@Field("City") String City,
                                    @Field("Post_code") String Post_code);

    @FormUrlEncoded
    @POST("Send_enginner_alladdress")
    Call<ModelGetAddress> GET_ADDRESS_CALL(@Field("Enginnerid") String Enginnerid);

    @FormUrlEncoded
    @POST("delete_enigneer_alladdress")
    Call<ModelRemoveProject> Remove_Address(@Field("id") String Addressid);

    @FormUrlEncoded
    @POST("Edit_enigneer_alladdress")
    Call<ModelAddress> UPDATE_CALL(@Field("id") String id,@Field("Company_name") String company_name,@Field("First_name") String First_name,@Field("Last_name") String Last_name,@Field("Address_line_1") String Address_line_1,@Field("Address_line_2") String Address_line_2,@Field("City") String City,
                                    @Field("Post_code") String Post_code);

    @FormUrlEncoded
    @POST("SendStore")
    Call<ModelCollectionStore> STORE_CALL(@Field("bussiness_id") String bussiness_id);

    @FormUrlEncoded
    @POST("SendDeleveryCost")
    Call<ModelDeliveryCost> DELIVERY_COST_CALL(@Field("bussiness_id") String bussiness_id);

    @FormUrlEncoded
    @POST("ApplyFilter")
    Call <ModelProductsFilter> PRODUCTS_FILTER_CALL(@Field("CatId") String CatId,@Field("FilterItem") String FilterItem);

}
