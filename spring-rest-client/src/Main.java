public class Main {
	
	public static void main(String[] args) {
		RestClient client = new RestClient();
		
		String path = "http://127.0.0.1:8080/spring-rest/greeting";
		client.get(path);
	}
}
