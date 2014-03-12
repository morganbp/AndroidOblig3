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
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class JSONMovieRequest {

	public interface OnMovieRequestListener {
		public void dataLoaded();
	}

	public static String tag_url = "imdburl";
	public static String tag_genre = "genres";
	public static String tag_votes = "votes";
	public static String tag_rating = "rating";
	public static String tag_title = "title";
	public static String tag_year = "year";

	private OnMovieRequestListener caller;

	private JSONObject jsonObject;
	private static final String jsonUrl = "http://deanclatworthy.com/imdb/?q=";

	public JSONMovieRequest(String movieSearch, Context c) {
		try {
			caller = (OnMovieRequestListener) c;
		} catch (ClassCastException ex) {
			Log.e(JSONMovieRequest.class.getName(),
					"Context c must implement OnMovieRequestLister");
		}
		String urlText = jsonUrl + movieSearch;
		new SearchForMovieTask().execute(urlText);
	}

	public boolean isSearchSuccess(){
		try{
			jsonObject.getString(tag_title);
		}catch(JSONException e){
			return false;
		}
		return true;
	}
	
	public String getTitle() {
		String title;
		try {
			title = jsonObject.getString(tag_title);
		} catch (JSONException e) {
			Log.d("hei", "hva skjer? " + e.getMessage());
			return null;
		}
		return title;
	}

	public String getURL() {
		String url;
		try {
			url = jsonObject.getString(tag_url);
		} catch (JSONException e) {
			return null;
		}
		return url;
	}

	public String[] getGenres() {
		String[] genres;
		try {
			String genresString = jsonObject.getString(tag_genre);
			genres = genresString.split(",");
		} catch (JSONException e) {
			return null;
		}
		return genres;
	}

	public long getVotes() {
		long votes;
		try {
			String votesString = jsonObject.getString(tag_votes);
			votes = Long.parseLong(votesString);
		} catch (JSONException e) {
			return -1;
		}
		return votes;
	}

	public int getYear() {
		int year;
		try {
			String yearString = jsonObject.getString(tag_year);
			year = Integer.parseInt(yearString);
		} catch (JSONException e) {
			return -1;
		}
		return year;
	}

	public float getRating() {
		float rating;
		try {
			String ratingString = jsonObject.getString(tag_rating);
			rating = Float.parseFloat(ratingString);
		} catch (JSONException e) {
			return -1.0f;
		}
		return rating;
	}

	protected String getJSONString(String search) throws IOException {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		try {
			URLEncoder.encode(search.trim(), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return "Invalid URL";
		}
		HttpGet httpGet;

		try {
			httpGet = new HttpGet(search.replaceAll("\\s+", "%20"));
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			return "Invalid parameter";
		}
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
			} else {
				Log.e(MainActivity.class.toString(), "Failed to downlad file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		}
		return builder.toString();
	}

	private class SearchForMovieTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... params) {

			try {
				String jsonString = getJSONString(params[0]);
				jsonObject = new JSONObject(jsonString);
				return jsonString;
			} catch (IOException ex) {
				return "Couldn't retrive web page.";
			} catch (JSONException ex) {
				ex.printStackTrace();
				return "Couldn't parse JSONObject";
			}

		}

		@Override
		protected void onPostExecute(String result) {
			caller.dataLoaded();
		}

	}

}
