package learning.android.yamba;

import org.apache.http.client.HttpClient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;

public class StatusAct extends Activity implements OnClickListener, TextWatcher{
	
	private static final String TAG="StatusAct";
	EditText editText;
	Button updateButton;
//	Twitter twitter;
	TextView textCount;
//	SharedPreferences prefs;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.status);
        
        editText = (EditText) findViewById(R.id.editText);
        updateButton = (Button) findViewById(R.id.buttonUpdate);
        
        updateButton.setOnClickListener(this);
        
        textCount=(TextView)findViewById(R.id.textCount);
        textCount.setText("140");
        textCount.setTextColor(Color.GREEN);
        editText.addTextChangedListener(this);
                
//        prefs=PreferenceManager.getDefaultSharedPreferences(this);
//        prefs.registerOnSharedPreferenceChangeListener(this);
    }
    
//    private Twitter getTwitter() {
//		if(twitter==null){
//			String username, password, apiRoot;
//			username=prefs.getString("username", "");
//			password=prefs.getString("password", "");
//			apiRoot=prefs.getString("apiRoot", "http://yamba.marakana.com/api");
//		
//	        twitter = new Twitter(username, password);
//	        twitter.setAPIRootUrl(apiRoot);
//		}
//		
//		return twitter;
//	}
    
    class PostToTwitter extends AsyncTask<String, Integer, String>{
    	
    	@Override
    	protected String doInBackground(String... statuses){
    		try{
    			YambaApp yamba=(YambaApp) getApplication();
    			Twitter.Status status=yamba.getTwitter().updateStatus(statuses[0]);
    			return status.text;
    		}
    		catch (TwitterException e) {
				Log.e(TAG, e.toString());
				e.printStackTrace();
				return "Failed to post";
			}
    	}
    	
    	@Override
    	protected void onProgressUpdate(Integer... values){
    		super.onProgressUpdate(values);
    	}
    	
    	@Override
    	protected void onPostExecute(String result){
    		Toast.makeText(StatusAct.this, result, Toast.LENGTH_LONG).show();
    	}
    }
    
    public void afterTextChanged(Editable statusText) {
		int count=140-statusText.length();
		textCount.setText(Integer.toString(count));
		textCount.setTextColor(Color.GREEN);
		if(count<10)
			textCount.setTextColor(Color.YELLOW);
		if(count<0)
			textCount.setTextColor(Color.RED);
	}
    
    public void beforeTextChanged(CharSequence s, int start, int before, int count){
    	
    }
    
    public void onClick(View v) {
    	try{
    		((YambaApp) getApplication()).getTwitter().setStatus(editText.getText().toString());
    	}
//		new PostToTwitter().execute(status);
    	catch(TwitterException e){
    		Log.d(TAG, "Twitter setStatus Falied"+e);    		
    	}
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater=getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.itemServiceStart:
			startService(new Intent(this, UpdaterService.class));
			break;
			
		case R.id.itemServiceStop:
			stopService(new Intent(this, UpdaterService.class));
			break;
			
		case R.id.itemPrefs:
			startActivity(new Intent(this, PrefsAct.class));
			break;

		default:
			break;
		}
		return true;
	}
}