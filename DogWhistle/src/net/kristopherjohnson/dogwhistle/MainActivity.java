package net.kristopherjohnson.dogwhistle;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	public static final String TAG = "MainActivity";

	private BroadcastReceiver whistleBlownReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button button = (Button) findViewById(R.id.button_whistle);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				blowWhistle();
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerWhistleBlownBroadcastReceiver();
	}

	@Override
	protected void onPause() {
		super.onPause();
		unregisterWhistleBlownBroadcastReceiver();
	}

	private LocalBroadcastManager getLocalBroadcastManager() {
		return LocalBroadcastManager.getInstance(this);
	}

	private void registerWhistleBlownBroadcastReceiver() {
		whistleBlownReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				onWhistleBlown();
			}
		};
		IntentFilter filter = new IntentFilter(Whistle.ACTION_WHISTLE_BLOWN);
		getLocalBroadcastManager().registerReceiver(whistleBlownReceiver,
				filter);
	}

	private void unregisterWhistleBlownBroadcastReceiver() {
		if (whistleBlownReceiver != null) {
			getLocalBroadcastManager().unregisterReceiver(whistleBlownReceiver);
			whistleBlownReceiver = null;
		}
	}

	private void blowWhistle() {
		Intent intent = new Intent(WhistleService.ACTION_BLOW_WHISTLE, null,
				this, WhistleService.class);
		startService(intent);
	}

	private void onWhistleBlown() {
		Toast.makeText(this, R.string.text_whistle_blown, Toast.LENGTH_SHORT)
				.show();
	}
}
