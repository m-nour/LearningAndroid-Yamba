package learning.android.yamba;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PrefsAct extends PreferenceActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);
	}
}
