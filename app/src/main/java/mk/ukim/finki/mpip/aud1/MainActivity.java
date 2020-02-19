package mk.ukim.finki.mpip.aud1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnLaunchActivity2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getLifecycle().addObserver(new LifecycleAwareComponent());
        initUI();
        bindEvent();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void initUI() {
             btnLaunchActivity2 = findViewById(R.id.btnLaunchActivity2);
    }

    public void bindEvent() {
        btnLaunchActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callExplicitIntent();
            }
        });
    }

    public void callActivity2(View view ){
        shareToSocialNetwork();
    }

    public void callExplicitIntent() {
        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        startActivity(intent);
    }

    public void callInplicitIntent() {
        Intent intent = new Intent();
        intent.setAction(Activity2.INTENT_FOR_ACTIVITY_2);
        intent.putExtra(Intent.EXTRA_SUBJECT,"My Subject");
        intent.putExtra(Intent.EXTRA_TEXT,"Body of the message");
        intent.setType("text/plain");
        startActivity(intent);
    }

    public void shareToSocialNetwork() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_SUBJECT,"FINKI WEB PAGE");
        intent.putExtra(Intent.EXTRA_TEXT,"https://finki.ukim.mk");
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent,"Share"));
    }


}
