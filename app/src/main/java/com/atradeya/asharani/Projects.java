package com.atradeya.asharani;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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


public class Projects extends Fragment {

    Home home;
    List<ProjectDatum> projectData;
    List<ProjectDatum> mylist;

    List<GetAddress> AddressData;
    List<GetAddress> myaddress;

    Button btn_add;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    Adapter_projects adapter_projects;

    Adapter_Address adapter_address;
    LinearLayout layout_add_project;
    TextView txtvw_project;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_projects, container, false);

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    return true;
                }

                home.drawerLayout.openDrawer(Gravity.START);


                return false;
            }
        });

        mylist= new ArrayList<>();
        myaddress= new ArrayList<>();

        ((Home)getActivity()).hideView(true);
        home.nav_search_layout.setVisibility(View.VISIBLE);

        recyclerView=view.findViewById(R.id.projects_recyclervw);
        btn_add=view.findViewById(R.id.addprobtn);
        layout_add_project=view.findViewById(R.id.layout_add_project);
        txtvw_project = view.findViewById(R.id.txtvw_project);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);



        String address_tab = Paper.book().read("address_tab");

        if (address_tab != null  && address_tab.equals("1")){

            txtvw_project.setText("Addresses");
            btn_add.setText("Add Address");
            layout_add_project.setVisibility(View.VISIBLE);


            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent= new Intent(getActivity(),Add_Address.class);
                    startActivity(intent);


                }
            });

            getaddress();

        }

        else {


            if(Paper.book().read("datarole", "5").equals("4")){

                if(Paper.book().read("permission_wholeseller", "5").equals("1")) {

                    layout_add_project.setVisibility(View.GONE);
                }

                else {

                    Paper.book().write("layout_project_edit","0");
                    layout_add_project.setVisibility(View.VISIBLE);

                }
            }



            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent= new Intent(getActivity(),Project_details_add_project.class);
                    startActivity(intent);

                }
            });
            getprojects();

        }




        return view;
    }

    private void getaddress() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String Wholeseller_engineer_id= Paper.book().read("Wholeseller_engineer_id");

        service.GET_ADDRESS_CALL(Wholeseller_engineer_id).enqueue(new Callback<ModelGetAddress>() {
            @Override
            public void onResponse(Call<ModelGetAddress> call, Response<ModelGetAddress> response) {

                if(response.body().getStatusCode().equals(200)) {

                    AddressData = response.body().getAddress();

                    for (int i = 0; i < AddressData.size(); i++) {

                        GetAddress address = new GetAddress();
                        address.setId(response.body().getAddress().get(i).getId());
                        address.setCompanyName(response.body().getAddress().get(i).getCompanyName());
                        address.setFirstName(response.body().getAddress().get(i).getFirstName());
                        address.setLastName(response.body().getAddress().get(i).getLastName());
                        address.setAddressOne(response.body().getAddress().get(i).getAddressOne());
                        address.setAddressTwo(response.body().getAddress().get(i).getAddressTwo());
                        address.setCity(response.body().getAddress().get(i).getCity());
                        address.setPostcode(response.body().getAddress().get(i).getPostcode());

                        myaddress.add(address);
                    }

                    Paper.book().write("projects", myaddress);

                  //  mylist.remove(0);
                    adapter_address = new Adapter_Address(getActivity(), myaddress);
                    recyclerView.setAdapter(adapter_address);
                }
                else{

                    Toast.makeText(getActivity(),"Not Found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ModelGetAddress> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getprojects() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String userid= Paper.book().read("userid");

        service.PROJECTS_CALL(userid).enqueue(new Callback<ModelProjects>() {
            @Override
            public void onResponse(Call<ModelProjects> call, Response<ModelProjects> response) {

                if(response.body().getStatusCode().equals(200)) {

                    projectData = response.body().getProjectData();

                    for (int i = 0; i < projectData.size(); i++) {

                        ProjectDatum projectDatum = new ProjectDatum();
                        projectDatum.setId(response.body().getProjectData().get(i).getId());
                        projectDatum.setProjectName(response.body().getProjectData().get(i).getProjectName());
                        projectDatum.setAddress(response.body().getProjectData().get(i).getAddress());
                        projectDatum.setJobStatus(response.body().getProjectData().get(i).getJobStatus());
                        projectDatum.setEmailAddress(response.body().getProjectData().get(i).getEmailAddress());
                        projectDatum.setContactNumber(response.body().getProjectData().get(i).getContactNumber());
                        projectDatum.setCustomer(response.body().getProjectData().get(i).getCustomer());
                        projectDatum.setDeliveryAddress(response.body().getProjectData().get(i).getDeliveryAddress());
                        projectDatum.setAllotedEngineers(response.body().getProjectData().get(i).getAllotedEngineers());

                        mylist.add(projectDatum);
                    }

                    Paper.book().write("projects", mylist);

                    mylist.remove(0);
                    adapter_projects = new Adapter_projects(getActivity(), mylist);
                    recyclerView.setAdapter(adapter_projects);
                }
                else{

                    Toast.makeText(getActivity(),"Not Found", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ModelProjects> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void onAttach (Activity activity) {
        super.onAttach(activity);
        if(activity.getClass()== Home.class){
            home = (Home) activity;
        }
    }


}
