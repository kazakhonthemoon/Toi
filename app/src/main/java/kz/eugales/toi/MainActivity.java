package kz.eugales.toi;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import kz.eugales.toi.pojo.Dish;
import kz.eugales.toi.services.DishService;
import kz.eugales.toi.utils.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DishFragment.OnListFragmentInteractionListener{

    public final int ADD_DISH = 1;

    @Inject
    DishService mDishService;

    DishFragment mDishFragment = null;

    Realm realm = null;

    int mMenuItemId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        realm = Realm.getDefaultInstance();

        if(Utils.isNetworkAvailable(getApplicationContext())){
            realm.beginTransaction();
            realm.deleteAll();
            realm.commitTransaction();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == ADD_DISH) {
                Dish dish = (Dish)data.getExtras().getSerializable("dish");
                Snackbar.make(getCurrentFocus(),"Блюдо " + dish.getName() + " добавлено", Snackbar.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_add_dish:
                Intent intent = new Intent(this, AddDishActivity.class);
                startActivityForResult(intent, ADD_DISH);
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (mMenuItemId != id) {

            switch (id) {
                case R.id.nav_gastronomy:
                    mDishFragment = DishFragment.newInstance(1);
                    break;
                case R.id.nav_confectionary:
                    mDishFragment = DishFragment.newInstance(2);
                    break;
                case R.id.nav_bakery:
                    mDishFragment = DishFragment.newInstance(3);
                    break;
                case R.id.nav_hotel:
                    mDishFragment = DishFragment.newInstance(4);
                    break;
                case R.id.nav_camp:
                    mDishFragment = DishFragment.newInstance(5);
                    break;
                case R.id.nav_share:
                    mDishFragment = DishFragment.newInstance(0);
                    break;
                case R.id.nav_send:
                    mDishFragment = DishFragment.newInstance(0);
                    break;
            }

            if (mDishFragment != null)
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, mDishFragment).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        mMenuItemId = id;
        return true;
    }


    @Override
    public void onListFragmentInteraction() {

    }

    @Override
    public void onAddToBasketInteraction(int dishId) {
        Snackbar.make(getCurrentFocus(), "Добавлено в корзину", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onStartDetailActivity(int dishId) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("dishId", dishId);
        startActivity(intent);
    }

}
