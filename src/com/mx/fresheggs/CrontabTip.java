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


import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.os.Vibrator;
import android.widget.Toast;

public class CrontabTip extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
    	Vibrator b=(Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
    	b.vibrate(3000);
        Toast.makeText(context, "repeating_received", Toast.LENGTH_SHORT).show();
    }
}

