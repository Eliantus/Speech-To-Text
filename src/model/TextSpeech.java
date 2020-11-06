/**
 * 
 */
package model;

import java.io.*;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.text_to_speech.v1.model.SynthesizeOptions;
import com.ibm.watson.text_to_speech.v1.util.WaveUtils;

/**
 * @author elian
 *
 */
public class TextSpeech {
	
	private TextToSpeech textToSpeech;
	private SynthesizeOptions synthesizeOptions;
	
	public void TextSpeechEn(String text, String path) {
		IamAuthenticator authenticator = new IamAuthenticator("qcChQAYMckgtfwPN1g62-OGNr1JEMEWAEbEwLV962_pz");
		textToSpeech = new TextToSpeech(authenticator);
		textToSpeech.setServiceUrl("https://api.us-south.text-to-speech.watson.cloud.ibm.com/instances/8748f4af-0b2d-44da-8981-ca0d8a7dd6d7");

		try {
		  synthesizeOptions =
		    new SynthesizeOptions.Builder()
		      .text(text)
		      .accept("audio/wav")
		      .voice("en-US_AllisonV3Voice")
		      .build();

		  InputStream inputStream =
		    textToSpeech.synthesize(synthesizeOptions).execute().getResult();
		  InputStream in = WaveUtils.reWriteWaveHeader(inputStream);

		  OutputStream out = new FileOutputStream(path);
		  byte[] buffer = new byte[1024];
		  int length;
		  while ((length = in.read(buffer)) > 0) {
		    out.write(buffer, 0, length);
		  }

		  out.close();
		  in.close();
		  inputStream.close();
		} catch (IOException e) {
		  e.printStackTrace();
		}
	}
	
	public void TextSpeechFr(String text, String path) {
		IamAuthenticator authenticator = new IamAuthenticator("qcChQAYMckgtfwPN1g62-OGNr1JEMEWAEbEwLV962_pz");
		textToSpeech = new TextToSpeech(authenticator);
		textToSpeech.setServiceUrl("https://api.us-south.text-to-speech.watson.cloud.ibm.com/instances/8748f4af-0b2d-44da-8981-ca0d8a7dd6d7");

		try {
		  synthesizeOptions =
		    new SynthesizeOptions.Builder()
		      .text(text)
		      .accept("audio/wav")
		      .voice("fr-FR_ReneeV3Voice")
		      .build();

		  InputStream inputStream =
		    textToSpeech.synthesize(synthesizeOptions).execute().getResult();
		  InputStream in = WaveUtils.reWriteWaveHeader(inputStream);

		  OutputStream out = new FileOutputStream(path);
		  byte[] buffer = new byte[1024];
		  int length;
		  while ((length = in.read(buffer)) > 0) {
		    out.write(buffer, 0, length);
		  }

		  out.close();
		  in.close();
		  inputStream.close();
		} catch (IOException e) {
		  e.printStackTrace();
		}
	}
	
}
