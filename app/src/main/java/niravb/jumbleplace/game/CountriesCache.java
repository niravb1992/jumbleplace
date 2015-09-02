package niravb.jumbleplace.game;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.Loader;

import java.util.List;

import niravb.jumbleplace.Utilities;
import niravb.jumbleplace.data.models.CachedCountryModel;

class CountriesCache {

    private final Context context;

    public CountriesCache(Context context) {
        this.context = context;
    }

    public boolean shouldReload() {

        int userNumCountriesPref = Utilities.getNumberOfCountriesPreferenceValue(context);
        int cachedCountriesCount = CachedCountryModel.getCount(context);
        return cachedCountriesCount == 0 || cachedCountriesCount != userNumCountriesPref;

    }

    public void empty() {

        CachedCountryModel.deleteAll(context);

    }

    public void fill(List<String> countryNames) {

        for (String country : countryNames) {

            new CachedCountryModel(context, country).create();

        }

    }

    public Loader<Cursor> fetchData() {
        return CachedCountryModel.getAll(context);
    }

}
