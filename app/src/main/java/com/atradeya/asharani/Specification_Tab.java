package com.atradeya.asharani;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import io.paperdb.Paper;


public class Specification_Tab extends Fragment {

    TextView specification;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_specification__tab, container, false);

        specification=view.findViewById(R.id.specification);

        String spec= Paper.book().read("product_specification");
        specification.setText(Html.fromHtml(spec));

        return view;
    }

}
