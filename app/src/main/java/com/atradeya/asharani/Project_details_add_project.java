package com.atradeya.asharani;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Project_details_add_project extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener{

    String[] code,eng_id,addressList,DeliveryList,projectlist2,temp,mlist;;
    Home home;
    Projects projects;
    EditText proj_name,customer_name,full_address,delivery_address,contact_no,email_address,city,post_code,delivery_city,delivery_post_code;
    Button add_project;
    List<Oprativedatum> DatumList;
    List<Oprativedatum> AssignData;
    List<String[]> mylist;
    ArrayList<CheckBox> updatelist;
    MultiSelectionSpinner spinner_assign;
    LinearLayout layout_assign_engineer;
    static LinearLayout drop_update;
    String pro_name,customer_nam,full_addres,delivery_addres,contact,email_addres;
    FragmentTransaction fragmentTransaction;
    RecyclerView recyclerview_update_project;
    LinearLayoutManager layoutManager;
    Adapter_Update_project adapter_update_project;
    ListView my_list;
    List<Oprativedatum> myupdate;
    static MultiSelectionSpinner multiSelectSpinner;
    static TextView tv_drop_assign_eng;
    TextView txt_update_clear;
    TextView txt_update_cancel;
    TextView txt_update_submit;
    static String normorTrx;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.project_details_add_project);

        proj_name=findViewById(R.id.project_names_ad);
        customer_name=findViewById(R.id.customer_name_add);
        full_address=findViewById(R.id.address_add);
        delivery_address=findViewById(R.id.delivery_address_add);
        contact_no=findViewById(R.id.contact_add);
        email_address=findViewById(R.id.project_email_add);
        add_project=findViewById(R.id.btn_create_project);
        city=findViewById(R.id.city_add);
        post_code=findViewById(R.id.post_code_add);
        delivery_city=findViewById(R.id.delivery_city_add);
        delivery_post_code=findViewById(R.id.delivery_post_code_add);
        spinner_assign=findViewById(R.id.mySpinner1);
        layout_assign_engineer=findViewById(R.id.layout_assign_engineer);
        drop_update=findViewById(R.id.drop_update);
        tv_drop_assign_eng=findViewById(R.id.tv_drop_assign_eng);

        mylist=new ArrayList<String[]>();
        updatelist=new ArrayList<CheckBox>();
        mlist= new String[0];

        AssignData=new ArrayList<>();
        myupdate=new ArrayList<>();

        projects= new Projects();

        if(Paper.book().read("layout_project_edit","5").equals("1")){

            add_project.setText("Update Project");

            full_addres =Paper.book().read("full_addres");
            delivery_addres=Paper.book().read("delivery_addres");

            addressList = full_addres.split(",");
            DeliveryList = delivery_addres.split(",");
            Paper.book().read("alloted_engineers");
            proj_name.setText(Paper.book().read("pro_name"));
            customer_name.setText(Paper.book().read("customer_nam"));
            email_address.setText(Paper.book().read("email_addres"));
            contact_no.setText(Paper.book().read("contact"));
            full_address.setText(addressList [0]);
            city.setText(addressList [1]);
            post_code.setText(addressList [2]);
            delivery_address.setText(DeliveryList [0]);
            delivery_city.setText(DeliveryList [1]);
            delivery_post_code.setText(DeliveryList [2]);


            String userid= Paper.book().read("userid");

            Paper.book().write("engineer",userid);

            assign_engineer();

            add_project.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if((add_project.getText().equals("Update Project "))) {

                            Paper.book().write("dataset", "1");

                        }
                       // Paper.book().write("dataset", "1");

                        if (!TextUtils.isEmpty(proj_name.getText().toString())) {

                            if (!TextUtils.isEmpty(customer_name.getText().toString())) {

                                if (!TextUtils.isEmpty(full_address.getText().toString())) {

                                    if (!TextUtils.isEmpty(city.getText().toString())) {

                                        if (!TextUtils.isEmpty(post_code.getText().toString())) {

                                            if (!TextUtils.isEmpty(delivery_address.getText().toString())) {

                                                if (!TextUtils.isEmpty(delivery_city.getText().toString())) {

                                                    if (!TextUtils.isEmpty(delivery_post_code.getText().toString())) {

                                                        if (!TextUtils.isEmpty(contact_no.getText().toString())) {

                                                            if (!TextUtils.isEmpty(email_address.getText().toString())) {

                                                                if (validateSpinner(spinner_assign)) {

                                                                    Paper.book().write("layout_project_edit","0");


                                                                    jsonupdate1();

                                                                } else {

                                                                    Toast.makeText(getApplicationContext(), " Assign Engineers", Toast.LENGTH_SHORT).show();

                                                                }

                                                            } else {

                                                                Toast.makeText(getApplicationContext(), " Enter Email", Toast.LENGTH_SHORT).show();

                                                            }

                                                        } else {

                                                            Toast.makeText(getApplicationContext(), " Enter Contact No", Toast.LENGTH_SHORT).show();

                                                        }

                                                    } else {

                                                        Toast.makeText(getApplicationContext(), " Enter Delivery Post Code", Toast.LENGTH_SHORT).show();

                                                    }

                                                } else {

                                                    Toast.makeText(getApplicationContext(), " Enter Delivery City", Toast.LENGTH_SHORT).show();

                                                }

                                            } else {

                                                Toast.makeText(getApplicationContext(), " Enter Delivery Address", Toast.LENGTH_SHORT).show();

                                            }

                                        } else {

                                            Toast.makeText(getApplicationContext(), " Enter Post code", Toast.LENGTH_SHORT).show();

                                        }

                                    } else {

                                        Toast.makeText(getApplicationContext(), " Enter City", Toast.LENGTH_SHORT).show();

                                    }

                                } else {

                                    Toast.makeText(getApplicationContext(), " Enter Full Address", Toast.LENGTH_SHORT).show();
                                }

                            } else {

                                Toast.makeText(getApplicationContext(), " Enter Customer Name", Toast.LENGTH_SHORT).show();

                            }

                        } else {

                            Toast.makeText(getApplicationContext(), "Enter Project Name", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
        }

        else

                if ((Paper.book().read("datarole", "5").equals("4"))) {

                    if (Paper.book().read("permission_wholeseller", "5").equals("1")) {

                        String userid = Paper.book().read("userid");

                        layout_assign_engineer.setVisibility(View.GONE);

                        Paper.book().write("engineer", userid);

                        add_project.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Paper.book().write("layout_project_edit", "0");

                                if (!TextUtils.isEmpty(proj_name.getText().toString())) {

                                    if (!TextUtils.isEmpty(customer_name.getText().toString())) {

                                        if (!TextUtils.isEmpty(full_address.getText().toString())) {

                                            if (!TextUtils.isEmpty(city.getText().toString())) {

                                                if (!TextUtils.isEmpty(post_code.getText().toString())) {

                                                    if (!TextUtils.isEmpty(delivery_address.getText().toString())) {

                                                        if (!TextUtils.isEmpty(delivery_city.getText().toString())) {

                                                            if (!TextUtils.isEmpty(delivery_post_code.getText().toString())) {

                                                                if (!TextUtils.isEmpty(contact_no.getText().toString())) {

                                                                    if (!TextUtils.isEmpty(email_address.getText().toString())) {

                                                                        json_wholeseller();

                                                                    } else {

                                                                        Toast.makeText(getApplicationContext(), " Enter Email", Toast.LENGTH_SHORT).show();

                                                                    }

                                                                } else {

                                                                    Toast.makeText(getApplicationContext(), " Enter Contact No", Toast.LENGTH_SHORT).show();

                                                                }

                                                            } else {

                                                                Toast.makeText(getApplicationContext(), " Enter Delivery Post Code", Toast.LENGTH_SHORT).show();

                                                            }

                                                        } else {

                                                            Toast.makeText(getApplicationContext(), " Enter Delivery City", Toast.LENGTH_SHORT).show();

                                                        }

                                                    } else {

                                                        Toast.makeText(getApplicationContext(), " Enter Delivery Address", Toast.LENGTH_SHORT).show();

                                                    }

                                                } else {

                                                    Toast.makeText(getApplicationContext(), " Enter Post code", Toast.LENGTH_SHORT).show();

                                                }

                                            } else {

                                                Toast.makeText(getApplicationContext(), " Enter City", Toast.LENGTH_SHORT).show();

                                            }

                                        } else {

                                            Toast.makeText(getApplicationContext(), " Enter Full Address", Toast.LENGTH_SHORT).show();
                                        }

                                    } else {

                                        Toast.makeText(getApplicationContext(), " Enter Customer Name", Toast.LENGTH_SHORT).show();

                                    }

                                } else {

                                    Toast.makeText(getApplicationContext(), "Enter Project Name", Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                    }
                }


    else {

                    add_project.setText("Create Project");

                    assign_engineer();

            add_project.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (!TextUtils.isEmpty(proj_name.getText().toString())) {

                        if (!TextUtils.isEmpty(customer_name.getText().toString())) {

                            if (!TextUtils.isEmpty(full_address.getText().toString())) {

                                if (!TextUtils.isEmpty(city.getText().toString())) {

                                    if (!TextUtils.isEmpty(post_code.getText().toString())) {

                                        if (!TextUtils.isEmpty(delivery_address.getText().toString())) {

                                            if (!TextUtils.isEmpty(delivery_city.getText().toString())) {

                                                if (!TextUtils.isEmpty(delivery_post_code.getText().toString())) {

                                                    if (!TextUtils.isEmpty(contact_no.getText().toString())) {

                                                        if (!TextUtils.isEmpty(email_address.getText().toString())) {

                                                            if (validateSpinner(spinner_assign)) {

                                                                json();

                                                            } else {

                                                                Toast.makeText(getApplicationContext(), " Assign Engineers", Toast.LENGTH_SHORT).show();

                                                            }

                                                        } else {

                                                            Toast.makeText(getApplicationContext(), " Enter Email", Toast.LENGTH_SHORT).show();

                                                        }

                                                    } else {

                                                        Toast.makeText(getApplicationContext(), " Enter Contact No", Toast.LENGTH_SHORT).show();

                                                    }

                                                } else {

                                                    Toast.makeText(getApplicationContext(), " Enter Delivery Post Code", Toast.LENGTH_SHORT).show();

                                                }

                                            } else {

                                                Toast.makeText(getApplicationContext(), " Enter Delivery City", Toast.LENGTH_SHORT).show();

                                            }

                                        } else {

                                            Toast.makeText(getApplicationContext(), " Enter Delivery Address", Toast.LENGTH_SHORT).show();

                                        }

                                    } else {

                                        Toast.makeText(getApplicationContext(), " Enter Post code", Toast.LENGTH_SHORT).show();

                                    }

                                } else {

                                    Toast.makeText(getApplicationContext(), " Enter City", Toast.LENGTH_SHORT).show();

                                }

                            } else {

                                Toast.makeText(getApplicationContext(), " Enter Full Address", Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            Toast.makeText(getApplicationContext(), " Enter Customer Name", Toast.LENGTH_SHORT).show();

                        }

                    } else {

                        Toast.makeText(getApplicationContext(), "Enter Project Name", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }


}
private boolean validateSpinner(MultiSelectionSpinner spinner_assign) {


        View selectedView = spinner_assign.getSelectedView();
        if (selectedView != null && selectedView instanceof TextView) {
            TextView selectedTextView = (TextView) selectedView;

            if (selectedTextView.getText().equals("Assign Engineers")) {

                return false;
            }

        }
        return true;

    }

    private void assign_engineer() {

        Paper.book().write("dataset", "1");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String userid = Paper.book().read("userid");

        service.ASSIGN_ENGINEER_CALL(userid).enqueue(new Callback<AssignEngineer>() {
            @Override
            public void onResponse(Call<AssignEngineer> call, Response<AssignEngineer> response) {
                multiSelectSpinner = (MultiSelectionSpinner) findViewById(R.id.mySpinner1);
                if (response.body().getStatusCode().equals(200)) {
                    DatumList = response.body().getOprativedata();
                    eng_id=new String[DatumList.size()];
                    code = new String[DatumList.size()];

                    for (int i = 0; i < DatumList.size(); i++) {

                        code[i]=DatumList.get(i).getName();
                        eng_id[i]=DatumList.get(i).getId();

                    }

                    for (int i = 1; i < DatumList.size(); i++){

                        Oprativedatum project=new Oprativedatum();
                        project.setId(DatumList.get(i).getId());
                        project.setName(DatumList.get(i).getName());

                        AssignData.add(project);

                    }

                    if(Paper.book().read("layout_project_edit","").equals("1")) {
                        multiSelectSpinner.setVisibility(View.GONE);
                        drop_update.setVisibility(View.VISIBLE);
                        String projectlist = Paper.book().read("alloted_engineers");

                        tv_drop_assign_eng.setText(projectlist);


                        drop_update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Dialog dialog= new Dialog(Project_details_add_project.this,R.style.AlertDialogCustom);
                                dialog.setContentView(R.layout.custom_dialog_update_project);

                                txt_update_cancel=dialog.findViewById(R.id.txt_update_cancel);
                                txt_update_submit=dialog.findViewById(R.id.txt_update_submit);

                                RecyclerView recyclerView=dialog.findViewById(R.id.recyclerview_update);
                                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                                recyclerView.setLayoutManager(layoutManager);

                                Log.e("data",AssignData.toString()+"");

                                adapter_update_project=new Adapter_Update_project(getApplicationContext(),AssignData,dialog);
                                recyclerView.setAdapter(adapter_update_project);


                                dialog.show();

                            }
                        });
                    }
                    else {

                        multiSelectSpinner.setVisibility(View.VISIBLE);

                    }

                    multiSelectSpinner.setItemsid(eng_id);
                    multiSelectSpinner.setItems(code);
                    multiSelectSpinner.hasNoneOption(true);
                    multiSelectSpinner.setSelection(new int[]{0});


                }
            }

            @Override
            public void onFailure(Call<AssignEngineer> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }

    private void updateProject(String toString) {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiLogin_Interface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            //Defining retrofit api service
            ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

            service.UPDATE_PROJECT_CALL(toString).enqueue(new Callback<ModelAddProject>() {
                @Override
                public void onResponse(Call<ModelAddProject> call, Response<ModelAddProject> response) {

                    if(response.body().getStatusCode().equals(200)){

                        Toast.makeText(getApplicationContext(),"Project Updated Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(Project_details_add_project.this,Home.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Change something", Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<ModelAddProject> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

    }

    private void json_wholeseller() {

        JSONObject order1 = new JSONObject();

        try {
            order1.put("assign_engineer",Paper.book().read("engineer"));
            order1.put("engineer_id",Paper.book().read("userid"));
            order1.put("project_Name", proj_name.getText().toString().trim());
            order1.put("customer_name", customer_name.getText().toString().trim());
            order1.put("full_address", full_address.getText().toString().trim());
            order1.put("delivery_address", delivery_address.getText().toString().trim());
            order1.put("contact_no", contact_no.getText().toString().trim());
            order1.put("email_address", email_address.getText().toString().trim());
            order1.put("city",city.getText().toString().trim());
            order1.put("post_code",post_code.getText().toString().trim());
            order1.put("delivery_city",delivery_city.getText().toString().trim());
            order1.put("delivery_post_code",delivery_post_code.getText().toString().trim());


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        JSONArray jsonArray= new JSONArray();
        jsonArray.put(order1);

        JSONObject orderobj = new JSONObject();
        try {
            orderobj.put("Orderdetails", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("whole",orderobj.toString()+"");
        addProject(orderobj.toString());

    }

    private void json() {

        JSONObject order1 = new JSONObject();

        try {

            order1.put("assign_engineer",Paper.book().read("engineer"));
            order1.put("engineer_id",Paper.book().read("userid"));
            order1.put("project_Name", proj_name.getText().toString().trim());
            order1.put("customer_name", customer_name.getText().toString().trim());
            order1.put("full_address", full_address.getText().toString().trim());
            order1.put("delivery_address", delivery_address.getText().toString().trim());
            order1.put("contact_no", contact_no.getText().toString().trim());
            order1.put("email_address", email_address.getText().toString().trim());
            order1.put("city",city.getText().toString().trim());
            order1.put("post_code",post_code.getText().toString().trim());
            order1.put("delivery_city",delivery_city.getText().toString().trim());
            order1.put("delivery_post_code",delivery_post_code.getText().toString().trim());


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        JSONArray jsonArray= new JSONArray();
        jsonArray.put(order1);

        JSONObject orderobj = new JSONObject();
        try {
            orderobj.put("Orderdetails", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("whole",orderobj.toString()+"");
        addProject(orderobj.toString());

    }

    private void jsonupdate1() {

        JSONObject order1 = new JSONObject();

        try {

            Log.e("finalString",Paper.book().read("finalString")+"");

            order1.put("id",Paper.book().read("pro_id_update"));
            order1.put("assign_engineer",tv_drop_assign_eng.getText().toString());
            Paper.book().write("finalString","");
            Paper.book().write("check","");
            order1.put("engineer_id",Paper.book().read("userid"));
            order1.put("project_Name", proj_name.getText().toString().trim());
            order1.put("customer_name", customer_name.getText().toString().trim());
            order1.put("full_address", full_address.getText().toString().trim());
            order1.put("delivery_address", delivery_address.getText().toString().trim());
            order1.put("contact_no", contact_no.getText().toString().trim());
            order1.put("email_address", email_address.getText().toString().trim());
            order1.put("city",city.getText().toString().trim());
            order1.put("post_code",post_code.getText().toString().trim());
            order1.put("delivery_city",delivery_city.getText().toString().trim());
            order1.put("delivery_post_code",delivery_post_code.getText().toString().trim());


        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }

        JSONArray jsonArray= new JSONArray();
        jsonArray.put(order1);

        JSONObject orderobj = new JSONObject();
        try {
            orderobj.put("Orderdetails", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("uwhole",orderobj.toString()+"");
        updateProject(orderobj.toString());

    }

    private void addProject(String cart_string) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.ADD_PROJECT_CALL(cart_string).enqueue(new Callback<ModelAddProject>() {
            @Override
            public void onResponse(Call<ModelAddProject> call, Response<ModelAddProject> response) {

                if(response.body().getStatusCode().equals(200)){

                    Toast.makeText(getApplicationContext(),"Project Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Project_details_add_project.this,Home.class);
                    startActivity(intent);
                }
                else
                    {
                    Toast.makeText(getApplicationContext(),"Error Occured", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ModelAddProject> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {

        Toast.makeText(this.getApplicationContext(),"Selected Engineers" + strings, Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        Paper.book().write("layout_project_edit", "0");
        Paper.book().write("finalString", "");

        // code here to show dialog

       super.onBackPressed();
    }

}

