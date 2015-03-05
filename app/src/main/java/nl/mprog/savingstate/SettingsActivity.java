package nl.mprog.savingstate;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by Equifilm on 5-3-2015.
 */
public class SettingsActivity extends PreferenceActivity {
    
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.preferences);
    }
    
}
