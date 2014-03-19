package troupmar.facebook;

import gate.Annotation;
import gate.AnnotationSet;
import gate.Corpus;
import gate.CreoleRegister;
import gate.DataStore;
import gate.Document;
import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.Node;
import gate.ProcessingResource;
import gate.creole.ExecutionException;
import gate.creole.ResourceInstantiationException;
import gate.creole.SerialAnalyserController;
import gate.persist.PersistenceException;
import gate.util.GateException;
import gate.util.InvalidOffsetException;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;



public class GateClient {
	
	private static boolean isGateInitilised = false;
	
	public void run() throws ResourceInstantiationException, ExecutionException, InvalidOffsetException, IOException
	{
		initialiseGate();
	}
	private void initialiseGate()
	{
		try {
            // set GATE home folder
            // Eg. /Applications/GATE_Developer_7.0
            File gateHomeFile = new File("/Applications/GATE_Developer_7.1");
            Gate.setGateHome(gateHomeFile);
            
            // set GATE plugins folder
            // Eg. /Applications/GATE_Developer_7.0/plugins            
            File pluginsHome = new File("/Applications/GATE_Developer_7.1/plugins");
            Gate.setPluginsHome(pluginsHome);            
            
            // initialise the GATE library
            Gate.init();
            
            // load ANNIE plugin
            CreoleRegister register = Gate.getCreoleRegister();
            URL annieHome = new File(pluginsHome, "ANNIE").toURL();
            register.registerDirectories(annieHome);
            
            // load Learning plugin
            URL learningHome = new File(pluginsHome, "Learning").toURL();
            register.registerDirectories(learningHome);
            
            
            // flag that GATE was successfuly initialised
            isGateInitilised = true;
            System.out.println(isGateInitilised);
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(GateClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GateException ex) {
            Logger.getLogger(GateClient.class.getName()).log(Level.SEVERE, null, ex);
        }
	}
	
	public Corpus loadCorpus() throws PersistenceException, MalformedURLException, ResourceInstantiationException, InvalidOffsetException
	{
		//
		File path = new File("/Users/Martin/Documents/Gate/GATEprojects");
		DataStore ds = Factory.openDataStore("gate.persist.SerialDataStore", path.toURI().toURL().toString());
		if(ds != null)
		{
			List corpusIds = ds.getLrIds("gate.corpora.SerialCorpusImpl");
			if(corpusIds != null)
			{
				FeatureMap fm = Factory.newFeatureMap();
		        fm.put(ds.DATASTORE_FEATURE_NAME, ds);
		        fm.put(ds.LR_ID_FEATURE_NAME, corpusIds.get(3));
		       	Corpus corpus = (Corpus) Factory.createResource("gate.corpora.SerialCorpusImpl", fm);
		       	return corpus;
			}
		}
		return null;
	}
	
	public Corpus getTestingCorpus() throws ResourceInstantiationException, ExecutionException, InvalidOffsetException
	{
		ProcessingResource documentResetPR = (ProcessingResource) Factory.createResource("gate.creole.annotdelete.AnnotationDeletePR");
		ProcessingResource tokenizerPR = (ProcessingResource) Factory.createResource("gate.creole.tokeniser.DefaultTokeniser");
		ProcessingResource sentenceSplitterPR = (ProcessingResource) Factory.createResource("gate.creole.splitter.SentenceSplitter");
		ProcessingResource posPR = (ProcessingResource) Factory.createResource("gate.creole.POSTagger");
		ProcessingResource gazetteerPR = (ProcessingResource) Factory.createResource("gate.creole.gazetteer.DefaultGazetteer");
		// SETTING PIPELINE FOR PROCESSING RESOURCES TO ADD IN
		SerialAnalyserController pipeline = (SerialAnalyserController) Factory.createResource("gate.creole.SerialAnalyserController");
		
		Document document1 = Factory.newDocument("I'm kinda disappointed.");
        Document document2 = Factory.newDocument("Awesome show last night!!");
        
        Corpus corpus = Factory.newCorpus("");
		// FILLING CORPUS WITH DOCUMENTS...
		corpus.add(document1);
		corpus.add(document2);
		
		pipeline.add(documentResetPR);
		pipeline.add(tokenizerPR);
		pipeline.add(sentenceSplitterPR);
		pipeline.add(posPR);
		pipeline.add(gazetteerPR);
		
		pipeline.setCorpus(corpus);
		pipeline.execute();
		return corpus;
	}
	
	public void learning() throws MalformedURLException, ResourceInstantiationException, PersistenceException, InvalidOffsetException, ExecutionException
	{
		SerialAnalyserController pipelineTrain = (SerialAnalyserController) Factory.createResource("gate.creole.SerialAnalyserController");
		
		
		File configBatch = new File("/Users/Martin/Documents/Gate/SentimentAnalysis/ml-config-file.xml");
        java.net.URI configBatchURI = configBatch.toURI();
		FeatureMap batchFeatureMap = Factory.newFeatureMap();
        batchFeatureMap.put("configFileURL", configBatchURI.toURL());
        
        ProcessingResource batchLMPR = (ProcessingResource) Factory.createResource("gate.learning.LearningAPIMain", batchFeatureMap);
        batchLMPR.setParameterValue("learningMode", "TRAINING");
        Corpus training = loadCorpus();
        
        
        System.out.println(training.getName());
        System.out.println("Number of documents: " + training.size());
        
        pipelineTrain.add(batchLMPR);
        pipelineTrain.setCorpus(training);
        pipelineTrain.execute();
        
        
        // testing
        SerialAnalyserController pipelineTest = (SerialAnalyserController) Factory.createResource("gate.creole.SerialAnalyserController");
        Corpus testing = getTestingCorpus();
        System.out.println(testing.size());
        
        batchLMPR.setParameterValue("learningMode", "APPLICATION");
        pipelineTest.add(batchLMPR);
        pipelineTest.setCorpus(testing);
        pipelineTest.execute();
        processAnnotations(testing);
        
	}
	
	private void processAnnotations(Corpus corpus) throws InvalidOffsetException
	{
		// LOOP ACROSS ALL DOCUMENTS IN CORPUS
		System.out.println(corpus.size());
		for(int i=0; i< corpus.size(); i++){
			 
		    // get document from the corpus
		    Document doc = corpus.get(i);
		 
		    // get the default annotation set for the document
		    AnnotationSet as_default = doc.getAnnotations();
		    FeatureMap futureMap = null;
		 
		    // GET ALL ANNOTATION OF SET TYPE
		    AnnotationSet annSetTokens = as_default.get("Token",futureMap);
		    AnnotationSet annSetLookup = as_default.get("Lookup",futureMap);
		    AnnotationSet annSetComment = as_default.get("Comment", futureMap);
		 
		    // print out the number of SPECIFIC ANNOTATIONS AND NUMBER OF A DOCUMENT, 
		    // WHERE THESE ANNOTATIONS BELONG TO
		    System.out.println("Document n. " + i);
		    System.out.println("Number of Token annotations: " + annSetTokens.size());
		    System.out.println("Number of Lookup annotations: " + annSetLookup.size());
		    System.out.println("Number of Comment annotations: " + annSetComment.size());
		    
		    // GET SPECIFIC ANNOTATIONS IN ARRAYS, THAT ARE EASIER TO MANIPULATE WITH
		    ArrayList tokenAnnotations = new ArrayList(annSetTokens);
		    ArrayList lookUpAnnotations = new ArrayList(annSetLookup);
		    ArrayList commentAnnotations = new ArrayList(annSetComment);
		 
		    // looop through the Token annotations
		    for(int j = 0; j < tokenAnnotations.size(); ++j) {
		 
		        // get a Token annotation
		        Annotation token = (Annotation)tokenAnnotations.get(j);
		 
		        // get features of a Token
		        FeatureMap annFM = token.getFeatures();
		 
		        // get the value of the "string" feature and print it in the console
		        String value = (String)annFM.get((Object)"string");
		        String count = annFM.toString();
		        //System.out.println(value);
		        //System.out.println(count);
		    }
		    loopOverAnnotations(commentAnnotations, doc);
		    
		}	
		
	}
	
	// METHOD TO LOOP OVER DIFFERENT TYPES OF ANNOTATION - MOSTLY FOR SELF MADE JAPE TYPES BASED ON LOOKUP FEATURES
	private void loopOverAnnotations(ArrayList annotations, Document doc) throws InvalidOffsetException
	{
		for(int i = 0; i < annotations.size(); ++i) {
			Annotation ann = (Annotation)annotations.get(i);
			
			FeatureMap annFM = ann.getFeatures();
			
			Node start = ann.getStartNode();
			Node end = ann.getEndNode();
			String value = doc.getContent().getContent(start.getOffset(), end.getOffset()).toString();
	        String count = annFM.toString();
	        System.out.println(value);
	        System.out.println(count);
			
		}
	}
	
}

