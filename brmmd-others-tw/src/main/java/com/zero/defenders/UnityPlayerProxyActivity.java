package com.zero.defenders;

import com.unity3d.player.*;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @deprecated Use UnityPlayerNativeActivity instead.
 */
public class UnityPlayerProxyActivity extends Activity
{
	protected void onCreate (Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		Intent intent = new Intent(this, com.zero.defenders.UnityPlayerNativeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		Bundle extras = getIntent().getExtras();
		if (extras != null)
			intent.putExtras(extras);
		startActivity(intent);
	}
}
