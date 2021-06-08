package com.atradeya.asharani;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Default_Delivery_address extends Fragment  {

    TextView txt_default_delivery_first_name,txt_default_delivery_last_name,txt_default_delivery_company_postcode,txt_default_delivery_company_city,txt_default_delivery_company_address,txt_default_delivery_company_name
            ,txt_default_delivery_address,txt_default_delivery_edit,txt_address_type;
    Home homeobj;
    EditText cmpny_name,address,cmpny_address,cmpny_city,cmpny_postcode,txtvw_address_first_name,txtvw_address_last_name;
    Button btn_save_address;
    Dialog dialog;
    public FragmentTransaction fragmentTransaction;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.default_delivery_address, container, false);

        txt_default_delivery_address=view.findViewById(R.id.txt_default_delivery_address);
        txt_default_delivery_company_address=view.findViewById(R.id.txt_default_delivery_company_address);
        txt_default_delivery_company_name=view.findViewById(R.id.txt_default_delivery_company_name);
        txt_default_delivery_company_city=view.findViewById(R.id.txt_default_delivery_company_city);
        txt_default_delivery_company_postcode=view.findViewById(R.id.txt_default_delivery_company_postcode);
        txt_default_delivery_edit = view.findViewById(R.id.txt_default_delivery_edit);
        txt_default_delivery_first_name = view.findViewById(R.id.txt_default_delivery_first_name);
        txt_default_delivery_last_name = view.findViewById(R.id.txt_default_delivery_last_name);

        /************************************************************* Fragment Back handler  ***********************************************/

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

        /*************************************************************************************************************************************/
        ((Home) getActivity()).hideView(true);

            Default_Delivery_address();


        txt_default_delivery_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               dialog= new Dialog(getActivity());

                dialog.setContentView(R.layout.default_new);

                txtvw_address_first_name = dialog.findViewById(R.id.txtvw_address_first_name);
                txtvw_address_last_name = dialog.findViewById(R.id.txtvw_address_last_name);
                txt_address_type = dialog.findViewById(R.id.txt_address_type);
                cmpny_name = dialog.findViewById(R.id.cmpny_name);
                address = dialog.findViewById(R.id.address);
                cmpny_address = dialog.findViewById(R.id.cmpny_address);
                cmpny_city = dialog.findViewById(R.id.cmpny_city);
                cmpny_postcode = dialog.findViewById(R.id.cmpny_postcode);
                btn_save_address = dialog.findViewById(R.id.btn_save_address);


                txt_address_type.setText("Delivery Address *");
                cmpny_name.setText(Paper.book().read("default_delivery_company_name"));
                address.setText(Paper.book().read("default_delivery_address"));
                cmpny_address.setText(Paper.book().read("default_delivery_company_address"));
                cmpny_city.setText(Paper.book().read("default_delivery_company_city"));
                cmpny_postcode.setText(Paper.book().read("default_delivery_company_postcode"));
                txtvw_address_first_name.setText(Paper.book().read("txt_billing_first_name"));
                txtvw_address_last_name.setText(Paper.book().read("txt_billing_last_name"));


                btn_save_address.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!TextUtils.isEmpty(cmpny_name.getText().toString())){

                            if (!TextUtils.isEmpty(address.getText().toString())){

                                if (!TextUtils.isEmpty(cmpny_address.getText().toString())){

                                    if (!TextUtils.isEmpty(cmpny_city.getText().toString())){

                                        if (!TextUtils.isEmpty(cmpny_postcode.getText().toString())){

                                            if (!TextUtils.isEmpty(txtvw_address_first_name.getText().toString())){

                                                if (!TextUtils.isEmpty(txtvw_address_last_name.getText().toString())){


                                                    UpdateAddress();

                                                }
                                                else {

                                                    Toast.makeText(getActivity(),"Please Enter Last Name",Toast.LENGTH_SHORT).show();

                                                }


                                            }
                                            else {

                                                Toast.makeText(getActivity(),"Please Enter First Name",Toast.LENGTH_SHORT).show();

                                            }


                                        }
                                        else {

                                            Toast.makeText(getActivity(),"Please Enter Company Postcode",Toast.LENGTH_SHORT).show();

                                        }

                                    }
                                    else {

                                        Toast.makeText(getActivity(),"Please Enter Company City",Toast.LENGTH_SHORT).show();

                                    }

                                }
                                else {

                                    Toast.makeText(getActivity(),"Please Enter Company Address",Toast.LENGTH_SHORT).show();

                                }

                            }
                            else {

                                Toast.makeText(getActivity(),"Please Enter Address",Toast.LENGTH_SHORT).show();

                            }

                        }
                        else {

                            Toast.makeText(getActivity(),"Please Enter Company Name",Toast.LENGTH_SHORT).show();

                        }


                    }
                });






                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

            }
        });

        return view;
    }

    private void UpdateAddress() {

        String Wholeseller_engineer_id = Paper.book().read("Wholeseller_engineer_id");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.UPDATE_ADDRESS_CALL(Wholeseller_engineer_id,txtvw_address_first_name.getText().toString(),txtvw_address_last_name.getText().toString(),"1",cmpny_city.getText().toString(),address.getText().toString(),
                cmpny_name.getText().toString(),cmpny_address.getText().toString(),cmpny_postcode.getText().toString()).enqueue(new Callback<ModelUpdateAddress>() {
            @Override
            public void onResponse(Call<ModelUpdateAddress> call, Response<ModelUpdateAddress> response) {

                if (response.body().getStatusCode().equals(200)){

                    Log.e("data1",response.body().getStatusCode().toString());


                    Toast.makeText(getActivity(),response.body().getMessage(), Toast.LENGTH_LONG).show();
                }

                else {

                    Log.e("data2",response.body().getStatusCode().toString());

                    Toast.makeText(getActivity(),"Already Updated", Toast.LENGTH_LONG).show();

                }

                dialog.dismiss();

                fragmentTransaction=getFragmentManager().beginTransaction().replace(R.id.containerr,new AccountDetails());
                fragmentTransaction.commit();


            }

            @Override
            public void onFailure(Call<ModelUpdateAddress> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });


    }

    private void Default_Delivery_address() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String Wholeseller_engineer_id = Paper.book().read("Wholeseller_engineer_id");
        android.util.Log.e("user22222",Paper.book().read("Wholeseller_engineer_id")+"");

        service.DEFAULT_DELIVERY_ADDRESS_CALL(Wholeseller_engineer_id).enqueue(new Callback<ModelDefaultDeliveryAddress>() {
            @Override
            public void onResponse(Call<ModelDefaultDeliveryAddress> call, Response<ModelDefaultDeliveryAddress> response) {

                if (response.body().getStatusCode().equals(200)){
                    Paper.book().write("default_delivery_address_statuscode",response.body().getStatusCode());
                    Paper.book().write("default_delivery_address",response.body().getDeleveryAddress());
                    Paper.book().write("default_delivery_company_address",response.body().getDeleveryCompanyAddress());
                    Paper.book().write("default_delivery_company_name",response.body().getDeleveryCompanyName());
                    Paper.book().write("default_delivery_company_city",response.body().getDeleveryCompanyCity());
                    Paper.book().write("default_delivery_company_postcode",response.body().getDeleveryCompanyPostcode());
                    Paper.book().write("default_delivery_last_name",response.body().getLastname());
                    Paper.book().write("default_delivery_first_name",response.body().getFirstname());


                    txt_default_delivery_last_name.setText(response.body().getLastname());
                    txt_default_delivery_first_name.setText(response.body().getFirstname());
                    txt_default_delivery_address.setText(response.body().getDeleveryAddress());
                    txt_default_delivery_company_address.setText(response.body().getDeleveryCompanyAddress());
                    txt_default_delivery_company_name.setText(response.body().getDeleveryCompanyName());
                    txt_default_delivery_company_city.setText(response.body().getDeleveryCompanyCity());
                    txt_default_delivery_company_postcode.setText(response.body().getDeleveryCompanyPostcode());

                }
                else {

                Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_LONG);

            }
        }

            @Override
            public void onFailure(Call<ModelDefaultDeliveryAddress> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity.getClass() == Home.class) {
            homeobj = (Home) activity;
        }

    }
}
