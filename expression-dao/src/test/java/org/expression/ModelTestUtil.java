package org.expression;

import org.expression.model.Expression;

public class ModelTestUtil {

  public static Expression createExpression(String expression, String description) {
    return createExpression(expression, description, null);
  }

  public static Expression createExpression(String expression, String description, String author) {
    Expression resultExpression = new Expression();
    resultExpression.setExpression(expression);
    resultExpression.setDescription(description);
    resultExpression.setAuthor(author);

    return resultExpression;
  }

}
