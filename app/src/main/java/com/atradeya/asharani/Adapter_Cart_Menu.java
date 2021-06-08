package com.atradeya.asharani;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import io.paperdb.Paper;

public class Adapter_Cart_Menu extends RecyclerView.Adapter<Adapter_Cart_Menu.ViewHolder> {

    Cart_Menu cart_menu;
    databaseSqlite database;
    Context mycontext;
    List<ModelCartMenu> cartMenuList;
    private MyInterface listener;

    public Adapter_Cart_Menu(Context context, List<ModelCartMenu> modelCartMenus, Cart_Menu c, MyInterface listener) {

        this.mycontext=context;
        this.cartMenuList=modelCartMenus;
        this.listener = listener;
        this.cart_menu=c;

        database=new databaseSqlite(mycontext);
    }

    public Adapter_Cart_Menu(Context cart_size) {

    }

    @NonNull
    @Override
    public Adapter_Cart_Menu.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.demo, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        viewHolder.cart_menu_productname.setText(cartMenuList.get(i).getProductname());
        viewHolder.cart_menu_quantity.setText(cartMenuList.get(i).getQuantity());
        final ModelCartMenu modelCartMenu=cartMenuList.get(i);
        Paper.book().write("modelcart",cartMenuList.get(i));
        Paper.book().write("pro_id",cartMenuList.get(i).getProductid());

        if((cartMenuList.get(i).getProductid()).equals("0") ){
            Glide.with(mycontext).load(R.drawable.add_product_cart_image).into(viewHolder.cart_menu_Image);
        }
        else {
            Glide.with(mycontext).load(cartMenuList.get(i).getProductimage()).into(viewHolder.cart_menu_Image);
        }

        viewHolder.cart_menu_price.setText(String.format("%.2f", Double.valueOf(Double.valueOf(cartMenuList.get(i).getPrice())*(Integer.valueOf(cartMenuList.get(i).getQuantity())))));

       /* if(Paper.book().read("permission_see_cost","2").equals("1")){
            viewHolder.cart_menu_price.setText(String.format("%.2f", Double.valueOf(Double.valueOf(cartMenuList.get(i).getPrice())*(Integer.valueOf(cartMenuList.get(i).getQuantity())))));
        }

        else {
            viewHolder.cart_menu_price.setText("0.00");

        }

        if(Paper.book().read("permission_wholeseller", "5").equals("1")) {
            viewHolder.cart_menu_price.setText(String.format("%.2f", Double.valueOf(Double.valueOf(cartMenuList.get(i).getPrice())*(Integer.valueOf(cartMenuList.get(i).getQuantity())))));

        }*/

        if (!(cartMenuList.get(i).getVariation_name().equals(""))){

            viewHolder.variation_slashs.setText("/");
            viewHolder.variation_names.setText(cartMenuList.get(i).getVariation_name());
        }

        viewHolder.Img_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int value= Integer.parseInt(cartMenuList.get(i).getQuantity());

                if (!(value ==1)) {
                    value = value - 1;

                    cartMenuList.get(i).setQuantity(String.valueOf(value));
                    modelCartMenu.setQuantity(String.valueOf(value));
                    ModelCartMenu modelCartMenu1 = database.getModelVCartMenu(cartMenuList.get(i).getProductid(),cartMenuList.get(i).getVariationid());
                    modelCartMenu1.setQuantity(String.valueOf(value));
                    database.updateVNote(modelCartMenu1);
                    notifyDataSetChanged();
                    listener.foo();
                }
                else

                {
                    Toast.makeText(mycontext,"Select Atleast 1 quantity", Toast.LENGTH_SHORT).show();

                }
            }

        });

        viewHolder.Image_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int value= Integer.parseInt(cartMenuList.get(i).getQuantity());
                    value = value +1;

                cartMenuList.get(i).setQuantity(String.valueOf(value));

                ModelCartMenu modelCartMenu1= database.getModelVCartMenu(cartMenuList.get(i).getProductid(),cartMenuList.get(i).getVariationid());
                modelCartMenu1.setQuantity(String.valueOf(value));
                database.updateVNote(modelCartMenu1);
                modelCartMenu.setQuantity(String.valueOf(value));
                notifyDataSetChanged();
                listener.foo();

            }
        });

        viewHolder.Img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database.deleteNote(modelCartMenu);
                cartMenuList.remove(i);
                notifyDataSetChanged();
                listener.foo();
                listener.updateCart();
                mycontext.sendBroadcast(new Intent(DatabaseChangedReceiver.ACTION_DATABASE_CHANGED));

                if(database.getNotesCount()==0){

                    FragmentTransaction fragmentTransaction;
                    fragmentTransaction = ((AppCompatActivity) mycontext).getSupportFragmentManager().beginTransaction().addToBackStack("home_category").replace(R.id.containerr, new EmptyCart());;
                    fragmentTransaction.commit();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartMenuList.size();
    }

    public void delete(int position) { //removes the row
        cartMenuList.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView cart_menu_Image,Img_edit,Img_delete,Img_minus,Image_plus;
        TextView cart_menu_productname,cart_menu_productid,cart_menu_price,cart_menu_quantity,variation_names,variation_slashs;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cart_menu=new Cart_Menu();
            Image_plus=itemView.findViewById(R.id.Img_plus);
            Img_minus=itemView.findViewById(R.id.Img_minus);
            cart_menu_Image=itemView.findViewById(R.id.cart_menu_image);
            cart_menu_productname=itemView.findViewById(R.id.cart_menu_productname);
            cart_menu_price=itemView.findViewById(R.id.cart_menu_price);
            cart_menu_quantity=itemView.findViewById(R.id.cart_menu_quantity);
            cardView=itemView.findViewById(R.id.cart_cardview);
            Img_delete=itemView.findViewById(R.id.itemdelete);
            variation_names=itemView.findViewById(R.id.variation_names);
            variation_slashs=itemView.findViewById(R.id.varslashs);

        }

        @Override
        public void onClick(View v) {
            delete(getAdapterPosition());
        }
    }

}

