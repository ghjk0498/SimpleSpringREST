package rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
	
	@Autowired
	private ServletContext servletContext;
	
	public List<Greeting> getList() {
		Object obj = servletContext.getAttribute("rest");
		List<Greeting> ls = new ArrayList<Greeting>();
		
		if (obj == null) {
			ls.add(new Greeting(0, "init"));
			servletContext.setAttribute("rest", ls);
		} else {
			if (obj instanceof List<?>) {
				for (Object o : (List<?>) obj) {
					if (o instanceof Greeting) {
						ls.add((Greeting) o);
					}
				}
			}
		}
		
		return ls;
	}
	
	@GetMapping("/greeting")
	public List<Greeting> greetingGet() {
		return getList();
	}
	
	@PostMapping("/greeting")
	public Greeting greetingPost(@RequestBody Greeting greeting) {
		List<Greeting> response = getList();
		response.add(greeting);
		servletContext.setAttribute("rest", response);
		
		return greeting;
	}
	
	@PutMapping("/greeting/{id}")
	public Greeting greetingPut(@PathVariable final long id, @RequestBody Greeting greeting) {
		List<Greeting> response = getList();
		response = response.stream()
			.map(x -> x.getId() != id ? x : greeting)
			.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
		servletContext.setAttribute("rest", response);
		
		return greeting;
	}
	
	@DeleteMapping("/greeting/{id}")
	public String greetingPut(@PathVariable final long id) {
		try {
			List<Greeting> response = getList();
			response = response.stream()
				.filter(x -> x.getId() != id)
				.collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
			servletContext.setAttribute("rest", response);
			
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			
			return "fail";
		}
	}
	
}