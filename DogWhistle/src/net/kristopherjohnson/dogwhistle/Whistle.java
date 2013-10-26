package net.kristopherjohnson.dogwhistle;

import android.content.Context;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

public class Whistle {

	public static final String TAG = "Whistle";

	/**
	 * Whenever a whistle is blown, it broadcasts an intent with this action
	 */
	public static final String ACTION_WHISTLE_BLOWN = "Whistle.ACTION_WHISTLE_BLOWN";

	/**
	 * 48000 is the native sampling rate of the HTC One
	 */
	private static final int sampleRate = 48000;

	private static final int streamType = AudioManager.STREAM_ALARM;
	private static final int channelConfig = AudioFormat.CHANNEL_OUT_MONO;
	private static final int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
	private static final int mode = AudioTrack.MODE_STATIC;

	private final Context context;

	public Whistle(Context context) {
		this.context = context;
	}

	public void blow(int durationMillis) {
		final short[] audioData = generatePCM16SquareWave(durationMillis);
		startPlayingPCM16AudioData(audioData);
		broadcastIntentWithAction(ACTION_WHISTLE_BLOWN);
	}

	private short[] generatePCM16SquareWave(int durationMillis) {
		final short[] audioData = new short[sampleRate];
		for (int i = 0; i < sampleRate; i += 2) {
			short amplitude = (short) Math.round(Math.sin(Math.PI * (double) i
					/ (double) sampleRate)
					* Short.MAX_VALUE);
			audioData[i] = (short) -amplitude;
			audioData[i + 1] = amplitude;
		}
		return audioData;
	}

	private void startPlayingPCM16AudioData(final short[] audioData) {
		AudioTrack track = new AudioTrack(streamType, sampleRate,
				channelConfig, audioFormat, audioData.length * 2, mode);
		track.write(audioData, 0, audioData.length);
		Log.d(TAG, "Start playing audio track");
		track.play();
	}

	private void broadcastIntentWithAction(String action) {
		LocalBroadcastManager.getInstance(context).sendBroadcast(
				new Intent(action));
	}
}
