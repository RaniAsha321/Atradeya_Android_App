package com.atradeya.asharani;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import io.paperdb.Paper;

public class Adapter_new_catalogue  extends RecyclerView.Adapter<Adapter_new_catalogue.ViewHolder> {

    Context mcontext;

    public Adapter_new_catalogue(Context context) {

        this.mcontext=context;
    }

    @NonNull
    @Override
    public Adapter_new_catalogue.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_catalogue_new, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_new_catalogue.ViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction;
                fragmentTransaction = ((AppCompatActivity) mcontext ).getSupportFragmentManager().beginTransaction().replace(R.id.containerr, new Catalogue_Promo_Images());
                fragmentTransaction.commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
