package kz.eugales.toi;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import kz.eugales.toi.pojo.Dish;
import kz.eugales.toi.pojo.Review;

public class DetailActivity extends AppCompatActivity {

    Realm realm;

    Dish dish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        int id = getIntent().getExtras().getInt("dishId");

        realm = Realm.getDefaultInstance();
        dish = realm.where(Dish.class).equalTo("id", id).findFirst();

        ViewHolder holder = new ViewHolder(getCurrentFocus());
        holder.mDish = dish;
        holder.reviewList = new ArrayList<>();//////////////////////////////
        holder.mTvName.setText(dish.getName());
        holder.mTvDescription.setText(dish.getDescription());
        holder.mTvPrice.setText("T" + String.valueOf(dish.getPrice()));

        Picasso.with(getApplicationContext())
                .load("http://img.povar.ru/uploads/8b/4e/89/f5/uzbekskii_plov-4860.jpg")
                .fit()
                .into(holder.mIvBackground);
        holder.mIvBackground.setColorFilter(Color.parseColor("#aaaaaa"), PorterDuff.Mode.MULTIPLY);

        holder.mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Добавлено в корзину",Snackbar.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);

        }
    }

    public class ViewHolder {
        public Dish mDish;
        public List<Review> reviewList;
        public final View mView;
        public final ImageView mIvBackground;
        public final TextView mTvName;
        public final TextView mTvDescription;
        public final TextView mTvPrice;
        public final ImageButton mBtnAdd;
        public final LinearLayout mReviews;

        public ViewHolder (View view){
            mView= view;
            mIvBackground = (ImageView) findViewById(R.id.dishBackgroundDetail);
            mTvName = (TextView) findViewById(R.id.nameDetail);
            mTvDescription = (TextView) findViewById(R.id.descriptionDetail);
            mTvPrice = (TextView) findViewById(R.id.priceDetail);
            mBtnAdd = (ImageButton) findViewById(R.id.btnAddDetail);
            mReviews = (LinearLayout) findViewById(R.id.review_container);
        }
    }
}
