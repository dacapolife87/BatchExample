package me.hjjang.batchexample;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class BatchexampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(BatchexampleApplication.class, args);
    }

}
