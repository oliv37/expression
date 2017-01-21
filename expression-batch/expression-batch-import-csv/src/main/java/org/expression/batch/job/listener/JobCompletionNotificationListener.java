package org.expression.batch.job.listener;

import org.expression.model.constant.CollectionNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

  private static final Logger log =
      LoggerFactory.getLogger(JobCompletionNotificationListener.class);

  @Autowired
  private MongoTemplate mongoTemplate;

  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED! Time to verify the results");

      long totalNbOfExpressions = mongoTemplate.count(new Query(), CollectionNames.EXPRESSIONS);

      log.info("There are {} expression(s) in the database", totalNbOfExpressions);
    }
  }
}
