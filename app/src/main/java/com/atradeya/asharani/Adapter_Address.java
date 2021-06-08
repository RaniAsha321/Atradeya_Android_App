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
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Adapter_Address extends RecyclerView.Adapter<Adapter_Address.Viewholder> {

    Context mcontext;
    List<GetAddress> myAddressList;

    public Adapter_Address(Context context, List<GetAddress> myaddress) {

        this.mcontext=context;
        this.myAddressList = myaddress;

    }

    @NonNull
    @Override
    public Adapter_Address.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_projects, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Address.Viewholder holder, int position) {

        String Address =myAddressList.get(position).getAddressOne().trim() +" , "+ myAddressList.get(position).getAddressTwo().trim() +" , "+myAddressList.get(position).getCity().trim()  +" , "+myAddressList.get(position).getPostcode().trim()  ;

        holder.txt_project.setText(Address);

        holder.layout_project_edit_bin.setVisibility(View.GONE);

        holder.layout_project_onclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Paper.book().write("address_id",myAddressList.get(position).getId());
                Paper.book().write("address_company_name",myAddressList.get(position).getCompanyName());
                Paper.book().write("address_first_name",myAddressList.get(position).getFirstName());
                Paper.book().write("address_last_name",myAddressList.get(position).getLastName());
                Paper.book().write("address_address1",myAddressList.get(position).getAddressOne());
                Paper.book().write("address_address2",myAddressList.get(position).getAddressTwo());
                Paper.book().write("address_city",myAddressList.get(position).getCity());
                Paper.book().write("address_postcode",myAddressList.get(position).getPostcode());

                Intent intent= new Intent(mcontext,Address_Already_Added.class);
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return myAddressList.size();
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
