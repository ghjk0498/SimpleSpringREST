package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import rest.GreetingController;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

	@Bean
	public GreetingController greetingController() {
		return new GreetingController();
	}
	
}
