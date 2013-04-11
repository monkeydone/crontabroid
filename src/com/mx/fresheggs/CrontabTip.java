/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mx.fresheggs;


import java.util.ArrayList;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.widget.Toast;

public class CrontabTip extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
    	Vibrator b=(Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
//    	b.vibrate(3000);
    	b.vibrate(30);
    	
		BeepSound beepSound = BeepSound.getInstance(context, R.raw.sound_receive_push);
		beepSound.play();
        Toast.makeText(context, "repeating_received", Toast.LENGTH_SHORT).show();
    }

public static class BeepSound{
	public static Context mContext;
	public static int mResId;
	
	private BeepSound(Context context, int resId){
		mContext = context;
		mResId = resId;
	}
	
	private static BeepSound instance = null;
	public static BeepSound getInstance(Context context, int resId) {
		if(instance==null) {
			instance=new BeepSound(context,resId);
		}
		return instance;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void play(){
		int id = mResId;
		ArrayList list = new ArrayList();
		list.add(mContext);
		list.add(id);
		
		 new AsyncTask<ArrayList, Integer, Void>(){
			 @Override
			protected Void doInBackground(ArrayList... params) {
				 Context mContext = (Context) params[0].get(0);
				 int resId = (Integer) params[0].get(1);
				 SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_RING, 1);
				 int soundID = soundPool.load(mContext, resId, 100);
				 AudioManager mgr = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
				 float streamVolumeCurrent = mgr.getStreamVolume(AudioManager.STREAM_RING);
				 float streamVolumeMax = mgr.getStreamMaxVolume(AudioManager.STREAM_RING);
				 final float stream = streamVolumeCurrent / streamVolumeMax;
				 SoundPool.OnLoadCompleteListener OnLoadCompleteListener = new SoundPool.OnLoadCompleteListener() {
					 @Override
					 public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
						 soundPool.play(sampleId, stream, stream, 1, 0, 1);
					 }
				 };
				 soundPool.setOnLoadCompleteListener(OnLoadCompleteListener);
				return null;
			 }
			 
		 }.execute(list);
	}
}

}
