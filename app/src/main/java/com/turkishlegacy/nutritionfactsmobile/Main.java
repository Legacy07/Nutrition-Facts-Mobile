package com.turkishlegacy.nutritionfactsmobile;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.turkishlegacy.nutritionfactsmobile.database.DatabaseHandler;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //database class
    DatabaseHandler db;
    //variables to hold information from search fragment and pass this to nutrition summary fragment
    public String name = "";
    public String quantity = "";
    public String calories = "";
    public String protein = "";
    public String carb = "";
    public String fat = "";

    public String searchName = "";
    public String searchQuantity = "";
    public String searchCalories = "";
    public String searchProtein = "";
    public String searchCarb = "";
    public String searchFat = "";

    //created automatically
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //action bar changes
        getSupportActionBar().setTitle("Nutrition Facts");

        db = new DatabaseHandler(this);

    }

    //created automatically
    //if back pressed then fragments will load again instead of having a blank page
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int fragments = getSupportFragmentManager().getBackStackEntryCount();
            if (fragments == 1) {
                finish();
            } else {
                if (getFragmentManager().getBackStackEntryCount() > 1) {
                    getFragmentManager().popBackStack();
                } else {
                    super.onBackPressed();
                }
            }
        }
    }

    //created automatically
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //created automatically
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    //created automatically
    //Navigation Drawer selections
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Home) {

            //when pressed back the fragments have a blank page
        } else if (id == R.id.nav_Diary) {
            //opens the diary Fragment
            DiaryFragment diaryFragment = new DiaryFragment();
            FragmentManager manager = getSupportFragmentManager();
            //replacing the fragment inside the layout
            manager.beginTransaction().replace(R.id.layout_Fragment, diaryFragment).commit();

        } else if (id == R.id.nav_NutritionSummary) {
            //opens the nutrition summary Fragment
            NutritionSummary_Fragment nutritionSummary_fragment = new NutritionSummary_Fragment();
            FragmentManager manager = getSupportFragmentManager();
            //replacing the fragment inside the layout
            manager.beginTransaction().replace(R.id.layout_Fragment, nutritionSummary_fragment).commit();

        } else if (id == R.id.nav_BMRCalculator) {

            //opens the add food Fragment
            CalculateBMR_Fragment calculateBMR_fragment = new CalculateBMR_Fragment();
            FragmentManager manager = getSupportFragmentManager();
            //replacing the fragment inside the layout
            manager.beginTransaction().replace(R.id.layout_Fragment, calculateBMR_fragment).commit();


        } else if (id == R.id.nav_AddFood) {
            //opens the add food Fragment
            AddFood_Fragment addFood_fragment = new AddFood_Fragment();
            FragmentManager manager = getSupportFragmentManager();
            //replacing the fragment inside the layout
            manager.beginTransaction().replace(R.id.layout_Fragment, addFood_fragment).commit();


        } else if (id == R.id.nav_ImportExport) {
            //opens the import/export Database Fragment
            ImportExportDatabase_Fragment importExportDatabase_fragment = new ImportExportDatabase_Fragment();
            FragmentManager manager = getSupportFragmentManager();
            //replacing the fragment inside the layout
            manager.beginTransaction().replace(R.id.layout_Fragment, importExportDatabase_fragment).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //getters and setters
    public String getName() {
        return this.name;
    }

    public void setName(String sName) {
        this.name = sName;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public void setQuantity(String sQuantity) {
        this.quantity = sQuantity;
    }

    public String getCalories() {
        return this.calories;
    }

    public void setCalories(String sCalories) {
        this.calories = sCalories;
    }

    public String getProtein() {
        return this.protein;
    }

    public void setProtein(String sProtein) {
        this.protein = sProtein;
    }

    public String getCarb() {
        return this.carb;
    }

    public void setCarb(String sCarb) {
        this.carb = sCarb;
    }

    public String getFat() {
        return this.fat;
    }

    public void setFat(String sFat) {
        this.fat = sFat;
    }


    //getters and setters
    public String getSearchName() {
        return this.searchName;
    }

    public void setSearchName(String sName) {
        this.searchName = sName;
    }

    public String getSearchQuantity() {
        return this.searchQuantity;
    }

    public void setSearchQuantity(String sQuantity) {
        this.searchQuantity = sQuantity;
    }

    public String getSearchCalories() {
        return this.searchCalories;
    }

    public void setSearchCalories(String sCalories) {
        this.searchCalories = sCalories;
    }

    public String getSearchProtein() {
        return this.searchProtein;
    }

    public void setSearchProtein(String sProtein) {
        this.searchProtein = sProtein;
    }

    public String getSearchCarb() {
        return this.searchCarb;
    }

    public void setSearchCarb(String sCarb) {
        this.searchCarb = sCarb;
    }

    public String getSearchFat() {
        return this.searchFat;
    }

    public void setSearchFat(String sFat) {
        this.searchFat = sFat;
    }



}
