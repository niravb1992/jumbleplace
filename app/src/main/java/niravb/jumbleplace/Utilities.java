package niravb.jumbleplace;

import android.content.Context;
import android.preference.PreferenceManager;

import java.util.Random;

public class Utilities {

    /**
     * Fisher-Yates shuffle https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
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

}
