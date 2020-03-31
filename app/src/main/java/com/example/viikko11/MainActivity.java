package com.example.viikko11;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import java.util.Locale;

//Käytetty apuna https://www.youtube.com/watch?v=fGcMLu1GJEc osat 1-3.


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, Settings.Settings_Send_Listener {
    private DrawerLayout drawer;

    private Toolbar ylapalkki;
    Text text = new Text();
    Settings settings = new Settings();

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

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, text).commit(); //Jotta sovelluksen käynnistyksessä saataisiin auki heti tämä seivu
        navigationView.setCheckedItem(R.id.nav_text);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_settings:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, settings).commit();
                break;
            case R.id.nav_text:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, text).commit();
                break;
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

    @Override
    public void Settings_Send(String viesti, int koko_fontti, int vari, boolean caps, boolean bold, boolean muokkaus) {


        Bundle bundle = new Bundle();
        bundle.putString("message", viesti);
        bundle.putInt("vari", vari);
        bundle.putInt("koko", koko_fontti);
        bundle.putBoolean("caps", caps);
        bundle.putBoolean("bold", bold);
        bundle.putBoolean("muokkaus", muokkaus);
        text.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, text, null);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_text);
    }
    @Override
    public void onStart(){
        super.onStart();
        try {
            String valinta = getIntent().getExtras().get("kieli").toString();
            Locale myLocale = new Locale(valinta);
            Resources resources = getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Configuration conf = resources.getConfiguration();
            conf.locale = myLocale;
            resources.updateConfiguration(conf, dm);
        }catch (NullPointerException e){

        }
    }
}
