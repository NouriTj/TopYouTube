package topyoutube.tncode.net.topyoutube.Adapter;

/**
 * Created by noure on 27/05/2017.
 */
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.OrderedRealmCollection;
import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import topyoutube.tncode.net.topyoutube.DAO.Country;
import topyoutube.tncode.net.topyoutube.DTO.CountryManager;
import topyoutube.tncode.net.topyoutube.R;
import topyoutube.tncode.net.topyoutube.Utils.Constants;
import topyoutube.tncode.net.topyoutube.Utils.Util;

public class CountryListAdapter extends RealmRecyclerViewAdapter<Country, RecyclerView.ViewHolder> implements Filterable {

    private final LinearLayout.LayoutParams normalLayoutParams;
    private final LinearLayout.LayoutParams lastLayoutParams;
    Context context;
    Realm realm;
    List<Country> countryList;
    private Filter countriesFilter;

        public CountryListAdapter(Context context, Realm realm, OrderedRealmCollection<Country> countryList) {
            super(context, countryList, true);
            this.context=context;
            this.realm=realm;
        this.countryList = countryList ;
            
            normalLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            lastLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.county_item_list, parent, false);
        CountryHolder holder = new CountryHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        
        if (position == countryList.size() - 1) {
            holder.itemView.setLayoutParams(lastLayoutParams);
        } else {
            holder.itemView.setLayoutParams(normalLayoutParams);
        }

        Country country = getData().get(position);
        CountryHolder mHolder = (CountryHolder) holder;

        mHolder.countryName.setText(country.getName());
        Picasso.with(context).load( Util.getPathAssetFlag(country.getCode())).into(mHolder.countryFlag);
    }

    public void filterResults(String text) {
        text = text == null ? null : text.toLowerCase().trim();
        if(text == null || text.isEmpty()) {
            updateData(realm.where(Country.class).findAll());
        } else {
            updateData(realm.where(Country.class)
                    .beginsWith("name", text, Case.INSENSITIVE) // TODO: change field
                    .findAll());
        }
    }
    @Override
    public Filter getFilter() {
        if (countriesFilter == null)
            countriesFilter = new CountryFilter(this);
        return countriesFilter;
    }

    //class holder to optimize listView
    class CountryHolder extends RecyclerView.ViewHolder{
        TextView countryName;
        ImageView countryFlag;

        public CountryHolder(View itemView) {
            super(itemView);

            countryName = (TextView) itemView.findViewById(R.id.country_name);
            countryFlag = (ImageView) itemView.findViewById(R.id.country_flag);
        }
    }

    //*
    private class CountryFilter extends Filter{
        private final CountryListAdapter adapter;
        private CountryFilter(CountryListAdapter adapter) {
            super();
            this.adapter = adapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            return new FilterResults();
        }

        @Override
        protected void publishResults(CharSequence constraint,
                                      FilterResults results) {
            adapter.filterResults(constraint.toString());
        }
    }//*/
}

