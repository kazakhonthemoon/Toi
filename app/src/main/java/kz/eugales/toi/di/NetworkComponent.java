package kz.eugales.toi.di;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * Created by Adil on 27.11.2016.
 */

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {
    //void inject(DishModule dishModule);
    Retrofit retrofit();
}
