package mk.ukim.finki.mpip.aud1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityViews extends AppCompatActivity {

    private Button btnSubmit;
    private TextView txtSubmittedText;
    private EditText editText;

    public ActivityViews() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_relative);
        initUI();
        initEvents();
    }

    private void initUI() {
        this.btnSubmit = (Button) findViewById(R.id.btnSubmit);
        txtSubmittedText = (TextView) findViewById(R.id.txtSubmittedText);
        editText = (EditText) findViewById(R.id.editText);
    }
    private void initEvents() {
        this.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtSubmittedText.setText(editText.getText().toString());
            }
        });
    }
}
