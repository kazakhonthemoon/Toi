package kz.eugales.toi;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.util.List;

import kz.eugales.toi.pojo.Dish;
import kz.eugales.toi.pojo.Review;

public class AddDishActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    ViewHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);
        holder = new ViewHolder(findViewById(R.id.activity_add_dish));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAddDish);

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
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == PICK_IMAGE) {

                    CropImage.activity(data.getData())
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .setAspectRatio(3,1)
                            .start(this);


                } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private class ViewHolder {
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
