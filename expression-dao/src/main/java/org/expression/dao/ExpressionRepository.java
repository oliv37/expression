package org.expression.dao;

import org.expression.model.Expression;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ExpressionRepository
    extends MongoRepository<Expression, String>, ExpressionRepositoryCustom {

}
