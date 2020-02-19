package mk.ukim.finki.mpip.aud1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LinearLayoutActivity extends AppCompatActivity {

    private EditText editTextView;
    private TextView txtResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.linear_layout_activity);
        initUI();
    }

    public void initUI() {
        editTextView = (EditText) findViewById(R.id.editTextView);
        txtResult = (TextView) findViewById(R.id.txtResult);
    }

    public void onSubmitClick(View view) {
        txtResult.setText(editTextView.getText().toString());

    }
}
