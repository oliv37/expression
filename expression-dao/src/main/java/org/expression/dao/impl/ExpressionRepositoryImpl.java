package org.expression.dao.impl;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import java.util.List;

import org.expression.dao.ExpressionRepositoryCustom;
import org.expression.dao.operation.CustomAggregationOperation;
import org.expression.model.Expression;
import org.expression.model.util.CollectionNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;

import com.mongodb.BasicDBObject;

public class ExpressionRepositoryImpl implements ExpressionRepositoryCustom {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public List<Expression> searchExpressions(String... words) {
    TextCriteria criteria = TextCriteria.forDefaultLanguage().matchingAny(words);

    Query query = TextQuery.queryText(criteria).sortByScore().with(new PageRequest(0, 20));

    return mongoTemplate.find(query, Expression.class);
  }

  @Override
  public List<Expression> findRandomExpressions(int size) {
    Aggregation aggregation = newAggregation(new CustomAggregationOperation(
        new BasicDBObject("$sample", new BasicDBObject("size", size))));

    AggregationResults<Expression> aggregationResults =
        mongoTemplate.aggregate(aggregation, CollectionNames.EXPRESSIONS, Expression.class);

    return aggregationResults.getMappedResults();
  }

}
