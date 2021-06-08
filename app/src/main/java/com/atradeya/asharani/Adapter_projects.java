package com.atradeya.asharani;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adapter_projects extends RecyclerView.Adapter<Adapter_projects.Viewholder> {
    Context mcontext;
    List<ProjectDatum> projectDatalist;

    public Adapter_projects(Context context, List<ProjectDatum> mylist) {

        this.mcontext=context;
        this.projectDatalist=mylist;
    }

    @NonNull
    @Override
    public Adapter_projects.Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_projects, viewGroup, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_projects.Viewholder viewholder, final int i) {

        viewholder.txt_project.setText(projectDatalist.get(i).getProjectName());
        viewholder.layout_project_onclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Paper.book().write("pro_name",projectDatalist.get(i).getProjectName());
                Paper.book().write("full_addres",projectDatalist.get(i).getAddress());
                Paper.book().write("delivery_addres",projectDatalist.get(i).getDeliveryAddress());
                Paper.book().write("job_statu",projectDatalist.get(i).getJobStatus());
                Paper.book().write("email_addres",projectDatalist.get(i).getEmailAddress());
                Paper.book().write("contact",projectDatalist.get(i).getContactNumber());
                Paper.book().write("customer_nam",projectDatalist.get(i).getCustomer());

                Intent intent= new Intent(mcontext,Project_details_already_added.class);
                mcontext.startActivity(intent);
            }
        });

        viewholder.layout_project_bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                removeProject(projectDatalist.get(i).getId());
                projectDatalist.remove(i);
                notifyDataSetChanged();
            }
        });

        viewholder.layout_project_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Paper.book().write("pro_id_update",projectDatalist.get(i).getId());
                Paper.book().write("pro_name",projectDatalist.get(i).getProjectName());
                Paper.book().write("full_addres",projectDatalist.get(i).getAddress());
                Paper.book().write("delivery_addres",projectDatalist.get(i).getDeliveryAddress());
                Paper.book().write("job_statu",projectDatalist.get(i).getJobStatus());
                Paper.book().write("email_addres",projectDatalist.get(i).getEmailAddress());
                Paper.book().write("contact",projectDatalist.get(i).getContactNumber());
                Paper.book().write("customer_nam",projectDatalist.get(i).getCustomer());
                Paper.book().write("alloted_engineers",projectDatalist.get(i).getAllotedEngineers());

                Intent intent= new Intent(mcontext,Project_details_add_project.class);
                mcontext.startActivity(intent);
                Paper.book().write("layout_project_edit","1");

            }

        });

        String address_tab = Paper.book().read("address_tab");

        if (address_tab != null  && address_tab.equals("1")){

            viewholder.layout_project_edit_bin.setVisibility(View.VISIBLE);

        }

        else {



            if(!(Paper.book().read("datarole", "5").equals("4"))){

                if(Paper.book().read("permission_wholeseller", "5").equals("1")) {

                    viewholder.layout_project_edit_bin.setVisibility(View.VISIBLE);
                }

                else {

                    viewholder.layout_project_edit_bin.setVisibility(View.GONE);

                }

            }

            else {

                viewholder.layout_project_edit_bin.setVisibility(View.GONE);

            }

        }




    }

    private void removeProject(String id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.Remove_Project(id).enqueue(new Callback<ModelRemoveProject>() {
            @Override
            public void onResponse(Call<ModelRemoveProject> call, Response<ModelRemoveProject> response) {

                if(response.body().getStatusCode().equals(200)){

                    Toast.makeText(mcontext,"Project Removed Successfully", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(mcontext,"Error Occured", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ModelRemoveProject> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mcontext,t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return projectDatalist.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView txt_project;
        LinearLayout layout_project_onclick,layout_project_edit_bin,layout_project_edit,layout_project_bin;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            txt_project=itemView.findViewById(R.id.project_name_row);
            layout_project_onclick=itemView.findViewById(R.id.layout_project_onclick);
            layout_project_edit_bin=itemView.findViewById(R.id.layout_project_edit_bin);
            layout_project_edit=itemView.findViewById(R.id.layout_project_edit);
            layout_project_bin=itemView.findViewById(R.id.layout_project_bin);

        }
    }
}

