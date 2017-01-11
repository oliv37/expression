package org.expression;

import javax.annotation.PreDestroy;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@SpringBootConfiguration
@EnableMongoRepositories(basePackages = "org.expression.dao")
@EnableMongoAuditing
public class DaoTestConfiguration extends AbstractMongoConfiguration {

  private Mongo mongo;

  @Override
  protected String getDatabaseName() {
    return "DB_EXP_TEST";
  }

  @Override
  public Mongo mongo() throws Exception {
    mongo = new MongoClient("127.0.0.1", 27017);
    return mongo;
  }

  @Override
  protected String getMappingBasePackage() {
    return "org.expression.model";
  }

  @PreDestroy
  private void closeConnection() {
    mongo.close();
  }

}
