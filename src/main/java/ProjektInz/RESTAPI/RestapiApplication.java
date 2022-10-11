package ProjektInz.RESTAPI;

import ProjektInz.RESTAPI.Service.OlxAccessTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
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
	@EventListener(ApplicationReadyEvent.class)
	public void appStart()
	{
		String dupa = olxAccessTokenProvider.getOlxBearerToken();
		System.out.println("xd");
	}
	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}

}