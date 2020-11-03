package com.temwa.mynurse.dbhelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.temwa.mynurse.models.Profile;
import com.temwa.mynurse.models.Transactions;

public class TransactionHelper {
	
	public static final String TAG = "TransactionHelper";
	
	//Database fields
	private SQLiteDatabase tDatabase;
	private DBHelper tDBHelper;
	private Context tContext;
	private String[] tAllColumns = { DBHelper.TRAN_MED_NAME, DBHelper.TRAN_MED_DATE,
			DBHelper.TRAN_PILL_COUNT, DBHelper.TRAN_RV_DATE, DBHelper.TRAN_TAKE_PILL,
			DBHelper.TRAN_TAKE_TIMES };
	
	public TransactionHelper(Context context) {
		this.tContext = context;
		tDBHelper = new DBHelper(context);
		
		//open database
		try {
			open();
		} catch (SQLException e) {
			Log.e(TAG, "SQLException on opening database " + e.getMessage());
			e.printStackTrace();
		}
	}

	private void open() throws SQLException {
		tDatabase = tDBHelper.getWritableDatabase();		
	}
	
	public void close() {
		tDBHelper.close();
	}
	
	public Transactions createTransaction(String drug_name, String drug_date, String rv_date, String no_pills,
		String take_pills, String dose) {
		ContentValues tcv = new ContentValues();
		tcv.put(DBHelper.TRAN_MED_NAME, drug_name);
		tcv.put(DBHelper.TRAN_MED_DATE, drug_date);
		tcv.put(DBHelper.TRAN_RV_DATE, rv_date);
		tcv.put(DBHelper.TRAN_PILL_COUNT, no_pills);
		tcv.put(DBHelper.TRAN_TAKE_PILL, take_pills);
		tcv.put(DBHelper.TRAN_TAKE_TIMES, dose);
		
		long insertId = tDatabase.insert(DBHelper.TRAN_TABLE, null, tcv);
		
		Cursor cursor = tDatabase.query(DBHelper.TRAN_TABLE, tAllColumns, null, null, null, null, null);
		
		cursor.moveToFirst();
		Transactions newTransaction = cursorToTransaction(cursor);
		cursor.close();
		return newTransaction;
	}
	
	public List<Transactions> getAllTransactions() {
		List<Transactions> listTransaction = new ArrayList<Transactions>();
		
		Cursor cursor = tDatabase.query(DBHelper.TRAN_TABLE, tAllColumns, null, null, null, null, null);
		
		if (cursor != null) {
			cursor.moveToFirst();
			while(!cursor.isAfterLast()) {
				Transactions transaction = cursorToTransaction(cursor);
				listTransaction.add(transaction);
				cursor.moveToNext();
			}
			cursor.close();
		}
		return listTransaction;
		
	}
	public void deleteTransaction(Transactions transaction) {
		long id = transaction.getId();
		
		
	}

	protected Transactions cursorToTransaction(Cursor cursor) {
		Transactions transaction = new Transactions();
		
		transaction.setTran_drugName(cursor.getString(0));
		transaction.setTran_drugDate(cursor.getString(1));
		transaction.setTran_pill_count(cursor.getString(2));
		transaction.setTran_rv_date(cursor.getString(3));
		transaction.setTran_take_pill(cursor.getString(4));
		transaction.setTran_take_times(cursor.getString(5));
		
		return transaction;
	}

}
