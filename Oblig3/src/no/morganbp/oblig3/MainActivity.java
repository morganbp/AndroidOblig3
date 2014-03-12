package no.morganbp.oblig3;

import no.morganbp.oblig3.JSONMovieRequest.OnMovieRequestListener;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnMovieRequestListener {

	public static final String EXTRA_MESSAGE = "no.morganbp.MainActivity";
	EditText editTextSearch;
	TextView textViewSearch;
	Button buttonSearch;
	JSONMovieRequest movieInfo;
	String searchString;
	Context c;
	boolean loadingData = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		c = this;
		initGUI();
	}

	private void initGUI() {
		editTextSearch = (EditText) findViewById(R.id.etSearch);
		textViewSearch = (TextView) findViewById(R.id.tvSearchText);
		buttonSearch = (Button) findViewById(R.id.bSearch);

		buttonSearch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				searchString = editTextSearch.getText().toString();
				ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if (networkInfo != null && networkInfo.isConnected()) {
					if (!loadingData) {
						loadingData = true;
						movieInfo = new JSONMovieRequest(searchString, c);
					}
				} else {
					textViewSearch.setText("No Internet connection available");
				}

			}

		});

	}

	@Override
	public void dataLoaded() {
		loadingData = false;
		if(movieInfo.isSearchSuccess()){
			Bundle b = new Bundle();
			b.putString(JSONMovieRequest.tag_title, movieInfo.getTitle());
			b.putString(JSONMovieRequest.tag_url, movieInfo.getURL());
			b.putLong(JSONMovieRequest.tag_votes, movieInfo.getVotes());
			b.putFloat(JSONMovieRequest.tag_rating, movieInfo.getRating());
			b.putInt(JSONMovieRequest.tag_year, movieInfo.getYear());
			b.putStringArray(JSONMovieRequest.tag_genre, movieInfo.getGenres());
			Intent intent = new Intent(this, MovieActivity.class);
			intent.putExtra(EXTRA_MESSAGE, b);
			startActivity(intent);
		}else{
			textViewSearch.setText("No results for \"" + searchString + "\"");
		}
		
		
	}

}
