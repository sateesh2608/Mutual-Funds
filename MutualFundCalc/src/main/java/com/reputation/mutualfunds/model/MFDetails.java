package com.reputation.mutualfunds.model;

import java.io.Serializable;

public class MFDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String schemeCode;

    String schemeName;

    String nav;

    String date;

	public MFDetails(String schemeCode, String schemeName, String nav, String date) {
		super();
		this.schemeCode = schemeCode;
		this.schemeName = schemeName;
		this.nav = nav;
		this.date = date;
	}
    
    
}
