package com.temwa.mynurse;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.temwa.mynurse.alarm.RepeatAlarmReceiverActivity;
import com.temwa.mynurse.dbhelper.TransactionHelper;
import com.temwa.mynurse.models.Transactions;

public class AddTransactioActivity extends Activity implements OnClickListener {
	private Button addTran;
//	private Button clearTran;
	private Button viewTran;
	private TextView stopAlarm;
	private EditText drug_name;
	private EditText number_pill;
	private EditText take_pill;
	private EditText drug_date;
	private EditText rv_date;
	private EditText dose;
	private RadioButton od;
	private RadioButton td;
	private RadioButton bd;
	private RadioButton fd;
	
	String dosage;
	
	int dYear, dMonth, dDay;
	int rYear, rMonth, rDay;
	static final int DIALOG_ID = 0;
	
	private TransactionHelper tTransactionHelper; 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_transactio);
		
		initViews();
		initCalendar();
		
		this.tTransactionHelper = new TransactionHelper(this);
	}

	private void initCalendar() {
		final Calendar cal = Calendar.getInstance();
		
		dYear = cal.get(Calendar.YEAR);
		dMonth = cal.get(Calendar.MONTH);
		dDay = cal.get(Calendar.DAY_OF_MONTH);
		
		rYear = cal.get(Calendar.YEAR);
		rMonth = cal.get(Calendar.MONTH);
		rDay = cal.get(Calendar.DAY_OF_MONTH);
		
	}

	private void initViews() {
		this.addTran = (Button) findViewById(R.id.save);
	//	this.clearTran = (Button) findViewById(R.id.btclear);
		this.viewTran = (Button) findViewById(R.id.btViewTransaction);
		this.stopAlarm = (TextView) findViewById(R.id.StopAlarm);
		this.drug_name = (EditText) findViewById(R.id.etMedName);
		this.drug_date = (EditText) findViewById(R.id.etMedDate);
		this.number_pill = (EditText) findViewById(R.id.etPillCount);
		this.take_pill = (EditText) findViewById(R.id.etDose);
		this.rv_date = (EditText) findViewById(R.id.etRvDate);
		this.bd = (RadioButton) findViewById(R.id.rbBD);
		this.od = (RadioButton) findViewById(R.id.rbOD);
		this.td = (RadioButton) findViewById(R.id.rbTD);
		this.fd = (RadioButton) findViewById(R.id.rbFD);
		this.dose = (EditText) findViewById(R.id.etdosage);
		
		addTran.setOnClickListener(this);
	//	clearTran.setOnClickListener(this);
		viewTran.setOnClickListener(this);
		stopAlarm.setOnClickListener(this);
		drug_date.setOnClickListener(this);
		rv_date.setOnClickListener(this);
		dose.setVisibility(View.GONE);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_transactio, menu);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.save:
			saveTransactions();
			break;
	/*		
		case R.id.btclear:
			clearViews();
			break;
		*/	
		case R.id.btViewTransaction:
			viewTransactions();
			break;
			
		case R.id.etMedDate:
			drugDateDialog();
			break;
			
		case R.id.etRvDate:
			reviewDateDialog();
			break;
			
		case R.id.StopAlarm:
			stopAlarm();
			break;
			
		default:
				break;
		}
	}

	private void stopAlarm() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(AddTransactioActivity.this, RepeatAlarmReceiverActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(AddTransactioActivity.this, 3, intent, 0);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.cancel(pendingIntent);
	}

	private void saveTransactions() {
		Editable drugname = drug_name.getText();
		Editable drugdate = drug_date.getText();
		Editable numberpill = number_pill.getText();
		Editable takepill = take_pill.getText();
		Editable rvdate = rv_date.getText();
		Editable taketimes = dose.getText();
		
		if (!TextUtils.isEmpty(drugname) && !TextUtils.isEmpty(drugdate) && !TextUtils.isEmpty(numberpill) 
				&& !TextUtils.isEmpty(takepill) &&	!TextUtils.isEmpty(rvdate)) {
			
			Transactions createdTransaction = tTransactionHelper.createTransaction(drugname.toString(), drugdate.toString(), rvdate.toString(), 
					numberpill.toString(), takepill.toString(), taketimes.toString());
			Toast.makeText(this, "Transaction added successfully", Toast.LENGTH_LONG).show();
			clearViews();
			finish();
			viewTransactions();			
		} else {
			Toast.makeText(this, "Some fields are empty", Toast.LENGTH_LONG).show();
		}
		
	}

	private void viewTransactions() {
		Intent intent = new Intent(this, ListTransactionActivity.class);
		startActivityForResult(intent, 0);
		
	}

	private void reviewDateDialog() {
		OnDateSetListener listener = new OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				rYear = year;
				rMonth = monthOfYear +1;
				rDay = dayOfMonth;
				
				rv_date.setText(rDay +"/"+ rMonth +"/"+ rYear);
			}
		};
		
		DatePickerDialog dpDialog = new DatePickerDialog(this, listener, rYear, rMonth, rDay);
		dpDialog.show();
		
	}

	public void drugDateDialog() {
		OnDateSetListener listener = new OnDateSetListener() {
			
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				dYear = year;
				dMonth = monthOfYear +1;
				dDay = dayOfMonth;
				
				drug_date.setText(dDay +"/"+ dMonth +"/"+ dYear);
			}
		}; 
		
		DatePickerDialog dpDialog = new DatePickerDialog(this, listener, dYear, dMonth, dDay);
		dpDialog.show();
		
	}
	
	public void doseClick(View v) {
		
		boolean checked = ((RadioButton)v).isChecked();
		
		switch (v.getId()) {
		case R.id.rbOD:
			if (checked)
				dosage = "Once a day";
				dose.setText(dosage);
				odAlarm();
			break;
		case R.id.rbBD:
			if (checked)
				dosage = "Twice a day";
				dose.setText(dosage);
				bdAlarm();
			break;
		case R.id.rbTD:
			if (checked)
				dosage = "Three Times a day";
				dose.setText(dosage);
				tdAlarm();
			break;
		case R.id.rbFD:
			if (checked)
				dosage = "Four Times a day";
				dose.setText(dosage);
				fdAlarm();
			break;
		default:
			break;
		}
	}

	private void fdAlarm() {
		//6 hourly
		int i = 21600; // = Integer.parseInt(string)
		Intent intent = new Intent(AddTransactioActivity.this, RepeatAlarmReceiverActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(AddTransactioActivity.this, 3, intent, 0);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 1000), i * 1000, pendingIntent);
		 
	}

	private void tdAlarm() {
		//8 hourly
		int i =  28800; // = Integer.parseInt(string)
		Intent intent = new Intent(AddTransactioActivity.this, RepeatAlarmReceiverActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(AddTransactioActivity.this, 3, intent, 0);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 1000), i * 1000, pendingIntent);
	
	}

	private void bdAlarm() {
		//12 hourly
		int i = 43200; // = Integer.parseInt(string)
		Intent intent = new Intent(AddTransactioActivity.this, RepeatAlarmReceiverActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(AddTransactioActivity.this, 3, intent, 0);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 1000), i * 1000, pendingIntent);
	
	}

	private void odAlarm() {
		//24 hourly
		int i = 86400; // = Integer.parseInt(string)
		Intent intent = new Intent(AddTransactioActivity.this, RepeatAlarmReceiverActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(AddTransactioActivity.this, 3, intent, 0);
		AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (i * 1000), i * 1000, pendingIntent);
	
	}

	private void clearViews() {
		drug_name.setText("");
		number_pill.setText("");
		take_pill.setText("");
		drug_date.setText("");
		rv_date.setText("");
		od.setChecked(false);
		bd.setChecked(false);
		td.setChecked(false);
		fd.setChecked(false);
	}
}
