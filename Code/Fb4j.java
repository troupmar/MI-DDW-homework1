package troupmar.facebook;

import java.net.MalformedURLException;

import facebook4j.Comment;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.PagableList;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.auth.AccessToken;
import facebook4j.conf.ConfigurationBuilder;

public class Fb4j {

	public static  Facebook createConfiguration() {
	    // Generate facebook instance.
	    Facebook facebook = new FacebookFactory().getInstance();
	    // Use default values for oauth app id.
	    facebook.setOAuthAppId("469574503170152","bc2771897a37574db50ca84a44650727");
	    // Get an access token from: 
	    // https://developers.facebook.com/tools/explorer
	    // Copy and paste it below.
	    String accessTokenString = "CAACEdEose0cBADLHZA5ynrciFFK2uSUbBDTlTvMtHEPfeesUxE6nfRhA0yFIZBoc1KH5lyae95v8ZBbvEISf7IJJI3aL8f5YQQ0nPLOKVtIiED7I9LcZBE1vMOn50v42ADDZAsQNmGn8LUBNN0NEkIVoy5KzyejeITUBgGYPM0fF73msWXrwwUZAFVGFAGiFDnC8WZCBsjl4QZDZD";
	    AccessToken at = new AccessToken(accessTokenString);
	    // Set access token.
	    facebook.setOAuthAccessToken(at);	
	    
	    return facebook;
	}
}
