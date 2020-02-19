package mk.ukim.finki.mpip.aud1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class FlickrItemDetailsActivity extends AppCompatActivity {

    private ImageView img;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flickr_item_details);
        bindViews();
        initViews();
    }

    private void bindViews() {
        img = findViewById(R.id.img);
    }

    private void initViews() {
        String url = getIntent().getStringExtra(getString(R.string.str_extra_image_url));
        Glide.with(FlickrItemDetailsActivity.this)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .crossFade()
                .into(img);
    }
}
