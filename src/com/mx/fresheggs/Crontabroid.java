package com.mx.fresheggs;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class Crontabroid extends Activity {
	Toast mToast;
	boolean mIsStart;
	Button mStartButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);

		// Watch for button clicks.
		mStartButton = (Button) findViewById(R.id.start_repeating);
		mStartButton.setOnClickListener(mStartRepeatingListener);
		Button button = (Button) findViewById(R.id.stop_repeating);
		button.setOnClickListener(mStopRepeatingListener);
//		button=(Button)findViewById(R.id.restart_repeating);
//		button.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				EditText text=(EditText)findViewById(R.id.mytime);
//				Toast.makeText(getApplicationContext(), "time="+text.getText(),Toast.LENGTH_LONG).show();
//				cancelAlarm();
//				startAlarm(Integer.parseInt(text.getText().toString()));
//			}
//		});
	}



	private OnClickListener mStartRepeatingListener = new OnClickListener() {
		public void onClick(View v) {
			EditText text = (EditText) findViewById(R.id.mytime);
			int time = 1;
			try {
				time = Integer.parseInt(text.getText().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (!mIsStart) {
				startAlarm(time);
				mIsStart = true;
				mStartButton.setText("restart");
			} else {
				cancelAlarm();
				startAlarm(time);

			}
		}

	};

	private void startAlarm(int minutes) {
		Intent intent = new Intent(Crontabroid.this, CrontabTip.class);
		PendingIntent sender = PendingIntent.getBroadcast(Crontabroid.this, 0,
				intent, 0);

		long firstTime = SystemClock.elapsedRealtime();
		firstTime += minutes* 60 * 1000;

		// Schedule the alarm!
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstTime,
				minutes * 60* 1000, sender);

		// Tell the user about what we did.
		if (mToast != null) {
			mToast.cancel();
		}
		// mToast = Toast.makeText(Crontabroid.this,
		// R.string.repeating_scheduled,
		// Toast.LENGTH_LONG);
		// mToast.show();
	}

	private void cancelAlarm() {
		// Create the same intent, and thus a matching IntentSender, for
		// the one that was scheduled.
		Intent intent = new Intent(Crontabroid.this, CrontabTip.class);
		PendingIntent sender = PendingIntent.getBroadcast(Crontabroid.this, 0,
				intent, 0);

		// And cancel the alarm.
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.cancel(sender);

		// Tell the user about what we did.
		if (mToast != null) {
			mToast.cancel();
		}
		// mToast = Toast.makeText(Crontabroid.this,
		// R.string.repeating_unscheduled,
		// Toast.LENGTH_LONG);
		// mToast.show();
	}

	private OnClickListener mStopRepeatingListener = new OnClickListener() {
		public void onClick(View v) {
			cancelAlarm();
		}

	};
}
