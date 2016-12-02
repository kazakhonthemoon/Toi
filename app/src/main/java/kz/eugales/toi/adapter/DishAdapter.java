package kz.eugales.toi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import kz.eugales.toi.DishFragment.OnListFragmentInteractionListener;
import kz.eugales.toi.R;
import kz.eugales.toi.pojo.Dish;

/**
 * Created by Adil on 27.11.2016.
 */

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.ViewHolder> {



    public ArrayList<Dish> DISHES;
    private final OnListFragmentInteractionListener mListener;
    //private ImageLoader imageLoader;
    private DisplayImageOptions options;
    Context mContext;


    public DishAdapter(Context context, OnListFragmentInteractionListener listener) {
        mListener = listener;
        DISHES = new ArrayList<>();
        //imageLoader = getConfiguredImageLoader(context);
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_dish, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mDish = DISHES.get(position);

        //imageLoader.displayImage("drawable://" + R.raw.uzb_plov, holder.mIvBackground, options);
        //imageLoader.displayImage("http://img.povar.ru/uploads/8b/4e/89/f5/uzbekskii_plov-4860.jpg", holder.mIvBackground, options);
        Picasso.with(mContext)
                .load(holder.mDish.getPicurl())
                .fit()
                .into(holder.mIvBackground);
        holder.mIvBackground.setColorFilter(Color.parseColor("#aaaaaa"), PorterDuff.Mode.MULTIPLY);

        holder.mTvName.setText(holder.mDish.getName());
        holder.mTvDescription.setText(holder.mDish.getDescription());
        holder.mTvPrice.setText("T " + String.valueOf(holder.mDish.getPrice()));
        holder.mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener!=null){
                    mListener.onAddToBasketInteraction(holder.mDish.getId());
                }
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onStartDetailActivity(holder.mDish.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return DISHES.size();
    }

    public void addDish(List<Dish> dishes){
        DISHES.clear();
        DISHES.addAll(dishes);
        notifyDataSetChanged();
    }

    private ImageLoader getConfiguredImageLoader(Context context){
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                // You can pass your own memory cache implementation
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                .build();

        options = new DisplayImageOptions.Builder()
                .displayer(new RoundedBitmapDisplayer(10)) //rounded corner bitmap
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();


        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
        return imageLoader;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public Dish mDish;
        public final View mView;
        public final ImageView mIvBackground;
        public final TextView mTvName;
        public final TextView mTvDescription;
        public final TextView mTvPrice;
        public final ImageButton mBtnAdd;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mIvBackground = (ImageView) itemView.findViewById(R.id.dishBackground);
            mTvName = (TextView) itemView.findViewById(R.id.name);
            mTvDescription = (TextView) itemView.findViewById(R.id.description);
            mTvPrice = (TextView) itemView.findViewById(R.id.price);
            mBtnAdd = (ImageButton) itemView.findViewById(R.id.btnAdd);
        }
    }
}
