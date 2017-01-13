package org.expression.dao;

import static org.expression.ModelTestUtil.createExpression;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.expression.model.Expression;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExpressionRepositoryTest {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private ExpressionRepository expressionRepository;

  @After
  public void tearDown() {
    mongoTemplate.remove(new Query(), Expression.class);
  }

  @Test
  public void itShouldSaveAnExpression() {
    Expression expression = createExpression("Mettre la charue avant les boeufs", "", "Danton");
    Expression expressionSaved = expressionRepository.save(expression);

    assertThat(expressionRepository.count(), is(1L));
    assertThat(expressionSaved.getAuthor(), is("Danton"));
    assertThat(expressionSaved.getViews(), is(0));
    assertThat(expressionSaved.getLikes(), is(0));
    assertNotNull(expressionSaved.getId());
    assertNotNull(expressionSaved.getCreatedAt());
    assertNotNull(expressionSaved.getLastModified());
    assertThat(expressionSaved.getVersion(), is(0L));
  }

  @Test
  public void itShouldGivePaginationInformationWhenUsingFindAll() {
    List<Expression> expressions = Arrays.asList(createExpression("exp1", ""),
        createExpression("exp2", ""), createExpression("exp3", ""), createExpression("exp4", ""),
        createExpression("exp5", ""), createExpression("exp6", ""), createExpression("exp7", ""));

    expressionRepository.save(expressions);
    assertThat(expressionRepository.count(), is(7L));

    Pageable pageable = new PageRequest(0, 2);
    Page<Expression> expressionsPageOne = expressionRepository.findAll(pageable);

    assertThat(expressionsPageOne.getTotalElements(), is(7L));
    assertThat(expressionsPageOne.getTotalPages(), is(4));
    assertThat(expressionsPageOne.getSize(), is(2));
  }

  @Test
  public void itShouldUpdateAuditFieldsWhenModifyingAnExpression() {
    Expression newExp = expressionRepository.save(createExpression("exp1", "desc1"));

    DateTime initCreatedAt = newExp.getCreatedAt();
    DateTime initLastModified = newExp.getLastModified();

    assertNotNull(newExp.getId());
    assertEquals(newExp.getExpression(), "exp1");
    assertEquals((Long) 0L, newExp.getVersion());

    newExp.setExpression("exp2");
    Expression updatedExp = expressionRepository.save(newExp);

    assertEquals(newExp.getId(), updatedExp.getId());
    assertEquals(updatedExp.getExpression(), "exp2");
    assertEquals(updatedExp.getDescription(), "desc1");
    assertEquals((Long) 1L, updatedExp.getVersion());

    assertEquals(initCreatedAt, updatedExp.getCreatedAt());
    assertNotEquals(initLastModified, updatedExp.getLastModified());
    assertTrue(updatedExp.getLastModified().isAfter(initLastModified));
  }

  @Test
  public void itShouldFindExpressionsByTextSearch() {
    expressionRepository.save(createExpression("Mettre la charue", "Ceci est un test"));
    expressionRepository.save(createExpression("Test expression", "une mobylette est partie"));

    List<Expression> expressions =
        expressionRepository.searchExpressions("charue", "abris", "bus", "mobylette");

    assertEquals(2, expressions.size());
  }

  @Test
  public void itShouldFindRandomExpressions() {
    expressionRepository.save(createExpression("Mettre la charue", "Ceci est un test"));
    expressionRepository.save(createExpression("Test expression", "une mobylette est partie"));
    expressionRepository.save(createExpression("exp3", "desc7"));
    expressionRepository.save(createExpression("exp4", "desc4"));
    expressionRepository.save(createExpression("exp5", "desc4"));
    expressionRepository.save(createExpression("exp6", "desc4"));

    List<Expression> expressions = expressionRepository.findRandomExpressions(5);
    assertEquals(5, expressions.size());

    expressions = expressionRepository.findRandomExpressions(2);
    assertEquals(2, expressions.size());
  }

}
