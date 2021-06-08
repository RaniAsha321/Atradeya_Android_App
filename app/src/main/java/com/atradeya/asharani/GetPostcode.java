package com.atradeya.asharani;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class GetPostcode extends AppCompatActivity  implements LocationListener {

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

        if (android.os.Build.VERSION.SDK_INT >= 21) {
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

        layout_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   GPSTracker gps = new GPSTracker(GetPostcode.this);
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();

                Log.e("latitudelongitude1",latitude+"");
                Log.e("latitudelongitude2",longitude+"");*/

                txtvww_post_city.setVisibility(View.VISIBLE);

                //  OnGPS();
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

                if(!provider.contains("gps")){ //if gps is disabled
                    final Intent poke = new Intent();
                    poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                    poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                    poke.setData(Uri.parse("3"));
                    sendBroadcast(poke);
                }

            //    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    OnGPS();
                }
                else if (Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                        // return;
                    } else {
                        getLocation();
                    }
/*

                } else {

                    Toast.makeText(getApplicationContext(),"Gps Status: Your Device's GPS is Disable",Toast.LENGTH_SHORT).show();
                   // alertbox("Gps Status", "Your Device's GPS is Disable", mContext);
                }
*/

               /* if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Log.e("locationManager","1");
                   // getLocation();
                   // OnGPS();
                } else {

                    Log.e("locationManager","2");
                    getLocation();
                }*/
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

      /*  layout_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getCity();
            }
        });


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(tv_drop_city.getText().toString().equals("Select City"))){

                    SharedPreferences pref = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("city", tv_drop_city.getText().toString());
                    editor.apply();

                    Intent intent= new Intent(getApplicationContext(),Next_Login_Page.class);
                    startActivity(intent);

                }

                else {

                    Toast.makeText(getApplicationContext(),"Please Select City", Toast.LENGTH_SHORT).show();

                }
            }
        });
*/
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:

                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                        criteria = new Criteria();
                        criteria.setAccuracy(Criteria.ACCURACY_FINE);


                        provider = locationManager.getBestProvider(criteria, true);
                        locationManager.requestLocationUpdates(provider, 400, 1, this);

                        getLocation();
                       // locationManager.requestLocationUpdates(provider, 400, 1, this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }
        }
    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                GetPostcode.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                GetPostcode.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);

        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double latitude = locationGPS.getLatitude();
                double longitude = locationGPS.getLongitude();

                Log.e("latitudelongitude1",latitude+"");
                Log.e("latitudelongitude1",longitude+"");

                txtvww_post_city.setVisibility(View.VISIBLE);
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(GetPostcode.this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();

                    txtvww_post_city.setVisibility(View.VISIBLE);
                    edtxt_post_city.setText(city);
                    txtvww_post_city.setText(address);
                    Paper.book().write("city",city);
                    Paper.book().write("postcity",city);

                } catch (IOException e) {
                    e.printStackTrace();
                }




            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
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
                    /*fragmentTransaction=getSupportFragmentManager().beginTransaction().replace(R.id.containerr,new Wholeseller());
                    fragmentTransaction.commit();*/
                }

                else {

                    Toast.makeText(GetPostcode.this, "Please Enter Valid Postcode or City", Toast.LENGTH_SHORT).show();
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
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

/*    private void getCity() {

        final ProgressDialog progressDialogs = new ProgressDialog(GetPostcode.this,R.style.AlertDialogCustom);
        progressDialogs.setCancelable(false);
        progressDialogs.setMessage("Please Wait.......");
        progressDialogs.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        final ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String userid= Paper.book().read("userid");

        service.MODEL_CITY_LIST_CALL(userid).enqueue(new Callback<ModelCityList>() {
            @Override
            public void onResponse(Call<ModelCityList> call, Response<ModelCityList> response) {

                if (response.body().getStatusCode().equals(200)) {

                    myCityList = response.body().getCityList();

                    for (int i = 0; i < myCityList.size(); i++) {

                        CityList cityList = new CityList();
                        cityList.setTown(response.body().getCityList().get(i).getTown());
                        myList.add(cityList);
                    }

                    final Dialog dialog= new Dialog(GetPostcode.this);
                    dialog.setContentView(R.layout.custom_dropdown_select_city);

                    EditText editText=dialog.findViewById(R.id.editTextSearch);
                    RecyclerView recyclerView=dialog.findViewById(R.id.recyclerView_city);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);

                    adapter_select_city=new Adapter_Select_City(GetPostcode.this,myList,dialog,tv_drop_city);
                    recyclerView.setAdapter(adapter_select_city);

                    //adding a TextChangedListener
                    //to call a method whenever there is some change on the EditText

                    TextWatcher edittw = new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                            filter(editable.toString());

                        }
                    };

                    editText.addTextChangedListener(edittw);

                    dialog.show();

                    if((Paper.book().read("selected_city")!=null)){

                        tv_drop_city.setText(Paper.book().read("selected_city"));

                    }

                    else {
                        tv_drop_city.setText("Select City");

                    }

                    progressDialogs.dismiss();
                }

            }

            @Override
            public void onFailure(Call<ModelCityList> call, Throwable t) {

                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
                progressDialogs.dismiss();
            }
        });

    }

    private void filter(String text) {

        //new array list that will hold the filtered data
        ArrayList<CityList> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (CityList s : myList) {
            //if the existing elements contains the search input
            if (s.getTown().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        adapter_select_city.filterList(filterdNames);

    }*/
}


