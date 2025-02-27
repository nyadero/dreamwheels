package com.dreamwheels.dreamwheels.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FlywayInitializer implements CommandLineRunner {
    private final Flyway flyway;

    public FlywayInitializer(Flyway flyway) {
        this.flyway = flyway;
    }

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     * @throws Exception on error
     */
    @Override
    public void run(String... args) throws Exception {
      flyway.migrate();
    }
}
