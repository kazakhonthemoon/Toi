package kz.eugales.toi;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import javax.inject.Inject;

import kz.eugales.toi.pojo.Dish;
import kz.eugales.toi.services.DishService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDishActivity extends AppCompatActivity  {

    private static final int PICK_IMAGE = 1;
    ViewHolder holder;

    @Inject
    DishService mDishService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        holder = new ViewHolder(findViewById(R.id.activity_add_dish));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAddDish);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        holder.mIvAddImage.setClickable(true);
        holder.mIvAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //downloadDialogFragment.show(getSupportFragmentManager(), "ddf");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        ((DishesApplication) getApplication()).getDishComponent().inject(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == PICK_IMAGE) {

                    CropImage.activity(data.getData())
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(3, 1)
                            .start(this);


                } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    holder.mImageUri = result.getUri();
                    Picasso.with(getApplicationContext())
                            .load(result.getUri())
                            .into(holder.mIvAddImage);
                }
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_dish, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_add_dish_ready:

                mDishService.insertDish(createDish()).enqueue(new Callback<Dish>() {
                    @Override
                    public void onResponse(Call<Dish> call, Response<Dish> response) {

                        Intent intent = new Intent();
                        intent.putExtra("dish", response.body());
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Dish> call, Throwable t) {
                        t.printStackTrace();
                    }
                });


                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }



    private Dish createDish() {
        String name = holder.mEtName.getText().toString();
        String description = holder.mEtDescription.getText().toString();
        String price = holder.mEtPrice.getText().toString();
        String url = holder.mImageUri!=null?holder.mImageUri.getPath():null;

        Dish dish = new Dish();
        dish.setName(name);
        dish.setCategory(1);
        dish.setDescription(description);
        dish.setPrice(Float.valueOf(price));
        dish.setPicurl(url!=null?url:null);
        return dish;
    }

    private class ViewHolder {
        public Uri mImageUri;
        public final View mView;
        public final ImageView mIvAddImage;
        public final EditText mEtName;
        public final EditText mEtDescription;
        public final EditText mEtPrice;

        public ViewHolder(View view) {
            mView = view;
            mIvAddImage = (ImageView) mView.findViewById(R.id.ivAddImage);
            mEtName = (EditText) mView.findViewById(R.id.etAddName);
            mEtDescription = (EditText) mView.findViewById(R.id.etAddDescription);
            mEtPrice = (EditText) mView.findViewById(R.id.etAddPrice);
        }
    }

}
