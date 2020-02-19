package mk.ukim.finki.mpip.aud1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import mk.ukim.finki.mpip.aud1.adapters.TextViewAdapter;

public class TextRecyclerViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private TextViewAdapter textViewAdapter;
    private List<String> data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view_text_view);
        data = loadData();
        initUI();
    }

    private void initUI() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        textViewAdapter = new TextViewAdapter(TextRecyclerViewActivity.this,data);
        recyclerView.setAdapter(textViewAdapter);

    }

    private List<String> loadData() {
        List<String> a = new ArrayList<>();
        a.add(new String("data1"));
        a.add(new String("data2"));
        a.add(new String("data3"));
        return a;
    }

    public void addNewItem(View view) {
        textViewAdapter.addNewItemToTheList("New Item");
    }
}
