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

public class MslRestClient {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting MslRest java client.\n");

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

        String authUrl = provider.retrieveRequestToken(consumer, OAuth.OUT_OF_BAND);

        System.out.println("Request token: " + consumer.getToken());
        System.out.println("Token secret: " + consumer.getTokenSecret());

        System.out.println("Now visit the server-manager of this MSL server and grant this app authorization");
        System.out.println("Enter the verification code and hit ENTER when you're done:");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String verificationCode = br.readLine();

        System.out.println("read this verification code: " + verificationCode);

        System.out.println("Fetching access token...");

        provider.retrieveAccessToken(consumer, verificationCode);

        System.out.println("Access token: " + consumer.getToken());
        System.out.println("Token secret: " + consumer.getTokenSecret());
        
        URL url = new URL("https://vmbg-solink.martellotech.com/mslrest/ping");
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

    }
}
