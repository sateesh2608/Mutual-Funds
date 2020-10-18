package com.reputation.mutualfunds.service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.reputation.mutualfunds.model.MFDetails;

@Service("mFService")
public class MFServiceImpl implements MFService {

	public static final String FUNDS_CACHE_KEY = "#schemeCode";
    // Return value from cache when forceReload = false
    // Execute the method and do not use cache when forceReload = true
    public static final String DO_NOT_USE_CACHE_ON_FORCE_RELOAD = "!#forceReload";
    public static final MFDetails EMPTY_SCHEME = new MFDetails(null, null, null, null);
    private static final String MFAPI_WEBSITE_BASE_URL = "https://api.mfapi.in/mf/";
    private static final String DATE_PATTERN_DD_MM_YYYY = "dd-MM-yyyy";
    private static final String SEPARATOR = ";";
    private static final String FUNDS_CACHE = "fundsCache";
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MFServiceImpl.class);
    
  /*  @Autowired
    CacheManager cacheManager;
    private Map<String, MFDetails> fundsMap = new HashMap<>();
	*/
	@Override
	public boolean loadNavForAllFunds(String schemeNumber) throws IOException {

		log.info("Loading All Funds...");
        URL url = new URL(MFAPI_WEBSITE_BASE_URL);

        String jsonResponse = getResponseFromURL(MFAPI_WEBSITE_BASE_URL + schemeNumber);
        System.out.println(jsonResponse);
        /*List<String> lines = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = in.readLine()) != null) {
                lines.add(line);
            }
        }
        lines.stream()
                .map(line -> line.split(SEPARATOR))
                .filter(elements -> elements.length >= 5)
                .forEach(elements -> {
                    fundsMap.put(elements[0], new MFDetails(elements[0], elements[3], elements[4], elements[5]));
                });

        cacheManager.getCache(FUNDS_CACHE).clear();*/
        return true;
	}

	@Override
	public MFDetails getNav(boolean forceReload, String schemeCode) throws IOException {
		return null;
	}

	@Override
	public MFDetails getNavOnDate(String schemeCode, String date) {
		return null;
	}
	

	  public String getAdjustedDateForNAV(String inputDate) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN_DD_MM_YYYY);
	        LocalDate adjustedDate = LocalDate.parse(inputDate, formatter);
	        if (adjustedDate.getDayOfWeek() == DayOfWeek.SATURDAY || adjustedDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
	            adjustedDate = adjustedDate.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
	        }
	        return adjustedDate.format(formatter);
	    }
	
	public String getResponseFromURL(String URL) throws IOException {
        StringBuilder response = new StringBuilder();
        URL url = new URL(URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        if (conn.getResponseCode() != 200) {
            log.error("Request for URL: {} failed. Response Code: [{}]", URL, conn.getResponseCode());

        } else {
            log.info("Request for URL: [{}] successful.", url);
            Scanner sc = new Scanner(url.openStream());
            while (sc.hasNext()) {
                response.append(sc.nextLine());
            }
        }

        return response.toString();
    }

//read JSON Response method name
    public String getNavFromJsonResponse(String response, String inputDate) {
        JSONObject jsonObject = new JSONObject(response);
        JSONArray data = (JSONArray) jsonObject.get("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject apiResponseRow = data.getJSONObject(i);
            if (apiResponseRow.getString("date").equalsIgnoreCase(inputDate)) {
                return apiResponseRow.getString("nav");
            }
        }
        return "";
    }


}
