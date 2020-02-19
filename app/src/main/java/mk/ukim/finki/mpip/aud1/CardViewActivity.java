package mk.ukim.finki.mpip.aud1;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import mk.ukim.finki.mpip.aud1.adapters.CardViewAdapter;
import mk.ukim.finki.mpip.aud1.clients.FlickrApiClient;
import mk.ukim.finki.mpip.aud1.models.FlickrItem;
import mk.ukim.finki.mpip.aud1.models.FlickrResponse;
import mk.ukim.finki.mpip.aud1.persistence.repository.FlickItemRepository;
import mk.ukim.finki.mpip.aud1.repository.FlickrApiInterface;
import mk.ukim.finki.mpip.aud1.services.DownloadFlickrItemsService;
import mk.ukim.finki.mpip.aud1.viewmodels.FlickrItemsViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static mk.ukim.finki.mpip.aud1.services.DownloadFlickrItemsService.DATABASE_UPDATED;

public class CardViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<FlickrItem> data = null;
    private CardViewAdapter cardViewAdapter;
    private LinearLayoutManager linearLayoutManager;
    private Handler mHandler;
    private ProgressBar progressBar;
    private FlickItemRepository flickItemRepository;
    private FloatingActionButton fab = null;
    private SearchView searchView = null;
    private UpdatedItemsBroadcastReceiver receiver;
    private FlickrItemsViewModel flickrItemsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.receiver = new UpdatedItemsBroadcastReceiver();
        setContentView(R.layout.activity_card_view);
        mHandler = new Handler();
        flickItemRepository = new FlickItemRepository(CardViewActivity.this);
        flickrItemsViewModel = ViewModelProviders.of(CardViewActivity.this).get(FlickrItemsViewModel.class);

        initViews();
        bindEvents();
        initList();

        initObservers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        // Get the SearchView and set the searchable configuration
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent startServiceIntent = new Intent(CardViewActivity.this,DownloadFlickrItemsService.class);
                startServiceIntent.putExtra(getString(R.string.download_flickr_tag),s);
                startService(startServiceIntent);
//                syncData(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        startProgress();
        IntentFilter filter = new IntentFilter(DATABASE_UPDATED);
        this.registerReceiver(receiver,filter);
    }

    private void initViews() {
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        progressBar = findViewById(R.id.progressBar);
        fab = findViewById(R.id.fab);
    }

    private void bindEvents() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CardViewActivity.this,DownloadFlickrItemsService.class);
                intent.putExtra(getString(R.string.download_flickr_tag),"ФИНКИ");
                CardViewActivity.this.startService(
                        intent);
                Snackbar.make(view, "Syncing", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void initList() {
        recyclerView = findViewById(R.id.recyclerView);
        cardViewAdapter = new CardViewAdapter(CardViewActivity.this, data);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cardViewAdapter);
    }

    private void initObservers() {
        flickrItemsViewModel.getFlickrItems().observe(CardViewActivity.this, new Observer<List<FlickrItem>>() {
            @Override
            public void onChanged(@Nullable List<FlickrItem> flickrItems) {
                cardViewAdapter.updateData(flickrItems);
            }
        });
    }

    private void syncData(String searchTag) {
        FlickrApiInterface service = FlickrApiClient.getRetrofit().create(FlickrApiInterface.class);

        service.getPublicPhotos(searchTag).enqueue(new Callback<FlickrResponse>() {
            @Override
            public void onResponse(Call<FlickrResponse> call, Response<FlickrResponse> response) {
                if (response.isSuccessful()) {
                    flickrItemsViewModel.updateData(response.body().getItems());
                }
            }

            @Override
            public void onFailure(Call<FlickrResponse> call, Throwable t) {

            }
        });
    }

    private void startProgress() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 30; i++) {
                    final int finalProgress = i;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(finalProgress);
                        }
                    });

                }
            }
        }).start();
    }

    public class UpdatedItemsBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            flickItemRepository.listAllFlickrItems().observe(CardViewActivity.this, new Observer<List<FlickrItem>>() {
                @Override
                public void onChanged(@Nullable List<FlickrItem> flickrItems) {
                    cardViewAdapter.updateData(flickrItems);
                    Toast.makeText(CardViewActivity.this,"Synced",Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
