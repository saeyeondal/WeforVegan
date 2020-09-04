package com.example.weforvegan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback {
    SearchFrag searchFrag;
    ScannerFrag scannerFrag;
    MypageFrag mypageFrag;
    RecipeFrag recipeFrag;
    ResultFrag resultFrag;
    RequestFrag requestFrag;
    RecommendFrag recommendFrag;
    AddRequestFrag addRequestFrag;
    LikeRecipePage likeRecipePage;
    DrawerLayout drawer;
    Toolbar toolbar;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        searchFrag = new SearchFrag();
        scannerFrag = new ScannerFrag();
        mypageFrag = new MypageFrag();
        recipeFrag = new RecipeFrag();
        resultFrag = new ResultFrag();
        requestFrag = new RequestFrag();
        recommendFrag = new RecommendFrag();
        addRequestFrag = new AddRequestFrag();
        likeRecipePage = new LikeRecipePage();

        if(savedInstanceState == null)
            navigationView.getMenu().performIdentifierAction(R.id.nav_search, 0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onFragmentSelected(int position, Bundle bundle) {
        Fragment curFragment = null;

        if( position == 0 )
            curFragment = searchFrag;
        else if( position == 1 )
            curFragment = scannerFrag;
        else if( position == 2 )
            curFragment = recommendFrag;
        else if( position == 3 )
            curFragment = mypageFrag;
        else if( position == 4)
            curFragment = requestFrag;
        else if( position == 5)
            curFragment = likeRecipePage;

        getSupportFragmentManager().beginTransaction().replace(R.id.container, curFragment).commit();
    }

    public void onFragmentChanged(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (index) {
            case 1:
                transaction.replace(R.id.container, resultFrag);
                break;
            case 2:
                transaction.replace(R.id.container, addRequestFrag);
                break;
            case 3:
                transaction.replace(R.id.container, requestFrag);
                break;
        }

        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        if(id == R.id.nav_search)
            onFragmentSelected(0, null);
        else if(id == R.id.nav_scanner)
            onFragmentSelected(1, null);
        else if(id == R.id.nav_recommend)
            onFragmentSelected(2, null);
        else if(id == R.id.nav_mypage)
            onFragmentSelected(3, null);
        else if(id == R.id.nav_request)
            onFragmentSelected(4, null);
        else if(id == R.id.nav_like){
            onFragmentSelected(5, null);
        }
        else if(id == R.id.nav_logout){
            Intent intent = new Intent(this, LoginPage.class); //파라메터는 현재 액티비티, 전환될 액티비티
            LoginPage.logState = "logout";
            startActivity(intent); //엑티비티 요청
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }
}
