package com.turkishlegacy.nutritionfactsmobile;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.turkishlegacy.nutritionfactsmobile.database.DatabaseHandler;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //database class
    DatabaseHandler db;
    //variables to hold information from search fragment and pass this to nutrition summary fragment
    public String breakfastFoodName = "";
    public String breakfastFoodQuantity = "";
    public String breakfastFoodCalories = "";
    public String breakfastFoodProtein = "";
    public String breakfastFoodCarb = "";
    public String breakfastFoodFat = "";
    ///////////////////////////////
    public String lunchFoodName = "";
    public String lunchFoodQuantity = "";
    public String lunchFoodCalories = "";
    public String lunchFoodProtein = "";
    public String lunchFoodCarb = "";
    public String lunchFoodFat = "";
    ////////////////////////////
    public String dinnerFoodName = "";
    public String dinnerFoodQuantity = "";
    public String dinnerFoodCalories = "";
    public String dinnerFoodProtein = "";
    public String dinnerFoodCarb = "";
    public String dinnerFoodFat = "";

//    public String searchName = "";
//    public String searchQuantity = "";
//    public String searchCalories = "";
//    public String searchProtein = "";
//    public String searchCarb = "";
//    public String searchFat = "";

    boolean isBreakfast = false;
    boolean isLunch = false;
    boolean isDinner = false;

    SearchFragment searchFragment;
    FoodNutritions_Fragment foodNutritions_fragment;

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


//        searchFragment = new SearchFragment();
//        foodNutritions_fragment = new FoodNutritions_Fragment();


//        FragmentManager fm = getSupportFragmentManager();
//        searchFragment = (SearchFragment) fm.findFragmentById(R.id.search_fragment_layout);
//        foodNutritions_fragment = (FoodNutritions_Fragment) fm.findFragmentById(R.id.fragment_food_nutritions);
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
            manager.beginTransaction().replace(R.id.content_layout, diaryFragment).commit();

        } else if (id == R.id.nav_NutritionSummary) {
            //opens the nutrition summary Fragment
            NutritionSummary_Fragment nutritionSummary_fragment = new NutritionSummary_Fragment();
            FragmentManager manager = getSupportFragmentManager();
            //replacing the fragment inside the layout
            manager.beginTransaction().replace(R.id.content_layout, nutritionSummary_fragment).commit();

        } else if (id == R.id.nav_BMRCalculator) {

            //opens the add food Fragment
            CalculateBMR_Fragment calculateBMR_fragment = new CalculateBMR_Fragment();
            FragmentManager manager = getSupportFragmentManager();
            //replacing the fragment inside the layout
            manager.beginTransaction().replace(R.id.content_layout, calculateBMR_fragment).commit();


        } else if (id == R.id.nav_AddFood) {
            //opens the add food Fragment
            AddFood_Fragment addFood_fragment = new AddFood_Fragment();
            FragmentManager manager = getSupportFragmentManager();
            //replacing the fragment inside the layout
            manager.beginTransaction().replace(R.id.content_layout, addFood_fragment).commit();


        } else if (id == R.id.nav_ImportExport) {
            //opens the import/export Database Fragment
            ImportExportDatabase_Fragment importExportDatabase_fragment = new ImportExportDatabase_Fragment();
            FragmentManager manager = getSupportFragmentManager();
            //replacing the fragment inside the layout
            manager.beginTransaction().replace(R.id.content_layout, importExportDatabase_fragment).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //getters and setters
    public String getBreakfastFoodName() {
        return this.breakfastFoodName;
    }

    public void setBreakfastFoodName(String sName) {
        this.breakfastFoodName = sName;
    }

    public String getBreakfastFoodQuantity() {
        return this.breakfastFoodQuantity;
    }

    public void setBreakfastFoodQuantity(String sQuantity) {
        this.breakfastFoodQuantity = sQuantity;
    }

    public String getBreakfastFoodCalories() {
        return this.breakfastFoodCalories;
    }

    public void setBreakfastFoodCalories(String sCalories) {
        this.breakfastFoodCalories = sCalories;
    }

    public String getBreakfastFoodProtein() {
        return this.breakfastFoodProtein;
    }

    public void setBreakfastFoodProtein(String sProtein) {
        this.breakfastFoodProtein = sProtein;
    }

    public String getBreakfastFoodCarb() {
        return this.breakfastFoodCarb;
    }

    public void setBreakfastFoodCarb(String sCarb) {
        this.breakfastFoodCarb = sCarb;
    }

    public String getBreakfastFoodFat() {
        return this.breakfastFoodFat;
    }

    public void setBreakfastFoodFat(String sFat) {
        this.breakfastFoodFat = sFat;
    }

    ////////////////////////////////////////////////////////
    public String getLunchFoodName() {
        return this.lunchFoodName;
    }

    public void setLunchFoodName(String sName) {
        this.lunchFoodName = sName;
    }

    public String getLunchFoodQuantity() {
        return this.lunchFoodQuantity;
    }

    public void setLunchFoodQuantity(String sQuantity) {
        this.lunchFoodQuantity = sQuantity;
    }

    public String getLunchFoodCalories() {
        return this.lunchFoodCalories;
    }

    public void setLunchFoodCalories(String sCalories) {
        this.lunchFoodCalories = sCalories;
    }

    public String getLunchFoodProtein() {
        return this.lunchFoodProtein;
    }

    public void setLunchFoodProtein(String sProtein) {
        this.lunchFoodProtein = sProtein;
    }

    public String getLunchFoodCarb() {
        return this.lunchFoodCarb;
    }

    public void setLunchFoodCarb(String sCarb) {
        this.lunchFoodCarb = sCarb;
    }

    public String getLunchFoodFat() {
        return this.lunchFoodFat;
    }

    public void setLunchFoodFat(String sFat) {
        this.lunchFoodFat = sFat;
    }

    ///////////////////////////////////////////////////

    public String getDinnerFoodName() {
        return this.dinnerFoodName;
    }

    public void setDinnerFoodName(String sName) {
        this.dinnerFoodName = sName;
    }

    public String getDinnerFoodQuantity() {
        return this.dinnerFoodQuantity;
    }

    public void setDinnerFoodQuantity(String sQuantity) {
        this.dinnerFoodQuantity = sQuantity;
    }

    public String getDinnerFoodCalories() {
        return this.dinnerFoodCalories;
    }

    public void setDinnerFoodCalories(String sCalories) {
        this.dinnerFoodCalories = sCalories;
    }

    public String getDinnerFoodProtein() {
        return this.dinnerFoodProtein;
    }

    public void setDinnerFoodProtein(String sProtein) {
        this.dinnerFoodProtein = sProtein;
    }

    public String getDinnerFoodCarb() {
        return this.dinnerFoodCarb;
    }

    public void setDinnerFoodCarb(String sCarb) {
        this.dinnerFoodCarb = sCarb;
    }

    public String getDinnerFoodFat() {
        return this.dinnerFoodFat;
    }

    public void setDinnerFoodFat(String sFat) {
        this.dinnerFoodFat = sFat;
    }

    /////////////////////////////////////////////////////
    public void setIsBreakfast(Boolean isBreakfast) {
        this.isBreakfast = isBreakfast;
    }

    public Boolean getIsBreakfast() {
        return this.isBreakfast;
    }

    public void setIsLunch(Boolean isLunch) {
        this.isLunch = isLunch;
    }

    public Boolean getIsLunch() {
        return this.isLunch;
    }

    public void setIsDinner(Boolean isDinner) {
        this.isDinner = isDinner;
    }

    public Boolean getIsDinner() {
        return this.isDinner;
    }

///////////////////////////////////////////////////////////
//    //getters and setters
//    public String getSearchName() {
//        return this.searchName;
//    }
//
//    public void setSearchName(String sName) {
//        this.searchName = sName;
//    }
//
//    public String getSearchQuantity() {
//        return this.searchQuantity;
//    }
//
//    public void setSearchQuantity(String sQuantity) {
//        this.searchQuantity = sQuantity;
//    }
//
//    public String getSearchCalories() {
//        return this.searchCalories;
//    }
//
//    public void setSearchCalories(String sCalories) {
//        this.searchCalories = sCalories;
//    }
//
//    public String getSearchProtein() {
//        return this.searchProtein;
//    }
//
//    public void setSearchProtein(String sProtein) {
//        this.searchProtein = sProtein;
//    }
//
//    public String getSearchCarb() {
//        return this.searchCarb;
//    }
//
//    public void setSearchCarb(String sCarb) {
//        this.searchCarb = sCarb;
//    }
//
//    public String getSearchFat() {
//        return this.searchFat;
//    }
//
//    public void setSearchFat(String sFat) {
//        this.searchFat = sFat;
//    }


//    @Override
//    public void setNameInterface(String sName) {
//        foodNutritions_fragment.updateName(sName);
//    }
}
