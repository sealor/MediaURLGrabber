package io.github.sealor.mediaurlgrabber;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.github.sealor.mediaurlgrabber.grabber.MediaUrlGrabber;

public class MainActivity extends AppCompatActivity {

	public static final String LOG_TAG = "MediaURLGrabber";

	TextView urlTextView;
	TextView logTextView;
	Button sendUrlButton;

	MediaUrlGrabber mediaUrlGrabber = new MediaUrlGrabber();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		urlTextView = findViewById(R.id.urlTextView);
		logTextView = findViewById(R.id.logTextView);
		sendUrlButton = findViewById(R.id.send_url_button);

		final Intent intent = getIntent();
		final String action = intent.getAction();

		Log.i(LOG_TAG, "Action: " + action + "\n");

		new Thread(new Runnable() {
			@Override
			public void run() {
				schedulePrintLog();

				Log.i(LOG_TAG, "Start URL requesting ...\n");

				final String videoPageUrl = intent.getStringExtra(Intent.EXTRA_TEXT);
				Log.i(LOG_TAG, "Video Page URL: " + videoPageUrl + "\n");

				try {
					final String videoUrl = mediaUrlGrabber.grabMediaUrl(videoPageUrl);
					Log.i(LOG_TAG, "Video URL: " + videoUrl + "\n");

					if (Intent.ACTION_SEND.equals(action) && videoUrl != null) {
						urlTextView.post(new Runnable() {
							@Override
							public void run() {
								schedulePrintLog();

								urlTextView.setText(videoPageUrl);
							}
						});

						sendUrlButton.post(new Runnable() {
							@Override
							public void run() {
								schedulePrintLog();

								sendUrlButton.setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										schedulePrintLog();

										Intent koreIntent = new Intent();
										koreIntent.setPackage("org.xbmc.kore");
										koreIntent.setAction(Intent.ACTION_SEND);
										koreIntent.putExtra(Intent.EXTRA_TEXT, videoUrl);
										koreIntent.setType("text/plain");
										startActivity(koreIntent);

										Log.i(LOG_TAG, "Send XBMC Intent: " + intent + "\n");
									}
								});
							}
						});
					}
				} catch (Throwable e) {
					Log.e(LOG_TAG, "Video URL not available", e);
					schedulePrintLog();
				}
			}
		}).start();

		schedulePrintLog();
	}

	public void schedulePrintLog() {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				printLog();
			}
		});
	}

	public void printLog() {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, -5);
		String time = format.format(cal.getTime());

		String[] cmd = new String[]{"logcat", "-d", "-t", time, "-v", "long", "-s", LOG_TAG + ":V", "AndroidRuntime:E"};

		BufferedReader bufferedReader = null;
		try {
			Process process = Runtime.getRuntime().exec(cmd);
			bufferedReader = new BufferedReader(
					new InputStreamReader(process.getInputStream()));

			StringBuilder log = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null)
				if (!line.isEmpty()) {
					log.append(line);
					log.append("\n");
				}

			logTextView.setText(log.toString());
		} catch (IOException ignored) {
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ignored) {
			}
		}
	}

}
