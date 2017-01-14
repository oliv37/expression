package org.expression.service;

import java.util.List;

import org.expression.dao.ExpressionRepository;
import org.expression.model.Expression;
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

  public List<Expression> findRandomExpressions(int size) {
    if (size <= 0) {
      return Lists.newArrayList();
    }

    return expressionRepository.findRandomExpressions(size);
  }

}
