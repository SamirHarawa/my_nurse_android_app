package com.temwa.mynurse.alarm;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.temwa.mynurse.R;

public class RepeatAlarmReceiverActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm);
		
		Button stopAlarm = (Button) findViewById(R.id.StopAlarm);
		
		stopAlarm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.StopAlarm:
			finish();
			break;
		default:
			break;
		}
		
	}
	

}
