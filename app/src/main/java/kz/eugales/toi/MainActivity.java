package kz.eugales.toi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, DishFragment.OnListFragmentInteractionListener {

    @Inject
    DishService mDishService;

    DishFragment mDishFragment = null;

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

        ((DishesApplication) getApplication()).getDishComponent().inject(this);

        refreshRealm();


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
                Intent intent = new Intent(this,AddDishActivity.class);
                startActivity(intent);
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    int mMenuItemId = 0;

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

    private void refreshRealm() {

        mDishService.getDishes().enqueue(new Callback<List<Dish>>() {
            Realm realm = Realm.getDefaultInstance();

            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                realm.beginTransaction();
                realm.insertOrUpdate(response.body());
                realm.commitTransaction();
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }
}
