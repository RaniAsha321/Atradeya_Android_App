package com.atradeya.asharani;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.widget.Constraints.TAG;


class MyLocationListener  implements LocationListener {

    Context context;

    @Override
    public void onLocationChanged(Location loc) {
        Log.e("ankit",   " Lat: " + loc.getLatitude() + " Lng: "
                + loc.getLongitude());

        Toast.makeText(
                context,
                "Location changed: Lat: " + loc.getLatitude() + " Lng: "
                        + loc.getLongitude(), Toast.LENGTH_SHORT).show();
        String longitude = "Longitude: " + loc.getLongitude();
        Log.v(TAG, longitude);
        String latitude = "Latitude: " + loc.getLatitude();
        Log.v(TAG, latitude);
        Log.e("ankit","2");
        /*------- To get city name from coordinates -------- */
        String cityName = null;
        Geocoder gcd = new Geocoder(context, Locale.getDefault());
        List<Address> addresses;
        try {
            Log.e("ankit","3");
            addresses = gcd.getFromLocation(loc.getLatitude(),
                    loc.getLongitude(), 1);
            if (addresses.size() > 0) {
                System.out.println(addresses.get(0).getLocality());
                cityName = addresses.get(0).getLocality();
            }
        }
        catch (IOException e) {
            Log.e("ankit","4");
            e.printStackTrace();

        }
        String s = longitude + "\n" + latitude + "\n\nMy Current City is: "
                + cityName;

        Log.e("ankit",s+"");


    }

    @Override
    public void onProviderDisabled(String provider) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
}