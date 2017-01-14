package org.expression.service;

import java.util.List;

import org.expression.dao.ExpressionRepository;
import org.expression.model.Expression;
import org.expression.service.constant.ServiceConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.google.common.collect.Lists;

@Service
public class ExpressionService {

  @Autowired
  private ExpressionRepository expressionRepository;

  public Expression save(Expression expression) {
    return expressionRepository.save(expression);
  }

  public List<Expression> searchExpressions(String searchText) {
    if (StringUtils.isEmpty(searchText)) {
      return Lists.newArrayList();
    }

    return expressionRepository.searchExpressions(searchText.split(" "));
  }

  public List<Expression> findRandomExpressions(Integer size) {
    if (size == null || size <= 0) {
      size = ServiceConstant.DEFAULT_RANDOM_SEARCH_SIZE;
    }

    return expressionRepository.findRandomExpressions(size);
  }

}
