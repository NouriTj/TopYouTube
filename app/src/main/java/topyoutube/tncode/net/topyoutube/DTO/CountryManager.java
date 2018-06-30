package topyoutube.tncode.net.topyoutube.DTO;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;
import topyoutube.tncode.net.topyoutube.DAO.Country;
import topyoutube.tncode.net.topyoutube.R;
import topyoutube.tncode.net.topyoutube.Utils.Constants;
import topyoutube.tncode.net.topyoutube.Utils.Util;

/**
 * Created by noure on 27/05/2017.
 */

public class CountryManager {

    public static Realm realm;

    public static void initCountries(Context context) {
        realm.init(context);
        realm = Realm.getDefaultInstance();
        RealmResults<Country> countriesFromDB = realm.where(Country.class).findAll();
        Object[] countries = countriesFromDB.toArray();
        if (countries == null || countries.length == 0) {
            setCountries(context);
        }
    }

    @NonNull
    public static RealmResults<Country> getCountries() {
        RealmResults<Country> countriesFromDB = realm.where(Country.class).findAll();
        //RealmResults<Country> countriesFromDB = realm.where(Country.class).contains("name","Tunis").findAll();

        //countriesFromDB.sort("Name","Desc");
        return countriesFromDB.sort("name") ;
        //return countriesFromDB.sort("name").subList(0, countriesFromDB.size());
/*
        for (Object countryCode :    countriesFromDB.toArray()) {
            countries.add()
            language += "\nCountry Code = " + ((Country)countryCode).getCode() + ", Country Name = " + ((Country)countryCode).getName();
        }
        return language;
        //*/
    }

    private static void setCountries(Context context) {
        String[] locales = Locale.getISOCountries();
        String countriesCodes = context.getString(R.string.countries_code);

        for (String countryCode : locales) {

            if (countriesCodes.contains(countryCode)) {
                Locale locale = new Locale("", countryCode);
                realm.beginTransaction();
                Country country = realm.createObject(Country.class); // Create a new object
                country.setCode(locale.getCountry());
                country.setName(locale.getDisplayCountry());
                realm.commitTransaction();
            }
        }
    }

    public static List<Country> getCountries(Context context, String filterByName) {

        RealmResults<Country> countriesFromDB = realm.where(Country.class).contains("name",filterByName).findAll();
        //countriesFromDB.sort("Name","Desc");
        return countriesFromDB.sort("name").subList(0, countriesFromDB.size());
        //return countriesFromDB.sort("name").subList(0, countriesFromDB.size());
/*
        for (Object countryCode :    countriesFromDB.toArray()) {
            countries.add()
            language += "\nCountry Code = " + ((Country)countryCode).getCode() + ", Country Name = " + ((Country)countryCode).getName();
        }
        return language;
        //*/
    }
    @NonNull
    public static String getPathFlagCountry(Context context) {
        return Util.getPathAssetFlag( getSelectedCountryCode(context));
    }

    @NonNull
    public static void setPathFlagCountry(Context context, String countryCode) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(Constants.SELECTED_COUNTRY_Key, countryCode);
        editor.commit();
    }
    public static String getSelectedCountryCode(Context context) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String countryCode = settings.getString(Constants.SELECTED_COUNTRY_Key, Constants.DEFAULT_COUNTRY_Key);
        return countryCode;
    }
}
