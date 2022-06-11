package com.example.navigation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

//    private AppBarConfiguration mAppBarConfiguration;

    FragmentManager fm;
    DrawerLayout drawer;
    Toolbar toolbar;
    String fName;
    LinearLayout ll_home,ll_LogOut;
    Fragment fragment;

    boolean doubleBackToExitPressedOnce = false;

    //botttom navigation
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);

        ll_home = findViewById(R.id.ll_home);
        ll_LogOut = findViewById(R.id.ll_LogOut);

        fName = "Dashboard";

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white_color));

        //hide drawer
//        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //unhide drawer
//        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        toolbar.setTitle("Navigation");

        fm = getSupportFragmentManager();

        //bottom navigation
        bottomNavigationView = findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.bottom_home:
                        toolbar.setTitle("HOME");
                            replaceFragment(new HomeFragment());
                        return true;
                    case R.id.bottom_mybookings:
                            toolbar.setTitle("HISTORY");
                            replaceFragment(new HomeFragment());
                        return true;
                    case R.id.bottom_myprofile:
                            toolbar.setTitle("MY PROFILE");
                        replaceFragment(new HomeFragment());
                        return true;
                    case R.id.bottom_more:
                        toolbar.setTitle("MORE");
                        replaceFragment(new HomeFragment());
                        return true;
                }
                return true;
            }
        });
    }

    public void onClick(View v) {

        fm = getSupportFragmentManager();
        switch (v.getId()){
            case R.id.ll_home:
                replaceFragment(new HomeFragment());
//                fm.beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();
//                fName = DocDashboard.class.getSimpleName();
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.ll_LogOut:
                logOut();
        }
    }


    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        }else if (getSupportFragmentManager().findFragmentById(R.id.frame_layout)!=getSupportFragmentManager().findFragmentByTag("Dashboard")) {
//            username.equalsIgnoreCase("admin")
//            if (uRole==1){
//                if(!fName.getClass().getSimpleName().equalsIgnoreCase("DoctorDashboard")){
//                    fm.beginTransaction().replace(R.id.frame_layout,new DoctorDashboard()).addToBackStack(null).commit();
//                    doubleBackPress();
//                }
////username.equalsIgnoreCase("staff")
//            }else if(uRole==0) {
//                if (!fName.getClass().getSimpleName().equalsIgnoreCase("PatDashboard")) {
//                    fm.beginTransaction().replace(R.id.frame_layout, new PatDashboard()).addToBackStack(null).commit();
//                    doubleBackPress();
//                }
//            }

        } else {
            super.onBackPressed();

        }
    }

    private void doubleBackPress() {
        if (!doubleBackToExitPressedOnce){
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(MainActivity.this, "Please click back again to exit !!", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            },500);
        }else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


    private void logOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog_logout, null);
        builder.setView(dialogView);
        builder.setCancelable(false);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        TextView btn_no = dialogView.findViewById(R.id.btn_no);
        TextView btn_yes = dialogView.findViewById(R.id.btn_yes);

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Toast.makeText(MainActivity.this, "logout", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this, MainActivity.class));
//                finish();
            }
        });
    }


    public void replaceFragment(Fragment fragment) {
        this.fragment=fragment;
        fName = fragment.getClass().getSimpleName();
        fm = getSupportFragmentManager();
//        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE); //back null
        FragmentTransaction fTransaction = fm.beginTransaction();
        fTransaction.replace(R.id.frame_layout, fragment);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//        fTransaction.replace( R.id.frame_layout, new HomeFragment() ).addToBackStack( "tag" ).commit();
//        fragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        fTransaction.addToBackStack(null);
        fTransaction.commit();
    }


    //another fragment to fragment
//     ((MainActivity) mContext).replaceFragment(new BookListFragment());
}