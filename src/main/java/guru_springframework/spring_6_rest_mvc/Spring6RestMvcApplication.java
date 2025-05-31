package guru_springframework.spring_6_rest_mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class Spring6RestMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring6RestMvcApplication.class, args);
	}

}
