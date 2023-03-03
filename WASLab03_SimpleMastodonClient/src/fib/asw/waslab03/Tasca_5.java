package fib.asw.waslab03;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.json.JSONArray;
import org.json.JSONObject;

public class Tasca_5 {
	
	private static final String LOCALE = "ca";

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'a les' HH:mm:ss", new Locale(LOCALE));
		String now = sdf.format(new Date());
	
		String my_status = "Hola, @fib_asw@mastodont.cat, ja he arribat! #waslab03\n[" + now + "]";
	
		JSONObject body = new JSONObject();
		body.put("status", my_status);
		body.put("language", LOCALE);
	
		String URI = "https://mastodont.cat/api/v1/accounts/109862447110628983/statuses";
		String TOKEN = Token.get();
	
		try {
			String output = Request.get(URI)
					.addHeader("Authorization","Bearer "+TOKEN)
					.execute()
					.returnContent()
					.asString();
	
			JSONObject result = (JSONObject) new JSONArray(output).get(0);
			String id = "109862447110628983";						//id fib_asw
			String url = result.getString("url");
			
			String URI2 = "https://mastodont.cat/api/v1/statuses/"+ result.getString("id") + "/reblog";
			String output2 = Request.post(URI2)
					.addHeader("Authorization","Bearer "+TOKEN)
					.execute()
					.returnContent()
					.asString();
	
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}


