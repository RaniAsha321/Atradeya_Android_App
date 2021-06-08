package com.atradeya.asharani;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp_Fourth_Screen extends AppCompatActivity {

    Button btn_signup_fourth_previous,btn_signup_fourth_submit;

    EditText edtxt_signup_contact_first_name,edtxt_signup_contact_last_name,edtxt_signup_contact_telephone,edtxt_signup_contact_email_address,edtxt_signup_account_password,
            edtxt_signup_account_confirm_password,edtxt_signup_billing_business_name,edtxt_signup_billing_first_name,edtxt_signup_billing_last_name,edtxt_signup_billing_address_line1,
            edtxt_signup_billing_address_line2,edtxt_signup_billing_city,edtxt_signup_billing_postcode,edtxt_signup_shipping_Business_name,edtxt_signup_shipping_first_name,
            edtxt_signup_shipping_last_name,edtxt_signup_shipping_address_line1,edtxt_signup_shipping_address_line2,edtxt_signup_shipping_city,edtxt_signup_shipping_postcode;

    String contact_first_name,contact_last_name,contact_telephone,contact_email_address,account_password,billing_business_name,billing_first_name,billing_last_name,
            billing_address_line1,billing_address_line2,billing_city,billing_postcode,shipping_Business_name,shipping_first_name,shipping_last_name,
            shipping_address_line1,shipping_address_line2,shipping_city,shipping_postcode,account_confirm_password;

    CheckBox checkBox1;
    String deviceid;

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_screen);

        checkBox1 = findViewById(R.id.checkBox1);
        edtxt_signup_contact_first_name = findViewById(R.id.edtxt_signup_contact_first_name);
        edtxt_signup_contact_last_name = findViewById(R.id.edtxt_signup_contact_last_name);
        edtxt_signup_contact_telephone = findViewById(R.id.edtxt_signup_contact_telephone);
        edtxt_signup_contact_email_address = findViewById(R.id.edtxt_signup_contact_email_address);
        edtxt_signup_account_password = findViewById(R.id.edtxt_signup_account_password);
        btn_signup_fourth_submit = findViewById(R.id.btn_signup_fourth_submit);
        edtxt_signup_account_confirm_password  = findViewById(R.id.edtxt_signup_account_confirm_password);
        edtxt_signup_billing_business_name  = findViewById(R.id.edtxt_signup_billing_business_name);
        edtxt_signup_billing_first_name  = findViewById(R.id.edtxt_signup_billing_first_name);
        edtxt_signup_billing_last_name  = findViewById(R.id.edtxt_signup_billing_last_name);
        edtxt_signup_billing_address_line1  = findViewById(R.id.edtxt_signup_billing_address_line1);
        edtxt_signup_billing_address_line2  = findViewById(R.id.edtxt_signup_billing_address_line2);
        edtxt_signup_billing_city= findViewById(R.id.edtxt_signup_billing_city);
        edtxt_signup_billing_postcode= findViewById(R.id.edtxt_signup_billing_postcode);
        edtxt_signup_shipping_Business_name= findViewById(R.id.edtxt_signup_shipping_Business_name);
        edtxt_signup_shipping_first_name= findViewById(R.id.edtxt_signup_shipping_first_name);
        edtxt_signup_shipping_last_name= findViewById(R.id.edtxt_signup_shipping_last_name);
        edtxt_signup_shipping_address_line1 = findViewById(R.id.edtxt_signup_shipping_address_line1);
        edtxt_signup_shipping_address_line2= findViewById(R.id.edtxt_signup_shipping_address_line2);
        edtxt_signup_shipping_city = findViewById(R.id.edtxt_signup_shipping_city);
        edtxt_signup_shipping_postcode = findViewById(R.id.edtxt_signup_shipping_postcode);
        /*contact_first_name,contact_last_name,contact_telephone,contact_email_address,account_password,billing_business_name,billing_first_name,billing_last_name,
                billing_address_line1,billing_address_line2,billing_city,billing_postcode,shipping_Business_name,shipping_first_name,shipping_last_name,
                shipping_address_line1,shipping_address_line2,shipping_city,shipping_postcode
*/

        contact_first_name = edtxt_signup_contact_first_name.getText().toString();
        contact_last_name = edtxt_signup_contact_last_name.getText().toString();
        contact_telephone = edtxt_signup_contact_telephone.getText().toString();
        contact_email_address = edtxt_signup_contact_email_address.getText().toString();
        account_password = edtxt_signup_account_password.getText().toString();
        account_confirm_password = edtxt_signup_account_confirm_password.getText().toString();
        billing_business_name = edtxt_signup_billing_business_name.getText().toString();
        billing_first_name = edtxt_signup_billing_first_name.getText().toString();
        billing_last_name = edtxt_signup_billing_last_name.getText().toString();
        billing_address_line1 = edtxt_signup_billing_address_line1.getText().toString();
        billing_address_line2 = edtxt_signup_billing_address_line2.getText().toString();
        billing_city = edtxt_signup_billing_city.getText().toString();
        billing_postcode = edtxt_signup_billing_postcode.getText().toString();
        shipping_Business_name = edtxt_signup_shipping_Business_name.getText().toString();
        shipping_first_name = edtxt_signup_shipping_first_name.getText().toString();
        shipping_last_name = edtxt_signup_shipping_last_name.getText().toString();
        shipping_address_line1 = edtxt_signup_shipping_address_line1.getText().toString();
        shipping_address_line2 = edtxt_signup_shipping_address_line2.getText().toString();
        shipping_city = edtxt_signup_shipping_city.getText().toString();
        shipping_postcode = edtxt_signup_shipping_postcode.getText().toString();


      /*  btn_signup_fourth_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignUp_Fourth_Screen.this, SignUp_Email_Screen.class);
                startActivity(intent);

            }
        });*/


        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // TODO Auto-generated method stub

                if(isChecked){
                    edtxt_signup_shipping_Business_name.setText(edtxt_signup_billing_business_name.getText().toString());
                    edtxt_signup_shipping_first_name.setText(edtxt_signup_billing_first_name.getText().toString());
                    edtxt_signup_shipping_last_name.setText(edtxt_signup_billing_last_name.getText().toString());
                    edtxt_signup_shipping_address_line1.setText(edtxt_signup_billing_address_line1.getText().toString());
                    edtxt_signup_shipping_address_line2.setText(edtxt_signup_billing_address_line2.getText().toString());
                    edtxt_signup_shipping_city.setText(edtxt_signup_billing_city.getText().toString());
                    edtxt_signup_shipping_postcode.setText(edtxt_signup_billing_postcode.getText().toString());

                }
                else{
                    edtxt_signup_shipping_Business_name.setText("");
                    edtxt_signup_shipping_first_name.setText("");
                    edtxt_signup_shipping_last_name.setText("");
                    edtxt_signup_shipping_address_line1.setText("");
                    edtxt_signup_shipping_address_line2.setText("");
                    edtxt_signup_shipping_city.setText("");
                    edtxt_signup_shipping_postcode.setText("");



                }
            }
        });



        btn_signup_fourth_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                    if(!TextUtils.isEmpty(edtxt_signup_contact_first_name.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                        if(!TextUtils.isEmpty(edtxt_signup_contact_last_name.getText().toString())) {     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                            if (!TextUtils.isEmpty(edtxt_signup_contact_telephone.getText().toString())) {     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                if(!TextUtils.isEmpty(edtxt_signup_contact_email_address.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                    if(!TextUtils.isEmpty(edtxt_signup_account_password.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                        if(!TextUtils.isEmpty(edtxt_signup_account_confirm_password.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                            if (edtxt_signup_account_password.getText().toString().matches(edtxt_signup_account_confirm_password.getText().toString())){

                                                if(!TextUtils.isEmpty(edtxt_signup_billing_business_name.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                                    if(!TextUtils.isEmpty(edtxt_signup_billing_first_name.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                                        if(!TextUtils.isEmpty(edtxt_signup_billing_last_name.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                                            if(!TextUtils.isEmpty(edtxt_signup_billing_address_line1.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                                                if(!TextUtils.isEmpty(edtxt_signup_billing_address_line2.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                                                    if(!TextUtils.isEmpty(edtxt_signup_billing_city.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                                                        if(!TextUtils.isEmpty(edtxt_signup_billing_postcode.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                                                            if(!TextUtils.isEmpty(edtxt_signup_shipping_Business_name.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                                                                if(!TextUtils.isEmpty(edtxt_signup_shipping_first_name.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                                                                    if(!TextUtils.isEmpty(edtxt_signup_shipping_last_name.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                                                                        if(!TextUtils.isEmpty(edtxt_signup_shipping_address_line1.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                                                                            if(!TextUtils.isEmpty(edtxt_signup_shipping_address_line2.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                                                                                if(!TextUtils.isEmpty(edtxt_signup_shipping_city.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                                                                                    if(!TextUtils.isEmpty(edtxt_signup_shipping_postcode.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                                                                                                        /**************************************************Register Device to the Firebase************************************************************/

                                                                                                        FirebaseInstanceId.getInstance().getInstanceId()
                                                                                                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                                                                                    @Override
                                                                                                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                                                                                        if (!task.isSuccessful()) {
                                                                                                                            return;
                                                                                                                        }

                                                                                                                        Log.e("data","1");

                                                                                                                        // Get new Instance ID token
                                                                                                                        deviceid = task.getResult().getToken();

                                                                                                                        Log.e("data",deviceid+"");
                                                                                                                        Paper.book().write("deviceid",deviceid);
                                                                                                                        RegisterUser();                      // Calling Login Api
                                                                                                                    }
                                                                                                                });
                                                                                                        /***************************************************************End***************************************************************************/





                                                                                                }
                                                                                                else{

                                                                                                    Toast.makeText(getApplicationContext(),"Please Enter Shipping Postcode", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            }
                                                                                            else{

                                                                                                Toast.makeText(getApplicationContext(),"Please Enter Shipping City", Toast.LENGTH_SHORT).show();
                                                                                            }

                                                                                        }
                                                                                        else{

                                                                                            Toast.makeText(getApplicationContext(),"Please Enter Shipping Address Line 2", Toast.LENGTH_SHORT).show();
                                                                                        }

                                                                                    }
                                                                                    else{

                                                                                        Toast.makeText(getApplicationContext(),"Please Enter Shipping Address Line 1", Toast.LENGTH_SHORT).show();
                                                                                    }

                                                                                }
                                                                                else{

                                                                                    Toast.makeText(getApplicationContext(),"Please Enter Shipping Last Name", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }
                                                                            else{

                                                                                Toast.makeText(getApplicationContext(),"Please Enter Shipping First Name", Toast.LENGTH_SHORT).show();
                                                                            }

                                                                        }
                                                                        else{

                                                                            Toast.makeText(getApplicationContext(),"Please Enter Shipping Business Name", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    }
                                                                    else{

                                                                        Toast.makeText(getApplicationContext(),"Please Billing Postcode", Toast.LENGTH_SHORT).show();
                                                                    }

                                                                }
                                                                else{

                                                                    Toast.makeText(getApplicationContext(),"Please Enter Billing City", Toast.LENGTH_SHORT).show();
                                                                }

                                                            }
                                                            else{

                                                                Toast.makeText(getApplicationContext(),"Please Enter Billing Address Line 2", Toast.LENGTH_SHORT).show();
                                                            }

                                                        }
                                                        else{

                                                            Toast.makeText(getApplicationContext(),"Please Enter Billing Address Line 1", Toast.LENGTH_SHORT).show();
                                                        }

                                                    }
                                                    else{

                                                        Toast.makeText(getApplicationContext(),"Please Enter Billing Last Name", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                                else{

                                                    Toast.makeText(getApplicationContext(),"Please Enter Billing First Name", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                            else{

                                                Toast.makeText(getApplicationContext(),"Please Enter Billing Business Name", Toast.LENGTH_SHORT).show();
                                            }

                                            }
                                            else{

                                                Toast.makeText(getApplicationContext(),"Password Mismatch", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                        else{

                                            Toast.makeText(getApplicationContext(),"Please Enter confirm Password", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    else{

                                        Toast.makeText(getApplicationContext(),"Please Enter Password", Toast.LENGTH_SHORT).show();
                                    }

                                    }
                                    else{

                                        Toast.makeText(getApplicationContext(),"Please Enter Contact Email Address", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                else{

                                    Toast.makeText(getApplicationContext(),"Please Enter Contact Telephone ", Toast.LENGTH_SHORT).show();
                                }

                            }

                            else{

                                Toast.makeText(getApplicationContext(),"Please Enter Last Name", Toast.LENGTH_SHORT).show();
                            }

                    }
                    else{

                        Toast.makeText(getApplicationContext(),"Please Enter First Name", Toast.LENGTH_SHORT).show();
                    }
            }
        });

    }

    private void RegisterUser() {

       /*contact_first_name,contact_last_name,contact_telephone,contact_email_address,account_password,billing_business_name,billing_first_name,billing_last_name,
                billing_address_line1,billing_address_line2,billing_city,billing_postcode,shipping_Business_name,shipping_first_name,shipping_last_name,
                shipping_address_line1,shipping_address_line2,shipping_city,shipping_postcode
*/

        contact_email_address = edtxt_signup_contact_email_address.getText().toString();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.REGISTER_USER_DETAILS_CALL( edtxt_signup_contact_first_name.getText().toString(),edtxt_signup_contact_last_name.getText().toString(),edtxt_signup_contact_telephone.getText().toString(),
                edtxt_signup_contact_email_address.getText().toString(),edtxt_signup_account_confirm_password.getText().toString(),edtxt_signup_billing_business_name.getText().toString(),
                edtxt_signup_billing_first_name.getText().toString(),edtxt_signup_billing_last_name.getText().toString(),edtxt_signup_billing_address_line1.getText().toString(),
                edtxt_signup_billing_address_line2.getText().toString(),edtxt_signup_billing_city.getText().toString(),edtxt_signup_billing_postcode.getText().toString(),
                edtxt_signup_shipping_Business_name.getText().toString(),edtxt_signup_shipping_first_name.getText().toString(),edtxt_signup_shipping_last_name.getText().toString(),
                edtxt_signup_shipping_address_line1.getText().toString(),edtxt_signup_shipping_address_line2.getText().toString(),edtxt_signup_shipping_city.getText().toString(),
                edtxt_signup_shipping_postcode.getText().toString()).enqueue(new Callback<ModelRegisterUserDetails>() {
            @Override
            public void onResponse(Call<ModelRegisterUserDetails> call, Response<ModelRegisterUserDetails> response) {

                if (response.body().getStatusCode().equals(200)) {

                        Paper.book().write("unique_id",response.body().getId());

                        SharedPreferences pref1 = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = pref1.edit();

                        editor1.putString("email", contact_email_address);
                        editor1.putString("password", account_confirm_password);
                        editor1.putString("stripe_publish_key", response.body().getPublishKey());
                        editor1.putString("unique_id", response.body().getId());
                        editor1.putString("username", response.body().getUserName());
                        editor1.putString("role", response.body().getRole());
                        editor1.putString("Image", response.body().getImage());
                        editor1.putString("permission_cat", response.body().getCategorie());
                        editor1.putString("permission_orders", response.body().getOrders());
                        editor1.putString("permission_pro_details", response.body().getProjectDetails());
                        editor1.putString("permission_catelogues", response.body().getCatalogues());
                        editor1.putString("permission_all_orders", response.body().getAllOrders());
                        editor1.putString("permission_awaiting", response.body().getOrdersToApprove());
                        editor1.putString("permission_see_cost", response.body().getSeecost());
                        editor1.putString("permission_add_product", response.body().getAddproduct());
                        editor1.putInt("awaiting_data", response.body().getNumberofawating());
                        editor1.putString("permission_wholeseller", response.body().getPermissionWholeseller());
                        editor1.putString("Wholeseller_engineer_id", String.valueOf(response.body().getWholesellerEngineerId()));
                        editor1.putString("business_name", response.body().getBussinessName());
                        editor1.putString("ViewWholesellerPage", response.body().getViewWholesellerPage());
                        editor1.putString("first_name", response.body().getFirstName());
                        editor1.putString("last_name", response.body().getLastName());
                        editor1.putString("wholeseller_bus_id", response.body().getBusinessId());

                        editor1.apply();

                        Paper.book().write("popup_register_sign","");


                        Paper.book().write("user","2");
                        Paper.book().write("login_type","5");
                        Paper.book().write("email",contact_email_address);
                        Paper.book().write("username",response.body().getUserName());
                        Paper.book().write("userid", response.body().getId());
                        Paper.book().write("business_name", response.body().getBussinessName());
                        Paper.book().write("datarole", response.body().getRole());
                        Paper.book().write("permission_see_cost", response.body().getSeecost());
                        Paper.book().write("permission_cats", response.body().getCategorie());
                        Paper.book().write("permission_orderss", response.body().getOrders());
                        Paper.book().write("permission_pro_detailssse", response.body().getProjectDetails());
                        Paper.book().write("permission_cateloguess", response.body().getCatalogues());
                        Paper.book().write("permission_all_orders", response.body().getAllOrders());
                        Paper.book().write("permission_awaiting", response.body().getOrdersToApprove());
                        Paper.book().write("awaiting_data", response.body().getNumberofawating());
                        Paper.book().write("permission_add_product", response.body().getAddproduct());
                        Paper.book().write("permission_wholeseller", response.body().getPermissionWholeseller());
                        Paper.book().write("Wholeseller_engineer_id", response.body().getWholesellerEngineerId());
                        Paper.book().write("ViewWholesellerPage", response.body().getViewWholesellerPage());
                        Paper.book().write("business_id", response.body().getBusinessId());
                        Paper.book().write("stripe_publish_key", response.body().getPublishKey());
                        Paper.book().write("wholeseller_bus_id",response.body().getBusinessId());
                        Paper.book().write("first_name",response.body().getFirstName());
                        Paper.book().write("last_name", response.body().getLastName());




                    if (response.body().getViewWholesellerPage().equals("0")){

                        Intent intent = new Intent(SignUp_Fourth_Screen.this,Home.class);
                        startActivity(intent);


                    }

                    else {

                        Intent intent = new Intent(SignUp_Fourth_Screen.this, GetPostcode.class);
                        intent.putExtra("username", response.body().getUserName());
                        intent.putExtra("unique_id", response.body().getId());
                        intent.putExtra("role", response.body().getRole());
                        intent.putExtra("Image", response.body().getImage());
                        intent.putExtra("permission_cat", response.body().getCategorie());
                        intent.putExtra("permission_orders", response.body().getOrders());
                        intent.putExtra("permission_pro_details", response.body().getCategorie());
                        intent.putExtra("permission_catelogues", response.body().getOrders());
                        intent.putExtra("permission_all_orders", response.body().getAllOrders());
                        intent.putExtra("permission_awaiting", response.body().getOrdersToApprove());
                        intent.putExtra("permission_see_cost", response.body().getSeecost());
                        intent.putExtra("awaiting_data", response.body().getNumberofawating());
                        intent.putExtra("permission_wholeseller", response.body().getPermissionWholeseller());
                        intent.putExtra("permission_add_product", response.body().getAddproduct());
                        intent.putExtra("Wholeseller_engineer_id", response.body().getWholesellerEngineerId());
                        intent.putExtra("business_name", response.body().getBussinessName());
                        intent.putExtra("ViewWholesellerPage", response.body().getViewWholesellerPage());
                        intent.putExtra("stripe_publish_key", response.body().getPublishKey());
                        intent.putExtra("email", contact_email_address);
                        startActivity(intent);

                    }

                }

                else {

                    Toast.makeText(SignUp_Fourth_Screen.this,"Email Already Exist", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ModelRegisterUserDetails> call, Throwable t) {

                Toast.makeText(SignUp_Fourth_Screen.this,t.getMessage() , Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });
    }
}
