package com.temwa.mynurse.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.temwa.mynurse.R;
import com.temwa.mynurse.models.Profile;

public class ListProfilesAdapter extends BaseAdapter {
	
	public static final String TAG = "ListProfileAdapter";
	
	private List<Profile> pItems;
	private LayoutInflater pInflater;
	
	public ListProfilesAdapter(Context context, List<Profile> listProfiles) {
		this.setItems(listProfiles);
		this.pInflater = LayoutInflater.from(context);
	}

	public void setItems(List<Profile> pItems) {
		this.pItems = pItems;
	}
	
	public List<Profile> getItems() {
		return pItems;
	}

	@Override
	public int getCount() {
		return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
	}

	@Override
	public Profile getItem(int position) {
		return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getId() :  0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		
		if (v == null) {
			v = pInflater.inflate(R.layout.list_item_profile, parent, false);
			holder = new ViewHolder();
			holder.txtFname = (TextView) v.findViewById(R.id.txtFname);
			holder.txtLname = (TextView) v.findViewById(R.id.txtLname);
			holder.txtSex = (TextView) v.findViewById(R.id.txtSex);
			holder.txtDob = (TextView) v.findViewById(R.id.txtDob);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		
		//fill row data
		Profile currentProfile = getItem(position);
		if (currentProfile != null) {
		holder.txtFname.setText(currentProfile.getProfile_fnName());
		holder.txtLname.setText(currentProfile.getProfile_lname());
		holder.txtSex.setText(currentProfile.getProfile_gender());
		holder.txtDob.setText(currentProfile.getProfile_dob());
		}
		return v;
	}
	
	class ViewHolder {
		TextView txtFname;
		TextView txtLname;
		TextView txtSex;
		TextView txtDob;
	}

}
