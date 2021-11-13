import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class RestClient {

	private static final String USER_AGENT = "Mozila/5.0";
	
	public void get(String requestUrl) {
		HttpGet httpGet = new HttpGet(requestUrl);
		httpGet.addHeader("User-Agent", USER_AGENT);
		httpGet.addHeader("Content-type", "application/json");
		
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			CloseableHttpResponse response = client.execute(httpGet);
			
			System.out.println(response.getStatusLine());
			String json = EntityUtils.toString(response.getEntity(), "UTF-8");
			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
