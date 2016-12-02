package kz.eugales.toi.presenter;

import android.content.Context;

import java.util.List;
import kz.eugales.toi.pojo.Dish;
import rx.Observer;

import static rx.Observable.from;

/**
 * Created by Adil on 27.11.2016.
 */

public class DishPresenter extends BasePresenter implements Observer<List<Dish>> {

    ItemViewListener mItemViewListener;

    public DishPresenter(ItemViewListener itemViewListener) {
        this.mItemViewListener = itemViewListener;
    }

    @Override
    public void onCompleted() {
        mItemViewListener.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        mItemViewListener.onError(e);
    }

    @Override
    public void onNext(List<Dish> dishes) {
        mItemViewListener.onItems(dishes);
    }

    public void fetchDishes(){
        unsubscribeAll();
        subscribe(mItemViewListener.getItems(), DishPresenter.this);
    }
}
