import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestClient {

	private static final String USER_AGENT = "Mozila/5.0";
	
	public String get(String requestUrl) {
		HttpGet httpGet = new HttpGet(requestUrl);
		httpGet.addHeader("User-Agent", USER_AGENT);
		httpGet.addHeader("Content-type", "application/json");
		
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			CloseableHttpResponse response = client.execute(httpGet);
			
			System.out.println("GET : " + response.getStatusLine());
			String json = EntityUtils.toString(response.getEntity(), "UTF-8");
			return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String get(String requestUrl, long id) {
		return get(requestUrl + "/" + id);
	}
	
	public void post(String requestUrl, Greeting greeting) {
		HttpPost httpPost = new HttpPost(requestUrl);
		httpPost.addHeader("User-Agent", USER_AGENT);
		httpPost.addHeader("Content-type", "application/json");
		
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			ObjectMapper mapper = new ObjectMapper();
			StringEntity entity = new StringEntity(mapper.writeValueAsString(greeting));
			httpPost.setEntity(entity);
			
			CloseableHttpResponse response = client.execute(httpPost);
			
			System.out.println("POST : " + response.getStatusLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void put(String requestUrl, long id, Greeting greeting) {
		HttpPut httpPut = new HttpPut(requestUrl + "/" + id);
		httpPut.addHeader("User-Agent", USER_AGENT);
		httpPut.addHeader("Content-type", "application/json");
		
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			ObjectMapper mapper = new ObjectMapper();
			StringEntity entity = new StringEntity(mapper.writeValueAsString(greeting));
			httpPut.setEntity(entity);
			
			CloseableHttpResponse response = client.execute(httpPut);
			
			System.out.println("PUT : " + response.getStatusLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String requestUrl, long id) {
		HttpDelete httpDelete = new HttpDelete(requestUrl + "/" + id);
		httpDelete.addHeader("User-Agent", USER_AGENT);
		httpDelete.addHeader("Content-type", "application/json");
		
		try (CloseableHttpClient client = HttpClients.createDefault()) {
			CloseableHttpResponse response = client.execute(httpDelete);
			
			System.out.println("DELETE : " + response.getStatusLine());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
