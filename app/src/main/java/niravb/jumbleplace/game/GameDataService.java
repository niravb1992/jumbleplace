package niravb.jumbleplace.game;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

import niravb.jumbleplace.Utilities;

public class GameDataService extends IntentService {

    public static final String KEY_COUNTRIES_LIST = "countriesList";

    public GameDataService() {
        super("GameDataService");
    }

    private ArrayList<String> getListOfCountriesFromJSONData(JsonObject jsonObject) {

        ArrayList<String> countries = new ArrayList<>();
        JsonArray countriesArray = jsonObject.getAsJsonArray("countries");

        for (int i = 0; i < countriesArray.size(); i++) {

            countries.add(countriesArray.get(i).getAsString());

        }

        return countries;

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final ResultReceiver receiver = intent.
                getParcelableExtra(GameController.KEY_GAME_DATA_RECEIVER);

        int prefNumCountries = Utilities.
                getNumberOfCountriesPreferenceValue(getApplicationContext());

        int nCountries = (receiver != null) ? prefNumCountries * 2 : prefNumCountries;

        Ion.with(getApplicationContext())
                .load(Utilities.API_ENDPOINT + "?n=" + nCountries)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {

                        if (result != null) {

                            ArrayList<String> countries = getListOfCountriesFromJSONData(result);

                            CountriesCache countriesCache = new CountriesCache(getApplicationContext());

                            countriesCache.empty();

                            if (receiver != null) {

                                List<String> countriesToCache = countries.subList(0,
                                        countries.size() / 2);

                                List<String> countriesForCurrentGame =
                                        countries.subList(countries.size() / 2,
                                                countries.size());

                                countriesCache.fill(countriesToCache);

                                Bundle bundle = new Bundle();
                                bundle.putStringArrayList(KEY_COUNTRIES_LIST,
                                        new ArrayList<>(countriesForCurrentGame));

                                receiver.send(0, bundle);
                            } else {

                                countriesCache.fill(countries);

                            }
                        }

                    }
                });


    }
}
