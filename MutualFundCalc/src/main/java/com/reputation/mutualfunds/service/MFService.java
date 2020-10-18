package com.reputation.mutualfunds.service;

import java.io.IOException;

import com.reputation.mutualfunds.model.MFDetails;

public interface MFService {
	boolean loadNavForAllFunds(String Schema) throws IOException;
    MFDetails getNav(boolean forceReload, String schemeCode) throws IOException;
    MFDetails getNavOnDate(String schemeCode, String date);
}
