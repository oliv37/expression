package org.expression.batch.mapper;

import org.expression.model.Expression;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;

public class ExpressionLineMapper extends DefaultLineMapper<Expression> {

  public ExpressionLineMapper() {
    setLineTokenizer(new DelimitedLineTokenizer(";") {
      {
        setNames(new String[] {"id", "expression", "description", "author"});
      }
    });
    setFieldSetMapper(new BeanWrapperFieldSetMapper<Expression>() {
      {
        setTargetType(Expression.class);
      }
    });
  }

}
