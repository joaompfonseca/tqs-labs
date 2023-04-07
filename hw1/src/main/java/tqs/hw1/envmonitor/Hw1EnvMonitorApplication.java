package tqs.hw1.envmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "tqs.hw1.envmonitor.external")
public class Hw1EnvMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(Hw1EnvMonitorApplication.class, args);
	}

}
