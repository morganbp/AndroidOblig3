package no.morganbp.oblig3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	EditText editTextSearch;
	TextView textViewSearch;
	Button buttonSearch;
	String searchString, jsonString;
	final String jsonUrl = "http://deanclatworthy.com/imdb/?q=";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGUI();
    }

	private void initGUI() {
		editTextSearch = (EditText)findViewById(R.id.etSearch);
		textViewSearch = (TextView)findViewById(R.id.tvSearchText);
		buttonSearch = (Button)findViewById(R.id.bSearch);
		
		buttonSearch.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				searchString = editTextSearch.getText().toString();
				String urlText = jsonUrl + searchString;
				ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
				if(networkInfo != null && networkInfo.isConnected()){
					new SearchForMovieTask().execute(urlText);
				}else{
					textViewSearch.setText("No Internet connection available");
				}
			}
			
		});
		
	}

	protected String searchForMovie(String search) throws IOException {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		try {
			URLEncoder.encode(search.trim(),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return "Invalid URL";
		}
		HttpGet httpGet;
		
		try{
			httpGet = new HttpGet(search.replaceAll("\\s+", "%20"));
		}catch(IllegalArgumentException ex){
			ex.printStackTrace();
			return "Invalid parameter";
		}
		try{
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if(statusCode == 200){
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while((line = reader.readLine()) != null){
					builder.append(line);
				}
			}else{
				Log.e(MainActivity.class.toString(), "Failed to downlad file");
			}
		}catch(ClientProtocolException e){
			e.printStackTrace();
		}
		return builder.toString();
		
	}
	
	public void parseJSON(String jsonString) {
		
		try {
			JSONObject parentObject = new JSONObject(jsonString);
			String url = parentObject.getString("imdburl");
			textViewSearch.setText(url);
		} catch (JSONException e) {
			textViewSearch.setText("couldn't find movie");
		}
		
	}
	
	private class SearchForMovieTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {
			
			try{
				return searchForMovie(params[0]);
			}catch(IOException ex){
				return "Couldn't retrive web page.";
			}
			
		}

		@Override
		protected void onPostExecute(String result) {
			parseJSON(result);
		}
		
		
	}


	

   
    
}
