package com.temwa.mynurse.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
	public static final String TAG = "DBHelper";
	
	//profile table
	public static String TABLE_PROFILE = "profile";
	//public static String PROFILE_ID = "_id";
	public static String PROFILE_FNAME = "fname";
	public static String PROFILE_LNAME = "lname";
	public static String PROFILE_GENDER = "gender";
	public static String PROFILE_DOB = "dateofbirth";
	
	//transaction table
	public static String TRAN_TABLE = "transactions";
//	public static String TRAN_ID = PROFILE_ID;
	public static String TRAN_MED_NAME = "drug_name";
	public static String TRAN_MED_DATE = "drug_date";
	public static String TRAN_PILL_COUNT = "no_pills";
	public static String TRAN_TAKE_PILL = "take_pill";
	public static String TRAN_TAKE_TIMES = "take_times";
	public static String TRAN_RV_DATE = "rv_date";
	
	private static final String DATABASE_NAME = "mynurse";
	private static final int DATABASE_VERSION = 1;
	
	//SQL statement of profile table creation
	private static final String SQL_CREATE_TABLE_PROFILE = "CREATE TABLE "+ TABLE_PROFILE + "("
			+ PROFILE_FNAME + " TEXT NOT NULL, "
			+ PROFILE_LNAME + " TEXT NOT NULL, "
			+ PROFILE_GENDER + " TEXT NOT NULL, "
			+ PROFILE_DOB + " TEXT NOT NULL "
			+ ");";
	
	//SQL statement of transactions creation
	private static final String SQL_CREATE_TABLE_TRANSACTION = "CREATE TABLE "+ TRAN_TABLE + "("
			+ TRAN_MED_NAME + " TEXT NOT NULL, "
			+ TRAN_MED_DATE + " TEXT NOT NULL, "
			+ TRAN_PILL_COUNT + " TEXT NOT NULL, "
			+ TRAN_TAKE_PILL + " TEXT NOT NULL, "
			+ TRAN_TAKE_TIMES + " TEXT NOT NULL, "
			+ TRAN_RV_DATE + " TEXT "
			+ ");";
	

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(SQL_CREATE_TABLE_PROFILE);
		database.execSQL(SQL_CREATE_TABLE_TRANSACTION);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, 
				"upgrading the databse from version "+ oldVersion + " to " + newVersion);
		
		//clear all data
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
		db.execSQL("DROP TABLE IF EXISTS " + TRAN_TABLE);
		
		//recreate the tables
		onCreate(db);

	}
	
	public DBHelper(Context context, String name, CursorFactory factory, int version) {
		super(context, DATABASE_NAME, factory, DATABASE_VERSION);
	}

}
