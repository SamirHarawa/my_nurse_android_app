package com.temwa.mynurse;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.temwa.mynurse.adapter.ListProfilesAdapter;
import com.temwa.mynurse.dbhelper.ProfileHelper;
import com.temwa.mynurse.models.Profile;

public class ListProfileActivity extends Activity implements OnItemLongClickListener, OnItemClickListener, OnClickListener {
	
	public static final String TAG = "ListProfile";
	
	public static final String EXTRA_ADDED_PROFILE = "extra_key_added_profile";
	
	private ListView pListViewProfiles;
	private TextView mTxtEmptyListProfiles;
	private ImageButton addProfile;
	
	private ListProfilesAdapter pAdapter;
	private List<Profile> pListProfiles;
	private ProfileHelper pProfileHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_profile);
		
		initViews();
		
		pProfileHelper = new ProfileHelper(this);
		pListProfiles = pProfileHelper.getAllProfiles();
		
		
		if (pListProfiles != null && !pListProfiles.isEmpty()) {
			pAdapter = new ListProfilesAdapter(this, pListProfiles);
			pListViewProfiles.setAdapter(pAdapter);
			
			addProfile.setEnabled(false);
		} else {
			mTxtEmptyListProfiles.setVisibility(View.VISIBLE);
			pListViewProfiles.setVisibility(View.GONE);
			
			addProfile.setEnabled(true);
		}
	}

	private void initViews() {
		this.pListViewProfiles = (ListView) findViewById(R.id.list_profiles);
		this.mTxtEmptyListProfiles = (TextView) findViewById(R.id.txt_empty_list_profiles);
		this.addProfile = (ImageButton) findViewById(R.id.btn_add_profiles);
		
		this.pListViewProfiles.setOnItemClickListener(this);
		this.pListViewProfiles.setOnItemLongClickListener(this);
		this.addProfile.setOnClickListener(this);
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_profile, menu);
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
		case R.id.btn_add_profiles:
			Intent intent = new Intent(this, AddProfileActivity.class);
			startActivityForResult(intent, 0);
			break;
		default:
			break;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Profile clickedProfile = pAdapter.getItem(position);
		Log.d(TAG, "clickedItem : "+clickedProfile.getProfile_fnName() +" "+clickedProfile.getProfile_lname());
		
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
			long id) {
		Profile clickedProfile = pAdapter.getItem(position);
		Log.d(TAG, "longClickedItem : "+clickedProfile.getProfile_fnName()+ " " +clickedProfile.getProfile_lname());
		showDeleteDialogConfirmation(clickedProfile);
		return true;
	}

	private void showDeleteDialogConfirmation(final Profile clickedProfile) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		
		alertDialogBuilder.setTitle("Delete");
		alertDialogBuilder.setMessage("Are you sure you want to delete profile \""+clickedProfile.getProfile_fnName()+ " "+ clickedProfile.getProfile_lname());
		
		//set position button yes message
		alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//delete profile
				if (pProfileHelper != null) {
					pProfileHelper.deleteProfile(clickedProfile);
					pListProfiles.remove(clickedProfile);
					
					//refresh the list
					if (pListProfiles.isEmpty()) {
						pListProfiles = null;
						pListViewProfiles.setVisibility(View.GONE);
						mTxtEmptyListProfiles.setVisibility(View.VISIBLE);
					}
					pAdapter.setItems(pListProfiles);
					pAdapter.notifyDataSetChanged();
				}
				dialog.dismiss();
				Toast.makeText(ListProfileActivity.this, "The profile deleted successfully", Toast.LENGTH_LONG).show();
			}
		});
		
		//set neutral button no
		alertDialogBuilder.setNeutralButton(android.R.string.no, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
		
	}
}
