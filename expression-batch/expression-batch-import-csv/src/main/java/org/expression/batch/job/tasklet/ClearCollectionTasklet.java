package org.expression.batch.job.tasklet;

import org.expression.model.Expression;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class ClearCollectionTasklet implements Tasklet {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext)
      throws Exception {
    mongoTemplate.remove(new Query(), Expression.class);
    return RepeatStatus.FINISHED;
  }

}
