package org.expression.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.expression.dao.ExpressionRepository;
import org.expression.model.Expression;
import org.expression.service.constant.ServiceConstant;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
  public void itShouldCallTheDAOWhenSearchingRandomlyWithAnySize() {
    expressionService.findRandomExpressions(null);
    expressionService.findRandomExpressions(0);
    expressionService.findRandomExpressions(-1);
    expressionService.findRandomExpressions(1);
    expressionService.findRandomExpressions(10);
    expressionService.findRandomExpressions(100);

    verify(expressionRepository, times(6)).findRandomExpressions(anyInt());
  }

  @Test
  public void itShouldControlTheSizeOfTheRandomSearch() {
    int default_search_size = ServiceConstant.DEFAULT_RANDOM_SEARCH_SIZE;
    int max_search_size = ServiceConstant.MAX_RANDOM_SEARCH_SIZE;

    expressionService.findRandomExpressions(null);
    expressionService.findRandomExpressions(0);
    expressionService.findRandomExpressions(-1);
    expressionService.findRandomExpressions(max_search_size + 1);
    expressionService.findRandomExpressions(max_search_size);

    InOrder inOrder = Mockito.inOrder(expressionRepository);

    inOrder.verify(expressionRepository, times(4)).findRandomExpressions(default_search_size);
    inOrder.verify(expressionRepository).findRandomExpressions(max_search_size);
  }

  @Test
  public void itShouldReturnAnEmptyListWhenSearchingExpressionWithNoTerms() {
    assertEquals(0, expressionService.searchExpressions(null).size());
    assertEquals(0, expressionService.searchExpressions("").size());

    verify(expressionRepository, times(0)).searchExpressions(any());
  }

  @Test
  public void itShouldReturnTheDAOResultWhenSearchingExpressionsWithAnyTermsInArgument() {
    assertEquals(2, expressionService.searchExpressions("test").size());
    assertEquals(2, expressionService.searchExpressions("test dao").size());

    verify(expressionRepository).searchExpressions("test");
    verify(expressionRepository).searchExpressions("test", "dao");
  }

}
