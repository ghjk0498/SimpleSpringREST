
public class Main {
	
	public static void main(String[] args) {
		RestClient client = new RestClient();
		
		String path = "http://127.0.0.1:8080/spring-rest/greeting";
		
		// Get
		String json = client.get(path);
		System.out.println(json);
		
		// Post
		client.post(path, new Greeting(1, "Post"));
		System.out.println(client.get(path));
		
		// Put
		client.put(path, 1, new Greeting(1, "Put"));
		System.out.println(client.get(path));
		
		// Delete
		client.delete(path, 1);
		System.out.println(client.get(path));
	}
}
