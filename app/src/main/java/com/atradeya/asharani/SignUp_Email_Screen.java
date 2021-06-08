package com.atradeya.asharani;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp_Email_Screen extends AppCompatActivity {

    EditText edtxt_signup_first_screen_email;
    Button btn_signup_first_register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_first_screen);

        edtxt_signup_first_screen_email = findViewById(R.id.edtxt_signup_first_screen_email);
        btn_signup_first_register= findViewById(R.id.btn_signup_first_register);

        edtxt_signup_first_screen_email.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        edtxt_signup_first_screen_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        btn_signup_first_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(edtxt_signup_first_screen_email.getText().toString())){     // VALIDATE EITHER EMAIL IS FILLED OR NOT

                    register();

                }
                else{

                    Toast.makeText(getApplicationContext(),"Please Enter Email", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void register() {

        final String email = edtxt_signup_first_screen_email.getText().toString();


        final ProgressDialog progressDialogs = new ProgressDialog(SignUp_Email_Screen.this,R.style.AlertDialogCustom);
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

        service.Register_Email(email).enqueue(new Callback<ModelRegister>() {
            @Override
            public void onResponse(Call<ModelRegister> call, Response<ModelRegister> response) {

                if (response.body().getStatusCode().equals(200)) {

                    Toast.makeText(getApplicationContext(), "Password Send Successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUp_Email_Screen.this, SignUp_Fourth_Screen.class);
                    intent.putExtra("email",email);
                    intent.putExtra("password",response.body().getPassword());

                    startActivity(intent);

                }

                else {

                    Toast.makeText(getApplicationContext(), "E-mail Already Registered", Toast.LENGTH_SHORT).show();
                }
                progressDialogs.dismiss();

            }

            @Override
            public void onFailure(Call<ModelRegister> call, Throwable t) {

                Toast.makeText(SignUp_Email_Screen.this,t.getMessage() , Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                progressDialogs.dismiss();

            }
        });


    }

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
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

}
