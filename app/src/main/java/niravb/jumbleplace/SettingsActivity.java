package niravb.jumbleplace;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;


public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }


    public static class GamePrefsFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.game_preferences);

            findPreference(getString(R.string.pref_number_of_countries)).
                    setOnPreferenceChangeListener(this);

        }


        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {

            String val = newValue.toString();
            preference.setSummary(val);

            return true;
        }
    }

}

