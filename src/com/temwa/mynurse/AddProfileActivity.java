package com.temwa.mynurse;

import java.util.Calendar;

import com.temwa.mynurse.dbhelper.ProfileHelper;
import com.temwa.mynurse.models.Profile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class AddProfileActivity extends Activity implements OnClickListener {
	public static final String TAG = "AddProfile";
	
	private Button addProfile;
	private Button clear;
	private Button viewProfile;
	private EditText fname;
	private EditText lname;
	private EditText dob;
	private EditText sex;
	private RadioButton male;
	private RadioButton female;
	
	String profileGender;
	int year_x, month_x, day_x;
	static final int DIALOG_ID = 0;
	
	private ProfileHelper pProfileHelper;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_profile);
		
		initViews();
		initCalendar();
		
		this.pProfileHelper = new ProfileHelper(this);
	}

	private void initCalendar() {
		final Calendar cal = Calendar.getInstance();
		year_x = cal.get(Calendar.YEAR);
		month_x = cal.get(Calendar.MONTH);
		day_x = cal.get(Calendar.DAY_OF_MONTH);
		
	}

	private void initViews() {
		this.addProfile = (Button) findViewById(R.id.btAddProfile);
		this.clear = (Button) findViewById(R.id.btClear);
		this.viewProfile = (Button) findViewById(R.id.btViewProfile);
		this.fname = (EditText) findViewById(R.id.etName);
		this.lname = (EditText) findViewById(R.id.etlname);
		this.dob = (EditText) findViewById(R.id.etDOB);
		this.male = (RadioButton) findViewById(R.id.rbM);
		this.female = (RadioButton) findViewById(R.id.rbF);
		this.sex = (EditText) findViewById(R.id.etSex);
		//(RadioGroup) findViewById(R.id.sex);
		
		this.addProfile.setOnClickListener(this);
		this.clear.setOnClickListener(this);
		this.viewProfile.setOnClickListener(this);
		this.dob.setOnClickListener(this);
		
		this.sex.setVisibility(View.GONE);
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btAddProfile:
			addProfile();
			break;
		case R.id.btClear:
			clearViews();
			break;
		case R.id.btViewProfile:
			viewProfile();
			break;
		case R.id.etDOB:
			showDialog(DIALOG_ID);
			break;
			default:
				break;
		}
		
	}	

	private void addProfile() {
		Editable firstname = fname.getText();
		Editable lastname = lname.getText();
		Editable birthDate = dob.getText();
		Editable profilesex = sex.getText();
		
		if (!TextUtils.isEmpty(firstname) && !TextUtils.isEmpty(lastname)
				&& !TextUtils.isEmpty(profilesex) && !TextUtils.isEmpty(birthDate)) {
			//add profile
			Profile createdProfile = pProfileHelper.createProfile(firstname.toString(), lastname.toString(), 
					profilesex.toString(), birthDate.toString());
			Log.d(TAG, "added profile : " + createdProfile.getProfile_fnName() +" "+ createdProfile.getProfile_lname());
		//	Intent intent = new Intent();
		//	intent.putExtra(ListProfileActivity.EXTRA_ADDED_PROFILE, createdProfile);
		//	setResult(RESULT_OK, intent);
			Toast.makeText(this, "Profile created successfully", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(this, AddTransactioActivity.class);
			startActivityForResult(intent, 0);
			finish();
		} else {
			Toast.makeText(this, "Some fields are empty", Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		if(id == DIALOG_ID) 
			return new DatePickerDialog(this, dpickerListener, year_x, month_x, day_x);
		return null;
	}

	private DatePickerDialog.OnDateSetListener dpickerListener = new DatePickerDialog.OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			year_x = year;
			month_x = monthOfYear+1;
			day_x = dayOfMonth;
			
			dob.setText(day_x+"/"+month_x +"/"+year_x);
		}
	};

	private void viewProfile() {
		Intent intent = new Intent(this, ListProfileActivity.class);
		startActivityForResult(intent, 0);
		
	}

	private void clearViews() {
		fname.setText("");
		lname.setText("");
		dob.setText("");
		sex.setText("");
		male.setChecked(false);
		female.setChecked(false);
		
	}
	
	public void sexClick(View v) {
		boolean checked = ((RadioButton)v).isChecked();
		
		switch (v.getId()) {
		case R.id.rbM:
			if(checked)
				profileGender = "Male";
			sex.setText(profileGender);
			break;
			
		case R.id.rbF:
			if (checked)
				profileGender = "Female";
			sex.setText(profileGender);
			break;
			default:
				break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
}
