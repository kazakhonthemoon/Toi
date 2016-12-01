package kz.eugales.toi.di;

import dagger.Module;
import dagger.Provides;
import kz.eugales.toi.annotations.PerActivity;
import kz.eugales.toi.services.DishService;
import retrofit2.Retrofit;

/**
 * Created by Adil on 27.11.2016.
 */

@Module
public class DishModule {

    @Provides
    @PerActivity
    DishService provideDishService(Retrofit retrofit){
        return retrofit.create(DishService.class);
    }
}
