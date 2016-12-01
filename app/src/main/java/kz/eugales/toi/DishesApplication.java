package kz.eugales.toi;

import android.app.Application;

import io.realm.Realm;
import kz.eugales.toi.di.DaggerDishComponent;
import kz.eugales.toi.di.DaggerNetworkComponent;
import kz.eugales.toi.di.DishComponent;
import kz.eugales.toi.di.DishModule;
import kz.eugales.toi.di.NetworkComponent;
import kz.eugales.toi.di.NetworkModule;


/**
 * Created by Adil on 29.11.2016.
 */

public class DishesApplication extends Application {

    private DishComponent mDishComponent;

    public DishesApplication() {
    }

    @Override
    public void onCreate() {
        NetworkComponent networkComponent = DaggerNetworkComponent.builder()
                .networkModule(new NetworkModule("http://192.168.100.4:8080", getApplicationContext())).build();
        mDishComponent = DaggerDishComponent.builder()
                .networkComponent(networkComponent)
                .dishModule(new DishModule())
                .build();

        Realm.init(this);

        super.onCreate();
    }

    public DishComponent getDishComponent() {
        return mDishComponent;
    }
}
