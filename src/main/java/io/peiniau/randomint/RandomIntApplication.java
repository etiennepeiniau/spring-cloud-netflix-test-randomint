package io.peiniau.randomint;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
public class RandomIntApplication {

    public static void main(String[] args) {
        SpringApplication.run(RandomIntApplication.class, args);
    }
}

@RestController
class RandomIntController {

    private static final Logger log = LoggerFactory.getLogger(RandomIntController.class);

    private static final int DEFAULT_BOUND = 101;

    private Random random = new Random();

    @GetMapping("/random")
    @ResponseBody
    public IntValueResponse random(@RequestParam(required = false) Integer bound) {
        IntValueResponse intValueResponse = new IntValueResponse(random.nextInt(bound != null ? bound : DEFAULT_BOUND));
        log.info("Int: {} for Bound: {}", intValueResponse, bound);
        return intValueResponse;
    }

}

@Data
@AllArgsConstructor
class IntValueResponse {

    private Integer value;

}