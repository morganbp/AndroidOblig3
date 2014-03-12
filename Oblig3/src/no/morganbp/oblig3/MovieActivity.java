package no.morganbp.oblig3;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MovieActivity extends Activity {

	private static int match_parent = LinearLayout.LayoutParams.MATCH_PARENT;
	private static int wrap_content = LinearLayout.LayoutParams.WRAP_CONTENT;

	TextView tvTitle, tvURL, tvRating, tvVotes, tvGenres;
	String title, url;
	String[] genres;
	long votes;
	int year;
	float rating;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		collectData();
		LinearLayout ll = createGUI();
		setContentView(ll);

	}

	private void collectData() {
		Bundle bundle = getIntent().getBundleExtra(MainActivity.EXTRA_MESSAGE);
		title = bundle.getString(JSONMovieRequest.tag_title);
		url = bundle.getString(JSONMovieRequest.tag_url);
		genres = bundle.getStringArray(JSONMovieRequest.tag_genre);
		votes = bundle.getLong(JSONMovieRequest.tag_votes);
		year = bundle.getInt(JSONMovieRequest.tag_year);
		rating = bundle.getFloat(JSONMovieRequest.tag_rating);
	}

	private LinearLayout createGUI() {

		tvTitle = new TextView(this);
		tvTitle.setText(title + " (" + Integer.toString(year) + ")");
		tvTitle.setTextSize(22f);
		tvTitle.setLayoutParams(new LinearLayout.LayoutParams(match_parent,
				wrap_content));
		String genresString = "";
		for(int i = 0; i < genres.length;i++){
			if(i < (genres.length-1))
				genresString += genres[i] + ", ";
			else
				genresString += genres[i];
		}
		
		tvRating = new TextView(this);
		tvRating.setText("Rating: " + Float.toString(rating));
		tvTitle.setLayoutParams(new LinearLayout.LayoutParams(match_parent,
				wrap_content));
		
		tvVotes = new TextView(this);
		tvVotes.setText("Votes: " + Long.toString(votes));
		tvTitle.setLayoutParams(new LinearLayout.LayoutParams(match_parent,
				wrap_content));
		
		tvGenres = new TextView(this);
		tvGenres.setText("Genres: " + genresString);
		tvTitle.setLayoutParams(new LinearLayout.LayoutParams(match_parent,
				wrap_content));
		
		
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		ll.addView(tvTitle);
		ll.addView(tvRating);
		ll.addView(tvVotes);
		ll.addView(tvGenres);
		return ll;
	}

}
