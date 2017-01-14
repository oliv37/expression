package org.expression.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.expression.dao.ExpressionRepository;
import org.expression.model.Expression;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ExpressionServiceTest {

  @InjectMocks
  private ExpressionService expressionService;

  @Mock
  private ExpressionRepository expressionRepository;

  @Before
  public void initMocks() {
    MockitoAnnotations.initMocks(this);
    List<Expression> result = Arrays.asList(new Expression(), new Expression());

    when(expressionRepository.findRandomExpressions(anyInt())).thenReturn(result);
    when(expressionRepository.searchExpressions(anyVararg())).thenReturn(result);
  }

  @Test
  public void itShouldReturnTheDAOResultWhenSearchingRandomlyWithAnySize() {
    assertEquals(2, expressionService.findRandomExpressions(null).size());
    assertEquals(2, expressionService.findRandomExpressions(0).size());
    assertEquals(2, expressionService.findRandomExpressions(-1).size());
    assertEquals(2, expressionService.findRandomExpressions(1).size());
    assertEquals(2, expressionService.findRandomExpressions(10).size());
  }

  // TODO : tester qu'un appel à expressionService.findRandomExpressions(0)
  // fait bien un appel à expressionRepository.findRandomExpressions(10);

  @Test
  public void itShouldReturnAnEmptyListWhenSearchingExpressionWithNoTerms() {
    assertEquals(0, expressionService.searchExpressions(null).size());
    assertEquals(0, expressionService.searchExpressions("").size());
  }

  @Test
  public void itShouldReturnTheDAOResultWhenSearchingExpressionsWithAnyTermsInArgument() {
    assertEquals(2, expressionService.searchExpressions("test").size());
    assertEquals(2, expressionService.searchExpressions("test dao").size());
  }

  // TODO : tester qu'un appel à expressionService.searchExpressions("test dao")
  // fait bien un appel à expressionRepository.searchExpressions("test", "dao");

}
