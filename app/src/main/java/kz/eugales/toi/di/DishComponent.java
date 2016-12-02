package kz.eugales.toi.di;

import dagger.Component;
import kz.eugales.toi.AddDishActivity;
import kz.eugales.toi.DishFragment;
import kz.eugales.toi.annotations.PerActivity;

/**
 * Created by Adil on 27.11.2016.
 */

@PerActivity
@Component(modules = {DishModule.class}, dependencies = {NetworkComponent.class})
public interface DishComponent {
    void inject(DishFragment dishFragment);
    void inject(AddDishActivity addDishActivity);
}
