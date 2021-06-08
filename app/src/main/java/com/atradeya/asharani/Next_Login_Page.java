package com.atradeya.asharani;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Next_Login_Page extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    List<Category> modelProductsCategories;
    List<Category> intentmodelProductsCategories;
    Button splashLogin;
    TextView user;
    Intent intentvalues;
    String user_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.next_login);

        splashLogin = findViewById(R.id.splash_login);
        user = findViewById(R.id.user);


        /*********************************************** SETTING STATUS BAR WHITE ******************************************************************/

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        /***********************************************************End******************************************************************************/




        intentvalues = getIntent();

            SharedPreferences sharedPreferences =getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
            sharedPreferences.getString("role", "");
            String email = sharedPreferences.getString("email", "");
            String values = sharedPreferences.getString("username", "");
            sharedPreferences.getString("UserImage", "");
            sharedPreferences.getString("permission_cat", null);
            sharedPreferences.getString("permission_orders",null);
            sharedPreferences.getString("permission_pro_details",null);
            sharedPreferences.getString("permission_catelogues",null);
            sharedPreferences.getString("permission_all_orders",null);
            sharedPreferences.getString("permission_awaiting",null);
            sharedPreferences.getString("permission_see_cost",null);
            String business_name = sharedPreferences.getString("business_name",null);
            String Wholeseller_engineer_id = sharedPreferences.getString("Wholeseller_engineer_id",null);
            user_id =sharedPreferences.getString("unique_id",null);
            String Image =  sharedPreferences.getString("Image",null);

            String first_name = sharedPreferences.getString("first_name", "");
            String last_name = sharedPreferences.getString("last_name", "");

            sharedPreferences.getAll();



            String login_type =Paper.book().read("login_type");

            if (login_type.equals("1")){

                user_id="0";
                user.setText("Guest");
            }

            else {

                user.setText(first_name+" "+last_name);

               // user_id =Paper.book().read("userid");

                Paper.book().write("email", email);
                Paper.book().write("business_name", business_name);
                Paper.book().write("username", values);
                Paper.book().write("UserImage", Image);
                Paper.book().write("userid", user_id);
                Paper.book().write("unique_id", user_id);
                Paper.book().write("Wholeseller_engineer_id", Wholeseller_engineer_id);
                Paper.book().write("search_product","");
            }




            splashLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!(user_id.equals(""))) {

                        getProducts();         //API CALLING METHOD

                    } else {

                        Toast.makeText(getApplicationContext(), "No Product Found", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Next_Login_Page.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }

    private void getProducts() {

    /********************************************************  NEXT LOGIN API USING RETROFIT ******************************************************************/

   // String user_id=Paper.book().read("unique_id");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

            service.Products(user_id).enqueue(new Callback<ModelProductsCategory>() {
                @Override
                public void onResponse(Call<ModelProductsCategory> call, Response<ModelProductsCategory> response) {

                    Log.e("data_HM","1");

                    assert response.body() != null;
                    modelProductsCategories = response.body().getCategory();

                    if (response.body().getStatusCode().equals(200)) {

                        Paper.book().write("awaiting_data",response.body().getNumberofawating());

                        Intent intent = new Intent(Next_Login_Page.this, Home.class);

                        startActivity(intent);

                        intentmodelProductsCategories = new ArrayList<>();

                        for (int i = 0; i < modelProductsCategories.size(); i++) {

                            Category category = new Category();
                            category.setCatId(response.body().getCategory().get(i).getCatId());
                            category.setCatImage(response.body().getCategory().get(i).getCatImage());
                            category.setCatName(response.body().getCategory().get(i).getCatName());
                            category.setStatus(response.body().getCategory().get(i).getStatus());
                            category.setFillter(response.body().getCategory().get(i).getFillter());

                            intentmodelProductsCategories.add(category);

                            Log.e("data_HM",response.body().getCategory().get(i).getFillter()+"");
                        }



                        Paper.book().write("categorylist", intentmodelProductsCategories);
                    }

                    else {

                        Toast.makeText(getApplicationContext(), "No Products Found", Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onFailure(Call<ModelProductsCategory> call, Throwable t) {

                    Log.e("data_HM","2");
                    Log.e("data_HM", t.getMessage()+"");

                    Toast.makeText(Next_Login_Page.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    t.printStackTrace();

                }
            });

    /************************************************************************************************************************************************/

    }

    }



