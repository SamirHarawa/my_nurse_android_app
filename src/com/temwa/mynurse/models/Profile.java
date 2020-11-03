package com.temwa.mynurse.models;

public class Profile {
	
	public static final String TAG = "Profile";
	
	private long pId;
	private String profile_fnName;
	private String profile_lname;
	private String profile_gender;
	private String profile_dob;
	
	public Profile() {}
	
	public Profile(String fname, String lname, String gender, String dob) {
		this.profile_fnName = fname;
		this.profile_lname = lname;
		this.profile_gender = gender;
		this.profile_dob = dob;
	}

	public long getId() {
		return pId;
	}

	public void setId(long pId) {
		this.pId = pId;
	}

	public String getProfile_fnName() {
		return profile_fnName;
	}

	public void setProfile_fnName(String profile_fnName) {
		this.profile_fnName = profile_fnName;
	}

	public String getProfile_lname() {
		return profile_lname;
	}

	public void setProfile_lname(String profile_lname) {
		this.profile_lname = profile_lname;
	}

	public String getProfile_gender() {
		return profile_gender;
	}

	public void setProfile_gender(String profile_gender) {
		this.profile_gender = profile_gender;
	}

	public String getProfile_dob() {
		return profile_dob;
	}

	public void setProfile_dob(String profile_dob) {
		this.profile_dob = profile_dob;
	}

}
