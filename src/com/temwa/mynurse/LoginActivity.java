package com.temwa.mynurse;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.temwa.mynurse.adapter.ListProfilesAdapter;
import com.temwa.mynurse.dbhelper.ProfileHelper;
import com.temwa.mynurse.models.Profile;

public class LoginActivity extends Activity implements OnClickListener {
	private Button login;
	private Button register;
	private EditText USERNAME;
	private EditText USERPASS;
	private String username;
	private String userpass;
	public TextView attempts;
	
	private String test_pass;
	private String test_user;
	private String[] users = {};
	
	
	Context ctx = this;
	int counter = 3;
	
	private ProfileHelper pProfileHelper;
	private ListProfilesAdapter pAdapter;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		initView();	
	}
	
	public void userProfile(View v, int position, long id) {
		Profile user_profile = pAdapter.getItem(position);
		String user_name = user_profile.getProfile_fnName();
		String user_pass = user_profile.getProfile_dob();
	}
	
	private void initView() {
		this.login = (Button) findViewById(R.id.btlogin);
		this.register = (Button) findViewById(R.id.btReg);
		this.USERNAME = (EditText) findViewById(R.id.etUsername);
		this.USERPASS = (EditText) findViewById(R.id.etPasswrd);
		this.attempts = (TextView) findViewById(R.id.tvAtt);
		register.setEnabled(false);
		attempts.setVisibility(View.GONE);
		
		this.login.setOnClickListener(this);
		this.register.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
		case R.id.btlogin:
			userLogin();
			break;
			
		case R.id.btReg:
			Intent intent = new Intent(v.getContext(), AddProfileActivity.class);
			startActivityForResult(intent, 0);
			break;
		default:
			break;
		}
		
	}

	private void userLogin() {

		username = USERNAME.getText().toString().trim();
		userpass = USERPASS.getText().toString().trim();
		
		if (username.equals("") && userpass.equals("")) {
			Toast.makeText(getBaseContext(), "Login successfull...\n Welcome", Toast.LENGTH_LONG).show();
			
			Intent intent = new Intent(this, AddTransactioActivity.class);
			startActivityForResult(intent, 0);
		} else {
			Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
			
			attempts.setVisibility(View.VISIBLE);
			attempts.setBackgroundColor(Color.RED);
			counter --;
			attempts.setText(Integer.toString(counter));
			
			if (counter == 0) {
				login.setEnabled(false);
				register.setEnabled(true);
			}
			
		}
		
	}
}
