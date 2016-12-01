package kz.eugales.toi.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Adil on 29.11.2016.
 */

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    Application application();
}
