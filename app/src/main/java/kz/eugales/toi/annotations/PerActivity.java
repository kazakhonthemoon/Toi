package kz.eugales.toi.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Adil on 27.11.2016.
 */

@Scope
@Retention(value = RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
