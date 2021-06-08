package com.atradeya.asharani;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.atradeya.asharani.Filter_POJO.Product;
import com.bumptech.glide.Glide;

import java.util.List;

import io.paperdb.Paper;

public class Adapter_Super_Filter extends RecyclerView.Adapter<Adapter_Super_Filter.ViewHolder> {

    Context mContext;
    List<Product> product;
    String publishkey;

    public Adapter_Super_Filter(List<com.atradeya.asharani.Filter_POJO.Product> products, Context mcontext, String publishkey) {

        this.product=products;
        this.mContext=mcontext;
        this.publishkey=publishkey;
    }
    @NonNull
    @Override
    public Adapter_Super_Filter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_super_category, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Super_Filter.ViewHolder viewHolder, int i) {

        viewHolder.product_name.setText(product.get(i).getTitle());

        Glide.with(mContext).load(product.get(i).getProductsImages()).into(viewHolder.product_image);

        if (!product.get(i).getProductsImages().equals("")){

            Glide.with(mContext).load(product.get(i).getProductsImages()).into(viewHolder.product_image);

        }

        viewHolder.price.setText(product.get(i).getExVat());

        if(Paper.book().read("permission_wholeseller", "5").equals("1")) {

            viewHolder.price.setText(product.get(i).getExVat());

        }

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction fragmentTransaction;
                fragmentTransaction=((AppCompatActivity)mContext).getSupportFragmentManager().beginTransaction().addToBackStack("mo").replace(R.id.containerr,new Product_Detail());
                fragmentTransaction.commit();

                Paper.book().write("whichbusiness",product.get(i).getBusinessId());
                Paper.book().write("getProductId",product.get(i).getId());
                Paper.book().write("product_name",product.get(i).getTitle());
                Paper.book().write("product_first_img",product.get(i).getProductsImages());
                Paper.book().write("product_second_img",product.get(i).getProductImageTwo());
                Paper.book().write("product_description",product.get(i).getDescription());
                Paper.book().write("product_specification",product.get(i).getSpecification());
                Paper.book().write("product_pdf_info",product.get(i).getReviews());
                Paper.book().write("product_price",product.get(i).getExVat());
                Paper.book().write("pro_inc_vat",product.get(i).getIncVat());
              //  Paper.book().write("pdf1",product.get(i).getpdf());
                Paper.book().write("pdftitle1",product.get(i).getPdfManual());
               // Paper.book().write("pdf2",product.get(i).getPdf2());
                Paper.book().write("pdftitle2",product.get(i).getPdfManual2());
               // Paper.book().write("pdf3",product.get(i).getPdf3());
                Paper.book().write("pdftitle3",product.get(i).getPdfManual3());
               // Paper.book().write("pdf4",product.get(i).getPdf4());
                Paper.book().write("pdftitle4",product.get(i).getPdfManual4());
               // Paper.book().write("pdf5",product.get(i).getPdf5());
                Paper.book().write("pdftitle5",product.get(i).getPdfManual5());
               // Paper.book().write("pdf6",product.get(i).getPdf6());
                Paper.book().write("pdftitle6",product.get(i).getPdfManual6());
                Paper.book().write("pdf_name1",product.get(i).getPdfName());
                Paper.book().write("pdf_name2",product.get(i).getPdf2Name());
                Paper.book().write("pdf_name3",product.get(i).getPdf3Name());
                Paper.book().write("pdf_name4",product.get(i).getPdf4Name());
                Paper.book().write("pdf_name5",product.get(i).getPdf5Name());
                Paper.book().write("pdf_name6",product.get(i).getPdf6Name());
                Paper.book().write("publish_key",publishkey);

            }
        });
    }

    @Override
    public int getItemCount() {
        return product.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView product_image;
        TextView product_name,price;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            price=itemView.findViewById(R.id.product_price);
            product_image=itemView.findViewById(R.id.imgview_super_category);
            product_name=itemView.findViewById(R.id.txt_product_name_super_category);
            cardView=itemView.findViewById(R.id.cardview_super_category);
        }
    }
}
