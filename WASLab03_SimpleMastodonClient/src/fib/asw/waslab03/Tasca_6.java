package fib.asw.waslab03;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.hc.client5.http.fluent.Request;
import org.apache.hc.core5.http.ContentType;
import org.json.JSONArray;
import org.json.JSONObject;

public class Tasca_6 {
	
	private static final String LOCALE = "ca";

	public static void main(String[] args) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 'a les' HH:mm:ss", new Locale(LOCALE));
		String now = sdf.format(new Date());

		JSONObject body = new JSONObject();
		body.put("language", LOCALE);

		String URI = "https://mastodont.cat/api/v1/trends/tags?limit=10";
		String TOKEN = Token.get();

		try {
			String output = Request.get(URI)
					.bodyString(body.toString(), ContentType.parse("application/json"))
					.addHeader("Authorization","Bearer "+TOKEN)
					.execute()
					.returnContent()
					.asString();

			JSONArray result = new JSONArray(output);
			
			System.out.println("Els 10 tags més populars a Mastodon " + now);
			
			for(int i = 0; i < result.length(); ++i) {
				JSONObject trendTag = (JSONObject) result.get(i);
				String nomTag = trendTag.getString("name");
		
				System.out.println();
				System.out.println("*************************************************");
				System.out.println("* Tag: "+ nomTag);
				System.out.println("*************************************************");

				
				String output2 = Request.get("https://mastodont.cat/api/v1/timelines/tag/"+nomTag+"?limit=5")
						.bodyString(body.toString(), ContentType.parse("application/json"))
						.addHeader("Authorization","Bearer "+TOKEN)
						.execute()
						.returnContent()
						.asString();

				JSONArray result2 = new JSONArray(output2);
				for(int j = 0; j < result2.length(); ++j) {
					JSONObject post = (JSONObject) result2.get(j);
					
					String content = post.getString("content").replaceAll("<[^>]*>", "");
					JSONObject account = post.getJSONObject("account");
					
					System.out.println("- " + account.getString("display_name") + "(" + account.getString("acct") + "): " + content);
					System.out.println("-------------------------------------------------");
				}
				
			}

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
