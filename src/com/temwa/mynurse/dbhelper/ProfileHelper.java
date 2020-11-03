package com.temwa.mynurse.dbhelper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.temwa.mynurse.models.Profile;

public class ProfileHelper {
	
	public static final String TAG = "ProfileHelper";
	
	//Database fields
	private SQLiteDatabase pDatabase;
	private DBHelper pDBHelper;
	private Context pContext;
	private String[] pAllColumns = {  DBHelper.PROFILE_FNAME, 
			DBHelper.PROFILE_LNAME, DBHelper.PROFILE_GENDER, DBHelper.PROFILE_DOB };
	
	public ProfileHelper(Context context) {
		this.pContext = context;
		pDBHelper = new DBHelper(context);
		
		//open database
		try {
			open();
		} catch (SQLException e) {
			Log.e(TAG, "SQLException on opening database " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void open() throws SQLException {
		pDatabase = pDBHelper.getWritableDatabase();
	}
	
	public void close() {
		pDBHelper.close();
	}
	
	public Profile createProfile(String f_name, String l_name, String sex, String birth_date) {
		ContentValues pcv = new ContentValues();
		pcv.put(DBHelper.PROFILE_FNAME, f_name);
		pcv.put(DBHelper.PROFILE_LNAME, l_name);
		pcv.put(DBHelper.PROFILE_GENDER, sex);
		pcv.put(DBHelper.PROFILE_DOB, birth_date);
		long insertId = pDatabase.insert(DBHelper.TABLE_PROFILE, null, pcv);
		
		Cursor cursor = pDatabase.query(DBHelper.TABLE_PROFILE, pAllColumns,
				null, null, null, null, null);
		
		cursor.moveToFirst();
		Profile newProfile = cursorToProfile(cursor);
		cursor.close();
		return newProfile;
	}
	
	public Profile getProfile() {
		
		Cursor cursor = pDatabase.query(DBHelper.TABLE_PROFILE, pAllColumns, null, null, null, null, null);
		//String[] users = new String[] {};
		cursor.moveToFirst();
		 Profile getdata = cursorToProfile(cursor);
		 cursor.close();
		  
		
		return getdata;
		
	}
	

	
	public void deleteProfile(Profile profile) {
		long id = profile.getId();
		
		
	}
	
	public List<Profile> getAllProfiles() {
		List<Profile> listProfile = new ArrayList<Profile>();
		
		Cursor cursor = pDatabase.query(DBHelper.TABLE_PROFILE, pAllColumns, null, null, null, null, null);
		
		if (cursor != null) {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Profile profile = cursorToProfile(cursor);
				listProfile.add(profile);
				cursor.moveToNext();
			}
			cursor.close();
		}
		return listProfile;
	}
/*	
	public Profile getProfileById(long id) {
		Cursor cursor = pDatabase.query(DBHelper.TABLE_PROFILE, pAllColumns,
				null, null, null, null);
		
		if (cursor != null) {
			cursor.moveToFirst();
		}
		Profile profile = cursorToProfile(cursor);
		return profile;
	}
*/
	protected Profile cursorToProfile(Cursor cursor) {
		Profile profile = new Profile();
	//	profile.setId(cursor.getLong(0));
		profile.setProfile_fnName(cursor.getString(0));
		profile.setProfile_lname(cursor.getString(1));
		profile.setProfile_gender(cursor.getString(2));
		
		return profile;
	}

	
}
