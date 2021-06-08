package com.atradeya.asharani;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

public class Login extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    Button btnLogin, btnRegister;
    TextView txt_forgot_password;
    EditText user_email, user_password;
    String deviceid;
    String popup_register_sign;
    private databaseSqlite db;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        user_email = findViewById(R.id.edtxt_email);
        user_password = findViewById(R.id.edtxt_password);
        txt_forgot_password = findViewById(R.id.forgot_password);
        user_email.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        user_password.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_login_type_signup);

        onConfigurationChanged();

        user_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        user_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });


        popup_register_sign = Paper.book().read("popup_register_sign");

        db = new databaseSqlite(getApplicationContext());



        /*********************************************** SETTING STATUS BAR WHITE ******************************************************************/

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        /************************************************************End*****************************************************************************/


        txt_forgot_password.setPaintFlags(txt_forgot_password.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);  // SET UNDERLINE BELOW FORGOT PASSWORD
        forgot_Password();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Paper.book().write("popup_register_sign", "2");

                Intent intent = new Intent(Login.this, SignUp_Fourth_Screen.class);

                startActivity(intent);

            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(user_email.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                    if(!TextUtils.isEmpty(user_password.getText().toString())){   // VALIDATE EITHER PASSWORD IS FILLED OR NOT


    /**************************************************Register Device to the Firebase************************************************************/

                        FirebaseInstanceId.getInstance().getInstanceId()
                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                        if (!task.isSuccessful()) {
                                            return;
                                        }

                                        // Get new Instance ID token
                                        deviceid = task.getResult().getToken();
                                        Paper.book().write("deviceid",deviceid);
                                        login();                        // Calling Login Api
                                    }
                                });
    /***************************************************************End***************************************************************************/
                    }
                    else{

                        Toast.makeText(getApplicationContext(),"Please Enter Password", Toast.LENGTH_SHORT).show();
                    }
                }
                else{

                    Toast.makeText(getApplicationContext(),"Please Enter Email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void onConfigurationChanged() {

        int nightModeFlags =
                getApplicationContext().getResources().getConfiguration().uiMode &
                        Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                setTheme(R.style.AppThemeDark);
               // Toast.makeText(this, "Dark", Toast.LENGTH_SHORT).show();
                break;

            case Configuration.UI_MODE_NIGHT_NO:
                setTheme(R.style.AppTheme);
              //  Toast.makeText(this, "No_Dark", Toast.LENGTH_SHORT).show();

                break;

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                setTheme(R.style.AppThemeDark);
                break;
        }
    }

    private void forgot_Password() {

        txt_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Login.this, Forgot_Password.class);
                startActivity(intent);
            }
        });
    }

/*

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this,R.style.AlertDialogCustom)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Login.super.onBackPressed();
                        finish();

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
*/

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    private void login() {

                popup_register_sign = "1";

                final String email = user_email.getText().toString();
                final String password1 = user_password.getText().toString();

                final ProgressDialog progressDialogs = new ProgressDialog(Login.this,R.style.AlertDialogCustom);
                progressDialogs.setCancelable(false);
                progressDialogs.setMessage("Please Wait.......");
                progressDialogs.show();

    /********************************************************  LOGIN API USING RETROFIT ******************************************************************/


        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiLogin_Interface.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                //Defining retrofit api service
                ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

                Log.e("emailer",user_email.getText().toString());
                Log.e("emailer2",user_password.getText().toString());

                (service.getClient("application/x-www-form-urlencoded", user_email.getText().toString(),
                        user_password.getText().toString(),deviceid,"Android")).enqueue(new Callback<ModelLogin>() {

                    @Override
                    public void onResponse(Call<ModelLogin> call, Response<ModelLogin> response) {

                        Log.e("emailer","response");

                        assert response.body() != null;
                        Log.e("emailerrt",response.body().getStatusCode().toString()+"");


                        if (response.body().getStatusCode().equals(200)) {

                            if (response.body().getPermissionWholeseller().equals("1")) {


                                Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            Paper.book().write("popup_register_sign","");


                            SharedPreferences pref = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();

                            editor.putString("email", email);
                            editor.putString("password", password1);
                            editor.putString("stripe_publish_key", response.body().getPublishKey());
                            editor.putString("unique_id", response.body().getId());
                            editor.putString("username", response.body().getUserName());
                            editor.putString("role", response.body().getRole());
                            editor.putString("Image", response.body().getImage());
                            editor.putString("permission_cat", response.body().getCategorie());
                            editor.putString("permission_orders", response.body().getOrders());
                            editor.putString("permission_pro_details", response.body().getProjectDetails());
                            editor.putString("permission_catelogues", response.body().getCatalogues());
                            editor.putString("permission_all_orders", response.body().getAllOrders());
                            editor.putString("permission_awaiting", response.body().getOrdersToApprove());
                            editor.putString("permission_see_cost", response.body().getSeecost());
                            editor.putString("permission_add_product", response.body().getAddproduct());
                            editor.putInt("awaiting_data", response.body().getNumberofawating());
                            editor.putString("permission_wholeseller", response.body().getPermissionWholeseller());
                            editor.putString("Wholeseller_engineer_id", String.valueOf(response.body().getWholesellerEngineerId()));
                            editor.putString("business_name", response.body().getBussinessName());
                            editor.putString("ViewWholesellerPage", response.body().getViewWholesellerPage());
                            editor.putString("first_name", response.body().getFirstName());
                            editor.putString("last_name", response.body().getLastName());
                            editor.putString("wholeseller_bus_id", response.body().getBusinessId());

                            editor.apply();

                            Paper.book().write("user","2");

                            Paper.book().write("login_type","5");
                            Paper.book().write("wholeseller_bus_id",response.body().getBusinessId());
                            Paper.book().write("userid", response.body().getId());
                            Paper.book().write("email",email);
                            Paper.book().write("user",response.body().getId());
                            Paper.book().write("first_name",response.body().getFirstName());
                            Paper.book().write("last_name", response.body().getLastName());
                            Paper.book().write("username",response.body().getUserName());
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


                                Intent intent = new Intent(Login.this,Home.class);
                                startActivity(intent);

                          /*  if (response.body().getViewWholesellerPage().equals("0")){



                            }*/

/*                            else {

                                Intent intent = new Intent(Login.this, GetPostcode.class);
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
                                intent.putExtra("email", email);
                                startActivity(intent);

                            }*/

                            }

                            else {

                                Toast.makeText(getApplicationContext(), "User Not Found", Toast.LENGTH_SHORT).show();
                            }



                        }


                         else

                            Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialogs.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ModelLogin> call, Throwable t) {

                        Log.e("emailer","failure");
                        Toast.makeText(Login.this,t.getMessage() , Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                        progressDialogs.dismiss();
                    }
                });

        }

     /************************************************************End********************************************************************************/






    }


