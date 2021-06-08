package com.atradeya.asharani;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class Forgot_Password extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFileforgot";
    EditText email;
    Button send;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.forgot_pass);
        email=findViewById(R.id.edtxt_email_forgot);
        send=findViewById(R.id.btn_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(email.getText().toString().trim().equals(""))){
                    forgot();

                    email.setText("");
                }
                else

                    Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_LONG).show();
            }
        });
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

    private void forgot() {

        final ProgressDialog progressDialog=new ProgressDialog(Forgot_Password.this,R.style.AlertDialogCustom);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait.......");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface newservice = retrofit.create(ApiLogin_Interface.class);

        (newservice.Forgot("application/x-www-form-urlencoded",email.getText().toString().trim()
        )).enqueue(new Callback<ModelForgot>() {

            @Override
            public void onResponse(Call<ModelForgot> call, Response<ModelForgot> response) {

                if (response.body().getStatusCode().equals(200))
                {

                    Toast.makeText(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    Intent intent=new Intent(Forgot_Password.this, Login.class);
                    startActivity(intent);
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("email",email.getText().toString() );
                    editor.apply();
                }
                else

                    Toast.makeText(getApplicationContext(),response.body().getMessage(), Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
            }
            @Override
            public void onFailure(Call<ModelForgot> call, Throwable t) {
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }
}

