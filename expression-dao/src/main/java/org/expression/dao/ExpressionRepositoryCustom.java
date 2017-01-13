package org.expression.dao;

import java.util.List;

import org.expression.model.Expression;

public interface ExpressionRepositoryCustom {

  List<Expression> searchExpressions(String... words);

  List<Expression> findRandomExpressions(int size);

}
