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
	public static void printComments() throws FacebookException {
		Facebook facebook = createConfiguration();
		
		PagableList<Comment> comments = facebook.getPostComments("4048760954976", new Reading().limit(170));
		
		for(int i=0; i<comments.size(); i++) {
			String comment = comments.get(i).getMessage();
			System.out.println(comment);
		}
		
	}
	
	private static Facebook createConfiguration() {
	    // Generate facebook instance.
	    Facebook facebook = new FacebookFactory().getInstance();
	    // Use default values for oauth app id.
	    facebook.setOAuthAppId("469574503170152","bc2771897a37574db50ca84a44650727");
	    // Get an access token from: 
	    // https://developers.facebook.com/tools/explorer
	    // Copy and paste it below.
	    String accessTokenString = "CAAGrE1V1AGgBANCr9ZC5YHMzsjaZBHUATIjG8wYOyGPWE9MbGwUUwdVyEyxZBGBF4BLSZAHSTmzchVIw1JZBIChWT4ZBxZBT1LVRZBjk5RHmLKoxuMYzrca9pig2T0snDQb1UJiR23ZCO3TypNbjut1PkcZAbVmttDeSwenWSvcvIHHRRYnZBPY0LrwBTZAyQNg7sDRwN2JkA3RcVAZDZD";
	    AccessToken at = new AccessToken(accessTokenString);
	    // Set access token.
	    facebook.setOAuthAccessToken(at);	
	    
	    return facebook;
	}
}
