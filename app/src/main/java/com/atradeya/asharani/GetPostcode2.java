package com.atradeya.asharani;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetPostcode2 extends AppCompatActivity   implements LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    Button btn_submit,btn_search_post_city,btn_login_post_city;
    LinearLayout layout_city;
    List<CityList> myList;
    List<CityList> myCityList;
    Adapter_Select_City adapter_select_city;
    TextView tv_drop_city,txtvww_post_city;
    EditText edtxt_post_city;
    LinearLayout layout_gps;
    List<Wholselear> wholseller_list;
    List<Wholselear> my_list;
    Adapter_wholesellers adapter_wholesellers;
    FragmentTransaction fragmentTransaction;
    LocationManager locationManager;
    Boolean isInitialized = false, isStart = false, isFirstTimeLocationChange = true;
    Location mLastLocation;
    String provider;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private Criteria criteria;
    LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private double longitude;
    private double latitude;
    Location currLocation, prevLocation;
    Home home;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_city);

       /* btn_submit=findViewById(R.id.btn_submit_postcode);
        layout_city=findViewById(R.id.layout_city);
        tv_drop_city=findViewById(R.id.tv_drop_city);
        myList=new ArrayList<>();
*/
        /*********************************************** SETTING STATUS BAR WHITE ******************************************************************/

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        btn_search_post_city=findViewById(R.id.btn_search_post_city);
        btn_login_post_city=findViewById(R.id.btn_login_post_city);
        edtxt_post_city=findViewById(R.id.edtxt_post_city);
        txtvww_post_city=findViewById(R.id.txtvww_post_city);
        layout_gps=findViewById(R.id.layout_gps);
        my_list=new ArrayList<>();


        home = new Home();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();

        }

        layout_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TrackerGPS();

            }
        });

        btn_login_post_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });

        btn_search_post_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (!TextUtils.isEmpty(edtxt_post_city.getText().toString().trim())){

                   Log.e("amiter","1");
                   searchwholesalers(edtxt_post_city.getText().toString().trim());

               }
               else {
                   Toast.makeText(getApplicationContext(),"Please Enter Valid Postcode or City",Toast.LENGTH_SHORT).show();
               }
            }
        });

        /************************************************************End*****************************************************************************/

    }

    public void TrackerGPS() {

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();

        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        }

        else {

            getCurrentLocation();
        }
    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    // Checking if Google Play Services Available or not
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        0).show();
            }
            return false;
        }
        return true;
    }


    private void searchwholesalers(String postcity) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.WHOLESELLERS_CALL(postcity).enqueue(new Callback<ModelWholesellers>() {
            @SuppressLint("WrongConstant")
            @Override
            public void onResponse(Call<ModelWholesellers> call, Response<ModelWholesellers> response) {
                Log.e("amiter","2");

                Log.e("amiter",response.body().getStatusCode().toString());
                if(response.body().getStatusCode().equals(200)){


                    SharedPreferences pref = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("city", postcity);
                    editor.apply();

                    Paper.book().write("city",postcity);
                    Paper.book().write("postcity",postcity);
                    Paper.book().write("searchwholesalers","1");


                    Intent intent = new Intent(getApplicationContext(),Home.class);
                    startActivity(intent);
                }

                else {

                    Toast.makeText(GetPostcode2.this, "Please Enter Valid Postcode or City", Toast.LENGTH_SHORT).show();
                }

                txtvww_post_city.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<ModelWholesellers> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });


    }


    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;

        if (!mLastLocation.hasAccuracy()) {
            return;
        }
        if (mLastLocation.getAccuracy() > 5) {
            return;
        }

        if(isFirstTimeLocationChange){
            // build_retrofit_and_get_response();
            isFirstTimeLocationChange=false;
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

 /*       if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();

        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        }

        else {

            getCurrentLocation();
        }

        */
      //  getCurrentLocation();
       // getCurrentLocation();
       // getLocation();
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            //getting last known location
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
           // currMarker = setMarkerToLocation(mLastLocation);
            //setting location change listener
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    private void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if (location != null) {
            //Getting longitude and latitude
               longitude = location.getLongitude();
               latitude = location.getLatitude();

               Log.e("dfgh",longitude+"");
               Log.e("dfgh",latitude+"");


            txtvww_post_city.setVisibility(View.VISIBLE);
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(GetPostcode2.this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

                Log.e("geocoder_address",address+"");
                Log.e("geocoder_city",city+"");
                Log.e("geocoder_state",state+"");
                Log.e("geocoder_country",country+"");
                Log.e("geocoder_postalCode",postalCode+"");
                Log.e("geocoder_knownName",knownName+"");


                txtvww_post_city.setVisibility(View.VISIBLE);
                txtvww_post_city.setText(address);

                if (city != null){

                    edtxt_post_city.setText(city);
                    Paper.book().write("city",city);
                    Paper.book().write("postcity",city);
                }

                else {

                    edtxt_post_city.setText(postalCode);
                    Paper.book().write("city",postalCode);
                    Paper.book().write("postcity",postalCode);
                }


            } catch (IOException e) {
                e.printStackTrace();
            }




            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

            LocationManager locationManager = (LocationManager)
                    getSystemService(Context.LOCATION_SERVICE);
            String provider = locationManager.getBestProvider(new Criteria(), true);

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                return;
            }

        }

        else
            Toast.makeText(GetPostcode2.this, "No GPS Location found...Try Again!! or enter City/Postal Code", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    /*
     * In Android 6.0 Marshmallow, application will not be granted any permission at installation time.
     * Instead, application has to ask user for a permission one-by-one at runtime.
     * */

    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                /*
                 * Here I can add code to explain user that why this app require
                 * ACCESS_FINE_LOCATION permission if If the permission was denied previously
                 * */

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return true;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

}


