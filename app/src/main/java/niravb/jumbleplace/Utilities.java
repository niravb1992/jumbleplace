package niravb.jumbleplace;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import java.util.Random;

public class Utilities {

    public static final int TABLE_ID_COLUMN_INDEX = 0;
    public static final String CREATED_AT_FORMAT = "MM/dd/yyyy h:mm a";
    public static final String API_ENDPOINT = "https://powerful-oasis-8314.herokuapp.com/countries";

    /**
     * Fisher-Yates shuffle https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
     *
     * @param toShuffle The string to shuffle
     * @return The shuffled string
     */
    public static String shuffleString(String toShuffle) {
        char[] toShuffleChars = toShuffle.toCharArray();
        Random r = new Random();
        for (int i = toShuffleChars.length - 1; i >= 1; i--) {
            int j = r.nextInt(i);
            char atJ = toShuffleChars[j];
            toShuffleChars[j] = toShuffleChars[i];
            toShuffleChars[i] = atJ;
        }
        return new String(toShuffleChars);
    }

    public static int getNumberOfCountriesPreferenceValue(Context context) {
        String prefNumOfCountriesKey = context.
                getString(R.string.pref_number_of_countries);
        String prefNumOfCountriesDefaultValue = context.
                getString(R.string.pref_number_of_countries_default);

        return Integer.parseInt(PreferenceManager.
                getDefaultSharedPreferences(context).
                getString(prefNumOfCountriesKey,
                        prefNumOfCountriesDefaultValue));
    }

    public static Intent getScoreShareIntent(Context context, String scoreText) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                String.format(context.getString(R.string.score_share_text), scoreText));
        sendIntent.setType("text/plain");
        return sendIntent;
    }

}
