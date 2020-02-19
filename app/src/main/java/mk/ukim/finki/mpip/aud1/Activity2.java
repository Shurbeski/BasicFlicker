package mk.ukim.finki.mpip.aud1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Activity2 extends AppCompatActivity {

    public final static String INTENT_FOR_ACTIVITY_2="INTENT_FOR_ACTIVITY_2";

    TextView txtMyText = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);
        txtMyText = findViewById(R.id.txtMyText);

    }

    public void callActivity1(View view) {
        Intent intent = new Intent(Activity2.this,MainActivity.class);
        startActivity(intent);
    }
}
