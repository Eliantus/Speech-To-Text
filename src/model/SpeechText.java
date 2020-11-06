/**
 * 
 */
package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.speech_to_text.v1.SpeechToText;
import com.ibm.watson.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.speech_to_text.v1.model.SpeechRecognitionResults;

/**
 * @author Eliantus De MICHEL
 *
 */
public class SpeechText {
	
	private SpeechRecognitionResults speechRecognitionResults;
	private SpeechToText speechToText;
	RecognizeOptions recognizeOptions;
	private String result="";
		
	public void SpeechTextEn(String path) {
		IamAuthenticator authenticator = new IamAuthenticator("qFmpIxfHTbO-flxTdstCHYLyUrr7aHEaplxmngo2z94w");
		speechToText = new SpeechToText(authenticator);
		speechToText.setServiceUrl("https://api.us-south.speech-to-text.watson.cloud.ibm.com/instances/29241c00-2ad6-4bd4-aa7c-e5998e445055");

		try {
			recognizeOptions = new RecognizeOptions.Builder()
		    .audio(new FileInputStream(path))
		    .contentType("audio/wav")
		    .model("en-US_BroadbandModel")
		    .build();

		 speechRecognitionResults =
				    speechToText.recognize(recognizeOptions).execute().getResult();
		 
				  //System.out.println(speechRecognitionResults);
		      
		} catch (FileNotFoundException e) {
		 e.printStackTrace();
		}
		
	}
	
	public void SpeechTextFr(String path) {
		IamAuthenticator authenticator = new IamAuthenticator("qFmpIxfHTbO-flxTdstCHYLyUrr7aHEaplxmngo2z94w");
		speechToText = new SpeechToText(authenticator);
		speechToText.setServiceUrl("https://api.us-south.speech-to-text.watson.cloud.ibm.com/instances/29241c00-2ad6-4bd4-aa7c-e5998e445055");

		try {
			recognizeOptions = new RecognizeOptions.Builder()
		    .audio(new FileInputStream(path))
		    .contentType("audio/wav")
		    .model("fr-FR_NarrowbandModel")
		    .build();

		 speechRecognitionResults =
				    speechToText.recognize(recognizeOptions).execute().getResult();
		 
		      
		} catch (FileNotFoundException e) {
		 e.printStackTrace();
		}
		
	}
	
	public void Parse() {
		String jsonString = speechRecognitionResults.toString();
		JSONObject obj = new JSONObject(jsonString);
		JSONArray arr = obj.getJSONArray("results");
		JSONArray arr1;
		
		for(int i=0; i<arr.length(); i++) {
			
			arr1=arr.getJSONObject(i).getJSONArray("alternatives");
			result = result.concat(arr1.getJSONObject(0).getString("transcript")+"\n");		
		}
	}
	
	public String ToTextFr(String path) {
		SpeechTextFr(path);
		Parse();
		return result;
		
	}
	
	public String ToTextEn(String path) {
		SpeechTextEn(path);
		Parse();
		return result;
		
	}
	
	public void stop() {
		speechToText.recognize(recognizeOptions).cancel();
	}

}
