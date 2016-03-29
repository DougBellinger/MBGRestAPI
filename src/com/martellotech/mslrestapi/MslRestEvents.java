package com.martellotech.mslrestapi;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.crypto.dsig.SignatureMethod;

import oauth.signpost.OAuth;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.signature.PlainTextMessageSigner;
import oauth.signpost.signature.HmacSha1MessageSigner;

public class MslRestEvents {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting MslRest java client Using Stored Token.\n");

        OAuthConsumer consumer = new DefaultOAuthConsumer(
                "marwatch",
                "ff0f91935f8dfc4070896166e0507f4aa9b16422"
                );

        // Plaintext signature.
        //consumer.setMessageSigner(new PlainTextMessageSigner());
        // HMAC-SHA1 signature
        consumer.setMessageSigner(new HmacSha1MessageSigner());

        OAuthProvider provider = new DefaultOAuthProvider("https://vmbg-solink.martellotech.com/mslrest/initiate",
                "https://vmbg-solink.martellotech.com/mslrest/token",
                "https://vmbg-solink.martellotech.com/mslrest/authorize");

        provider.setOAuth10a(true);
        consumer.setTokenWithSecret("or7z4alsrn2+pyuwm33djq==", "ghjbunneqyqwapfcsdz1tw==");
        //provider.retrieveAccessToken(consumer, "658773");
        
      URL url = new URL("https://vmbg-solink.martellotech.com/mslrest/mbg/v1/metrics/1454963194/1455049594/");
      // URL url = new URL("https://vmbg-solink.martellotech.com/mslrest/msl/v1/events/count/");
      //URL url = new URL("https://vmbg-solink.martellotech.com/mslrest/mbg/v1/metrics/");
      //URL url = new URL("https://vmbg-solink.martellotech.com/mslrest/msl/v1/installed_blades/");
      //  HttpURLConnection request = (HttpURLConnection) url.openConnection();
      HttpURLConnection request = (HttpURLConnection) url.openConnection();
        
        consumer.sign(request);
        
        InputStream ins = request.getInputStream();
        InputStreamReader isr = new InputStreamReader(ins);
        BufferedReader in = new BufferedReader(isr);
     
        String inputLine;
     
        while ((inputLine = in.readLine()) != null)
        {
          System.out.println(inputLine);
        }
     
        in.close();
        System.out.println("Done!");
        
    }
}
