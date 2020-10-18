package com.reputation.mutualfunds.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reputation.mutualfunds.service.MFService;

@RestController
public class MFRestController {

    @Autowired
    @Qualifier("mFService")
    private MFService mFService;
    
    @RequestMapping("getQuote")
    public void getScheme(@RequestParam String schemeNumber) throws IOException{
    	
    	mFService.loadNavForAllFunds(schemeNumber);
    	
    }
    
}
