package hk.gov.hyd.bunting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"hk.gov.hyd.bunting"})
public class BuntingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuntingApplication.class, args);
	}

}
