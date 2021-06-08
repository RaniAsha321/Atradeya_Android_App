package com.atradeya.asharani;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class Adapter_Viewpager extends FragmentStatePagerAdapter {

    int tabcount;

    public Adapter_Viewpager(FragmentManager fragmentManager, int tabCount) {

        super(fragmentManager);
        this.tabcount=tabCount;

    }

    @Override
    public Fragment getItem(int i) {

        switch ( i) {

            case 0:
        {
            return new Description_Tab();
        }
            case 1:
        {
            return new Specification_Tab();
        }
            case 2:
        {
            return new Pdf_indo_Tab();
        }

        default:

        return null;

      }
    }

    @Override
    public int getCount() {
        return tabcount;
    }

}
