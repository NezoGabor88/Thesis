package iit.unimiskolc.FRI;

import iit.unimiskolc.FRI.controller.FriDataController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "iit.unimiskolc.FRI.model")
public class FriDataServiceApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(FriDataController.class);

    public static void main(final String[] args) {
        LOGGER.info("Attempting to launch Fri Data Application...");
        SpringApplication.run(FriDataServiceApplication.class, args);
        LOGGER.info("Fri Data Application started!");
    }

}
