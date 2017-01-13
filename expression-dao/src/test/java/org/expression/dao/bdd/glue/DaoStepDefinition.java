package org.expression.dao.bdd.glue;

import static org.junit.Assert.assertEquals;

import org.expression.DaoTestConfiguration;
import org.expression.ModelTestUtil;
import org.expression.dao.ExpressionRepository;
import org.expression.model.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;

import cucumber.api.java.After;
import cucumber.api.java8.En;

@ContextConfiguration(classes = DaoTestConfiguration.class)
public class DaoStepDefinition implements En {

  @Autowired
  private ExpressionRepository expressionRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  private Expression expression;

  @After
  public void clearExpressionCollection() {
    System.out.println("tearDown");
    mongoTemplate.remove(new Query(), Expression.class);
  }

  public DaoStepDefinition() {
    Given("^A new expression$", () -> {
      expression = ModelTestUtil.createExpression("exp", "desc");
    });

    When("^I call the save method$", () -> {
      mongoTemplate.save(expression);
    });

    Then("^I should have one expression in the collection$", () -> {
      assertEquals(1, expressionRepository.count());
      assertEquals("exp", expressionRepository.findAll().get(0).getExpression());
    });
  }

}
