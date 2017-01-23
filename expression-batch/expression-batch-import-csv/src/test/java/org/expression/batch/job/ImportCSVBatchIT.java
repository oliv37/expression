package org.expression.batch.job;

import static org.junit.Assert.assertEquals;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.expression.model.Expression;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class ImportCSVBatchIT {

  @Autowired
  private MongoTemplate mongoTemplate;

  @After
  public void tearDown() {
    mongoTemplate.remove(new Query(), Expression.class);
  }

  @Test
  public void testDefaultSettings() throws Exception {
    long totalNbOfExpressions = mongoTemplate.count(new Query(), Expression.class);

    URL url = this.getClass().getClassLoader().getResource("expression-data-test.csv");
    long nbFileLines = Files.lines(Paths.get(url.toURI())).skip(1).count();

    assertEquals(nbFileLines, totalNbOfExpressions);
  }

}
