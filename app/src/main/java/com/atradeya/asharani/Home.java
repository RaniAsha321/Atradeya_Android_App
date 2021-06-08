package com.atradeya.asharani;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.navigation.NavigationView;
import com.atradeya.asharani.Payment_Stripe.AddPayment;

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

import static com.atradeya.asharani.GetPostcode.MY_PERMISSIONS_REQUEST_LOCATION;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,MyInterface, Nav_Drawer , LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static final String MY_PREFS_NAME = "MyPrefsFile";

    GetPostcode2 getPostcode2 = new GetPostcode2();
    AddPayment addPayment;
    LinearLayout Search_postcode_layout,Search_product_layout,layout_search_view;
    Orders_Menu sizedata;
    int awaiting_data;
    TextView heading;
    databaseSqlite databaseSqlite;
    Adapter_Cart_Menu adapterCartMenu;
    Search_items items;
    EditText search_txtvw,search_postcode;
    List<ModelCartMenu> modelCartMenuList;
    List<String> mydatalist;
    LinearLayout search_layout,idlayout,nav_search_layout;
    Intent intent;
    Context context;
    ImageView menuIcon,Img_cart,Img_Search_icon,img_icon;
    public static DrawerLayout drawerLayout;
    NavigationView navigationView;
    public FragmentTransaction fragmentTransaction;
    TextView cart_size,txt_blink,navUsername;
    List<Category> intentmodelProductsCategories;
    List<Category> modelProductsCategories;
    Animation animation;
    Wholeseller wholeseller;
    List<CityList> myList;
    List<CityList> myCityList;
    Adapter_Select_City adapter_select_city;
    SharedPreferences sharedPreferences;
    String userid,provider;
    LinearLayout layout_home_filter,layout_gps_home;
    LinearLayout navsearchlayout,layout_nav_address,popup_register_cross,popup_signin,popup_signup,layout_nav_cat,layout_nav_wholeseller, layout_nav_order,layout_nav_account,layout_nav_catalogue,layout_nav_login,layout_nav_logout;
    TextView txtvw_nav_address,txtvw_nav_cat,txtvw_nav_wholeseller,txtvw_nav_order,txtvw_nav_account,txtvw_nav_catalogue,txtvw_nav_login,txtvw_nav_logout;
    ImageView imgvw_nav_address,imgvw_nav_cat,imgvw_nav_wholeseller,imgvw_nav_order,imgvw_nav_account,imgvw_nav_catalogue,imgvw_nav_login,imgvw_nav_logout;
    TextView txtvw_filter_home_sub_cat,textview_version;
    LocationManager locationManager;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private double longitude;
    private double latitude;
    Boolean isInitialized = false, isStart = false, isFirstTimeLocationChange = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imgvw_nav_cat = findViewById(R.id.imgvw_nav_cat);
        textview_version = findViewById(R.id.textview_version);
        imgvw_nav_wholeseller = findViewById(R.id.imgvw_nav_wholeseller);
        imgvw_nav_order = findViewById(R.id.imgvw_nav_order);
        imgvw_nav_account = findViewById(R.id.imgvw_nav_account);
        imgvw_nav_catalogue = findViewById(R.id.imgvw_nav_catalogue);
        imgvw_nav_login = findViewById(R.id.imgvw_nav_login);
        imgvw_nav_address = findViewById(R.id.imgvw_nav_address);
        imgvw_nav_logout = findViewById(R.id.imgvw_nav_logout);
        layout_home_filter = findViewById(R.id.layout_home_filter);
        txtvw_nav_cat = findViewById(R.id.txtvw_nav_cat);
        txtvw_nav_wholeseller = findViewById(R.id.txtvw_nav_wholeseller);
        txtvw_nav_order = findViewById(R.id.txtvw_nav_order);
        txtvw_nav_account = findViewById(R.id.txtvw_nav_account);
        txtvw_filter_home_sub_cat = findViewById(R.id.txtvw_filter_home_sub_cat);
        txtvw_nav_address= findViewById(R.id.txtvw_nav_address);
        txtvw_nav_catalogue = findViewById(R.id.txtvw_nav_catalogue);
        txtvw_nav_logout = findViewById(R.id.txtvw_nav_logout);
        txtvw_nav_login = findViewById(R.id.txtvw_nav_login);
        search_txtvw = findViewById(R.id.search_txtvw);
        search_postcode= findViewById(R.id.search_postcode);
        menuIcon = findViewById(R.id.menu_icon);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        Img_cart = findViewById(R.id.nav_cart);
        cart_size = findViewById(R.id.cart_size);
        Img_Search_icon = findViewById(R.id.search_icon);
        idlayout = findViewById(R.id.idlayout);
        heading = findViewById(R.id.heading);
        img_icon=findViewById(R.id.img_icon);
        Search_product_layout=findViewById(R.id.Search_product_layout);
        Search_postcode_layout=findViewById(R.id.Search_postcode_layout);
        layout_search_view=findViewById(R.id.layout_search_view);
        wholeseller=new Wholeseller();
        nav_search_layout=findViewById(R.id.navsearchlayout);
        sizedata=new Orders_Menu();
        search_txtvw.setRawInputType(InputType.TYPE_CLASS_TEXT);
        search_layout = findViewById(R.id.searchview_layout);
        layout_gps_home = findViewById(R.id.layout_gps_home);
        //drawerLayout =findViewById(R.id.layout_drawer);

        layout_nav_cat = findViewById(R.id.layout_nav_cat);
        layout_nav_wholeseller = findViewById(R.id.layout_nav_wholeseller);
        layout_nav_order = findViewById(R.id.layout_nav_order);
        layout_nav_account = findViewById(R.id.layout_nav_account);
        layout_nav_catalogue = findViewById(R.id.layout_nav_catalogue);
        layout_nav_login = findViewById(R.id.layout_nav_login);
        layout_nav_logout = findViewById(R.id.layout_nav_logout);
        layout_nav_address = findViewById(R.id.layout_nav_address);
        navsearchlayout = findViewById(R.id.navsearchlayout);

        databaseSqlite = new databaseSqlite(getApplicationContext());
        addPayment=new AddPayment();
        myList=new ArrayList<>();

        int versionCode = BuildConfig.VERSION_CODE;

        textview_version.setText(String.valueOf(versionCode));

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

        Paper.book().write("wholesaler_id","0");
        String user =Paper.book().read("user");
        String searchwholesalers = Paper.book().read("searchwholesalers");


        if( searchwholesalers != null && searchwholesalers.equals("1")){

            Paper.book().write("searchwholesalers","0");
            fragmentTransaction=getSupportFragmentManager().beginTransaction().replace(R.id.containerr,new Wholeseller());
            fragmentTransaction.commit();
        }

        else {

            fragmentTransaction=getSupportFragmentManager().beginTransaction().replace(R.id.containerr,new Home_Categories());
            fragmentTransaction.commit();
        }

        if (user.equals("0")){

            layout_nav_cat.setVisibility(View.GONE);
            layout_nav_wholeseller.setVisibility(View.VISIBLE);
            layout_nav_order.setVisibility(View.GONE);
            layout_nav_account.setVisibility(View.GONE);
            layout_nav_catalogue.setVisibility(View.GONE);
            layout_nav_logout.setVisibility(View.GONE);
            layout_nav_login.setVisibility(View.VISIBLE);
            layout_nav_address.setVisibility(View.GONE);

        }

        else if(Paper.book().read("ViewWholesellerPage", "5").equals("1")) {

            layout_nav_cat.setVisibility(View.VISIBLE);
            layout_nav_wholeseller.setVisibility(View.VISIBLE);
            layout_nav_order.setVisibility(View.VISIBLE);
            layout_nav_account.setVisibility(View.VISIBLE);
            layout_nav_catalogue.setVisibility(View.VISIBLE);
            layout_nav_logout.setVisibility(View.VISIBLE);
            layout_nav_login.setVisibility(View.GONE);
            layout_nav_address.setVisibility(View.VISIBLE);

        }

        else {

          /*  layout_nav_cat.setVisibility(View.VISIBLE);
            layout_nav_wholeseller.setVisibility(View.GONE);
            layout_nav_order.setVisibility(View.VISIBLE);
            layout_nav_account.setVisibility(View.VISIBLE);
            layout_nav_catalogue.setVisibility(View.VISIBLE);
            layout_nav_logout.setVisibility(View.VISIBLE);
            layout_nav_login.setVisibility(View.GONE);
            layout_nav_address.setVisibility(View.VISIBLE);*/
            layout_nav_cat.setVisibility(View.VISIBLE);

            layout_nav_order.setVisibility(View.VISIBLE);
            layout_nav_wholeseller.setVisibility(View.VISIBLE);
            layout_nav_account.setVisibility(View.VISIBLE);
            layout_nav_logout.setVisibility(View.VISIBLE);
            layout_nav_login.setVisibility(View.GONE);
            layout_nav_address.setVisibility(View.VISIBLE);
            layout_nav_catalogue.setVisibility(View.VISIBLE);

            String useerid = Paper.book().read("userid");

            Paper.book().write("user", useerid);

            if (Paper.book().read("permission_cats", "5").equals("1")){

                layout_nav_cat.setVisibility(View.VISIBLE);

            }

            else if (Paper.book().read("permission_cats", "5").equals("0")) {
                layout_nav_cat.setVisibility(View.GONE);

            }

             if ( userid !=null && userid.equals("0")) {

                layout_nav_cat.setVisibility(View.VISIBLE);

            }


            if (Paper.book().read("permission_orderss", "5").equals("1")){

                layout_nav_order.setVisibility(View.VISIBLE);

            }

            else if (Paper.book().read("permission_orderss", "5").equals("0")) {
                layout_nav_order.setVisibility(View.GONE);

            }

            if (Paper.book().read("permission_wholeseller", "5").equals("1")){

                layout_nav_wholeseller.setVisibility(View.VISIBLE);

            }

            else if (Paper.book().read("permission_wholeseller", "5").equals("0")) {
                layout_nav_wholeseller.setVisibility(View.GONE);

            }


            if (Paper.book().read("permission_cateloguess", "5").equals("1")){

                layout_nav_catalogue.setVisibility(View.VISIBLE);

            }
            else if (Paper.book().read("permission_cateloguess", "5").equals("0")) {
                layout_nav_catalogue.setVisibility(View.GONE);

            }

        }

        Bundle bundle = getIntent().getExtras();

        if(bundle != null){

            userid =bundle.getString("userid");

        }

        sharedPreferences =getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        sharedPreferences.getString("role", "");
        sharedPreferences.getString("permission_cat", "");
        sharedPreferences.getString("permission_orders", "");
        sharedPreferences.getString("permission_pro_details", "");
        sharedPreferences.getString("permission_catelogues", "");
        sharedPreferences.getString("permission_all_orders", "");
        sharedPreferences.getString("permission_awaiting", "");

        search_postcode.setText(sharedPreferences.getString("city", ""));


        if(!(String.valueOf(databaseSqlite.getNotesCount()).equals("0"))) {

           cart_size.setBackgroundResource(R.drawable.circle_view);
           cart_size.setText(String.valueOf(databaseSqlite.getNotesCount()));              // SET NUMBER OF ITEM IN THE CART USING SQLITE DATABASE "getNotesCount() METHOD"
       }

       else{

           cart_size.setText(String.valueOf(databaseSqlite.getNotesCount()));
       }

       String first_name = Paper.book().read("first_name");
       String last_name = Paper.book().read("last_name");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navUsername = findViewById(R.id.nav_header_name);

        if (user.equals("0")){
            navUsername.setText("Guest");
        }
        else {

            navUsername.setText(first_name +"  "+last_name);

        }


        registerReceiver(mReceiver,new IntentFilter(DatabaseChangedReceiver.ACTION_DATABASE_CHANGED));

        items=new Search_items();

        adapterCartMenu = new Adapter_Cart_Menu(getApplicationContext());

        mydatalist=new ArrayList<>();

        modelCartMenuList=new ArrayList<>();


        img_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragmentTransaction=getSupportFragmentManager().beginTransaction().addToBackStack("category").replace(R.id.containerr,new Home_Categories());
                fragmentTransaction.commit();
                search_txtvw.setText("");
            }
        });

        /*********************************************** SETTING STATUS BAR WHITE ******************************************************************/

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.white));
        }

        /************************************************************End*****************************************************************************/

        Paper.book().write("search","0");

        String cart_summary = Paper.book().read("cart_summary");

        if ((cart_summary != null) && (cart_summary.equals("1"))){

            Paper.book().write("cart_summary","");

            fragmentTransaction=getSupportFragmentManager().beginTransaction().addToBackStack("home_category").replace(R.id.containerr, new Cart_Menu());
            fragmentTransaction.commit();

        }

        String remove_address = Paper.book().read("remove_address");

        if (remove_address != null  && remove_address.equals("1")){

            Paper.book().write("remove_address","");
            fragmentTransaction=getSupportFragmentManager().beginTransaction().addToBackStack("projects").replace(R.id.containerr,new Projects());
            fragmentTransaction.commit();

        }


        navigationView.setNavigationItemSelectedListener(this);

        modelCartMenuList=Paper.book().read("Menus", new ArrayList<ModelCartMenu>());

        layout_gps_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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


/*                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

                if(!provider.contains("gps")){ //if gps is disabled
                    final Intent poke = new Intent();
                    poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
                    poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                    poke.setData(Uri.parse("3"));
                    sendBroadcast(poke);
                }

                if (Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            MY_PERMISSIONS_REQUEST_LOCATION);
                } else {
                    getLocation();
                }
                *//*if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    // OnGPS();
                } else {
                    getLocation();
                }*/
            }
        });

        Img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(String.valueOf(databaseSqlite.getNotesCount()).equals("0"))) {

                    fragmentTransaction=getSupportFragmentManager().beginTransaction().addToBackStack("home_category").replace(R.id.containerr, new Cart_Menu());
                    fragmentTransaction.commit();

                }

                else {

                    fragmentTransaction=getSupportFragmentManager().beginTransaction().addToBackStack("home_category").replace(R.id.containerr, new EmptyCart());
                    fragmentTransaction.commit();
                }
            }
        });

        search_txtvw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    search_txtvw.setFocusableInTouchMode(true);
                    layout_search_view.setVisibility(View.GONE);
                    Search_postcode_layout.setVisibility(View.GONE);
                    Search_product_layout.setVisibility(View.VISIBLE);

            }
        });

        idlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String post_wholesaler = Paper.book().read("post_wholesaler");
                String search_id=Paper.book().read("search_id");
                Paper.book().write("pay_Card_status","0");

                Log.e("search_11",searchwholesalers+"");
                Log.e("search_12",post_wholesaler+"");


                if ((Search_postcode_layout.getVisibility() == View.GONE) &&(Search_product_layout.getVisibility() == View.VISIBLE) ) {

                    Log.e("search_pradeep", "1");

                    if (layout_gps_home.getVisibility() == View.VISIBLE) {

                        if (!TextUtils.isEmpty(search_txtvw.getText().toString().trim())) {

                            Paper.book().write("postcity", search_txtvw.getText().toString().trim());
                            fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.containerr, new Wholeseller());
                            fragmentTransaction.commit();
                        }

                        else if (post_wholesaler != null && post_wholesaler.equals("1")){

                            Paper.book().write("post_wholesaler","0");
                            search_txtvw.requestFocus();
                            search_txtvw.requestFocusFromTouch();
                            fragmentTransaction=getSupportFragmentManager().beginTransaction().addToBackStack("cate").replace(R.id.containerr, new Search_items());
                            fragmentTransaction.commit();
                        }

                        else {
                            Toast.makeText(getApplicationContext(), "Please Enter Valid Postcode or City", Toast.LENGTH_SHORT).show();

                        }

                    }

                    else {

                        if (searchwholesalers != null && searchwholesalers.equals("1")) {
                            Paper.book().write("searchwholesalers", "0");
                            if (!TextUtils.isEmpty(search_txtvw.getText().toString().trim())) {

                                Paper.book().write("postcity", search_txtvw.getText().toString().trim());
                                fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.containerr, new Wholeseller());
                                fragmentTransaction.commit();
                            }

                           else if (TextUtils.isEmpty(search_txtvw.getText().toString().trim())) {

                                Paper.book().write("post_wholesaler", "0");
                                search_txtvw.requestFocus();
                                search_txtvw.requestFocusFromTouch();
                                fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack("cate").replace(R.id.containerr, new Search_items());
                                fragmentTransaction.commit();
                            }


                            else if (post_wholesaler != null && post_wholesaler.equals("1")) {

                                Paper.book().write("post_wholesaler", "0");
                                search_txtvw.requestFocus();
                                search_txtvw.requestFocusFromTouch();
                                fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack("cate").replace(R.id.containerr, new Search_items());
                                fragmentTransaction.commit();
                            } else {
                                Toast.makeText(getApplicationContext(), "Please Enter Valid Postcode or City", Toast.LENGTH_SHORT).show();

                            }

                        } else if (search_id != null) {
                            search_txtvw.requestFocus();
                            search_txtvw.requestFocusFromTouch();
                            fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack("cate").replace(R.id.containerr, new Search_items());
                            fragmentTransaction.commit();
                        } else {

                            search_txtvw.requestFocus();
                            search_txtvw.requestFocusFromTouch();
                            fragmentTransaction = getSupportFragmentManager().beginTransaction().addToBackStack("cate").replace(R.id.containerr, new Search_items());
                            fragmentTransaction.commit();

                        }


                    }


                }

                if ((Search_postcode_layout.getVisibility() == View.VISIBLE) &&(Search_product_layout.getVisibility() == View.GONE) ){
                    Log.e("search_pradeep","2");
                    if(searchwholesalers.equals("1")) {

                        if (!TextUtils.isEmpty(search_postcode.getText().toString().trim())) {
                            Paper.book().write("postcity", search_postcode.getText().toString().trim());
                            fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.containerr, new Wholeseller());
                            fragmentTransaction.commit();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Enter Valid Postcode or City", Toast.LENGTH_SHORT).show();

                        }

                    }
                    if (search_txtvw.equals("")) {

                        search_txtvw.requestFocus();
                        search_txtvw.requestFocusFromTouch();

                        fragmentTransaction=getSupportFragmentManager().beginTransaction().addToBackStack("cate").replace(R.id.containerr, new Wholeseller());
                        fragmentTransaction.commit();
                    }

                }




                if  ((Search_product_layout.getVisibility() == View.GONE) &&(Search_postcode_layout.getVisibility() == View.VISIBLE)) {
                    Log.e("search_pradeep","3");
                    if(searchwholesalers != null && searchwholesalers.equals("1")) {

                        if (!TextUtils.isEmpty(search_txtvw.getText().toString().trim())) {

                            Paper.book().write("postcity", search_txtvw.getText().toString().trim());
                            fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.containerr, new Wholeseller());
                            fragmentTransaction.commit();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please Enter Valid Postcode or City", Toast.LENGTH_SHORT).show();

                        }

                    }

                    if (search_txtvw.equals("")){
                        search_txtvw.requestFocus();
                        search_txtvw.requestFocusFromTouch();
                        fragmentTransaction=getSupportFragmentManager().beginTransaction().addToBackStack("cate").replace(R.id.containerr, new Search_items());
                        fragmentTransaction.commit();
                    }
                }
            }
        });
        menuIcon();
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

            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(Home.this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();

                search_txtvw.setText(postalCode);

                if (city != null){

                    Paper.book().write("city",city);
                    Paper.book().write("postcity",city);
                    Paper.book().write("searchwholesalers","1");
                }

                else {

                    Paper.book().write("city",postalCode);
                    Paper.book().write("postcity",postalCode);
                    Paper.book().write("searchwholesalers","1");
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
            Toast.makeText(Home.this, "No GPS Location found...Try Again!! or enter City/Postal Code", Toast.LENGTH_SHORT).show();

    }

    private void OnGPS() {
        final androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
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
        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
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

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                Home.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                Home.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double latitude = locationGPS.getLatitude();
                double longitude = locationGPS.getLongitude();


                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                    String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city = addresses.get(0).getLocality();
                    String state = addresses.get(0).getAdminArea();
                    String country = addresses.get(0).getCountryName();
                    String postalCode = addresses.get(0).getPostalCode();
                    String knownName = addresses.get(0).getFeatureName();

                    search_txtvw.setText(city);

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


    private void getCity() {

        Paper.book().write("city","0");

        final ProgressDialog progressDialogs = new ProgressDialog(Home.this,R.style.AlertDialogCustom);
        progressDialogs.setCancelable(false);
        progressDialogs.setMessage("Please Wait.......");
        progressDialogs.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        final ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String userid= Paper.book().read("unique_id");

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

                    final Dialog dialog= new Dialog(Home.this);
                    dialog.setContentView(R.layout.custom_dropdown_select_city);

                    EditText editText=dialog.findViewById(R.id.editTextSearch);
                    RecyclerView recyclerView=dialog.findViewById(R.id.recyclerView_city);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);

                    adapter_select_city=new Adapter_Select_City(Home.this,myList,dialog,search_postcode);
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

                        search_postcode.setText(Paper.book().read("selected_city"));

                    }

                    else {
                        search_postcode.setText("Select City");

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

    }

    private void menuIcon() {

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        switch(id){
/*
            case R.id.nav_categories:

                Paper.book().write("save_business","");

                Paper.book().write("wholeseller_bus_id","0");
                Paper.book().write("pop_cancel_up","1");
                Paper.book().write("pay_Card_status","0");
                Paper.book().write("search","0");

                if (Paper.book().read("user").equals("0")){

                    getProducts(Paper.book().read("user"));
                }

                else {

                    Log.e("dare2",Paper.book().read("user")+"");
                    Log.e("dare3",Paper.book().read("unique_id")+"");


                    getProducts(Paper.book().read("userid"));
                }


                layout_search_view.setVisibility(View.GONE);
                Search_product_layout.setVisibility(View.VISIBLE);
                Search_postcode_layout.setVisibility(View.GONE);
                search_txtvw.setText("");

                break;

            case R.id.layout_nav_wholeseller:

                search_postcode.setText(sharedPreferences.getString("city", ""));
                Paper.book().write("search","1");
                fragmentTransaction=getSupportFragmentManager().beginTransaction().addToBackStack("category").replace(R.id.containerr,new Wholeseller());
                fragmentTransaction.commit();

                layout_search_view.setVisibility(View.GONE);
                Search_product_layout.setVisibility(View.GONE);
                Search_postcode_layout.setVisibility(View.VISIBLE);

                break;

            case R.id.layout_nav_order:

                fragmentTransaction=getSupportFragmentManager().beginTransaction().replace(R.id.containerr,new Orders_Menu());
                fragmentTransaction.commit();
                search_txtvw.setText("");

                break;

            case R.id.nav_project_details:

                if (Paper.book().read("permission_wholeseller", "5").equals("1")) {
                    Paper.book().write("dataset", "1");
                }
                fragmentTransaction=getSupportFragmentManager().beginTransaction().addToBackStack("projects").replace(R.id.containerr,new Projects());
                fragmentTransaction.commit();
                search_txtvw.setText("");

                break;

            case R.id.layout_nav_account:

                fragmentTransaction=getSupportFragmentManager().beginTransaction().replace(R.id.containerr,new AccountDetails());
                fragmentTransaction.commit();
                search_txtvw.setText("");

                break;

            case R.id.layout_nav_catalogue:

                fragmentTransaction=getSupportFragmentManager().beginTransaction().replace(R.id.containerr,new Catelogues_pdf());
                fragmentTransaction.commit();
                search_txtvw.setText("");

                break;

            case R.id.layout_nav_logout:

                new AlertDialog.Builder(this,R.style.AlertDialogCustom)
                        .setMessage("Are you sure you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                logout();
                            }
                        })

                        .setNegativeButton("No", null)
                        .show();

                break;

            case R.id.layout_nav_login:

                Intent intent = new Intent(Home.this, Login.class);
                startActivity(intent);

               // popup_login_signup();

                search_txtvw.setText("");

                break;*/

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void popup_login_signup() {


        final Dialog dialog = new Dialog(Home.this);
        dialog.setContentView(R.layout.popup_register_login);

        popup_signin = dialog.findViewById(R.id.popup_signin);
        popup_signup = dialog.findViewById(R.id.popup_signup);
        popup_register_cross = dialog.findViewById(R.id.popup_register_cross);

        dialog.show();

        popup_register_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });


        popup_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Paper.book().write("popup_register_sign", "1");

                Intent intent = new Intent(getApplicationContext(), Login.class);

                startActivity(intent);

            }
        });


        popup_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Paper.book().write("popup_register_sign", "2");

                Intent intent = new Intent(getApplicationContext(), SignUp_Email_Screen.class);

                startActivity(intent);

            }
        });




    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    private void logout() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        String userid= Paper.book().read("userid");     // Retrieve id from Login Api
        String deviceid=Paper.book().read("deviceid");
        Log.e("userid",userid+"");
        Log.e("userid",deviceid+"");

        service.LOGOUT_CALL("application/x-www-form-urlencoded",userid,deviceid,"Android" +
                "").enqueue(new Callback<ModelLogout>() {
            @Override
            public void onResponse(Call<ModelLogout> call, Response<ModelLogout> response) {

                if(response.body().getStatusCode().equals(200)){

                    SharedPreferences pref = getApplicationContext().getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();

                    editor.clear();

                    editor.commit();

                    databaseSqlite.deleteAll();

                    Paper.book().write("user","0");
                    Paper.book().write("datarole","");
                    Paper.book().write("permission_see_cost","");
                    Paper.book().write("permission_cat","");
                    Paper.book().write("permission_orders","");
                    Paper.book().write("permission_pro_detailsss","");
                    Paper.book().write("permission_catelogues","");
                    Paper.book().write("permission_all_orders","");
                    Paper.book().write("permission_awaiting","");
                    Paper.book().write("deviceid","");
                    Paper.book().write("permission_wholeseller", "");
                    Paper.book().write("ViewWholesellerPage", "");
                    Paper.book().write("username","");
                    Paper.book().write("email","");
                    Paper.book().write("city","");
                    Paper.book().write("postcity","");
                    Paper.book().write("searchwholesalers","");

                    navUsername.setText("Guest");

                    finish();
                    overridePendingTransition( 0, 0);
                    startActivity(getIntent());
                    overridePendingTransition( 0, 0);
                }

                else {

                    Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ModelLogout> call, Throwable t) {

                Toast.makeText(Home.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }


    public void getProducts(String id) {

        layout_search_view.setVisibility(View.GONE);
        Search_product_layout.setVisibility(View.VISIBLE);
        Search_postcode_layout.setVisibility(View.GONE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiLogin_Interface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Defining retrofit api service
        ApiLogin_Interface service = retrofit.create(ApiLogin_Interface.class);

        service.Products(id).enqueue(new Callback<ModelProductsCategory>() {
            @Override
            public void onResponse(Call<ModelProductsCategory> call, Response<ModelProductsCategory> response) {

                modelProductsCategories = response.body().getCategory();

                if (response.body().getStatusCode().equals(200)) {

                    Paper.book().write("awaiting_data", response.body().getNumberofawating());

                    intentmodelProductsCategories = new ArrayList<>();

                    for (int i = 0; i < modelProductsCategories.size(); i++) {

                        Category category = new Category();
                        category.setCatId(response.body().getCategory().get(i).getCatId());
                        category.setCatImage(response.body().getCategory().get(i).getCatImage());
                        category.setCatName(response.body().getCategory().get(i).getCatName());
                        category.setStatus(response.body().getCategory().get(i).getStatus());
                        category.setFillter(response.body().getCategory().get(i).getFillter());

                        intentmodelProductsCategories.add(category);
                    }


                    Paper.book().write("categorylist", intentmodelProductsCategories);

                    fragmentTransaction=getSupportFragmentManager().beginTransaction().addToBackStack("category").replace(R.id.containerr,new Home_Categories());
                    fragmentTransaction.commit();



                } else {

                    Toast.makeText(context, "No Products Found", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ModelProductsCategory> call, Throwable t) {

                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });

    }

        @Override
    public void foo() {
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.txtvw_nav_cat: {

                Log.e("navigation","1");

                layout_gps_home.setVisibility(View.GONE);
                txtvw_nav_cat.setTextColor(getResources().getColor(R.color.themered));
                txtvw_nav_wholeseller.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_order.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_account.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_catalogue.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_login.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_logout.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_address.setTextColor(getResources().getColor(R.color.themeblack));

                imgvw_nav_cat.setImageDrawable(getResources().getDrawable(R.drawable.catagories_red));
                imgvw_nav_wholeseller.setImageDrawable(getResources().getDrawable(R.drawable.wholeseller_black));
                imgvw_nav_order.setImageDrawable(getResources().getDrawable(R.drawable.order_black));
                imgvw_nav_account.setImageDrawable(getResources().getDrawable(R.drawable.account_details_black));
                imgvw_nav_catalogue.setImageDrawable(getResources().getDrawable(R.drawable.catalogues_black));
                imgvw_nav_login.setImageDrawable(getResources().getDrawable(R.drawable.logout_black));
                imgvw_nav_logout.setImageDrawable(getResources().getDrawable(R.drawable.logout_black));
                imgvw_nav_address.setImageDrawable(getResources().getDrawable(R.drawable.address_black));

                drawerLayout.closeDrawers();
                search_txtvw.setHint("Search Products");

                Paper.book().write("wholesaler_id","0");
                Paper.book().write("save_business","");
                Paper.book().write("pop_cancel_up","1");
                Paper.book().write("pay_Card_status","0");
                Paper.book().write("search","0");

                if (Paper.book().read("user").equals("0")){

                    getProducts(Paper.book().read("user"));
                }

                else {

                    Log.e("dare2",Paper.book().read("user")+"");
                    Log.e("dare23232",Paper.book().read("userid")+"");
                    Log.e("dare3",Paper.book().read("unique_id")+"");


                    getProducts(Paper.book().read("userid"));
                }


                layout_search_view.setVisibility(View.GONE);
                Search_product_layout.setVisibility(View.VISIBLE);
                Search_postcode_layout.setVisibility(View.GONE);
                search_txtvw.setText("");


            }
            break;
            case R.id.txtvw_nav_wholeseller: {

                layout_gps_home.setVisibility(View.VISIBLE);
                txtvw_nav_cat.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_wholeseller.setTextColor(getResources().getColor(R.color.themered));
                txtvw_nav_order.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_account.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_catalogue.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_login.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_logout.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_address.setTextColor(getResources().getColor(R.color.themeblack));

                imgvw_nav_cat.setImageDrawable(getResources().getDrawable(R.drawable.catagories_black));
                imgvw_nav_wholeseller.setImageDrawable(getResources().getDrawable(R.drawable.wholeseller_themered));
                imgvw_nav_order.setImageDrawable(getResources().getDrawable(R.drawable.order_black));
                imgvw_nav_account.setImageDrawable(getResources().getDrawable(R.drawable.account_details_black));
                imgvw_nav_catalogue.setImageDrawable(getResources().getDrawable(R.drawable.catalogues_black));
                imgvw_nav_login.setImageDrawable(getResources().getDrawable(R.drawable.logout_black));
                imgvw_nav_logout.setImageDrawable(getResources().getDrawable(R.drawable.logout_black));
                imgvw_nav_address.setImageDrawable(getResources().getDrawable(R.drawable.address_black));
                search_txtvw.setHint("Enter City or Postcode");
                String postcity = Paper.book().read("postcity");
                search_txtvw.setText(postcity);
                drawerLayout.closeDrawers();
                search_txtvw.setText(sharedPreferences.getString("city", ""));
                Paper.book().write("search","1");
                fragmentTransaction=getSupportFragmentManager().beginTransaction().addToBackStack("category").replace(R.id.containerr,new Wholeseller());
                fragmentTransaction.commit();

                layout_search_view.setVisibility(View.GONE);
                Search_product_layout.setVisibility(View.VISIBLE);
                Search_postcode_layout.setVisibility(View.GONE);

            }
            break;

            case R.id.txtvw_nav_order:

                layout_gps_home.setVisibility(View.GONE);
                txtvw_nav_cat.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_wholeseller.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_order.setTextColor(getResources().getColor(R.color.themered));
                txtvw_nav_account.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_catalogue.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_login.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_logout.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_address.setTextColor(getResources().getColor(R.color.themeblack));

                imgvw_nav_cat.setImageDrawable(getResources().getDrawable(R.drawable.catagories_black));
                imgvw_nav_wholeseller.setImageDrawable(getResources().getDrawable(R.drawable.wholeseller_black));
                imgvw_nav_order.setImageDrawable(getResources().getDrawable(R.drawable.order_red));
                imgvw_nav_account.setImageDrawable(getResources().getDrawable(R.drawable.account_details_black));
                imgvw_nav_catalogue.setImageDrawable(getResources().getDrawable(R.drawable.catalogues_black));
                imgvw_nav_login.setImageDrawable(getResources().getDrawable(R.drawable.logout_black));
                imgvw_nav_logout.setImageDrawable(getResources().getDrawable(R.drawable.logout_black));
                imgvw_nav_address.setImageDrawable(getResources().getDrawable(R.drawable.address_black));

                drawerLayout.closeDrawers();
                Paper.book().write("wholesaler_id","0");
                fragmentTransaction=getSupportFragmentManager().beginTransaction().replace(R.id.containerr,new Orders_Menu());
                fragmentTransaction.commit();
                search_txtvw.setText("");

                break;

            case R.id.nav_project_details:

                layout_gps_home.setVisibility(View.GONE);
                Paper.book().write("address_tab","0");
                Paper.book().write("wholesaler_id","0");
                if (Paper.book().read("permission_wholeseller", "5").equals("1")) {
                    Paper.book().write("dataset", "1");
                }
                fragmentTransaction=getSupportFragmentManager().beginTransaction().addToBackStack("projects").replace(R.id.containerr,new Projects());
                fragmentTransaction.commit();
                search_txtvw.setText("");

                break;


            case R.id.txtvw_nav_address:

                layout_gps_home.setVisibility(View.GONE);
                txtvw_nav_cat.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_wholeseller.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_order.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_account.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_address.setTextColor(getResources().getColor(R.color.themered));
                txtvw_nav_catalogue.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_login.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_logout.setTextColor(getResources().getColor(R.color.themeblack));

                imgvw_nav_cat.setImageDrawable(getResources().getDrawable(R.drawable.catagories_black));
                imgvw_nav_wholeseller.setImageDrawable(getResources().getDrawable(R.drawable.wholeseller_black));
                imgvw_nav_order.setImageDrawable(getResources().getDrawable(R.drawable.order_black));
                imgvw_nav_account.setImageDrawable(getResources().getDrawable(R.drawable.account_details_black));
                imgvw_nav_account.setImageDrawable(getResources().getDrawable(R.drawable.account_details_black));
                imgvw_nav_catalogue.setImageDrawable(getResources().getDrawable(R.drawable.catalogues_black));
                imgvw_nav_login.setImageDrawable(getResources().getDrawable(R.drawable.logout_black));
                imgvw_nav_logout.setImageDrawable(getResources().getDrawable(R.drawable.logout_black));
                imgvw_nav_address.setImageDrawable(getResources().getDrawable(R.drawable.address_red));


                Paper.book().write("layout_address_edit","2");
                Paper.book().write("wholesaler_id","0");

                if (Paper.book().read("permission_wholeseller", "5").equals("1")) {
                    Paper.book().write("dataset", "1");
                }
                Paper.book().write("address_tab","1");
                fragmentTransaction=getSupportFragmentManager().beginTransaction().addToBackStack("projects").replace(R.id.containerr,new Projects());
                fragmentTransaction.commit();
                search_txtvw.setText("");

                drawerLayout.closeDrawers();

                break;

            case R.id.txtvw_nav_account:

                layout_gps_home.setVisibility(View.GONE);
                txtvw_nav_cat.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_wholeseller.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_order.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_account.setTextColor(getResources().getColor(R.color.themered));
                txtvw_nav_catalogue.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_login.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_logout.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_address.setTextColor(getResources().getColor(R.color.themeblack));

                imgvw_nav_cat.setImageDrawable(getResources().getDrawable(R.drawable.catagories_black));
                imgvw_nav_wholeseller.setImageDrawable(getResources().getDrawable(R.drawable.wholeseller_black));
                imgvw_nav_order.setImageDrawable(getResources().getDrawable(R.drawable.order_black));
                imgvw_nav_account.setImageDrawable(getResources().getDrawable(R.drawable.account_details_red));
                imgvw_nav_catalogue.setImageDrawable(getResources().getDrawable(R.drawable.catalogues_black));
                imgvw_nav_login.setImageDrawable(getResources().getDrawable(R.drawable.logout_black));
                imgvw_nav_logout.setImageDrawable(getResources().getDrawable(R.drawable.logout_black));
                imgvw_nav_address.setImageDrawable(getResources().getDrawable(R.drawable.address_black));
                Paper.book().write("wholesaler_id","0");
                drawerLayout.closeDrawers();

                fragmentTransaction=getSupportFragmentManager().beginTransaction().replace(R.id.containerr,new AccountDetails());
                fragmentTransaction.commit();
                search_txtvw.setText("");

                break;




            case R.id.txtvw_nav_catalogue:

                layout_gps_home.setVisibility(View.GONE);
                txtvw_nav_cat.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_wholeseller.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_order.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_account.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_catalogue.setTextColor(getResources().getColor(R.color.themered));
                txtvw_nav_login.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_logout.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_address.setTextColor(getResources().getColor(R.color.themeblack));

                imgvw_nav_cat.setImageDrawable(getResources().getDrawable(R.drawable.catagories_black));
                imgvw_nav_wholeseller.setImageDrawable(getResources().getDrawable(R.drawable.wholeseller_black));
                imgvw_nav_order.setImageDrawable(getResources().getDrawable(R.drawable.order_black));
                imgvw_nav_account.setImageDrawable(getResources().getDrawable(R.drawable.account_details_black));
                imgvw_nav_catalogue.setImageDrawable(getResources().getDrawable(R.drawable.catalogues_red));
                imgvw_nav_login.setImageDrawable(getResources().getDrawable(R.drawable.logout_black));
                imgvw_nav_logout.setImageDrawable(getResources().getDrawable(R.drawable.logout_black));
                imgvw_nav_address.setImageDrawable(getResources().getDrawable(R.drawable.address_black));
                Paper.book().write("wholesaler_id","0");
                drawerLayout.closeDrawers();

                fragmentTransaction=getSupportFragmentManager().beginTransaction().replace(R.id.containerr,new New_Catalogues());
                fragmentTransaction.commit();
                search_txtvw.setText("");

                break;

            case R.id.txtvw_nav_logout:

                txtvw_nav_cat.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_wholeseller.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_order.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_account.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_catalogue.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_login.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_logout.setTextColor(getResources().getColor(R.color.themered));
                txtvw_nav_address.setTextColor(getResources().getColor(R.color.themeblack));

                imgvw_nav_cat.setImageDrawable(getResources().getDrawable(R.drawable.catagories_black));
                imgvw_nav_wholeseller.setImageDrawable(getResources().getDrawable(R.drawable.wholeseller_black));
                imgvw_nav_order.setImageDrawable(getResources().getDrawable(R.drawable.order_black));
                imgvw_nav_account.setImageDrawable(getResources().getDrawable(R.drawable.account_details_black));
                imgvw_nav_catalogue.setImageDrawable(getResources().getDrawable(R.drawable.catalogues_black));
                imgvw_nav_login.setImageDrawable(getResources().getDrawable(R.drawable.logout_black));
                imgvw_nav_logout.setImageDrawable(getResources().getDrawable(R.drawable.logout_red));
                imgvw_nav_address.setImageDrawable(getResources().getDrawable(R.drawable.address_black));
                Paper.book().write("wholesaler_id","0");
                drawerLayout.closeDrawers();

                new AlertDialog.Builder(this,R.style.AlertDialogCustom)
                        .setMessage("Are you sure you want to Logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                logout();
                            }
                        })

                        .setNegativeButton("No", null)
                        .show();

                break;

            case R.id.txtvw_nav_login:


                txtvw_nav_cat.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_wholeseller.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_order.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_account.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_catalogue.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_login.setTextColor(getResources().getColor(R.color.themered));
                txtvw_nav_logout.setTextColor(getResources().getColor(R.color.themeblack));
                txtvw_nav_address.setTextColor(getResources().getColor(R.color.themeblack));

                imgvw_nav_cat.setImageDrawable(getResources().getDrawable(R.drawable.catagories_black));
                imgvw_nav_wholeseller.setImageDrawable(getResources().getDrawable(R.drawable.wholeseller_black));
                imgvw_nav_order.setImageDrawable(getResources().getDrawable(R.drawable.order_black));
                imgvw_nav_account.setImageDrawable(getResources().getDrawable(R.drawable.account_details_black));
                imgvw_nav_catalogue.setImageDrawable(getResources().getDrawable(R.drawable.catalogues_black));
                imgvw_nav_login.setImageDrawable(getResources().getDrawable(R.drawable.logout_red));
                imgvw_nav_logout.setImageDrawable(getResources().getDrawable(R.drawable.logout_black));
                imgvw_nav_address.setImageDrawable(getResources().getDrawable(R.drawable.address_black));
                Paper.book().write("wholesaler_id","0");
                drawerLayout.closeDrawers();

                Intent intent = new Intent(Home.this, Login.class);
                startActivity(intent);

                // popup_login_signup();

                search_txtvw.setText("");

                break;


        }

    }

    @Override
    public void updateCart() {
   }

    private DatabaseChangedReceiver mReceiver = new DatabaseChangedReceiver() {

        public void onReceive(Context context, Intent intent) {

           if((databaseSqlite.getNotesCount()) != 0)
           {
               cart_size.setVisibility(View.VISIBLE);
               cart_size.setBackgroundResource(R.drawable.circle_view);
               cart_size.setText(String.valueOf(databaseSqlite.getNotesCount()));
           }

           else {

               cart_size.setText(String.valueOf(databaseSqlite.getNotesCount()));

               cart_size.setVisibility(View.GONE);
           }
        }

    };

    public void hideView(boolean b) {

        if(b){

            search_layout.setVisibility(View.GONE);
        }
        else {

            search_layout.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void drawer_nav() {

        //drawerLayout.openDrawer(Gravity.START);

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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}




