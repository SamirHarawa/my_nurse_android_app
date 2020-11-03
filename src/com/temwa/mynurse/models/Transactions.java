package com.temwa.mynurse.models;

public class Transactions {
	
	private long tId;
	private String tran_drugName;
	private String tran_drugDate;
	private String tran_pill_count;
	private String tran_take_pill;
	private String tran_take_times;
	private String tran_rv_date;
	
	public Transactions() {}
	
	public Transactions(String drugname, String drugdate, String pillcount, String takepill, String taketimes, String rvdate) {
		this.tran_drugName = drugname;
		this.tran_drugDate = drugdate;
		this.tran_pill_count = pillcount;
		this.tran_take_pill = takepill;
		this.tran_take_times = taketimes;
		this.tran_rv_date = rvdate;
	}

	public String getTran_rv_date() {
		return tran_rv_date;
	}

	public void setTran_rv_date(String tran_rv_date) {
		this.tran_rv_date = tran_rv_date;
	}

	public long getId() {
		return tId;
	}

	public void setId(long tId) {
		this.tId = tId;
	}

	public String getTran_drugName() {
		return tran_drugName;
	}

	public void setTran_drugName(String tran_drugName) {
		this.tran_drugName = tran_drugName;
	}

	public String getTran_drugDate() {
		return tran_drugDate;
	}

	public void setTran_drugDate(String tran_drugDate) {
		this.tran_drugDate = tran_drugDate;
	}

	public String getTran_pill_count() {
		return tran_pill_count;
	}

	public void setTran_pill_count(String tran_pill_count) {
		this.tran_pill_count = tran_pill_count;
	}

	public String getTran_take_pill() {
		return tran_take_pill;
	}

	public void setTran_take_pill(String tran_take_pill) {
		this.tran_take_pill = tran_take_pill;
	}

	public String getTran_take_times() {
		return tran_take_times;
	}

	public void setTran_take_times(String tran_take_times) {
		this.tran_take_times = tran_take_times;
	}
	

}
