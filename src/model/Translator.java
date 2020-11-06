/**
 * 
 */
package model;

import org.json.JSONArray;
import org.json.JSONObject;

import com.ibm.cloud.sdk.core.security.IamAuthenticator;
import com.ibm.watson.language_translator.v3.LanguageTranslator;
import com.ibm.watson.language_translator.v3.model.TranslateOptions;
import com.ibm.watson.language_translator.v3.model.TranslationResult;

/**
 * @author Eliantus De MICHEL
 *
 */
public class Translator {
	
	private TranslationResult result;
	private String trad;
	private TranslateOptions translateOptions;
	LanguageTranslator languageTranslator;
	
	public void translate(String text, String model) {
		
		IamAuthenticator authenticator = new IamAuthenticator("0_lXn9oXin6jNei2hN4fuji8-1iu72R6E_phHzdl8DHs");
		languageTranslator = new LanguageTranslator("2018-05-01", authenticator);
		languageTranslator.setServiceUrl("https://api.us-south.language-translator.watson.cloud.ibm.com/instances/3cdfd31b-9689-4f45-b9b3-58be811a98dc");

		translateOptions = new TranslateOptions.Builder()
		  .addText(text)
		  .modelId(model)
		  .build();

		result = languageTranslator.translate(translateOptions)
		  .execute().getResult();
		
	}
	
	public String Parse() {
		String jsonString = result.toString();
		JSONObject obj = new JSONObject(jsonString);
		JSONArray arr = obj.getJSONArray("translations");
		trad = arr.getJSONObject(0).getString("translation");
		return trad;
	}
	
	public void stop() {
		languageTranslator.translate(translateOptions).cancel();		
	}

}
