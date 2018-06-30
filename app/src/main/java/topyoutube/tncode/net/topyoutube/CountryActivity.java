package topyoutube.tncode.net.topyoutube;

import android.graphics.Movie;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import io.realm.CountryRealmProxy;
import topyoutube.tncode.net.topyoutube.Adapter.CountryListAdapter;
import topyoutube.tncode.net.topyoutube.Adapter.DividerItemDecoration;
import topyoutube.tncode.net.topyoutube.Adapter.RecyclerTouchListener;
import topyoutube.tncode.net.topyoutube.DTO.CountryManager;

public class CountryActivity extends AppCompatActivity {

    RecyclerView lvCountries;
    CountryListAdapter countryAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setTitle(R.string.country_activity_title);// show title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//show back button

        CountryManager.initCountries(getApplicationContext());

        lvCountries = (RecyclerView) findViewById(R.id.lv_countries);

        countryAdaper = new CountryListAdapter(this, CountryManager.realm, CountryManager.getCountries());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        lvCountries.setLayoutManager(mLayoutManager);
        lvCountries.setHasFixedSize(true);
        lvCountries.setItemAnimator(new DefaultItemAnimator());
        lvCountries.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        lvCountries.setAdapter(countryAdaper);
        lvCountries.addOnItemTouchListener(
                new RecyclerTouchListener(getApplicationContext(), new RecyclerTouchListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        CountryManager.setPathFlagCountry(getApplicationContext(), countryAdaper.getItem(position).getCode());
                        finish();
                    }
                })
        );
        /*
        lvCountries.setRecyclerListener(countryAdaper); .setAdapter(countryAdaper);
        lvCountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CountryManager.setPathFlagCountry(getApplicationContext(), countryAdaper.getItem(position).getCode());
                finish();
            }
        });
        //*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_country, menu);

        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                    countryAdaper.getFilter().filter(newText);

                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();//finish your activity
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
