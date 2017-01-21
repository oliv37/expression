package org.expression.batch.processor;

import java.util.concurrent.ThreadLocalRandom;

import org.expression.model.Expression;
import org.springframework.batch.item.ItemProcessor;

import com.google.common.base.Strings;

public class ExpressionItemProcessor implements ItemProcessor<Expression, Expression> {

  @Override
  public Expression process(Expression expression) throws Exception {
    final Expression transformedExpression = new Expression();

    if (!Strings.isNullOrEmpty(expression.getId())) {
      transformedExpression.setId(expression.getId());
    }
    transformedExpression.setExpression(expression.getExpression());
    transformedExpression.setDescription(expression.getDescription());
    transformedExpression.setAuthor(expression.getAuthor());

    // final LocalDateTime now = LocalDateTime.now();
    final int random = ThreadLocalRandom.current().nextInt(0, 100);

    transformedExpression.setActive(true);
    // transformedExpression.setCreatedAt(now);
    // transformedExpression.setLastModified(now);
    transformedExpression.setLikes(0);
    transformedExpression.setViews(0);
    // transformedExpression.setVersion(0L);
    transformedExpression.setRandom(random);

    return transformedExpression;
  }

}
