package org.expression.dao.impl;

import java.util.List;

import org.expression.dao.ExpressionRepositoryCustom;
import org.expression.model.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class ExpressionRepositoryImpl implements ExpressionRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public List<Expression> searchExpressions(String searchText) {
    System.out.println(searchText);
    System.out.println(mongoTemplate.getClass());
    return null;
  }

}
