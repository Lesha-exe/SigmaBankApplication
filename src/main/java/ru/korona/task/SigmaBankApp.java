package ru.korona.task;

import static org.springframework.boot.Banner.Mode.LOG;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SigmaBankApp implements CommandLineRunner {
    private static final Logger LOG = LoggerFactory.getLogger(SigmaBankApp.class);
    @Autowired private WorkerDataApplication workerDataApplication;

    public static void main(String[] args) {
        LOG.info("STARTING THE APPLICATION");
        SpringApplication.run(SigmaBankApp.class, args);
        LOG.info("APPLICATION FINISHED");
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("EXECUTING : command line runner");
        workerDataApplication.run(args);
    }
}
