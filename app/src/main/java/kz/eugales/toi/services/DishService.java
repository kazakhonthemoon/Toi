package kz.eugales.toi.services;

import java.util.List;

import kz.eugales.toi.pojo.Dish;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Adil on 27.11.2016.
 */

public interface DishService {

    @GET("/v1/dishes/cid/{categoryId}")
    Observable<List<Dish>> getDishesByCategory(@Path("categoryId") int categoryId);

    @GET("/v1/dishes/")
    Call<List<Dish>> getDishes();

    @GET("/v1/dishes/did/{dishId}")
    Observable<Dish> getDish(@Path("dishId") int dishId);

    @POST("/v1/dishes/")
    Call<Dish> insertDish(@Body Dish dish);
}
