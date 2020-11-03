package com.temwa.mynurse.adapter;

import java.util.List;

import com.temwa.mynurse.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.temwa.mynurse.models.Transactions;

public class ListTransactionAdapter extends BaseAdapter {
	
	public static final String TAG = "ListTransactionAdapter";
	
	private List<Transactions> tItems;
	private LayoutInflater tInflater;
	
	public ListTransactionAdapter(Context context, List<Transactions> listTransactions) {
		this.setItems(listTransactions);
		this.tInflater = LayoutInflater.from(context);
	}

	public void setItems(List<Transactions> tItems) {
		this.tItems = tItems;
	}
	
	public List<Transactions> getItems() {
		return tItems;
	}

	@Override
	public int getCount() {
		return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
	}

	@Override
	public Transactions getItem(int position) {
		return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getId() : 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		ViewHolder holder;
		
		if (v == null) {
			v = tInflater.inflate(R.layout.list_item_transactions, parent, false);
			holder = new ViewHolder();
			holder.txtdrugname = (TextView) v.findViewById(R.id.txtDrugname);
			holder.txtdrugdate = (TextView) v.findViewById(R.id.txtDrugdate);
			holder.txtdose = (TextView) v.findViewById(R.id.txtdose);
			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}
		
		//fill row data
		Transactions currentTransaction = getItem(position);
		if (currentTransaction != null) {
			holder.txtdrugname.setText(currentTransaction.getTran_drugName());
			holder.txtdrugdate.setText(currentTransaction.getTran_drugDate());
			holder.txtdose.setText(currentTransaction.getTran_take_times());
		}
		return v;
	}
	
	class ViewHolder {
		TextView txtdrugname;
		TextView txtdrugdate;
		TextView txtdose;
	}

}
