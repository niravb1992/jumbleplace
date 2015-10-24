package niravb.jumbleplace.game;

import android.content.Context;
import android.os.Bundle;

import java.util.HashMap;
import java.util.List;

import niravb.jumbleplace.Utilities;

class GameViewModel {

    public boolean gameInProgress;
    public final HashMap<String, String> jumbledToOriginalCountries; // using HashMap instead of Map
    // here because HashMap is serializable, unlike Map.
    public String[] jumbledCountries;
    public int nextCountryIndex;
    public int score;
    public int numCountriesRemaining;

    public GameViewModel(Context context) {
        jumbledToOriginalCountries = new HashMap<>();
        jumbledCountries = new String[]{};
        nextCountryIndex = 0;
        score = 0;
        numCountriesRemaining = Utilities.getNumberOfCountriesPreferenceValue(context);
    }

    @SuppressWarnings("unchecked")
    public GameViewModel(Bundle bundle) {
        this.gameInProgress = bundle.getBoolean(GameStateKeys.GAME_IN_PROGRESS);
        this.jumbledToOriginalCountries = (HashMap<String, String>)
                bundle.getSerializable(GameStateKeys.JUMBLED_TO_ORIGINAL_COUNTRIES);
        this.jumbledCountries = bundle.getStringArray(GameStateKeys.JUMBLED_COUNTRIES);
        this.nextCountryIndex = bundle.getInt(GameStateKeys.NEXT_COUNTRY_INDEX);
        this.score = bundle.getInt(GameStateKeys.SCORE);
        this.numCountriesRemaining = bundle.getInt(GameStateKeys.NUM_COUNTRIES_REMAINING);
    }

    public Bundle populateBundleForStateSave(Bundle outState) {
        outState.putBoolean(GameStateKeys.GAME_IN_PROGRESS, gameInProgress);
        outState.putSerializable(GameStateKeys.JUMBLED_TO_ORIGINAL_COUNTRIES, jumbledToOriginalCountries);
        outState.putStringArray(GameStateKeys.JUMBLED_COUNTRIES, jumbledCountries);
        outState.putInt(GameStateKeys.NEXT_COUNTRY_INDEX, nextCountryIndex - 1);
        outState.putInt(GameStateKeys.SCORE, score);
        outState.putInt(GameStateKeys.NUM_COUNTRIES_REMAINING, numCountriesRemaining);
        return outState;
    }

    public void populateCountriesDataFromAPI(List<String> data) {

        jumbledToOriginalCountries.clear();

        for (String country : data) {
            String shuffledCountry = Utilities.shuffleString(country);
            jumbledToOriginalCountries
                    .put(shuffledCountry, country);
        }

        jumbledCountries =
                jumbledToOriginalCountries
                        .keySet()
                        .toArray(new
                                String[jumbledToOriginalCountries.size()]);

    }

    private class GameStateKeys {
        public static final String GAME_IN_PROGRESS = "gameInProgress";
        public static final String JUMBLED_TO_ORIGINAL_COUNTRIES = "jumbledToOriginalCountries";
        public static final String JUMBLED_COUNTRIES = "jumbledCountries";
        public static final String NEXT_COUNTRY_INDEX = "nextCountryIndex";
        public static final String SCORE = "score";
        public static final String NUM_COUNTRIES_REMAINING = "numCountriesRemaining";
    }

}
