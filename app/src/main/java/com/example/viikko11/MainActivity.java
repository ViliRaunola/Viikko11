package com.example.viikko11;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    private Toolbar ylapalkki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ylapalkki = findViewById(R.id.toolbar);
        setSupportActionBar(ylapalkki);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle togglaus = new ActionBarDrawerToggle(this, drawer, ylapalkki, R.string.navigation_drawer_open, R.string.navigation_drawer_close); //Tehdään hampurilais menu
        drawer.addDrawerListener(togglaus);
        togglaus.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Text()).commit(); //Jotta sovelluksen käynnistyksessä saataisiin auki heti tämä seivu
        navigationView.setCheckedItem(R.id.nav_text);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Settings()).commit();
                break;
            case R.id.nav_text:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Text()).commit();

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) { //Jos painaessa takaisin nappia on side menu auki suljetaan se eikä poistuta ohjelmasta
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }
}