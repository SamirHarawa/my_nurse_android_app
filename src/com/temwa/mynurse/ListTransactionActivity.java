package com.temwa.mynurse;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.temwa.mynurse.adapter.ListTransactionAdapter;
import com.temwa.mynurse.dbhelper.TransactionHelper;
import com.temwa.mynurse.models.Transactions;

public class ListTransactionActivity extends Activity implements OnItemLongClickListener, OnItemClickListener, OnClickListener {
	
	public static final String TAG = "ListTransactions";
	
	public static final String EXTRA_ADDED_PROFILE = "extra_key_added_transaction";
	
	private ListView tListView;
	private TextView mTxtEmptyListTransactions;
	private ImageButton addTransaction;
	
	private ListTransactionAdapter tAdapter;
	private List<Transactions> tListTransactions;
	private TransactionHelper tTransactionHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_transaction);
		
		initViews();
		
		tTransactionHelper = new TransactionHelper(this);
		tListTransactions = tTransactionHelper.getAllTransactions();
		
		
		if (tListTransactions != null && !tListTransactions.isEmpty()) {
			tAdapter = new ListTransactionAdapter(this, tListTransactions);
			tListView.setAdapter(tAdapter);
			
			addTransaction.setEnabled(false);
		} else {
			mTxtEmptyListTransactions.setVisibility(View.VISIBLE);
			tListView.setVisibility(View.GONE);
			
			addTransaction.setEnabled(true);
		}
		
		
	}

	private void initViews() {
		
		this.tListView = (ListView) findViewById(R.id.list_transactions);
		this.mTxtEmptyListTransactions = (TextView) findViewById(R.id.txt_empty_list_transactions);
		this.addTransaction = (ImageButton) findViewById(R.id.btn_add_transactions);
		
		this.tListView.setOnItemClickListener(this);
		this.tListView.setOnItemLongClickListener(this);
		this.addTransaction.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list_transaction, menu);
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
		case R.id.btn_add_transactions:
			break;
		default:
			break;
		}
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Transactions clickedTransaction = tAdapter.getItem(position);
		Log.d(TAG, "clickedItem : "+clickedTransaction.getTran_drugName());
		
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
			long id) {
		Transactions clickedTransaction = tAdapter.getItem(position);
		Log.d(TAG, "longClickedItem : "+clickedTransaction.getTran_drugName());
		showDeleteDialogConfirmation(clickedTransaction);
		return true;
	}

	private void showDeleteDialogConfirmation(final Transactions clickedTransaction) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		
		alertDialogBuilder.setTitle("Delete");
		alertDialogBuilder.setMessage("Are you sure you want to delete this transaction \""+ clickedTransaction.getTran_drugName() +" on "+clickedTransaction.getTran_drugDate());
		
		//set position button yes message
		alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//delete transaction
				if (tTransactionHelper != null) {
					tTransactionHelper.deleteTransaction(clickedTransaction);
					tListTransactions.remove(clickedTransaction);
					
					//refresh the list
					if (tListTransactions.isEmpty()) {
						tListTransactions = null;
						tListView.setVisibility(View.GONE);
						mTxtEmptyListTransactions.setVisibility(View.VISIBLE);
					}
					tAdapter.setItems(tListTransactions);
					tAdapter.notifyDataSetChanged();
				}
				dialog.dismiss();
				Toast.makeText(ListTransactionActivity.this, "The transaction has been deleted successfully", Toast.LENGTH_LONG).show();
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
