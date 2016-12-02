package kz.eugales.toi;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.List;
import javax.inject.Inject;
import kz.eugales.toi.adapter.DishAdapter;
import kz.eugales.toi.pojo.Dish;
import kz.eugales.toi.presenter.DishPresenter;
import kz.eugales.toi.presenter.ItemViewListener;
import kz.eugales.toi.services.DishService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;

public class DishFragment extends Fragment implements ItemViewListener<List<Dish>> {
    private static final String ARG_CATEGORY_ID = "category_id";
    private int mCategoryId;
    private int mColumnCount;
    private DishAdapter mDishAdapter;
    public DishPresenter mDishPresenter;
    @Inject
    DishService mDishService;
    private OnListFragmentInteractionListener mListener;
    Realm realm;

    public DishFragment() {
        this.mCategoryId = 1;
        this.mColumnCount = 2;
    }

    public static DishFragment newInstance(int categoryId) {
        DishFragment fragment = new DishFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mCategoryId = getArguments().getInt(ARG_CATEGORY_ID);
        }
        this.mDishPresenter = new DishPresenter(this);
        this.mDishPresenter.onCreate();
        this.mDishAdapter = new DishAdapter(getActivity().getApplicationContext(), this.mListener);
        this.realm = Realm.getDefaultInstance();

        ((DishesApplication) getActivity().getApplicationContext()).getDishComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mDishPresenter.onResume();
        refreshRealm();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dish_list, container, false);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (this.mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, this.mColumnCount));
            }
            recyclerView.setAdapter(this.mDishAdapter);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            this.mListener = (OnListFragmentInteractionListener) context;
            return;
        }
        throw new RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onItems(List<Dish> dishes) {
        this.mDishAdapter.addDish(dishes);
    }

    @Override
    public Observable<List<Dish>> getItems() {
        RealmResults<Dish> dishRealmResults = this.realm.where(Dish.class).equalTo("category", Integer.valueOf(this.mCategoryId)).findAll();
        return Observable.just(dishRealmResults.subList(0, dishRealmResults.size()));
    }

    private void refreshRealm() {

        mDishService.getDishes().enqueue(new Callback<List<Dish>>() {


            @Override
            public void onResponse(Call<List<Dish>> call, Response<List<Dish>> response) {
                realm.beginTransaction();
                realm.insertOrUpdate(response.body());
                realm.commitTransaction();

                //mDishAdapter.notifyDataSetChanged();


                mDishPresenter.fetchDishes();
            }

            @Override
            public void onFailure(Call<List<Dish>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction();
        void onAddToBasketInteraction(int dishId);
        void onStartDetailActivity(int dishId);
    }
}