package org.expression.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class ImportCSVBatchApplication {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(ImportCSVBatchApplication.class, args);
  }

}
