package ProjektInz.RESTAPI;

import ProjektInz.RESTAPI.Service.OlxAccessTokenProvider;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class RestapiApplication {
	@Autowired
	private OlxAccessTokenProvider olxAccessTokenProvider;
	public static void main(String[] args) {

		SpringApplication.run(RestapiApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}
	@EventListener(ApplicationReadyEvent.class)
	public void funccjaTestowa()
	{
		try {
			String abc = olxAccessTokenProvider.getOlxBearerToken();
			System.out.printf("saasd");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
