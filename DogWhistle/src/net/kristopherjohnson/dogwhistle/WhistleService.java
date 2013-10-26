package net.kristopherjohnson.dogwhistle;

import android.app.IntentService;
import android.content.Intent;

public class WhistleService extends IntentService {

	public static final String TAG = "WhistleService";

	public static final String ACTION_BLOW_WHISTLE = "WhistleService.ACTION_BLOW_WHISTLE";

	private static int WHISTLE_BLOW_DURATION_MILLIS = 2000;

	public WhistleService() {
		super(TAG);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (ACTION_BLOW_WHISTLE.equals(intent.getAction())) {
			blowWhistle();
		}
	}

	private void blowWhistle() {
		Whistle w = new Whistle(this);
		w.blow(WHISTLE_BLOW_DURATION_MILLIS);
	}
}
