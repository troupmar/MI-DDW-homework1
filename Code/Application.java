package troupmar.facebook;

import java.io.IOException;
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
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.persist.PersistenceException;
import gate.util.InvalidOffsetException;

public class Application {
	
	public static void main(String[] args) throws FacebookException, ResourceInstantiationException, ExecutionException, InvalidOffsetException, IOException, PersistenceException, InvalidOffsetException {
		/*
		Facebook facebook = Fb4j.createConfiguration();
		//Post post = facebook.getPost("10152293557812112", new Reading().limit(100));
		PagableList<Comment> comments = facebook.getPostComments("4048760954976", new Reading().limit(170));
		//String mess = post.getMessage();
		//PagableList<Comment> comments = post.getComments();
		System.out.println(comments.size());
		for(int i=0; i<comments.size(); i++) {
			String comment = comments.get(i).getMessage();
			System.out.println(comment);
		}
		//ResponseList<Post> feed = facebook.getHome();

		System.out.println("All went well!");
		*/
		GateClient gate = new GateClient();
		gate.run();
		//gate.getTestingCorpus();
		gate.learning();
	}

}
