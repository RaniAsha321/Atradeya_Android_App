package com.atradeya.asharani;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Cart extends Fragment {

    LinearLayout cartlayout,drop_project,drop_supplier,layout_close,layout_close_bottom,layout_custom_address;
    List<String> variation_drop_project,variation_drop_supplier;
    PopupWindow popUp;
    TextView sub_total,vat,total,tv_drop,tv_drop_project,tv_drop_supplier;
    EditText order_desc;
    List<SupplierDatum> supplierData;
    List<SupplierDatum> supplierDatumList;
    Spinner spinner_supplier;
    RecyclerView recyclerView;
    List<ProjectDatum> mylist;
    List<ProjectDatum> projectlist;
    Spinner spinner;
    Adapter_cart_next adapter_cart_next;
    databaseSqlite databaseHelper;
    Button Proceed,btn_custom_address;
    FragmentTransaction transaction;
    List<ModelCartMenu> cartMenuList;
    LinearLayoutManager layoutManager;
    List<ProjectDatum> list;
    ListView my_list;
    String pop_cancel,address,city,postcode,final_address;
    RadioGroup radioGroup;
    EditText custm_address,custm_address_postcode,custm_address_city;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cart, container, false);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    getFragmentManager().popBackStack("cart", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                }
                return false;
            }
        });

        projectlist=new ArrayList<>();

        databaseHelper=new databaseSqlite(getActivity());
        cartMenuList=databaseHelper.getAllModelCartMenu();
        recyclerView=view.findViewById(R.id.cart_recycle);
        order_desc=view.findViewById(R.id.edtxt_order_desc);
        spinner=view.findViewById(R.id.spinner_projects);
        spinner_supplier=view.findViewById(R.id.spinner_supplier);
        Proceed=view.findViewById(R.id.btn_proceed);
        sub_total=view.findViewById(R.id.cart_sub_total);
        vat=view.findViewById(R.id.cart_vat);
        total=view.findViewById(R.id.cart_total);
        cartlayout=view.findViewById(R.id.cart_layout);
        drop_project=view.findViewById(R.id.drop_project);
        drop_supplier=view.findViewById(R.id.drop_supplier);
        tv_drop=view.findViewById(R.id.tv_drop);
        tv_drop_project=view.findViewById(R.id.tv_drop_project);
        tv_drop_supplier=view.findViewById(R.id.tv_drop_supplier);

        order_desc.setRawInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);

        supplierData=new ArrayList<>();
        list= new ArrayList<>();
        mylist= new ArrayList<>();
        variation_drop_project=new ArrayList<>();
        variation_drop_supplier=new ArrayList<>();

        ((Home)getActivity()).hideView(true);
        getsupplier();
        getProjects();

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        spinner.setPrompt("Select Project");
        spinner_supplier.setPrompt("Select Supplier");

        drop_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dropdownmenu_project();
            }
        });

        String business_id=Paper.book().read("business_id");
        String whichbusiness=Paper.book().read("whichbusiness");
        String business=Paper.book().read("save_business");
        String pop_cancel_up=Paper.book().read("pop_cancel_up");

        Log.e("tiger3",business+"");

        if(pop_cancel_up .equals("1")){

            pop_cancel=Paper.book().read("pop_cancel_up");
        }
        else {
            pop_cancel="0";
        }

            if(Paper.book().read("permission_wholeseller", "5").equals("1")){

                Log.e("tiger","1");
                drop_supplier.setVisibility(View.GONE);

                Paper.book().write("supplier_id","0");

                Proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Paper.book().write("order_desc",order_desc.getText().toString().trim());

                        if(!tv_drop_project.getText().equals("Select Project")){

                            if (!TextUtils.isEmpty(order_desc.getText().toString().trim())) {

                                    transaction=getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("li").replace(R.id.containerr, new Cart_Summary());
                                    transaction.commit();

                            }

                            else {

                                Toast.makeText(getActivity(), "Enter Order Description", Toast.LENGTH_SHORT).show();
                            }
                        }

                        else {

                            Toast.makeText(getActivity(), "Select Project", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

            else if(business !=null) {

                if(!(business.equals("0"))){


                    if (!(whichbusiness.equals(business_id))) {

                        Log.e("tigerqueen", business_id+"");

                        Log.e("tigerqueen", whichbusiness+"");



                        drop_supplier.setVisibility(View.GONE);

                        Paper.book().write("supplier_id", "0");

                        Proceed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Paper.book().write("order_desc", order_desc.getText().toString().trim());

                                if (!tv_drop_project.getText().equals("Select Project")) {

                                    if (!TextUtils.isEmpty(order_desc.getText().toString().trim())) {

                                        transaction = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("li").replace(R.id.containerr, new Cart_Summary());
                                        transaction.commit();

                                    } else {

                                        Toast.makeText(getActivity(), "Enter Order Description", Toast.LENGTH_SHORT).show();
                                    }
                                } else {

                                    Toast.makeText(getActivity(), "Select Project", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });




                    }

                    else {

                        Log.e("tiger", "565");
                        drop_supplier.setVisibility(View.VISIBLE);
                        Proceed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {


                                Paper.book().write("order_desc", order_desc.getText().toString().trim());

                                if (!tv_drop_project.getText().equals("Select Project")) {

                                    if (!TextUtils.isEmpty(order_desc.getText().toString().trim())) {

                                        if (!tv_drop_supplier.getText().equals("Select Supplier")) {

                                            transaction = getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("li").replace(R.id.containerr, new Cart_Summary());
                                            transaction.commit();
                                        } else {

                                            Toast.makeText(getActivity(), "Select Supplier", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {

                                        Toast.makeText(getActivity(), "Enter Order Description", Toast.LENGTH_SHORT).show();
                                    }
                                } else {

                                    Toast.makeText(getActivity(), "Select Project", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                    }

                }

                else {

                    drop_supplier.setVisibility(View.VISIBLE);
                    Proceed.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Paper.book().write("order_desc",order_desc.getText().toString().trim());

                            if(!tv_drop_project.getText().equals("Select Project")){

                                if (!TextUtils.isEmpty(order_desc.getText().toString().trim())) {

                                    if(!tv_drop_supplier.getText().equals("Select Supplier")){

                                        transaction=getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("li").replace(R.id.containerr, new Cart_Summary());
                                        transaction.commit();
                                    }

                                    else {

                                        Toast.makeText(getActivity(), "Select Supplier", Toast.LENGTH_SHORT).show();
                                    }

                                }

                                else {

                                    Toast.makeText(getActivity(), "Enter Order Description", Toast.LENGTH_SHORT).show();
                                }
                            }

                            else {

                                Toast.makeText(getActivity(), "Select Project", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });



                }


            }



        else{

                Log.e("tiger","55");
                drop_supplier.setVisibility(View.VISIBLE);
            Proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Paper.book().write("order_desc",order_desc.getText().toString().trim());

                    if(!tv_drop_project.getText().equals("Select Project")){

                        if (!TextUtils.isEmpty(order_desc.getText().toString().trim())) {

                            if(!tv_drop_supplier.getText().equals("Select Supplier")){

                                transaction=getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("li").replace(R.id.containerr, new Cart_Summary());
                                transaction.commit();
                            }

                            else {

                                Toast.makeText(getActivity(), "Select Supplier", Toast.LENGTH_SHORT).show();
                            }

                        }

                        else {

                            Toast.makeText(getActivity(), "Enter Order Description", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {

                        Toast.makeText(getActivity(), "Select Project", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }

        String Whole_Seller_product=Paper.book().read("Whole_Seller_product");

        if(Whole_Seller_product.equals("1")){

            drop_supplier.setVisibility(View.GONE);

            Paper.book().write("supplier_id","0");

            Proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Paper.book().write("order_desc",order_desc.getText().toString().trim());

                    if(!tv_drop_project.getText().equals("Select Project")){

                        if (!TextUtils.isEmpty(order_desc.getText().toString().trim())) {

                            transaction=getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("li").replace(R.id.containerr, new Cart_Summary());
                            transaction.commit();

                        }

                        else {

                            Toast.makeText(getActivity(), "Enter Order Description", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {

                        Toast.makeText(getActivity(), "Select Project", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }




      else{

            Log.e("tiger","65");
            drop_supplier.setVisibility(View.VISIBLE);
            Proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Paper.book().write("order_desc",order_desc.getText().toString().trim());

                    if(!tv_drop_project.getText().equals("Select Project")){

                        if (!TextUtils.isEmpty(order_desc.getText().toString().trim())) {

                            if(!tv_drop_supplier.getText().equals("Select Supplier")){

                                transaction=getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("li").replace(R.id.containerr, new Cart_Summary());
                                transaction.commit();
                            }

                            else {

                                Toast.makeText(getActivity(), "Select Supplier", Toast.LENGTH_SHORT).show();
                            }

                        }

                        else {

                            Toast.makeText(getActivity(), "Enter Order Description", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {

                        Toast.makeText(getActivity(), "Select Project", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }


        if(Paper.book().read("permission_wholeseller", "5").equals("1")){

            Log.e("tiger","1");
            drop_supplier.setVisibility(View.GONE);

            Paper.book().write("supplier_id","0");

            Proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Paper.book().write("order_desc",order_desc.getText().toString().trim());

                    if(!tv_drop_project.getText().equals("Select Project")){

                        if (!TextUtils.isEmpty(order_desc.getText().toString().trim())) {

                            transaction=getActivity().getSupportFragmentManager().beginTransaction().addToBackStack("li").replace(R.id.containerr, new Cart_Summary());
                            transaction.commit();

                        }

                        else {

                            Toast.makeText(getActivity(), "Enter Order Description", Toast.LENGTH_SHORT).show();
                        }
                    }

                    else {

                        Toast.makeText(getActivity(), "Select Project", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }


        drop_supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dropdownmenu_supplier();
            }
        });

        adapter_cart_next=new Adapter_cart_next(getActivity(),cartMenuList);
        recyclerView.setAdapter(adapter_cart_next);

        double value=databaseHelper.getAllPrices();
        String data= String.format("%.2f", value);
        double sub=databaseHelper.getAllInc();
        String data1= String.format("%.2f", sub);
        double vat1=sub-value;
        String vatvalue= String.format("%.2f", vat1);

        if(Paper.book().read("permission_see_cost","2").equals("1")){

            sub_total.setText(data);
            total.setText(data1);
            vat.setText(vatvalue);
        }


        else {

            sub_total.setText("0.00");
            total.setText("0.00");
            vat.setText("0.00");

        }

            if(Paper.book().read("permission_wholeseller", "5").equals("1")) {

                sub_total.setText(data);
                total.setText(data1);
                vat.setText(vatvalue);
            }

        return view;

    }

    private void dropdownmenu_project() {


        final Dialog dialog= new Dialog(getActivity());
        dialog.setContentView(R.layout.custom_dropdown_address_new);
        layout_custom_address = dialog.findViewById(R.id.layout_custom_address);
        radioGroup = dialog.findViewById(R.id.radioGroup);
        custm_address = dialog.findViewById(R.id.custm_address);
        custm_address_city = dialog.findViewById(R.id.custm_address_city);
        custm_address_postcode = dialog.findViewById(R.id.custm_address_postcode);
        btn_custom_address = dialog.findViewById(R.id.btn_custom_address);

        dialog.show();

        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        radioGroup.clearCheck();

        if (tv_drop_project.getText().toString().equals("Default Delivery Address")){

            layout_custom_address.setVisibility(View.GONE);
            ((RadioButton)radioGroup.getChildAt(0)).setChecked(true);

        }

        else if (!tv_drop_project.getText().toString().equals("Select Project")) {

            String values = tv_drop_project.getText().toString();

            String address[] = values.split(",");

            custm_address.setText(address[0]);
            custm_address_city.setText(address[1]);
            custm_address_postcode.setText(address[2]);

            layout_custom_address.setVisibility(View.VISIBLE);
            ((RadioButton)radioGroup.getChildAt(1)).setChecked(true);

        }


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                /*RadioButton radioButton1 = (RadioButton) group.findViewById(R.id.radioButton1);
                RadioButton radioButton2 = (RadioButton) group.findViewById(R.id.radioButton2);*/

                Log.e("idhorse",checkedId+"");

                if (checkedId != -1  && null != rb){

                    if (rb.getText().toString().equals("Default Delivery Address")) {

                        Log.e("idhorse1",rb.getText()+"");

                        layout_custom_address.setVisibility(View.GONE);
                        Paper.book().write("Project_Address","0");
                        tv_drop_project.setText(rb.getText());
                        dialog.dismiss();

                    }

                    else {

                        Log.e("idhorse2",rb.getText()+"");

                        layout_custom_address.setVisibility(View.VISIBLE);

                    }

                }

            }
        });


        btn_custom_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(custm_address.getText().toString())){


                    if (!TextUtils.isEmpty(custm_address_city.getText().toString())){


                        if (!TextUtils.isEmpty(custm_address_postcode.getText().toString())){

                            address =custm_address.getText().toString();
                            city =custm_address_city.getText().toString();
                            postcode =custm_address_postcode.getText().toString();

                            Paper.book().write("Project_Address","1");
                            Paper.book().write("address",address);
                            Paper.book().write("city",city);
                            Paper.book().write("postcode",postcode);

                            address =address.replaceAll(","," ");

                            final_address= address +"," +city +"," +postcode;

                            Paper.book().write("Custom_Address",final_address);
                            tv_drop_project.setText(final_address);
                            dialog.dismiss();

                        }

                        else {

                            Toast.makeText(getActivity(),"Please Enter PostCode",Toast.LENGTH_SHORT).show();

                        }


                    }

                    else {

                        Toast.makeText(getActivity(),"Please Enter City",Toast.LENGTH_SHORT).show();

                    }


                }

                else {

                    Toast.makeText(getActivity(),"Please Enter Address",Toast.LENGTH_SHORT).show();

                }

            }
        });





    }

    private void dropdownmenu_supplier() {

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.custom_dropdown_menu, null);

        layout_close = (LinearLayout) customView.findViewById(R.id.layout_popup_upper);
        layout_close_bottom=(LinearLayout) customView.findViewById(R.id.layout_popup_bottom);
        my_list = customView.findViewById(R.id.list);
        //instantiate popup window
        popUp = new PopupWindow(customView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, variation_drop_supplier);

        my_list.setAdapter(arrayAdapter);

        my_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                position=position+1;
                tv_drop_supplier.setText(supplierData.get(position).getSuppliersName());
                Paper.book().write("supplier_id",supplierData.get(position).getId());
                popUp.dismiss();

            }
        });
        //display the popup window
        popUp.showAtLocation(cartlayout, Gravity.CENTER, 0, 0);

        //close the popup window on button click
        layout_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.dismiss();
            }
        });

        layout_close_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp.dismiss();
            }
        });

    }

    private void getsupplier() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String userid = Paper.book().read("userid");

        service.SUPPLIER_CALL(userid).enqueue(new Callback<ModelSupplier>() {
            @Override
            public void onResponse(Call<ModelSupplier> call, Response<ModelSupplier> response) {

                if (response.body().getStatusCode().equals(200)) {

                    supplierDatumList = response.body().getSupplierData();

                    for (int i = 0; i < supplierDatumList.size(); i++) {

                        SupplierDatum supplierDatum = new SupplierDatum();
                        supplierDatum.setSuppliersName(response.body().getSupplierData().get(i).getSuppliersName());
                        supplierDatum.setId(response.body().getSupplierData().get(i).getId());
                        supplierData.add(supplierDatum);
                    }

                    for (int i = 1; i < supplierDatumList.size(); i++) {

                        SupplierDatum supplierDatum = new SupplierDatum();
                        supplierDatum.setSuppliersName(response.body().getSupplierData().get(i).getSuppliersName());
                        variation_drop_supplier.add(response.body().getSupplierData().get(i).getSuppliersName());
                    }
                }

            }

            @Override
            public void onFailure(Call<ModelSupplier> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void getProjects() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        final ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String userid= Paper.book().read("userid");

        service.PROJECTS_CALL(userid).enqueue(new Callback<ModelProjects>() {
            @Override
            public void onResponse(Call<ModelProjects> call, Response<ModelProjects> response) {

                if (response.body().getStatusCode().equals(200)){

                    projectlist=response.body().getProjectData();

                    for (int i=0; i<projectlist.size();i++) {

                        ProjectDatum datum = new ProjectDatum();
                        datum.setProjectName(response.body().getProjectData().get(i).getProjectName());
                        datum.setId(response.body().getProjectData().get(i).getId());
                        mylist.add(datum);
                    }

                    if(Paper.book().read("permission_wholeseller", "5").equals("1")) {

                        for (int i=0; i<projectlist.size();i++) {

                            ProjectDatum datum = new ProjectDatum();
                            datum.setProjectName(response.body().getProjectData().get(i).getProjectName());
                            variation_drop_project.add(response.body().getProjectData().get(i).getProjectName());
                        }
                    }

                    else {

                     for (int i=1; i<projectlist.size();i++) {

                        ProjectDatum datum = new ProjectDatum();
                        datum.setProjectName(response.body().getProjectData().get(i).getProjectName());
                        variation_drop_project.add(response.body().getProjectData().get(i).getProjectName());
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<ModelProjects> call, Throwable t) {

                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

}
