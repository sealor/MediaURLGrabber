package io.github.sealor.mediaurlgrabber;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.github.sealor.mediaurlgrabber.grabber.ArdMediathek;

public class MainActivity extends AppCompatActivity {

	TextView urlTextView;
	Button sendUrlButton;

	ArdMediathek ardMediathek = new ArdMediathek();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		urlTextView = findViewById(R.id.urlTextView);
		sendUrlButton = findViewById(R.id.send_url_button);

		final Intent intent = getIntent();
		final String action = intent.getAction();

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				final String videoPageUrl = intent.getStringExtra(Intent.EXTRA_TEXT);
				final String videoUrl = ardMediathek.resolveMp4Url(videoPageUrl);

				if (Intent.ACTION_SEND.equals(action) && videoUrl != null) {
					urlTextView.post(new Runnable() {
						@Override
						public void run() {
							urlTextView.setText(videoPageUrl);
						}
					});

					sendUrlButton.post(new Runnable() {
						@Override
						public void run() {
							sendUrlButton.setOnClickListener(new View.OnClickListener() {

								@Override
								public void onClick(View v) {
									Intent koreIntent = new Intent();
									koreIntent.setPackage("org.xbmc.kore");
									koreIntent.setAction(Intent.ACTION_SEND);
									koreIntent.putExtra(Intent.EXTRA_TEXT, videoUrl);
									koreIntent.setType("text/plain");
									startActivity(koreIntent);
								}
							});
						}
					});
				}
			}
		});
		thread.start();
	}

}
