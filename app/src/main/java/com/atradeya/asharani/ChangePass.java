package com.atradeya.asharani;

import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ChangePass extends Fragment {

    TextView textchange;
    EditText email,newpas,cnfmpas;
    Button submit;
    String userid,emailstring,newspasword,emailtext,confirm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.change_password, container, false);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    getFragmentManager().popBackStack("account", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }

                return false;
            }
        });

        textchange=view.findViewById(R.id.text_change);
        email=view.findViewById(R.id.edtxt_email_id);
        newpas=view.findViewById(R.id.edtxt_new_password);
        cnfmpas=view.findViewById(R.id.edtxt_confirm_password);
        submit=view.findViewById(R.id.btn_submit);
        userid= Paper.book().read("userid");
        emailstring=Paper.book().read("email");

        newspasword= newpas.getText().toString().trim();
        emailtext=email.getText().toString().trim();
        confirm=cnfmpas.getText().toString().trim();
        textchange.setPaintFlags(textchange.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String passwordnew= newpas.getText().toString().trim();
                String emails=email.getText().toString().trim();
                String passwordcnfm= confirm=cnfmpas.getText().toString().trim();

                if ((emails.equals(emailstring)) && !(passwordnew.equals("")) && (passwordcnfm.equals(passwordnew)) )

                {
                    changepassword();
                    email.setText("");
                    newpas.setText("");
                    cnfmpas.setText("");
                }

                else if((emails.equals(emailstring)) && !(passwordnew.equals("")) && !(passwordcnfm.equals("")))
                {
                    cnfmpas.setError("Mismatch password");
                }

                else
                    Toast.makeText(getActivity(),"Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        });


        return view;
    }

    private void changepassword() {

        final ProgressDialog progressDialog=new ProgressDialog(getActivity(),R.style.AlertDialogCustom);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait.......");
        progressDialog.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.CHANGE_PASSWORD_CALL("application/x-www-form-urlencoded",userid,newpas.getText().toString().trim()).enqueue(new Callback<ModelChangePassword>() {
            @Override
            public void onResponse(Call<ModelChangePassword> call, Response<ModelChangePassword> response) {

                if (response.body().getStatusCode().equals(200)){

                    Toast.makeText(getActivity(),response.body().getMessage(), Toast.LENGTH_LONG).show();

                }
                else {

                    Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_LONG);

                }

                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ModelChangePassword> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                progressDialog.dismiss();

            }
        });
    }
}
