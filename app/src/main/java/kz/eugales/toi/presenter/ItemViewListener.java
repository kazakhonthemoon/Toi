package kz.eugales.toi.presenter;

import java.util.List;

import io.realm.RealmModel;
import io.realm.RealmResults;
import rx.Observable;

/**
 * Created by Adil on 13.08.2016.
 */
public interface ItemViewListener<T> {

    void onCompleted();
    void onError(Throwable throwable);
    void onItems(T item);
    Observable<T> getItems();

}
