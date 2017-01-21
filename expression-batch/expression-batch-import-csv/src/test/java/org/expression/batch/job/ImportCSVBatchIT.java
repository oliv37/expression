package org.expression.batch.job;

import static org.junit.Assert.assertEquals;

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
    // TODO : récupérer le nombre de ligne - 1 dans le fichier CSV
    assertEquals(4, totalNbOfExpressions);
  }

}
