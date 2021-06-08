package com.atradeya.asharani;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
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

public class Login_Type extends AppCompatActivity {

    Button btn_login_type_guest,btn_login_type_login,btn_login_type_signup;
    public static final String MY_PREFS_NAME = "MyPrefsFile";
    List<Category> modelProductsCategories;
    List<Category> intentmodelProductsCategories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_type);

        btn_login_type_guest = findViewById(R.id.btn_login_type_guest);
        btn_login_type_login = findViewById(R.id.btn_login_type_login);
        btn_login_type_signup = findViewById(R.id.btn_login_type_signup);

        final SharedPreferences sp1 = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        final String username = sp1.getString("username", "Guest");

        btn_login_type_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Paper.book().write("login_type","1");

                    getProducts();         //API CALLING METHOD



            }
        });

        btn_login_type_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().write("login_type","2");
                Paper.book().write("user","2");
                Intent intent = new Intent(Login_Type.this, Login.class);

                startActivity(intent);

            }
        });

        btn_login_type_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().write("login_type","2");
                Intent intent = new Intent(Login_Type.this, SignUp_Email_Screen.class);
                intent.putExtra("username", "Guest");
                intent.putExtra("userid", "0");

                startActivity(intent);

            }
        });


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

        service.Products("0").enqueue(new Callback<ModelProductsCategory>() {
            @Override
            public void onResponse(Call<ModelProductsCategory> call, Response<ModelProductsCategory> response) {

                modelProductsCategories = response.body().getCategory();

                if (response.body().getStatusCode().equals(200)) {

                    Paper.book().write("awaiting_data",response.body().getNumberofawating());

                    Intent intent = new Intent(Login_Type.this, Home.class);
                    intent.putExtra("username", "Guest");

                    startActivity(intent);

                    intentmodelProductsCategories = new ArrayList<>();

                    for (int i = 0; i < modelProductsCategories.size(); i++) {

                        Category category = new Category();
                        category.setCatId(response.body().getCategory().get(i).getCatId());
                        category.setCatImage(response.body().getCategory().get(i).getCatImage());
                        category.setCatName(response.body().getCategory().get(i).getCatName());
                        category.setStatus(response.body().getCategory().get(i).getStatus());
                      /*  category.setFillter(response.body().getCategory().get(i).getFillter());*/

                        intentmodelProductsCategories.add(category);
                    }
                    Paper.book().write("categorylist", intentmodelProductsCategories);
                }

                else {

                    Toast.makeText(getApplicationContext(), "No Products Found", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ModelProductsCategory> call, Throwable t) {

                Toast.makeText(Login_Type.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });

        /************************************************************************************************************************************************/

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Paper.book().write("user","0");
    }
}
