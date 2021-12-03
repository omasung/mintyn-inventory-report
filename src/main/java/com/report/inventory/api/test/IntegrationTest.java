package com.report.inventory.api.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class IntegrationTest {	

	/**
	 * Order Report
	 */
	public void orderReport() {
		
        try {

			String startDate = "02/12/2021";
			String endDate = "03/12/2021";
			
			String url = "http://localhost:8090/inventory/report";            
			String USER_AGENT = "Mozilla/5.0";
	            
            try (CloseableHttpClient client = HttpClients.createDefault()) {
            	
                HttpGet request = new HttpGet(url);
                
                // add request header
                request.addHeader("User-Agent", USER_AGENT);
                request.addHeader("startDate", startDate);
                request.addHeader("endDate", endDate);
                
                HttpResponse response = client.execute(request);
                
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                StringBuilder result = new StringBuilder();
                
                String line = "";
                
                while ((line = rd.readLine()) != null) {
                    
                    result.append(line);
                    
                }                
                
                System.out.println("\n Integration Test Order Reporting:- For Order Reporting Send 'GET' URL : " + url);
                System.out.println(result.toString());                
                
                client.close();                   
                                               
            }
                                                                                               
            
        } catch (IOException | UnsupportedOperationException ex) {

            System.out.println("Could not retrieve transaction properties " + ex.getMessage());
            
        }		
		  	    	
    }	
	
}
