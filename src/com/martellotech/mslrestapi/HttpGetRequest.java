package com.martellotech.mslrestapi;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.*;  //HttpHead, HttpPut, HttpGet, etc...
import org.apache.http.util.EntityUtils;



public class HttpGetRequest { 
	public static void demo() throws IOException {           
	    String consumer_key = "marwatch";
	    String consumer_secret = "ff0f91935f8dfc4070896166e0507f4aa9b16422";
	    String access_token = "ouwulv5qsiunhujuenctxq==";
	    String access_secret= "pj0+nwtzqh+kaliwi/m85w==";

	    OAuthConsumer consumer = new CommonsHttpOAuthConsumer(consumer_key,
	consumer_secret);
	    consumer.setTokenWithSecret(access_token, access_secret);


	    String uri = "https://vmbg-solink.martellotech.com/mslrest/ping";
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpGet httpget = new HttpGet(uri);
	    try {
	        consumer.sign(httpget);
	    } catch (OAuthMessageSignerException ex) {
	        Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE,       null, ex);
	    } catch (OAuthExpectationFailedException ex) {
	        Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
	    } catch (OAuthCommunicationException ex) {
	        Logger.getLogger(HttpRequest.class.getName()).log(Level.SEVERE, null, ex);
	    }
	    HttpResponse response = (HttpResponse) httpclient.execute(httpget);
	    System.out.println(response.getStatusLine().toString());
	    HttpEntity entity = response.getEntity();
	    System.out.println();
	    System.out.println(EntityUtils.toString(entity));       
	}
	public static void main(String[] args) {
	    try {
	        demo();
	    }
	    catch(IOException ioe) {
	        System.out.println(ioe);
	    }
	}
}

