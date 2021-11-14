package rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class GreetingController {
	
	@Autowired
	private ServletContext servletContext;
	
	public List<Greeting> getList() {
		Object obj = servletContext.getAttribute("greetings");
		
		if (obj == null) {
			List<Greeting> ls = new ArrayList<Greeting>();
			servletContext.setAttribute("greetings", ls);
			return ls;
		} else {
			@SuppressWarnings("unchecked")
			List<Greeting> ls = (List<Greeting>) obj;
			return ls;
		}
	}
	
	@GetMapping("/greeting")
	public List<Greeting> greetingGet() {
		return getList();
	}
	
	@GetMapping("/greeting/{id}")
	public Greeting greetingGet(@PathVariable final long id) {
		return getList().stream().filter(x -> x.getId() == id).findAny().get();
	}
	
	@PostMapping("/greeting")
	public ResponseEntity<Object> greetingPost(@RequestBody HashMap<String, String> map) {
		List<Greeting> ls = getList();
		Long id = ls.size() != 0 ? ls.get(ls.size()-1).getId() + 1 : 0; // 마지막 요소의 id + 1
		Greeting greeting = new Greeting(id , map.get("content"));
		ls.add(greeting);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(id)
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/greeting/{id}")
	public ResponseEntity<Object> greetingPut(@PathVariable final long id,
			@RequestBody HashMap<String, String> map) {
		
		getList()
			.stream()
			.filter(x -> x.getId() == id)
			.findAny()
			.get()
			.setContent(map.get("content"));
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.build()
				.toUri();
		
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/greeting/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void greetingDelete(@PathVariable final long id) {
		getList().removeIf(x -> x.getId() == id);
	}
	
}