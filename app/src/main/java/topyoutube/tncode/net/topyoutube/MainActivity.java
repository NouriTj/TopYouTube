package topyoutube.tncode.net.topyoutube;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import topyoutube.tncode.net.topyoutube.Adapter.RecyclerTouchListener;
import topyoutube.tncode.net.topyoutube.Adapter.VideoListAdapter;
import topyoutube.tncode.net.topyoutube.DAO.Video;
import topyoutube.tncode.net.topyoutube.DAO.YTVideoAPI.ListVideoResult;
import topyoutube.tncode.net.topyoutube.DTO.CountryManager;
import topyoutube.tncode.net.topyoutube.DTO.VideoManager;

public class MainActivity extends AppCompatActivity   implements YouTubePlayer.OnInitializedListener {

public static final String API_KEY = "AIzaSyCe6tORd9Ch4lx-9Ku5SQ476uS9OtZYsWA";
public static final String VIDEO_ID = "o7VVHhK9zf0";

    RecyclerView lvVideos;
    FloatingActionButton fab;
    List<Video> viedoList = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lvVideos = (RecyclerView) findViewById(R.id.lv_videos);
        lvVideos.setNestedScrollingEnabled(false);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        lvVideos.setLayoutManager(mLayoutManager);
        lvVideos.setHasFixedSize(true);
        lvVideos.addOnItemTouchListener(
                new RecyclerTouchListener(getApplicationContext(), new RecyclerTouchListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        String s="TODO";
                        Toast.makeText(MainActivity.this, s,Toast.LENGTH_LONG).show();//todo add action video
                    }
                })
        );

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CountryActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loadVideoList(String countryCode, int maxResult) {
        final Call<ListVideoResult> call = VideoManager.mService.listVideos(countryCode, maxResult);
        call.enqueue(new Callback<ListVideoResult>() {
            @Override
            public void onResponse(Call<ListVideoResult> call, Response<ListVideoResult> response) {

                if(response.isSuccessful()) {
                    //Log.d("MainActivity", "posts loaded from API: "+response.body().getItems().get(0).getSnippet().getTitle());
                    viedoList = VideoManager.getVideoList(response.body());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    VideoListAdapter videoListAdapter = new VideoListAdapter(MainActivity.this, viedoList);
                    lvVideos.setAdapter(videoListAdapter);
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }else {
                    int statusCode  = response.code();
                    // handle request errors depending on status code
                    //Log.d("MainActivity", "posts loaded from API statusCode : "+statusCode);
                }
            }

            @Override
            public void onFailure(Call<ListVideoResult> call, Throwable t) {
                Log.d("MainActivity", "error loading from API"+t.getMessage()+"\n"+t.getStackTrace().toString());
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Picasso.with(MainActivity.this).load(CountryManager.getPathFlagCountry(getApplicationContext()))
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        fab.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Toast.makeText(MainActivity.this, "Picasso onBitmapFailed", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });

                // remove loading if the country doesn't change
                progressDialog = ProgressDialog.show(this, "", getResources().getString(R.string.loading) );
                loadVideoList(CountryManager.getSelectedCountryCode(getApplicationContext()), 10);
    }
}
